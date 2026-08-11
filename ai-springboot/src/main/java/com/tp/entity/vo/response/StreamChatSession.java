package com.tp.entity.vo.response;

/**
 * 包名称：com.tp.entity.vo.response
 * 记录名称：StreamChatSession
 * 记录描述：流式聊天会话
 *
 * @param sessionId      会话ID
 * @param userHash       用户哈希值
 * @param initialMessage 初始消息
 * @param startTime      开始时间
 * @param expiryTime     过期时间
 * @param status         状态
 * @param messageCount   消息数量
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 15:47
 */
public record StreamChatSession(String sessionId, Long userHash, String initialMessage, Long startTime, Long expiryTime,
                                String status, Integer messageCount) {
}
