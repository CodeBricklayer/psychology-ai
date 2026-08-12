package com.tp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tp.entity.KnowledgeArticle;
import org.apache.ibatis.annotations.Mapper;

/**
 * 包名称：com.tp.mapper
 * 接口名称：KnowledgeArticleMapper
 * 接口描述：知识文章Mapper接口
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12
 */
@Mapper
public interface KnowledgeArticleMapper extends BaseMapper<KnowledgeArticle> {

    /**
     * 增加文章阅读量
     *
     * @param id 文章ID
     * @return 影响行数
     */
    int increaseReadCount(String id);
}
