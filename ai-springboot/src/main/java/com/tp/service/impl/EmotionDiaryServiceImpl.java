package com.tp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.tp.entity.EmotionDiary;
import com.tp.mapper.EmotionDiaryMapper;
import com.tp.service.EmotionDiaryService;
import org.springframework.stereotype.Service;

/**
 * 包名称：com.tp.service.impl
 * 类名称：EmotionDiaryServiceImpl
 * 类描述：情绪日记服务实现类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12
 */
@Service
public class EmotionDiaryServiceImpl extends ServiceImpl<EmotionDiaryMapper, EmotionDiary>
        implements EmotionDiaryService {

    /**
     * 保存或更新用户指定日期的情绪日记
     *
     * @param diary 情绪日记实体
     */
    @Override
    public void saveOrUpdateByUserAndDate(EmotionDiary diary) {
        EmotionDiary existed = getOne(new LambdaQueryWrapper<EmotionDiary>()
                .select(EmotionDiary::getId)
                .eq(EmotionDiary::getUserId, diary.getUserId())
                .eq(EmotionDiary::getDiaryDate, diary.getDiaryDate()));
        if (existed == null) {
            save(diary);
            return;
        }
        diary.setId(existed.getId());
        updateById(diary);
    }

    /**
     * 分页查询情绪日记
     *
     * @param page         分页对象
     * @param userId       用户ID
     * @param minMoodScore 最低情绪评分
     * @param maxMoodScore 最高情绪评分
     * @return 情绪日记分页结果
     */
    @Override
    public IPage<EmotionDiary> page(Page<EmotionDiary> page, Long userId, Integer minMoodScore, Integer maxMoodScore) {
        LambdaQueryWrapper<EmotionDiary> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(userId != null, EmotionDiary::getUserId, userId)
                .ge(minMoodScore != null, EmotionDiary::getMoodScore, minMoodScore)
                .le(maxMoodScore != null, EmotionDiary::getMoodScore, maxMoodScore)
                .orderByDesc(EmotionDiary::getDiaryDate);
        return page(page, wrapper);
    }
}

