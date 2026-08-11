package com.tp.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 包名称：com.tp.entity.dto
 * 类名称：ConsultationSessionCreateDTO
 * 类描述：咨询会话创建DTO
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 15:52
 */
@Data
public class ConsultationSessionCreateDTO {

    /**
     * 会话标题
     */
    @Size(max = 200, message = "会话标题最多200个字符")
    private String sessionTitle;

    /**
     * 初始消息
     */
    @NotBlank(message = "初始消息不能为空")
    @Size(max = 2000, message = "初始消息最多2000个字符")
    private String initialMessage;
}