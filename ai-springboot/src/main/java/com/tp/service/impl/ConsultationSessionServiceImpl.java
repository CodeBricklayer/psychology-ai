package com.tp.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tp.converter.ConsultationMessageConverter;
import com.tp.common.ResultCode;
import com.tp.entity.ConsultationMessage;
import com.tp.entity.ConsultationSession;
import com.tp.entity.vo.response.ConsultationMessageResponseVO;
import com.tp.mapper.ConsultationMessageMapper;
import com.tp.mapper.ConsultationSessionMapper;
import com.tp.service.ConsultationSessionService;
import com.tp.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 包名称：com.tp.service.impl
 * 类名称：ConsultationSessionServiceImpl
 * 类描述：咨询会话服务实现类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 16:06
 */
@Service
@RequiredArgsConstructor
public class ConsultationSessionServiceImpl extends ServiceImpl<ConsultationSessionMapper, ConsultationSession> implements ConsultationSessionService {

    /**
     * 咨询消息Mapper
     */
    private final ConsultationMessageMapper consultationMessageMapper;
    /**
     * 咨询消息转换器
     */
    private final ConsultationMessageConverter consultationMessageConverter;


    /**
     * 分页查询用户的咨询会话
     *
     * @param page   分页对象
     * @param userId 用户ID
     * @return 咨询会话分页结果
     */
    @Override
    public IPage<ConsultationSession> pageByUser(Page<ConsultationSession> page, Long userId) {
        return page(page, new LambdaQueryWrapper<ConsultationSession>()
                .eq(ConsultationSession::getUserId, userId)
                .orderByDesc(ConsultationSession::getStartedAt));
    }

    /**
     * 查询会话消息列表
     *
     * @param sessionId 会话ID
     * @return 会话消息列表
     */
    @Override
    public List<ConsultationMessageResponseVO> listMessages(Long sessionId, Long userId) {
        getByUser(sessionId, userId);
        return consultationMessageMapper.selectList(new LambdaQueryWrapper<ConsultationMessage>()
                        .eq(ConsultationMessage::getSessionId, sessionId)
                        .orderByAsc(ConsultationMessage::getCreatedAt))
                .stream().map(consultationMessageConverter::toResponseVO).toList();
    }

    /**
     * 查询用户的咨询会话
     *
     * @param sessionId 会话ID
     * @param userId    用户ID
     * @return 咨询会话
     */
    @Override
    public ConsultationSession getByUser(Long sessionId, Long userId) {
        ConsultationSession session = getOne(new LambdaQueryWrapper<ConsultationSession>()
                .eq(ConsultationSession::getId, sessionId)
                .eq(ConsultationSession::getUserId, userId));
        if (session == null) {
            throw new BusinessException(ResultCode.CONSULTATION_SESSION_NOT_FOUND);
        }
        return session;
    }

    /**
     * 删除用户的咨询会话
     *
     * @param sessionId 会话ID
     * @param userId    用户ID
     * @return 是否删除成功
     */
    @Override
    public boolean removeByUser(Long sessionId, Long userId) {
        return remove(new LambdaQueryWrapper<ConsultationSession>()
                .eq(ConsultationSession::getId, sessionId)
                .eq(ConsultationSession::getUserId, userId));
    }

    /**
     * 获取会话情绪分析结果
     *
     * @param sessionId 会话ID
     * @param userId    用户ID
     * @return 情绪分析JSON字符串
     */
    @Override
    public String getEmotionAnalysis(Long sessionId, Long userId) {
        return getByUser(sessionId, userId).getLastEmotionAnalysis();
    }
}
