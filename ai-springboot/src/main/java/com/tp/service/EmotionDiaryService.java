package com.tp.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.IService;
import com.tp.entity.EmotionDiary;

/**
 * 包名称：com.tp.service
 * 接口名称：EmotionDiaryService
 * 接口描述：情绪日记服务接口
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12
 */
public interface EmotionDiaryService extends IService<EmotionDiary> {

    /**
     * 保存或更新用户指定日期的情绪日记。
     *
     * @param diary 情绪日记实体
     */
    void saveOrUpdateByUserAndDate(EmotionDiary diary);

    /**
     * 分页查询情绪日记
     *
     * @param page         分页对象
     * @param userId       用户ID
     * @param minMoodScore 最低情绪评分
     * @param maxMoodScore 最高情绪评分
     * @return 情绪日记分页结果
     */
    IPage<EmotionDiary> page(Page<EmotionDiary> page, Long userId, Integer minMoodScore, Integer maxMoodScore);
}
