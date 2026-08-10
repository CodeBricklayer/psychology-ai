package com.tp.entity.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 包名称：com.tp.entity.DTO.command
 * 类名称：UserLoginCommandDTO
 * 类描述：用户登录实体类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 9:56
 */
@Data
public class UserLoginCommandDTO {

    /**
     * 用户名或邮箱
     */
    @NotBlank(message = "用户名或邮箱不能为空")
    @Size(max = 100,message = "用户名或邮箱长度不能超过100个字符")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6,max = 20,message = "密码长度必须在6到20个字符之间")
    private String password;
}