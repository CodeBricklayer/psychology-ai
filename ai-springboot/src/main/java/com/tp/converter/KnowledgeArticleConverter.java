package com.tp.converter;

import com.tp.entity.KnowledgeArticle;
import com.tp.entity.dto.KnowledgeArticleCommandDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * 包名称：com.tp.converter
 * 接口名称：KnowledgeArticleConverter
 * 接口描述：知识文章转换器接口
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12 16:30
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface KnowledgeArticleConverter {

    /**
     * 将知识文章DTO转换为实体
     *
     * @param authorId   作者ID
     * @param commandDTO 知识文章DTO
     * @return 知识文章实体
     */
    @Mappings({
            @Mapping(target = "authorId", source = "authorId"),
            @Mapping(target = "readCount", expression = "java(0)"),
            @Mapping(target = "publishedAt", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", source = "commandDTO.status", defaultExpression = "java(0)")
    })
    KnowledgeArticle toEntity(Long authorId, KnowledgeArticleCommandDTO commandDTO);

    /**
     * 使用知识文章DTO更新实体
     *
     * @param authorId   作者ID
     * @param commandDTO 知识文章DTO
     * @param article    知识文章实体
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "authorId", source = "authorId"),
            @Mapping(target = "readCount", ignore = true),
            @Mapping(target = "publishedAt", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", ignore = true)
    })
    void updateEntity(Long authorId, KnowledgeArticleCommandDTO commandDTO,
                      @MappingTarget KnowledgeArticle article);
}
