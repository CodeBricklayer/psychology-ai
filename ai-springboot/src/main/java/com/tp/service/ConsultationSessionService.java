package com.tp.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.IService;
import com.tp.entity.ConsultationSession;
import com.tp.entity.vo.response.ConsultationMessageResponseVO;

import java.util.List;

/**
 * 包名称：com.tp.service
 * 接口名称：ConsultationSessionService
 * 接口描述：咨询会话服务接口
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 16:06
 */
public interface ConsultationSessionService extends IService<ConsultationSession> {

    /**
     * 分页查询用户的咨询会话
     *
     * @param page   分页对象
     * @param userId 用户ID
     * @return 咨询会话分页结果
     */
    IPage<ConsultationSession> pageByUser(Page<ConsultationSession> page, Long userId);

    /**
     * 查询会话消息列表
     *
     * @param sessionId 会话ID
     * @param userId    用户ID
     * @return 会话消息列表
     */
    List<ConsultationMessageResponseVO> listMessages(Long sessionId, Long userId);

    /**
     * 查询用户的咨询会话
     *
     * @param sessionId 会话ID
     * @param userId    用户ID
     * @return 咨询会话
     */
    ConsultationSession getByUser(Long sessionId, Long userId);

    /**
     * 删除用户的咨询会话
     *
     * @param sessionId 会话ID
     * @param userId    用户ID
     * @return 是否删除成功
     */
    boolean removeByUser(Long sessionId, Long userId);

    /**
     * 获取会话情绪分析结果
     *
     * @param sessionId 会话ID
     * @param userId    用户ID
     * @return 情绪分析JSON字符串
     */
    String getEmotionAnalysis(Long sessionId, Long userId);
}
