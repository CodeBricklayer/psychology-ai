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
import com.tp.util.ConsultationSessionIdUtil;
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
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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
        return Flux.defer(() -> {
            // 验证咨询会话ID格式
            Long dbSessionId = ConsultationSessionIdUtil.parse(sessionId);
            consultationSessionService.getByUser(dbSessionId, userId);

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
            Flux<String> chatFlux = chatClient.prompt(prompt)
                    .user(userMessage)
                    .advisors(advisorSpec ->
                            advisorSpec.param(ChatMemory.CONVERSATION_ID,
                                    PsychologyConstants.CONVERSATION_ID_PREFIX + sessionId))
                    .stream().content()
                    .doOnNext(aiReply::append);

            // 将阻塞的数据库写入切换到弹性线程池，避免占用AI流回调线程
            return chatFlux.concatWith(Mono.fromRunnable(() -> consultationMessageService.saveAiMessage(
                            dbSessionId, aiReply.toString(), "open-ai"))
                    .subscribeOn(Schedulers.boundedElastic())
                    .then(Mono.empty()));
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
