package com.tp.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.tp.entity.KnowledgeArticle;
import com.tp.entity.dto.KnowledgeArticleCommandDTO;
import com.tp.common.ResultCode;
import com.tp.converter.KnowledgeArticleConverter;
import com.tp.exception.BusinessException;
import com.tp.mapper.KnowledgeArticleMapper;
import com.tp.service.KnowledgeArticleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import lombok.RequiredArgsConstructor;

/**
 * 包名称：com.tp.service.impl
 * 类名称：KnowledgeArticleServiceImpl
 * 类描述：知识文章服务实现类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12
 */
@Service
@RequiredArgsConstructor
public class KnowledgeArticleServiceImpl extends ServiceImpl<KnowledgeArticleMapper, KnowledgeArticle>
        implements KnowledgeArticleService {

    /**
     * 知识文章转换器
     */
    private final KnowledgeArticleConverter knowledgeArticleConverter;

    /**
     * 分页查询知识文章
     *
     * @param page       分页对象
     * @param title      文章标题
     * @param categoryId 分类ID
     * @param status     文章状态
     * @return 文章分页结果
     */
    @Override
    public IPage<KnowledgeArticle> page(Page<KnowledgeArticle> page, String title, Long categoryId, Integer status) {
        LambdaQueryWrapper<KnowledgeArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(title), KnowledgeArticle::getTitle, title)
                .eq(categoryId != null, KnowledgeArticle::getCategoryId, categoryId)
                .eq(status != null, KnowledgeArticle::getStatus, status)
                .orderByDesc(KnowledgeArticle::getUpdatedAt);
        return page(page, wrapper);
    }

    /**
     * 获取知识文章详情并增加阅读量
     *
     * @param id 文章ID
     * @return 文章详情
     */
    @Override
    public KnowledgeArticle getDetail(String id) {
        KnowledgeArticle article = getById(id);
        if (article == null) {
            throw new BusinessException(ResultCode.KNOWLEDGE_ARTICLE_NOT_FOUND);
        }
        getBaseMapper().increaseReadCount(id);
        article.setReadCount((article.getReadCount() == null ? 0 : article.getReadCount()) + 1);
        return article;
    }

    /**
     * 创建知识文章
     *
     * @param authorId   作者ID
     * @param commandDTO 知识文章DTO
     * @return 创建后的知识文章
     */
    @Override
    public KnowledgeArticle createArticle(Long authorId, KnowledgeArticleCommandDTO commandDTO) {
        KnowledgeArticle article = knowledgeArticleConverter.toEntity(authorId, commandDTO);
        save(article);
        return article;
    }

    /**
     * 更新知识文章
     *
     * @param id         文章ID
     * @param authorId   作者ID
     * @param commandDTO 知识文章DTO
     */
    @Override
    public void updateArticle(String id, Long authorId, KnowledgeArticleCommandDTO commandDTO) {
        KnowledgeArticle article = getById(id);
        if (article == null) {
            throw new BusinessException(ResultCode.KNOWLEDGE_ARTICLE_NOT_FOUND);
        }
        knowledgeArticleConverter.updateEntity(authorId, commandDTO, article);
        if (commandDTO.getStatus() != null) {
            article.setStatus(commandDTO.getStatus());
        }
        if (!updateById(article)) {
            throw new BusinessException(ResultCode.KNOWLEDGE_ARTICLE_NOT_FOUND);
        }
    }

    /**
     * 更新知识文章状态
     *
     * @param id     文章ID
     * @param status 文章状态
     */
    @Override
    public void updateStatus(String id, Integer status) {
        boolean updated = lambdaUpdate().eq(KnowledgeArticle::getId, id)
                .set(KnowledgeArticle::getStatus, status).update();
        if (!updated) {
            throw new BusinessException(ResultCode.KNOWLEDGE_ARTICLE_NOT_FOUND);
        }
    }

    /**
     * 删除知识文章
     *
     * @param id 文章ID
     */
    @Override
    public void deleteArticle(String id) {
        if (!removeById(id)) {
            throw new BusinessException(ResultCode.KNOWLEDGE_ARTICLE_NOT_FOUND);
        }
    }
}
