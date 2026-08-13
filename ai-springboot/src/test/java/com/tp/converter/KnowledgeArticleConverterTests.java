package com.tp.converter;

import com.tp.entity.KnowledgeArticle;
import com.tp.entity.dto.KnowledgeArticleCommandDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 包名称：com.tp.converter
 * 类名称：KnowledgeArticleConverterTests
 * 类描述：知识文章转换器测试类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/13
 */
class KnowledgeArticleConverterTests {

    /**
     * 更新文章时保留数据库维护字段
     */
    @Test
    void shouldPreserveDatabaseManagedFieldsWhenUpdating() {
        KnowledgeArticleConverter converter = Mappers.getMapper(KnowledgeArticleConverter.class);
        LocalDateTime createdAt = LocalDateTime.of(2026, 8, 1, 10, 0);
        KnowledgeArticle article = new KnowledgeArticle();
        article.setId("article-1");
        article.setReadCount(12);
        article.setStatus(1);
        article.setCreatedAt(createdAt);

        KnowledgeArticleCommandDTO commandDTO = new KnowledgeArticleCommandDTO();
        commandDTO.setCategoryId(2L);
        commandDTO.setTitle("更新后的标题");
        commandDTO.setContent("更新后的内容");

        converter.updateEntity(3L, commandDTO, article);

        assertThat(article.getId()).isEqualTo("article-1");
        assertThat(article.getReadCount()).isEqualTo(12);
        assertThat(article.getStatus()).isEqualTo(1);
        assertThat(article.getCreatedAt()).isEqualTo(createdAt);
        assertThat(article.getAuthorId()).isEqualTo(3L);
        assertThat(article.getUpdatedAt()).isNotNull();
    }
}
