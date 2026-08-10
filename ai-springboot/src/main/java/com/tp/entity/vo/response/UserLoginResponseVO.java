package com.tp.entity.vo.response;

import lombok.Builder;
import lombok.Data;

/**
 * 包名称：com.tp.entity.vo.response
 * 类名称：UserLoginResponseVO
 * 类描述：用户登录响应VO
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 10:25
 */
@Data
@Builder
public class UserLoginResponseVO {

    /**
     * 登录token
     */
    private String token;

    /**
     * 角色类型
     */
    private Integer roleType;

    /**
     * 用户详情
     */
    private UserDetailResponseVO userInfo;
}