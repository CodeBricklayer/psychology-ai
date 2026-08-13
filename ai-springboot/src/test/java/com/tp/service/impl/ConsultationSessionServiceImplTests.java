package com.tp.service.impl;

import com.tp.common.ResultCode;
import com.tp.converter.ConsultationMessageConverter;
import com.tp.exception.BusinessException;
import com.tp.mapper.ConsultationMessageMapper;
import com.tp.mapper.ConsultationSessionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 包名称：com.tp.service.impl
 * 类名称：ConsultationSessionServiceImplTests
 * 类描述：咨询会话服务测试类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/13
 */
class ConsultationSessionServiceImplTests {

    /**
     * 咨询会话Mapper
     */
    private ConsultationSessionMapper consultationSessionMapper;

    /**
     * 咨询会话服务
     */
    private ConsultationSessionServiceImpl consultationSessionService;

    /**
     * 初始化测试数据
     */
    @BeforeEach
    void setUp() {
        consultationSessionMapper = mock(ConsultationSessionMapper.class);
        ConsultationMessageMapper consultationMessageMapper = mock(ConsultationMessageMapper.class);
        ConsultationMessageConverter consultationMessageConverter = mock(ConsultationMessageConverter.class);
        consultationSessionService = new ConsultationSessionServiceImpl(
                consultationMessageMapper, consultationMessageConverter);
        ReflectionTestUtils.setField(consultationSessionService, "baseMapper", consultationSessionMapper);
    }

    /**
     * 查询不属于当前用户的会话时抛出业务异常
     */
    @Test
    void shouldRejectSessionNotOwnedByCurrentUser() {
        when(consultationSessionMapper.selectOne(any())).thenReturn(null);

        assertThatThrownBy(() -> consultationSessionService.listMessages(1L, 2L))
                .isInstanceOf(BusinessException.class)
                .extracting("code")
                .isEqualTo(ResultCode.CONSULTATION_SESSION_NOT_FOUND.getCode());
    }
}
