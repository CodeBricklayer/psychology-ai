package com.tp.entity.dto.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 包名称：com.tp.entity.dto.command
 * 类名称：UserRegisterCommandDTO
 * 类描述：用户注册实体类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 10:12
 */
@Data
public class UserRegisterCommandDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度必须在3到50个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    private String username;

    /**
     * 昵称
     */
    @Size(max = 50, message = "昵称长度不能超过50个字符")
    private String nickname;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 50, message = "密码长度必须在6到50个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "密码只能包含字母、数字和下划线")
    private String password;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    @Size(min = 6, max = 50, message = "确认密码长度必须在6到50个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "确认密码只能包含字母、数字和下划线")
    private String confirmPassword;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    @Email(message = "邮箱格式错误")
    private String email;

    /**
     * 手机号
     */
    @Size(max = 20, message = "手机号长度不能超过20个字符")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式错误")
    private String phone;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 用户类型
     */

    private Integer userType;
}