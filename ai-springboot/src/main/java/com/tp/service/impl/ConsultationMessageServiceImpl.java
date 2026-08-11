package com.tp.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.tp.entity.ConsultationMessage;
import com.tp.mapper.ConsultationMessageMapper;
import com.tp.service.ConsultationMessageService;
import org.springframework.stereotype.Service;

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
public class ConsultationMessageServiceImpl extends ServiceImpl<ConsultationMessageMapper, ConsultationMessage> implements ConsultationMessageService {
}