package com.tp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tp.common.Result;
import com.tp.converter.EmotionDiaryConverter;
import com.tp.entity.EmotionDiary;
import com.tp.entity.dto.EmotionDiaryCommandDTO;
import com.tp.service.EmotionDiaryService;
import com.tp.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 包名称：com.tp.controller
 * 类名称：EmotionDiaryController
 * 类描述：情绪日记接口控制器
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12
 */
@RestController
@RequiredArgsConstructor
public class EmotionDiaryController {

    /**
     * 情绪日记服务
     */
    private final EmotionDiaryService emotionDiaryService;

    /**
     * 情绪日记转换器
     */
    private final EmotionDiaryConverter emotionDiaryConverter;

    /**
     * 创建或更新当前用户的情绪日记
     *
     * @param diary 情绪日记实体
     * @return 操作结果
     */
    @PostMapping("/emotion-diary")
    public Result<Void> save(@Valid @RequestBody EmotionDiaryCommandDTO commandDTO) {
        EmotionDiary diary = emotionDiaryConverter.toEntity(
                JwtTokenUtil.extractUserId(), commandDTO);
        emotionDiaryService.saveOrUpdateByUserAndDate(diary);
        return Result.ok();
    }

    /**
     * 分页查询情绪日记
     *
     * @param currentPage 当前页码
     * @param pageSize    每页数量
     * @param userId      用户ID
     * @param minMoodScore 最低情绪评分
     * @param maxMoodScore 最高情绪评分
     * @return 情绪日记分页结果
     */
    @GetMapping("/emotion-diary/admin/page")
    public Result<IPage<EmotionDiary>> page(
            @RequestParam(defaultValue = "1") long currentPage,
            @RequestParam(defaultValue = "10") long pageSize,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer minMoodScore,
            @RequestParam(required = false) Integer maxMoodScore) {
        return Result.ok(emotionDiaryService.page(
                new Page<>(currentPage, pageSize), userId, minMoodScore, maxMoodScore));
    }

    /**
     * 删除情绪日记
     *
     * @param id 情绪日记ID
     * @return 操作结果
     */
    @DeleteMapping("/emotion-diary/admin/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        emotionDiaryService.removeById(id);
        return Result.ok();
    }
}
