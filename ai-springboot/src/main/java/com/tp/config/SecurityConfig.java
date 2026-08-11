package com.tp.config;

import com.tp.util.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

/**
 * 包名称：com.tp.config
 * 类名称：SecurityConfig
 * 类描述：安全配置类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 12:29
 */
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    /**
     * 公开路径
     */
    private static final String[] PUBLIC_PATH = {"/", "/user/login", "/user/add"};

    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    /**
     * 配置安全过滤链
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                // 禁用csrf（API服务通常不需要）
                .csrf(AbstractHttpConfigurer::disable)
                // 配置会话管理为无状态（JWT需要）
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 配置请求授权规则
                .authorizeHttpRequests(auth -> auth
                        // 公开路径无需登录即可访问
                        .requestMatchers(PUBLIC_PATH).permitAll()
                        // 其他请求需要认证
                        .anyRequest().authenticated()
                )
                // 添加JWT认证过滤器
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

        ;
        return http.build();
    }

    /**
     * 配置JWT认证过滤器
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * 判断路径是否为公开路径
     *
     * @param requestUrl 路径
     * @return 是否为公开路径
     */
    public static boolean isPublicPath(String requestUrl) {
        return Arrays.stream(PUBLIC_PATH).anyMatch(path -> ANT_PATH_MATCHER.match(path, requestUrl));
    }
}