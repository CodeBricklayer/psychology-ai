package com.tp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tp.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 包名称：com.tp.mapper
 * 接口名称：UserMapper
 * 接口描述：用户映射接口
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 12:04
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
