package com.tp.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 包名称：com.tp.entity
 * 类名称：KnowledgeArticle
 * 类描述：知识文章实体类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12
 */

@Data
@TableName("knowledge_article")
public class KnowledgeArticle {

    /**
     * 文章ID
     */
    @TableId
    private String id;
    /**
     * 分类ID
     */
    @TableField("category_id")
    private Long categoryId;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章摘要
     */
    private String summary;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 封面图片
     */
    @TableField("cover_image")
    private String coverImage;
    /**
     * 文章标签
     */
    private String tags;
    /**
     * 作者ID
     */
    @TableField("author_id")
    private Long authorId;
    /**
     * 阅读数量
     */
    @TableField("read_count")
    private Integer readCount;
    /**
     * 文章状态
     */
    private Integer status;
    /**
     * 发布时间
     */
    @TableField("published_at")
    private LocalDateTime publishedAt;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;
    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
