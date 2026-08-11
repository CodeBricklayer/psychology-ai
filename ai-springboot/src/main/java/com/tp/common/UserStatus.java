package com.tp.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 包名称：com.tp.common
 * 类名称：UserStatus
 * 类描述：用户状态枚举类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 13:09
 */
@Getter
@AllArgsConstructor
public enum UserStatus {
    /**
     * 正常状态
     */
    NORMAL(1, "正常"),

    /**
     * 禁用状态
     */
    DISABLED(0, "禁用"),

    /**
     * 未知状态
     */
    UNKNOWN(-1, "未知");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 状态描述
     */
    private final String description;

    /**
     * 根据代码获取枚举值
     *
     * @param code 状态代码
     * @return 状态枚举值
     */
    public static UserStatus fromCode(Integer code) {
        return Arrays.stream(UserStatus.values())
                .filter(status -> status.getCode().equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }

}