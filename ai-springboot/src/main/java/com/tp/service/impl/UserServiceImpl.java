package com.tp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.tp.common.ResultCode;
import com.tp.converter.UserConverter;
import com.tp.entity.User;
import com.tp.entity.dto.command.UserLoginCommandDTO;
import com.tp.entity.vo.response.UserLoginResponseVO;
import com.tp.exception.BusinessException;
import com.tp.mapper.UserMapper;
import com.tp.service.UserService;
import com.tp.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 包名称：com.tp.service.impl
 * 类名称：UserServiceImpl
 * 类描述：用户服务实现类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 10:31
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 用户映射接口
     */
    private final UserMapper userMapper;

    /**
     * 用户转换器
     */
    private final UserConverter userConverter;

    /**
     * 密码编码器
     */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 用户登录
     *
     * @param commandDTO 登录命令DTO
     * @return 登录响应VO
     */
    @Override
    public UserLoginResponseVO login(UserLoginCommandDTO commandDTO) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, commandDTO.getUsername().trim())
                .or().eq(User::getEmail, commandDTO.getUsername().trim());

        User user = userMapper.selectOne(wrapper);

        // 判断用户是否存在
        if (user == null) {
            throw new BusinessException(ResultCode.USER_OR_PASSWORD_ERROR);
        }

        if (!user.isActive()) {
            throw new BusinessException(ResultCode.USER_OR_PASSWORD_ERROR);
        }

        // 验证密码
        String inputPassword = commandDTO.getPassword().trim();
        if (!passwordEncoder.matches(inputPassword, user.getPassword())) {
            throw new BusinessException(ResultCode.USER_OR_PASSWORD_ERROR);
        }

        // 生成 token
        String token = JwtTokenUtil.generateToken(user.getId(), user.getUsername(), user.getUserType());

        return userConverter.toUserLoginResponseVO(user, token, user.getUserType());
    }
}