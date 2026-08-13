package com.tp.service.impl;

import com.tp.common.ResultCode;
import com.tp.converter.KnowledgeArticleConverter;
import com.tp.exception.BusinessException;
import com.tp.mapper.KnowledgeArticleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 包名称：com.tp.service.impl
 * 类名称：KnowledgeArticleServiceImplTests
 * 类描述：知识文章服务测试类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/13
 */
class KnowledgeArticleServiceImplTests {

    /**
     * 知识文章Mapper
     */
    private KnowledgeArticleMapper knowledgeArticleMapper;

    /**
     * 知识文章服务
     */
    private KnowledgeArticleServiceImpl knowledgeArticleService;

    /**
     * 初始化测试数据
     */
    @BeforeEach
    void setUp() {
        knowledgeArticleMapper = mock(KnowledgeArticleMapper.class);
        knowledgeArticleService = new KnowledgeArticleServiceImpl(mock(KnowledgeArticleConverter.class));
        ReflectionTestUtils.setField(knowledgeArticleService, "baseMapper", knowledgeArticleMapper);
    }

    /**
     * 查询不存在的知识文章时抛出业务异常
     */
    @Test
    void shouldRejectMissingArticleDetail() {
        when(knowledgeArticleMapper.selectById("missing")).thenReturn(null);

        assertThatThrownBy(() -> knowledgeArticleService.getDetail("missing"))
                .isInstanceOf(BusinessException.class)
                .extracting("code")
                .isEqualTo(ResultCode.KNOWLEDGE_ARTICLE_NOT_FOUND.getCode());
    }
}
