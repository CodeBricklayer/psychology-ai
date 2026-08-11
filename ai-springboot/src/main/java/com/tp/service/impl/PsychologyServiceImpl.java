package com.tp.service.impl;

import cn.hutool.core.date.DateUtil;
import com.tp.common.ResultCode;
import com.tp.converter.ConsultationMessageConverter;
import com.tp.converter.ConsultationSessionConverter;
import com.tp.entity.ConsultationMessage;
import com.tp.entity.ConsultationSession;
import com.tp.entity.User;
import com.tp.entity.dto.ConsultationSessionCreateDTO;
import com.tp.entity.vo.response.StreamChatSession;
import com.tp.exception.BusinessException;
import com.tp.service.ConsultationMessageService;
import com.tp.service.ConsultationSessionService;
import com.tp.service.PsychologyService;
import com.tp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.ZoneOffset;
import java.util.Date;

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
     * 创建咨询会话
     *
     * @param userId 用户ID
     * @param create 咨询会话创建DTO
     * @return 咨询会话
     */
    @Override
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
        ConsultationMessage initialMessage = consultationMessageConverter.toEntity(userId, session.getId(), create);
        consultationMessageService.save(initialMessage);

        return new StreamChatSession("session_" + session.getId(), userId, create.getInitialMessage(), session.getStartedAt().toEpochSecond(ZoneOffset.UTC), null, "ACTIVE", 1);
    }
}