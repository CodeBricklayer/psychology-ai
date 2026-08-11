package com.tp.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tp.config.JwtConfig;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 包名称：com.tp.util
 * 类名称：JwtTokenUtil
 * 类描述：JWT 令牌工具类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 13:45
 */
@Component
public class JwtTokenUtil implements ApplicationContextAware {

    /**
     * 应用上下文
     */
    private static ApplicationContext applicationContext;

    /**
     * 签发者
     */
    private static final String ISSUER = "mental_health_assistant";

    /**
     * 生成 token
     *
     * @param userId   用户ID
     * @param username 用户名
     * @param roleType 角色类型
     * @return token
     */
    public static String generateToken(Long userId, String username, Integer roleType) {
        try {
            // 获取 JWT 配置
            JwtConfig jwtConfig = getJwtConfig();
            // 生成 签名的算法
            Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret());
            // 生成 过期时间
            Date expiration = new Date(System.currentTimeMillis() + jwtConfig.getExpiration());


            // 生成 token
            return JWT.create()
                    .withClaim("userId", userId)
                    .withClaim("username", username)
                    .withClaim("roleType", roleType)
                    // 过期时间
                    .withExpiresAt(expiration)
                    // 签发时间
                    .withIssuedAt(new Date())
                    // 签发者
                    .withIssuer(ISSUER)
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("生成 token 失败：" + e);
        }

    }

    /**
     * 设置应用上下文
     *
     * @param applicationContext 应用上下文
     * @throws BeansException 异常
     */
    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        // 设置应用上下文
        JwtTokenUtil.applicationContext = applicationContext;
    }

    /**
     * 获取 JWT 配置
     *
     * @return JWT 配置
     */
    private static JwtConfig getJwtConfig() {
        return JwtTokenUtil.applicationContext.getBean(JwtConfig.class);
    }
}