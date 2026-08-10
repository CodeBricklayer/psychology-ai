package com.tp.entity.vo.response;

import lombok.Data;

/**
 * 包名称：com.tp.entity.vo.response
 * 类名称：UserDetailResponseVO
 * 类描述：用户详情响应VO
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 10:27
 */
@Data
public class UserDetailResponseVO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别 1男 2女
     */
    private Integer gender;

    /**
     * 性别显示名称
     */
    private String genderDisplayName;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户类型显示名称
     */
    private String userTypeDisplayName;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 状态显示名称
     */
    private String statusDisplayName;

    /**
     * 展示名称
     */
    private String displayName;

    /**
     * 创建时间
     */
    private String createdAt;

    /**
     * 更新时间
     */
    private String updatedAt;
}