package com.tp.service.impl;

import com.tp.common.PsychologyConstants;
import com.tp.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HexFormat;

/**
 * 包名称：com.tp.service.impl
 * 类名称：TokenBlacklistServiceImpl
 * 类描述：基于Redis的Token黑名单服务实现类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/13
 */
@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    /**
     * Redis字符串操作模板
     */
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 将Token加入黑名单
     *
     * @param token     JWT Token
     * @param expiresAt Token过期时间
     */
    @Override
    public void block(String token, Date expiresAt) {
        if (!StringUtils.hasText(token) || expiresAt == null) {
            return;
        }
        Duration ttl = Duration.between(Instant.now(), expiresAt.toInstant());
        if (!ttl.isNegative() && !ttl.isZero()) {
            stringRedisTemplate.opsForValue().set(buildKey(token), "1", ttl);
        }
    }

    /**
     * 判断Token是否已加入黑名单
     *
     * @param token JWT Token
     * @return 是否已加入黑名单
     */
    @Override
    public boolean isBlocked(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(buildKey(token)));
    }

    /**
     * 构建Token黑名单Redis键
     *
     * @param token JWT Token
     * @return Redis键
     */
    private String buildKey(String token) {
        return PsychologyConstants.TOKEN_BLACKLIST_KEY_PREFIX + digest(token);
    }

    /**
     * 计算Token摘要
     *
     * @param token JWT Token
     * @return Token摘要
     */
    private String digest(String token) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(messageDigest.digest(
                    token.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("当前运行环境不支持SHA-256", e);
        }
    }
}
