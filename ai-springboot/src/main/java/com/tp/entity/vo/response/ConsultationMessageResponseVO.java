package com.tp.entity.vo.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 包名称：com.tp.entity.vo.response
 * 类名称：ConsultationMessageResponseVO
 * 类描述：咨询消息响应VO
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12 11:45
 */
@Data
public class ConsultationMessageResponseVO {

    /**
     * 消息ID
     */
    private Long id;

    /**
     * 会话ID
     */
    private Long sessionId;

    /**
     * 发送者类型 1:用户 2:AI助手
     */
    private Integer senderType;

    /**
     * 发送者类型描述
     */
    private String senderTypeDesc;

    /**
     * 消息类型 1:文本
     */
    private Integer messageType;

    /**
     * 消息类型描述
     */
    private String messageTypeDesc;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 情绪标签
     */
    private String emotionTag;

    /**
     * 使用的AI模型
     */
    private String aiModel;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 消息长度
     */
    private Integer contentLength;

    /**
     * 计算消息长度
     */
    public void calculateContentLength() {
        this.contentLength = content != null ? content.length() : 0;
    }
}