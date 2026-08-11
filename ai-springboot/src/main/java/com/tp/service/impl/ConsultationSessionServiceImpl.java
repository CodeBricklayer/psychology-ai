package com.tp.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.tp.entity.ConsultationSession;
import com.tp.mapper.ConsultationSessionMapper;
import com.tp.service.ConsultationSessionService;
import org.springframework.stereotype.Service;

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
public class ConsultationSessionServiceImpl extends ServiceImpl<ConsultationSessionMapper, ConsultationSession> implements ConsultationSessionService {
}