package com.tp.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 包名称：com.tp.common
 * 类名称：SenderTyp
 * 类描述：发送类型枚举
 * <p>
 * 用于表示发送消息的类型，包括用户和机器人。
 * <p>
 * 该枚举定义了发送类型的编码和描述，用于在代码中进行发送类型的判断和处理。
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 16:22
 */
@Getter
@AllArgsConstructor
public enum SenderType {
    /**
     * 用户
     */
    USER(1, "用户"),
    /**
     * 机器人
     */
    ROBOT(2, "机器人"),
    /**
     * 未知
     */
    UNKNOWN(0, "未知");

    /**
     * 发送类型编码
     */
    private final Integer code;

    /**
     * 发送类型描述
     */
    private final String description;

    /**
     * 根据发送类型代码获取枚举值
     *
     * @param code 发送类型代码
     * @return 发送枚举值
     */
    public static SenderType fromCode(Integer code) {
        return Arrays.stream(SenderType.values())
                .filter(senderType -> senderType.getCode().equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }
}