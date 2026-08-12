package com.tp.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 包名称：com.tp.entity.dto
 * 类名称：KnowledgeArticleCommandDTO
 * 类描述：知识文章新增或修改DTO
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12 16:30
 */
@Data
public class KnowledgeArticleCommandDTO {

    /**
     * 文章ID
     */
    private String id;

    /**
     * 分类ID
     */
    @NotNull(message = "文章分类不能为空")
    private Long categoryId;

    /**
     * 文章标题
     */
    @NotBlank(message = "文章标题不能为空")
    @Size(max = 200, message = "文章标题不能超过200个字符")
    private String title;

    /**
     * 文章摘要
     */
    @Size(max = 1000, message = "文章摘要不能超过1000个字符")
    private String summary;

    /**
     * 文章内容
     */
    @NotBlank(message = "文章内容不能为空")
    private String content;

    /**
     * 封面图片
     */
    @Size(max = 500, message = "封面图片路径不能超过500个字符")
    private String coverImage;

    /**
     * 文章标签
     */
    @Size(max = 500, message = "文章标签不能超过500个字符")
    private String tags;

    /**
     * 文章状态
     */
    private Integer status;
}
