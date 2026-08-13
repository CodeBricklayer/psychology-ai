package com.tp.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

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
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /**
     * 密钥
     */
    @NotBlank(message = "JWT密钥不能为空")
    @Size(min = 32, message = "JWT密钥长度不能小于32个字符")
    private String secret;

    /**
     * 过期时间（毫秒）
     */
    @Min(value = 1, message = "JWT过期时间必须大于0")
    private Long expiration;

    /**
     * 刷新过期时间（毫秒）
     */
    @Min(value = 1, message = "JWT刷新过期时间必须大于0")
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
