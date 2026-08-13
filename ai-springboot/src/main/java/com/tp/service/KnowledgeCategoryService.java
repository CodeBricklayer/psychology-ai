package com.tp.service;
import com.baomidou.mybatisplus.spring.service.IService;
import com.tp.entity.KnowledgeCategory;

import java.util.List;

/**
 * 包名称：com.tp.service
 * 接口名称：KnowledgeCategoryService
 * 接口描述：知识文章分类服务接口
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12
 */
public interface KnowledgeCategoryService extends IService<KnowledgeCategory> {

    /**
     * 查询启用的知识文章分类
     *
     * @return 分类列表
     */
    List<KnowledgeCategory> listEnabled();
}
