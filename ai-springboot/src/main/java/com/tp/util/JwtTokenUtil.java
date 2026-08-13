package com.tp.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tp.config.JwtConfig;
import com.tp.entity.dto.TokenVerificationResult;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
            throw new IllegalStateException("生成 token 失败", e);
        }

    }

    /**
     * 提取 token 信息
     *
     * @param jwtToken JWT Token
     * @return token 信息
     */
    public static TokenVerificationResult extractTokenInfo(String jwtToken) {
        DecodedJWT jwt = verifyToken(jwtToken);
        // 验证 有效载荷
        Long userId = jwt.getClaim("userId").asLong();
        String userName = jwt.getClaim("username").asString();
        Integer roleType = null;
        try {

            roleType = jwt.getClaim("roleType").asInt();
        } catch (Exception e) {
            String roleTypeStr = jwt.getClaim("roleType").asString();
            if (StringUtils.hasText(roleTypeStr)) {
                roleType = Integer.parseInt(roleTypeStr);
            }
        }
        if (userId != null && roleType != null && StringUtils.hasText(userName)) {
            // 构建 验证结果
            return TokenVerificationResult.builder()
                    .userId(userId)
                    .userName(userName)
                    .userType(roleType)
                    .isValid(true)
                    .build();
        } else {
            return TokenVerificationResult.builder()
                    .userId(userId)
                    .userName(userName)
                    .userType(roleType)
                    .isValid(false)
                    .build();
        }
    }

    /**
     * 验证 token
     *
     * @param jwtToken JWT Token
     * @return 验证结果
     */
    public static DecodedJWT verifyToken(String jwtToken) {
        if (!StringUtils.hasText(jwtToken)) {
            throw new JWTVerificationException("token不能为空");
        }
        // token 解码
        JwtConfig jwtConfig = getJwtConfig();
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
        return verifier.verify(jwtToken);
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

    /**
     * 从请求中提取 JWT Token
     *
     * @param request HttpServletRequest
     * @return JWT Token
     */
    public static String extractTokenFromRequest(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String token = request.getHeader("token");
        if (StringUtils.hasText(token)) {
            return token;
        }
        return null;
    }

    /**
     * 提取当前请求中的用户ID
     *
     * @return 用户ID
     */
    public static Long extractUserId() {
        String token = getCurrentToken();
        if (StringUtils.hasText(token)) {
            return extractTokenInfo(token).getUserId();
        }
        return null;
    }

    /**
     * 获取当前请求中的 Token
     *
     * @return Token
     */
    public static String getCurrentToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String token = (String) request.getAttribute("jwtToken");
            if (StringUtils.hasText(token)) {
                return token;
            }

            // 从请求头中提取 token
            token = extractTokenFromRequest(request);
            if (StringUtils.hasText(token)) {
                return token;
            }
            return null;
        }
        return null;
    }
}
