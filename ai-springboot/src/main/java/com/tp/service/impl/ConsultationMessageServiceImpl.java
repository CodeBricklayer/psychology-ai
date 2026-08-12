package com.tp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.tp.common.MessageType;
import com.tp.common.SenderType;
import com.tp.converter.ConsultationMessageConverter;
import com.tp.entity.ConsultationMessage;
import com.tp.entity.vo.response.ConsultationMessageResponseVO;
import com.tp.mapper.ConsultationMessageMapper;
import com.tp.service.ConsultationMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 包名称：com.tp.service.impl
 * 类名称：ConsultationMessageServiceImpl
 * 类描述：咨询消息服务实现类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 16:07
 */
@Service
@RequiredArgsConstructor
public class ConsultationMessageServiceImpl extends ServiceImpl<ConsultationMessageMapper, ConsultationMessage> implements ConsultationMessageService {

    /**
     * 咨询消息转换器
     */
    private final ConsultationMessageConverter consultationMessageConverter;

    /**
     * 根据会话ID统计消息数量
     *
     * @param sessionId 会话ID
     * @return 消息数量
     */
    @Override
    public Long countBySessionId(Long sessionId) {
        LambdaQueryWrapper<ConsultationMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConsultationMessage::getSessionId, sessionId);
        return count(queryWrapper);
    }

    /**
     * 根据会话ID获取最新消息
     *
     * @param sessionId 会话ID
     * @return 最新消息响应VO对象
     */
    @Override
    public ConsultationMessageResponseVO getLastMessageBySessionId(Long sessionId) {
        LambdaQueryWrapper<ConsultationMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConsultationMessage::getSessionId, sessionId)
                .orderByDesc(ConsultationMessage::getCreatedAt)
                .last("limit 1");
        ConsultationMessage lastMessage = getOne(queryWrapper);
        return consultationMessageConverter.toResponseVO(lastMessage);
    }

    /**
     * 保存用户咨询消息
     *
     * @param sessionId  会话ID
     * @param content    消息内容
     * @param emotionTag 情绪标签
     */
    @Override
    public void saveUserMessage(Long sessionId, String content, String emotionTag) {
        ConsultationMessage message = ConsultationMessage.builder()
                .sessionId(sessionId)
                .senderType(SenderType.USER.getCode())
                .messageType(MessageType.TEXT.getCode())
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        save(message);
    }

    /**
     * 保存咨询消息
     *
     * @param sessionId  会话ID
     * @param content    消息内容
     * @param aiModel    使用的AI模型
     */
    @Override
    public void saveAiMessage(Long sessionId, String content, String aiModel) {
        ConsultationMessage message = ConsultationMessage.builder()
                .sessionId(sessionId)
                .senderType(SenderType.ROBOT.getCode())
                .messageType(MessageType.TEXT.getCode())
                .content(content)
                .aiModel(aiModel)
                .createdAt(LocalDateTime.now())
                .build();
        save(message);
    }
}