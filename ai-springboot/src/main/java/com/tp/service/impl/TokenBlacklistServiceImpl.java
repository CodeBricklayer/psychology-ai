package com.tp.service.impl;

import com.tp.service.TokenBlacklistService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HexFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 包名称：com.tp.service.impl
 * 类名称：TokenBlacklistServiceImpl
 * 类描述：基于内存的Token黑名单服务实现类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/13
 */
@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    /**
     * Token摘要与过期时间映射
     */
    private final Map<String, Long> blockedTokens = new ConcurrentHashMap<>();

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
        long now = System.currentTimeMillis();
        blockedTokens.entrySet().removeIf(entry -> entry.getValue() <= now);
        if (expiresAt.getTime() > now) {
            blockedTokens.put(digest(token), expiresAt.getTime());
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
        String tokenDigest = digest(token);
        Long expiresAt = blockedTokens.get(tokenDigest);
        if (expiresAt == null) {
            return false;
        }
        if (expiresAt <= System.currentTimeMillis()) {
            blockedTokens.remove(tokenDigest, expiresAt);
            return false;
        }
        return true;
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
