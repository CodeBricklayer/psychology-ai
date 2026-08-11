package com.tp.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 包名称：com.tp.common
 * 枚举名称：UserType
 * 枚举描述：用户类型枚举类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 13:14
 */
@Getter
@AllArgsConstructor
public enum UserType {
    /**
     * 普通用户
     */
    USER(1, "普通用户"),
    /**
     * 管理员
     */
    ADMIN(2, "管理员"),
    /**
     * 未知用户类型
     */
    UNKNOWN(-1, "未知");

    /**
     * 用户类型码
     */
    private final Integer code;
    /**
     * 用户类型描述
     */
    private final String description;

    /**
     * 根据代码获取枚举值
     *
     * @param code 用户类型代码
     * @return 用户类型枚举值
     */
    public static UserType fromCode(Integer code) {
        return Arrays.stream(UserType.values())
                .filter(type -> type.getCode().equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }

    /**
     * 校验用户类型代码是否有效
     *
     * @param code 用户类型代码
     * @return 是否有效
     */
    public static Boolean validateCode(Integer code) {
        return fromCode(code) != UNKNOWN;
    }
}
