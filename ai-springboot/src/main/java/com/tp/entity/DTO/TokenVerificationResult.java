package com.tp.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 包名称：com.tp.entity.dto.command
 * 类名称：TokenVerificationResult
 * 类描述：JWT验证结果DTO
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 13:31
 */
@Builder
@AllArgsConstructor
@Data
public class TokenVerificationResult {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 是否有效
     */
    private Boolean isValid;
}