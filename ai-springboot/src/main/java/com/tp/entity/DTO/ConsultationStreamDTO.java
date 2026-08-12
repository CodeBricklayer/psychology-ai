package com.tp.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 包名称：com.tp.entity.dto
 * 类名称：ConsultationStreamDTO
 * 类描述：咨询流DTO
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12 11:14
 */
@Data
public class ConsultationStreamDTO {

    /**
     * 会话标识
     */
    @NotBlank(message = "会话标识不能为空")
    private String sessionId;

    /**
     * 用户输入内容
     */
    @NotBlank(message = "用户输入内容不能为空")
    @Size(max = 2000, message = "用户输入内容不能超过2000个字符")
    private String userMessage;
}