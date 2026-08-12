package com.tp.entity.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 包名称：com.tp.entity.dto
 * 类名称：KnowledgeArticleStatusCommandDTO
 * 类描述：知识文章状态修改DTO
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12 16:30
 */
@Data
public class KnowledgeArticleStatusCommandDTO {

    /**
     * 文章状态 0:草稿 1:已发布 2:已下线
     */
    @NotNull(message = "文章状态不能为空")
    @Min(value = 0, message = "文章状态不能小于0")
    @Max(value = 2, message = "文章状态不能大于2")
    private Integer status;
}
