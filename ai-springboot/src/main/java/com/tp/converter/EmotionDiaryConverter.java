package com.tp.converter;

import com.tp.entity.EmotionDiary;
import com.tp.entity.dto.EmotionDiaryCommandDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
 * 包名称：com.tp.converter
 * 接口名称：EmotionDiaryConverter
 * 接口描述：情绪日记转换器接口
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12 16:30
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EmotionDiaryConverter {

    /**
     * 将情绪日记DTO转换为实体
     *
     * @param userId     用户ID
     * @param commandDTO 情绪日记DTO
     * @return 情绪日记实体
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "aiEmotionAnalysis", ignore = true),
            @Mapping(target = "aiAnalysisUpdatedAt", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    })
    EmotionDiary toEntity(Long userId, EmotionDiaryCommandDTO commandDTO);
}
