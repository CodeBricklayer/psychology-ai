package com.tp.entity.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * 包名称：com.tp.entity.dto
 * 类名称：EmotionDiaryCommandDTO
 * 类描述：情绪日记新增或修改DTO
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12 16:30
 */
@Data
public class EmotionDiaryCommandDTO {

    /**
     * 日记日期
     */
    @NotNull(message = "日记日期不能为空")
    private LocalDate diaryDate;

    /**
     * 情绪评分
     */
    @NotNull(message = "情绪评分不能为空")
    @Min(value = 1, message = "情绪评分不能低于1")
    @Max(value = 10, message = "情绪评分不能高于10")
    private Integer moodScore;

    /**
     * 主要情绪
     */
    @NotBlank(message = "主要情绪不能为空")
    @Size(max = 50, message = "主要情绪不能超过50个字符")
    private String dominantEmotion;

    /**
     * 情绪触发因素
     */
    @NotBlank(message = "情绪触发因素不能为空")
    private String emotionTriggers;

    /**
     * 日记内容
     */
    @NotBlank(message = "日记内容不能为空")
    private String diaryContent;

    /**
     * 睡眠质量
     */
    @NotNull(message = "睡眠质量不能为空")
    @Min(value = 1, message = "睡眠质量不能低于1")
    @Max(value = 5, message = "睡眠质量不能高于5")
    private Integer sleepQuality;

    /**
     * 压力水平
     */
    @NotNull(message = "压力水平不能为空")
    @Min(value = 1, message = "压力水平不能低于1")
    @Max(value = 5, message = "压力水平不能高于5")
    private Integer stressLevel;
}
