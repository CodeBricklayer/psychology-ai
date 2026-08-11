package com.tp.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 包名称：com.tp.common
 * 类名称：GenderType
 * 类描述：性别类型枚举类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 17:55
 */
@Getter
@AllArgsConstructor
public enum GenderType {

    /**
     * 男
     */
    MALE(1, "男"),
    /**
     * 女
     */
    FEMALE(2, "女"),
    /**
     * 未知性别
     */
    UNKNOWN(-1, "未知");

    /**
     * 性别码
     */
    private final Integer code;
    /**
     * 性别描述
     */
    private final String description;

    public static GenderType fromCode(Integer code) {
        return Arrays.stream(GenderType.values())
                .filter(gender -> gender.getCode().equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }
}