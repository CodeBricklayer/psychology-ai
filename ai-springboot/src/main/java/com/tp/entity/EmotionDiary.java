package com.tp.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 包名称：com.tp.entity
 * 类名称：EmotionDiary
 * 类描述：情绪日记实体类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12
 */

@Data
@TableName("emotion_diary")
public class EmotionDiary {

    /**
     * 日记ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 日记日期
     */
    @TableField("diary_date")
    private LocalDate diaryDate;
    /**
     * 情绪评分
     */
    @TableField("mood_score")
    private Integer moodScore;
    /**
     * 主要情绪
     */
    @TableField("dominant_emotion")
    private String dominantEmotion;
    /**
     * 情绪触发因素
     */
    @TableField("emotion_triggers")
    private String emotionTriggers;
    /**
     * 日记内容
     */
    @TableField("diary_content")
    private String diaryContent;
    /**
     * 睡眠质量
     */
    @TableField("sleep_quality")
    private Integer sleepQuality;
    /**
     * 压力水平
     */
    @TableField("stress_level")
    private Integer stressLevel;
    /**
     * AI情绪分析结果
     */
    @TableField("ai_emotion_analysis")
    private String aiEmotionAnalysis;
    /**
     * AI分析更新时间
     */
    @TableField("ai_analysis_updated_at")
    private LocalDateTime aiAnalysisUpdatedAt;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;
    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
