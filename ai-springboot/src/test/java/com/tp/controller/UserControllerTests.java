package com.tp.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.tp.common.Result;
import com.tp.service.TokenBlacklistService;
import com.tp.service.UserService;
import com.tp.util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 包名称：com.tp.controller
 * 类名称：UserControllerTests
 * 类描述：用户接口控制器测试类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/13
 */
class UserControllerTests {

    /**
     * 退出登录时将当前Token加入黑名单
     */
    @Test
    void shouldBlockCurrentTokenWhenLogout() {
        UserService userService = mock(UserService.class);
        TokenBlacklistService tokenBlacklistService = mock(TokenBlacklistService.class);
        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        Date expiresAt = new Date(System.currentTimeMillis() + 60000);
        String token = "current-token";
        when(decodedJWT.getExpiresAt()).thenReturn(expiresAt);
        UserController controller = new UserController(userService, tokenBlacklistService);

        try (MockedStatic<JwtTokenUtil> jwtTokenUtil = mockStatic(JwtTokenUtil.class)) {
            jwtTokenUtil.when(JwtTokenUtil::getCurrentToken).thenReturn(token);
            jwtTokenUtil.when(() -> JwtTokenUtil.verifyToken(token)).thenReturn(decodedJWT);

            Result<Void> result = controller.logout();

            assertThat(result.getCode()).isEqualTo("200");
            verify(tokenBlacklistService).block(token, expiresAt);
        }
    }
}
