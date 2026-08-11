package com.tp.converter;

import com.tp.entity.ConsultationSession;
import com.tp.entity.dto.ConsultationSessionCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
 * 包名称：com.tp.converter
 * 接口名称：ConsultationSessionConverter
 * 接口描述：咨询会话转换器接口
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 16:12
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ConsultationSessionConverter {

    /**
     * 将咨询会话创建DTO转换为咨询会话实体
     *
     * @param userId 用户ID
     * @param create 咨询会话创建DTO
     * @return 咨询会话实体
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "lastEmotionAnalysis", ignore = true),
            @Mapping(target = "lastEmotionUpdatedAt", ignore = true),
            @Mapping(target = "startedAt", expression = "java(java.time.LocalDateTime.now())")

    })
    ConsultationSession toEntity(Long userId, ConsultationSessionCreateDTO create);
}
