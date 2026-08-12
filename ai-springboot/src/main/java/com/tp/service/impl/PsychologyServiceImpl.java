package com.tp.service.impl;

import cn.hutool.core.date.DateUtil;
import com.tp.common.PsychologyConstants;
import com.tp.common.ResultCode;
import com.tp.common.SenderType;
import com.tp.converter.ConsultationMessageConverter;
import com.tp.converter.ConsultationSessionConverter;
import com.tp.entity.ConsultationMessage;
import com.tp.entity.ConsultationSession;
import com.tp.entity.User;
import com.tp.entity.dto.ConsultationSessionCreateDTO;
import com.tp.entity.vo.response.ConsultationMessageResponseVO;
import com.tp.entity.vo.response.StreamChatSession;
import com.tp.exception.BusinessException;
import com.tp.service.ConsultationMessageService;
import com.tp.service.ConsultationSessionService;
import com.tp.service.PsychologyService;
import com.tp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.List;

/**
 * 包名称：com.tp.service.impl
 * 类名称：PsychologyServiceImpl
 * 类描述：心理咨询服务实现类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 16:00
 */
@Service
@RequiredArgsConstructor
public class PsychologyServiceImpl implements PsychologyService {

    /**
     * 咨询会话服务
     */
    private final ConsultationSessionService consultationSessionService;

    /**
     * 咨询消息服务
     */
    private final ConsultationMessageService consultationMessageService;

    /**
     * 用户服务
     */
    private final UserService userService;

    /**
     * 咨询会话转换器
     */
    private final ConsultationSessionConverter consultationSessionConverter;

    /**
     * 咨询消息转换器
     */
    private final ConsultationMessageConverter consultationMessageConverter;

    /**
     * 聊天客户端
     */
    private final ChatClient chatClient;

    /**
     * 创建咨询会话
     *
     * @param userId 用户ID
     * @param create 咨询会话创建DTO
     * @return 咨询会话
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public StreamChatSession startSession(Long userId, ConsultationSessionCreateDTO create) {
        //验证用户是否存在
        User user = userService.getById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.TOKEN_INVALID);
        }

        // 创建会话记录
        if (!StringUtils.hasText(create.getSessionTitle())) {
            create.setSessionTitle("宁渡AI助手 --- " + DateUtil.format(new Date(), "MM-dd HH:mm"));
        }
        ConsultationSession session = consultationSessionConverter.toEntity(userId, create);
        consultationSessionService.save(session);

        // 创建初始消息
        ConsultationMessage initialMessage = consultationMessageConverter.toEntity(session.getId(), create);
        consultationMessageService.save(initialMessage);
        String sessionId = "session_" + session.getId();
        return new StreamChatSession(
                sessionId,
                userId,
                create.getInitialMessage(),
                System.currentTimeMillis(),
                // 24小时过期时间
                System.currentTimeMillis() + 86400000L,
                "ACTIVE",
                1
        );
    }

    /**
     * 流式咨询
     *
     * @param userId      用户ID
     * @param sessionId   咨询会话ID
     * @param userMessage 用户消息
     * @return 咨询结果
     */
    @Override
    public Flux<String> streamChat(Long userId, String sessionId, String userMessage) {

        return Flux.create(sink -> {
            // 验证咨询会话ID格式
            Long dbSessionId = extractSessionId(sessionId);
            if (dbSessionId == null) {
                sink.error(new RuntimeException("咨询会话ID格式错误"));
                return;
            }

            // 验证咨询会话是否存在
            ConsultationSession session = consultationSessionService.getById(dbSessionId);
            if (session == null) {
                sink.error(new RuntimeException("咨询会话不存在"));
                return;
            }

            // 验证用户是否是会话创建者
            if (!session.getUserId().equals(userId)) {
                sink.error(new RuntimeException("您不是咨询会话的创建者"));
                return;
            }

            boolean isInitialMessage = false;

            if (consultationMessageService.countBySessionId(dbSessionId).equals(1L)) {
                ConsultationMessageResponseVO lastMessage = consultationMessageService.getLastMessageBySessionId(dbSessionId);
                if (lastMessage != null && lastMessage.getSenderType().equals(SenderType.USER.getCode()) && ObjectUtils.nullSafeEquals(lastMessage.getContent(), userMessage)) {
                    isInitialMessage = true;
                }
            }

            // 验证用户消息是否为初始咨询消息

            if (!isInitialMessage) {
                consultationMessageService.saveUserMessage(dbSessionId, userMessage, null);
            }

            Prompt prompt = new Prompt(List.of(new SystemMessage(PsychologyConstants.PSYCHOLOGICAL_SUPPORT_SYSTEM_PROMPT)));

            // 用于存储ai助手的回复
            StringBuilder aiReply = new StringBuilder();

            // 发送咨询请求
            chatClient.prompt(prompt)
                    .user(userMessage)
                    .advisors(advisorSpec ->
                            advisorSpec.param(ChatMemory.CONVERSATION_ID,
                                    PsychologyConstants.CONVERSATION_ID_PREFIX + sessionId))
                    .stream().content()
                    .doOnNext(fragment -> {
                                aiReply.append(fragment);
                                sink.next(fragment);
                            }
                    )
                    // 将AI返回的结果保存到咨询消息表
                    .doOnComplete(() -> {
                        consultationMessageService.saveAiMessage(dbSessionId, aiReply.toString(), "open-ai");
                        // AI 流结束后通知外层 Flux，控制器才能继续发送 done 事件
                        sink.complete();
                    })
                    .doOnError(sink::error)
                    // 订阅
                    .subscribe();

        });
    }

    /**
     * 从咨询会话ID中提取咨询会话ID
     *
     * @param sessionId 咨询会话ID
     * @return 咨询会话ID
     */
    private Long extractSessionId(String sessionId) {
        if (StringUtils.hasText(sessionId) && sessionId.startsWith(PsychologyConstants.SESSION_ID_PREFIX)) {
            return Long.parseLong(sessionId.substring(PsychologyConstants.SESSION_ID_PREFIX.length()));
        }
        return null;
    }
}
