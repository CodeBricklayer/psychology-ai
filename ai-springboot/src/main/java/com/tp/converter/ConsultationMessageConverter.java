package com.tp.converter;

import com.tp.common.MessageType;
import com.tp.common.SenderType;
import com.tp.entity.ConsultationMessage;
import com.tp.entity.dto.ConsultationSessionCreateDTO;
import com.tp.entity.vo.response.ConsultationMessageResponseVO;
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
    ConsultationMessage toEntity(Long sessionId, ConsultationSessionCreateDTO create);

    /**
     * 将咨询消息实体转换为咨询消息响应VO对象
     *
     * @param message 咨询消息实体
     * @return 咨询消息响应VO对象
     */
    @Mappings({
            @Mapping(target = "contentLength", expression = "java(message.getContent().length())"),
            @Mapping(target = "messageTypeDesc", expression = "java(MessageType.fromCode(message.getMessageType()).getDescription())"),
            @Mapping(target = "senderTypeDesc", expression = "java(SenderType.fromCode(message.getSenderType()).getDescription())")
    })
    ConsultationMessageResponseVO toResponseVO(ConsultationMessage message);
}
