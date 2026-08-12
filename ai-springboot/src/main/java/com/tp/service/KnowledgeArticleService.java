package com.tp.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.IService;
import com.tp.entity.KnowledgeArticle;

/**
 * 包名称：com.tp.service
 * 接口名称：KnowledgeArticleService
 * 接口描述：知识文章服务接口
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12
 */
public interface KnowledgeArticleService extends IService<KnowledgeArticle> {

    /**
     * 分页查询知识文章
     *
     * @param page       分页对象
     * @param title      文章标题
     * @param categoryId 分类ID
     * @param status     文章状态
     * @return 文章分页结果
     */
    IPage<KnowledgeArticle> page(Page<KnowledgeArticle> page, String title, Long categoryId, Integer status);

    /**
     * 获取知识文章详情
     *
     * @param id 文章ID
     * @return 文章详情
     */
    KnowledgeArticle getDetail(String id);
}
