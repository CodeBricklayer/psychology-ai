package com.tp.converter;

import com.tp.entity.User;
import com.tp.entity.vo.response.UserDetailResponseVO;
import com.tp.entity.vo.response.UserLoginResponseVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
 * 包名称：com.tp.converter
 * 接口名称：UserConverter
 * 接口描述：
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 17:40
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserConverter {

    /**
     * 将用户实体转换为用户详情响应VO
     *
     * @param user     用户实体
     * @return 用户详情响应VO
     */
    @Mappings({
            @Mapping(target = "birthday", source = "birthday", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "updatedAt", source = "updatedAt", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "userTypeDisplayName",
                    expression = "java(user.getUserTypeDisplayName())"),
            @Mapping(target = "statusDisplayName",
                    expression = "java(user.getStatusDisplayName())"),
            @Mapping(target = "displayName",
                    expression = "java(user.getDisplayName())"),
            @Mapping(target = "genderDisplayName",
                    expression = "java(user.getGenderDisplayName())")
    })
    UserDetailResponseVO toUserDetailResponseVO(User user);

    /**
     * 将用户实体转换为用户登录响应VO
     *
     * @param user     用户实体
     * @param token    JWT令牌
     * @param roleType 角色类型
     * @return 用户登录响应VO
     */
    @Mappings({
            @Mapping(target = "userInfo", source = "user"),
            @Mapping(target = "token", source = "token"),
            @Mapping(target = "roleType", source = "roleType")
    })
    UserLoginResponseVO toUserLoginResponseVO(
            User user,
            String token,
            Integer roleType
    );


}
