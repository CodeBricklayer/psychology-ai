package com.tp.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.tp.common.Result;
import com.tp.common.ResultCode;
import com.tp.entity.dto.TokenVerificationResult;
import com.tp.entity.dto.command.UserLoginCommandDTO;
import com.tp.entity.dto.command.UserRegisterCommandDTO;
import com.tp.entity.vo.response.UserDetailResponseVO;
import com.tp.entity.vo.response.UserLoginResponseVO;
import com.tp.service.UserService;
import com.tp.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 包名称：com.tp.controller
 * 类名称：UserController
 * 类描述：
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 9:41
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 用户服务接口
     */
    private final UserService userService;

    /**
     * 用户登录
     *
     * @param userLoginCommandDTO 用户登录实体类
     * @return 用户登录响应VO
     */
    @PostMapping("/login")
    public Result<UserLoginResponseVO> login(@Valid @RequestBody UserLoginCommandDTO userLoginCommandDTO) {
        return Result.ok(userService.login(userLoginCommandDTO));
    }

    /**
     * 用户注册
     *
     * @param userRegisterCommandDTO 用户注册实体类
     * @return 用户注册响应VO
     */
    @PostMapping("/add")
    public Result<UserDetailResponseVO> add(@Valid @RequestBody UserRegisterCommandDTO userRegisterCommandDTO) {
        return Result.ok(userService.register(userRegisterCommandDTO));
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户信息响应VO
     */
    @GetMapping("/current")
    public Result<UserDetailResponseVO> getCurrentUser() {

        // 获取当前登录用户信息
        TokenVerificationResult tokenVerificationResult = JwtTokenUtil.extractTokenInfo(JwtTokenUtil.getCurrentToken());
        if (tokenVerificationResult == null || !tokenVerificationResult.getIsValid()) {
            return Result.error(ResultCode.TOKEN_INVALID.getCode(), ResultCode.TOKEN_INVALID.getMsg());
        }
        Long userId = tokenVerificationResult.getUserId();
        return Result.ok(userService.getUserById(userId));
    }
}