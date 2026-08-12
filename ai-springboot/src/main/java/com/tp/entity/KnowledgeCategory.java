package com.tp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 包名称：com.tp.entity
 * 类名称：KnowledgeCategory
 * 类描述：知识文章分类实体类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12
 */

@Data
@TableName("knowledge_category")
public class KnowledgeCategory {

    /**
     * 分类ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 父分类ID
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     * 分类名称
     */
    @TableField("category_name")
    private String categoryName;
    /**
     * 分类编码
     */
    @TableField("category_code")
    private String categoryCode;
    /**
     * 分类描述
     */
    private String description;
    /**
     * 排序号
     */
    @TableField("sort_order")
    private Integer sortOrder;
    /**
     * 分类状态
     */
    private Integer status;
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
