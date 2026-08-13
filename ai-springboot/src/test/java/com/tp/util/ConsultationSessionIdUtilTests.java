package com.tp.util;

import com.tp.common.ResultCode;
import com.tp.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 包名称：com.tp.util
 * 类名称：ConsultationSessionIdUtilTests
 * 类描述：咨询会话ID工具类测试
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/13
 */
class ConsultationSessionIdUtilTests {

    /**
     * 支持带前缀和纯数字会话ID
     */
    @Test
    void shouldParseSupportedSessionIds() {
        assertThat(ConsultationSessionIdUtil.parse("session_12")).isEqualTo(12L);
        assertThat(ConsultationSessionIdUtil.parse("12")).isEqualTo(12L);
    }

    /**
     * 拒绝非法会话ID
     */
    @Test
    void shouldRejectInvalidSessionId() {
        assertThatThrownBy(() -> ConsultationSessionIdUtil.parse("session_invalid"))
                .isInstanceOf(BusinessException.class)
                .extracting("code")
                .isEqualTo(ResultCode.PARAM_INVALID.getCode());
    }
}
