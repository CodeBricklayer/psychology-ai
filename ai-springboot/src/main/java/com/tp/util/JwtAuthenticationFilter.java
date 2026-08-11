package com.tp.util;

import com.tp.common.ResultCode;
import com.tp.common.UserStatus;
import com.tp.config.SecurityConfig;
import com.tp.entity.dto.TokenVerificationResult;
import com.tp.entity.vo.response.UserDetailResponseVO;
import com.tp.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * 包名称：com.tp.util
 * 类名称：JwtAuthenticationFilter
 * 类描述：JWT认证过滤器
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 13:00
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private UserService userService;

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {

        // 公开路径不进行认证
        return SecurityConfig.isPublicPath(request.getServletPath());
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 获取请求的URI和方法
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        // 提取JWT Token
        String jwtToken = JwtTokenUtil.extractTokenFromRequest(request);
        // 如果JWT Token为空，清理Spring Security上下文
        if (!StringUtils.hasText(jwtToken)) {
            clearSecurityContext();
            // 写入错误响应
            ResponseUtil.writeError(response, ResultCode.ACCESS_UNAUTHORIZED);
            return;
        }

        // 验证JWT Token
        TokenVerificationResult tokenVerificationResult = JwtTokenUtil.extractTokenInfo(jwtToken);
        if (tokenVerificationResult == null || !tokenVerificationResult.getIsValid()) {
            clearSecurityContext();
            // 写入错误响应
            ResponseUtil.writeError(response, ResultCode.TOKEN_INVALID);
            return;
        }

        // 查询用户信息验证用户的状态
        try {
            UserDetailResponseVO userDetailResponseVO = userService.getUserById(tokenVerificationResult.getUserId());
            // 验证用户状态
            if (userDetailResponseVO == null || !UserStatus.NORMAL.getCode().equals(userDetailResponseVO.getStatus())) {
                clearSecurityContext();
                ResponseUtil.writeError(response, ResultCode.TOKEN_ACCESS_FORBIDDEN);
                return;
            }

            // 创建Spring Security认证对象
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_" + userDetailResponseVO.getUserType())
            );

            // 创建UsernamePasswordAuthenticationToken对象
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    // 用户名
                    userDetailResponseVO.getUsername(), null, authorities
            );

            // 设置认证对象到SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // 将token添加到请求属性中
            request.setAttribute("jwtToken", jwtToken);
        } catch (Exception e) {
            clearSecurityContext();
            ResponseUtil.writeError(response, ResultCode.TOKEN_INVALID);
            return;
        }

        // 继续处理请求
        filterChain.doFilter(request, response);
    }

    /**
     * 清理Spring Security上下文
     */
    private void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }
}