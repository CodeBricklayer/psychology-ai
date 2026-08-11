package com.tp.converter;

import com.tp.common.MessageType;
import com.tp.common.SenderType;
import com.tp.entity.ConsultationMessage;
import com.tp.entity.dto.ConsultationSessionCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
 * 包名称：com.tp.converter
 * 接口名称：ConsultationMessageConverter
 * 接口描述：咨询消息转换器接口
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 16:18
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {MessageType.class, SenderType.class}
)
public interface ConsultationMessageConverter {
    /**
     * 将咨询消息创建DTO转换为咨询消息实体
     *
     * @param create    咨询消息创建DTO
     * @param userId    用户ID
     * @param sessionId 会话ID
     * @return 咨询消息实体
     */
    @Mappings({
            @Mapping(target = "aiModel", ignore = true),
            @Mapping(target = "content", source = "create.initialMessage"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "emotionTag", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "messageType", expression = "java(MessageType.TEXT.getCode())"),
            @Mapping(target = "senderType", expression = "java(SenderType.USER.getCode())")
    })
    ConsultationMessage toEntity(Long userId, Long sessionId, ConsultationSessionCreateDTO create);
}
