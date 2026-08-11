package com.tp.service;

import com.tp.entity.dto.ConsultationSessionCreateDTO;
import com.tp.entity.vo.response.StreamChatSession;

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
    StreamChatSession startSession( Long userId,ConsultationSessionCreateDTO create);
}
