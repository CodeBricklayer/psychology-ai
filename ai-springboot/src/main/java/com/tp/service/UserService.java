package com.tp.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.tp.entity.User;
import com.tp.entity.dto.command.UserLoginCommandDTO;
import com.tp.entity.dto.command.UserRegisterCommandDTO;
import com.tp.entity.vo.response.UserDetailResponseVO;
import com.tp.entity.vo.response.UserLoginResponseVO;

/**
 * 包名称：com.tp.service.impl
 * 接口名称：UserService
 * 接口描述：用户服务接口
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 10:23
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param commandDTO 用户登录实体类
     * @return 用户登录响应VO
     */
    UserLoginResponseVO login(UserLoginCommandDTO commandDTO);

    /**
     * 用户注册
     *
     * @param userRegisterCommandDTO 用户注册实体类
     * @return 用户注册响应VO
     */
    UserDetailResponseVO register(UserRegisterCommandDTO userRegisterCommandDTO);

    /**
     * 获取用户详情
     *
     * @param userId 用户ID
     * @return 用户详情响应VO
     */
    UserDetailResponseVO getUserById(Long userId);
}
