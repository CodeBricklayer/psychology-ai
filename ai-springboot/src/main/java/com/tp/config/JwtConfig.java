package com.tp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 包名称：com.tp.config
 * 类名称：JwtConfig
 * 类描述：JWT 配置类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 13:41
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /**
     * 密钥
     */
    private String secret;

    /**
     * 过期时间（毫秒）
     */
    private Long expiration;

    /**
     * 刷新过期时间（毫秒）
     */
    private Long refreshExpiration;

    /**
     * token头部名称
     */
    private String header;

    /**
     * token前缀
     */
    private String tokenPrefix;

}