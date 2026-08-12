package com.tp.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tp.common.Result;
import com.tp.converter.KnowledgeArticleConverter;
import com.tp.entity.KnowledgeArticle;
import com.tp.entity.KnowledgeCategory;
import com.tp.entity.dto.KnowledgeArticleCommandDTO;
import com.tp.entity.dto.KnowledgeArticleStatusCommandDTO;
import jakarta.validation.Valid;
import com.tp.service.KnowledgeArticleService;
import com.tp.service.KnowledgeCategoryService;
import com.tp.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 包名称：com.tp.controller
 * 类名称：KnowledgeController
 * 类描述：知识文章接口控制器
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/knowledge")
public class KnowledgeController {

    /**
     * 知识文章分类服务
     */
    private final KnowledgeCategoryService categoryService;

    /**
     * 知识文章服务
     */
    private final KnowledgeArticleService articleService;

    /**
     * 知识文章转换器
     */
    private final KnowledgeArticleConverter articleConverter;

    /**
     * 获取知识文章分类树
     *
     * @return 分类列表
     */
    @GetMapping("/category/tree")
    public Result<List<KnowledgeCategory>> categoryTree() {
        return Result.ok(categoryService.lambdaQuery().eq(KnowledgeCategory::getStatus, 1)
                .orderByAsc(KnowledgeCategory::getSortOrder).list());
    }

    /**
     * 分页查询知识文章
     *
     * @param currentPage 当前页码
     * @param pageSize    每页数量
     * @param title       文章标题
     * @param categoryId  分类ID
     * @param status      文章状态
     * @return 文章分页结果
     */
    @GetMapping("/article/page")
    public Result<IPage<KnowledgeArticle>> articlePage(
            @RequestParam(defaultValue = "1") long currentPage,
            @RequestParam(defaultValue = "10") long pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer status) {
        return Result.ok(articleService.page(new Page<>(currentPage, pageSize), title, categoryId, status));
    }

    /**
     * 获取知识文章详情
     *
     * @param id 文章ID
     * @return 文章详情
     */
    @GetMapping("/article/{id}")
    public Result<KnowledgeArticle> articleDetail(@PathVariable String id) {
        return Result.ok(articleService.getDetail(id));
    }

    /**
     * 创建知识文章
     *
     * @param commandDTO 知识文章DTO
     * @return 创建后的文章实体
     */
    @PostMapping("/article")
    public Result<KnowledgeArticle> createArticle(@Valid @RequestBody KnowledgeArticleCommandDTO commandDTO) {
        KnowledgeArticle article = articleConverter.toEntity(
                JwtTokenUtil.extractUserId(), commandDTO);
        articleService.save(article);
        return Result.ok(article);
    }

    /**
     * 更新知识文章
     *
     * @param id      文章ID
     * @param commandDTO 知识文章DTO
     * @return 操作结果
     */
    @PutMapping("/article/{id}")
    public Result<Void> updateArticle(@PathVariable String id,
                                      @Valid @RequestBody KnowledgeArticleCommandDTO commandDTO) {
        KnowledgeArticle article = articleConverter.toEntity(
                JwtTokenUtil.extractUserId(), commandDTO);
        article.setId(id);
        articleService.updateById(article);
        return Result.ok();
    }

    /**
     * 更新知识文章状态
     *
     * @param id      文章ID
     * @param commandDTO 状态信息
     * @return 操作结果
     */
    @PutMapping("/article/{id}/status")
    public Result<Void> updateArticleStatus(@PathVariable String id,
                                            @Valid @RequestBody KnowledgeArticleStatusCommandDTO commandDTO) {
        articleService.lambdaUpdate().eq(KnowledgeArticle::getId, id)
                .set(KnowledgeArticle::getStatus, commandDTO.getStatus()).update();
        return Result.ok();
    }

    /**
     * 删除知识文章
     *
     * @param id 文章ID
     * @return 操作结果
     */
    @DeleteMapping("/article/{id}")
    public Result<Void> deleteArticle(@PathVariable String id) {
        articleService.removeById(id);
        return Result.ok();
    }
}
