package com.tp.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.IService;
import com.tp.entity.KnowledgeArticle;
import com.tp.entity.dto.KnowledgeArticleCommandDTO;

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

    /**
     * 创建知识文章
     *
     * @param authorId   作者ID
     * @param commandDTO 知识文章DTO
     * @return 创建后的知识文章
     */
    KnowledgeArticle createArticle(Long authorId, KnowledgeArticleCommandDTO commandDTO);

    /**
     * 更新知识文章
     *
     * @param id         文章ID
     * @param authorId   作者ID
     * @param commandDTO 知识文章DTO
     */
    void updateArticle(String id, Long authorId, KnowledgeArticleCommandDTO commandDTO);

    /**
     * 更新知识文章状态
     *
     * @param id     文章ID
     * @param status 文章状态
     */
    void updateStatus(String id, Integer status);

    /**
     * 删除知识文章
     *
     * @param id 文章ID
     */
    void deleteArticle(String id);
}
