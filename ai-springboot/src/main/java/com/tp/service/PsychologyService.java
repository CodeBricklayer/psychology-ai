package com.tp.service;

import com.tp.entity.dto.ConsultationSessionCreateDTO;
import com.tp.entity.dto.ConsultationStreamDTO;
import com.tp.entity.vo.response.StreamChatSession;
import reactor.core.publisher.Flux;

/**
 * 包名称：com.tp.service
 * 接口名称：PsychologyService
 * 接口描述：心理咨询服务接口
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 16:00
 */
public interface PsychologyService {

    /**
     * 创建咨询会话
     *
     * @param create 咨询会话创建DTO
     * @param userId 用户ID
     * @return 咨询会话
     */
    StreamChatSession startSession(Long userId, ConsultationSessionCreateDTO create);

    /**
     * 流式咨询
     *
     * @param userId    用户ID
     * @param sessionId 咨询会话ID
     * @param userMessage 用户消息
     * @return 咨询结果
     */
    Flux<String> streamChat(Long userId, String sessionId, String userMessage);
}
