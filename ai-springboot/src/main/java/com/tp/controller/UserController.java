package com.tp.controller;

import com.tp.common.Result;
import com.tp.entity.dto.command.UserLoginCommandDTO;
import com.tp.entity.vo.response.UserLoginResponseVO;
import com.tp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return userService.login(userLoginCommandDTO);
    }
}