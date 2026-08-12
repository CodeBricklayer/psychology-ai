package com.tp.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tp.entity.EmotionDiary;
import org.apache.ibatis.annotations.Mapper;

/**
 * 包名称：com.tp.mapper
 * 接口名称：EmotionDiaryMapper
 * 接口描述：情绪日记Mapper接口
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12
 */
@Mapper
public interface EmotionDiaryMapper extends BaseMapper<EmotionDiary> {
}
