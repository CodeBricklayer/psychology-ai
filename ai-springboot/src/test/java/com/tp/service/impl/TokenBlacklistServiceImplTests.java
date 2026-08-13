package com.tp.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        StringRedisTemplate redisTemplate = mock(StringRedisTemplate.class);
        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(redisTemplate.hasKey(anyString())).thenReturn(true);
        TokenBlacklistServiceImpl service = new TokenBlacklistServiceImpl(redisTemplate);
        String token = "active-token";

        service.block(token, new Date(System.currentTimeMillis() + 60000));

        assertThat(service.isBlocked(token)).isTrue();
        verify(valueOperations).set(
                eq("auth:token:blacklist:ba2efa2f1b25f88201ee27799fd967bdc0d3f98b94cf78f9be0abe9509e7ccd4"),
                eq("1"), any(Duration.class));
    }

    /**
     * 已过期Token不保留在黑名单中
     */
    @Test
    void shouldIgnoreExpiredToken() {
        StringRedisTemplate redisTemplate = mock(StringRedisTemplate.class);
        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(redisTemplate.hasKey(anyString())).thenReturn(false);
        TokenBlacklistServiceImpl service = new TokenBlacklistServiceImpl(redisTemplate);
        String token = "expired-token";

        service.block(token, new Date(System.currentTimeMillis() - 1000));

        assertThat(service.isBlocked(token)).isFalse();
        verify(valueOperations, never()).set(anyString(), anyString(), any(Duration.class));
    }
}
