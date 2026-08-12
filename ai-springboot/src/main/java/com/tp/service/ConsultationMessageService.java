package com.tp.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.tp.common.SenderType;
import com.tp.entity.ConsultationMessage;
import com.tp.entity.vo.response.ConsultationMessageResponseVO;

/**
 * 包名称：com.tp.service
 * 接口名称：ConsultationMessageService
 * 接口描述：咨询消息服务接口
 * *
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 16:06
 */
public interface ConsultationMessageService extends IService<ConsultationMessage> {

    /**
     * 根据会话ID统计消息数量
     *
     * @param sessionId 会话ID
     * @return 消息数量
     */
    Long countBySessionId(Long sessionId);

    /**
     * 根据会话ID获取最新消息
     *
     * @param sessionId 会话ID
     * @return 最新消息响应VO对象
     */
    ConsultationMessageResponseVO getLastMessageBySessionId(Long sessionId);

    /**
     * 保存用户咨询消息
     *
     * @param sessionId  会话ID
     * @param content    消息内容
     * @param emotionTag 情绪标签
     */
    void saveUserMessage(Long sessionId, String content, String emotionTag);

    /**
     * 保存咨询消息
     *
     * @param sessionId  会话ID
     * @param content    消息内容
     * @param aiModel    使用的AI模型
     * @param senderType 发送者类型
     * @return 保存的咨询消息实体
     */
    ConsultationMessage saveAiMessage(Long sessionId, String content, String aiModel);
}
