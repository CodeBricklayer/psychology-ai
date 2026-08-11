package com.tp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tp.entity.ConsultationMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 包名称：com.tp.mapper
 * 接口名称：ConsultationMessageMapper
 * 接口描述：咨询消息Mapper接口
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 16:05
 */
@Mapper
public interface ConsultationMessageMapper extends BaseMapper<ConsultationMessage> {
}
