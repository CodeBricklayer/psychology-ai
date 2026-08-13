package com.tp.service.impl;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 包名称：com.tp.service.impl
 * 类名称：TokenBlacklistServiceImplTests
 * 类描述：Token黑名单服务测试类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/13
 */
class TokenBlacklistServiceImplTests {

    /**
     * 未过期Token加入黑名单后不可使用
     */
    @Test
    void shouldBlockActiveToken() {
        TokenBlacklistServiceImpl service = new TokenBlacklistServiceImpl();
        String token = "active-token";

        service.block(token, new Date(System.currentTimeMillis() + 60000));

        assertThat(service.isBlocked(token)).isTrue();
    }

    /**
     * 已过期Token不保留在黑名单中
     */
    @Test
    void shouldIgnoreExpiredToken() {
        TokenBlacklistServiceImpl service = new TokenBlacklistServiceImpl();
        String token = "expired-token";

        service.block(token, new Date(System.currentTimeMillis() - 1000));

        assertThat(service.isBlocked(token)).isFalse();
    }
}
