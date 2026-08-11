package com.tp.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 包名称：com.tp.common
 * 枚举名称：MessageType
 * 枚举描述：
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 16:24
 */
@Getter
@AllArgsConstructor
public enum MessageType {
    /**
     * 文本消息
     */
    TEXT(1, "文本消息"),
    /**
     * 未知消息
     */
    UNKNOWN(0, "未知消息");

    /**
     * 消息类型
     */
    private final Integer code;

    /**
     * 消息类型描述
     */
    private final String description;


    /**
     * 根据消息类型代码获取枚举值
     *
     * @param code 消息类型代码
     * @return 消息枚举值
     */
    public static MessageType fromCode(Integer code) {
        return Arrays.stream(MessageType.values())
                .filter(messageType -> messageType.getCode().equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
