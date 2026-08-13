package com.tp.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tp.common.Result;
import com.tp.entity.ConsultationSession;
import com.tp.entity.vo.response.ConsultationMessageResponseVO;
import com.tp.service.ConsultationSessionService;
import com.tp.util.JwtTokenUtil;
import com.tp.util.ConsultationSessionIdUtil;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 包名称：com.tp.controller
 * 类名称：ConsultationSessionController
 * 类描述：咨询会话接口控制器
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/12
 */
@RestController
@Validated
@RequiredArgsConstructor
public class ConsultationSessionController {

    /**
     * 咨询会话服务
     */
    private final ConsultationSessionService consultationSessionService;

    /**
     * 分页查询当前用户的咨询会话
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 咨询会话分页结果
     */
    @GetMapping("/psychological-chat/sessions")
    public Result<IPage<ConsultationSession>> page(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "当前页码不能小于1") long pageNum,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "每页数量不能小于1")
            @Max(value = 100, message = "每页数量不能超过100") long pageSize) {
        return Result.ok(consultationSessionService.pageByUser(
                new Page<>(pageNum, pageSize), JwtTokenUtil.extractUserId()));
    }

    /**
     * 查询咨询会话消息
     *
     * @param sessionId 会话ID
     * @return 会话消息列表
     */
    @GetMapping("/psychological-chat/sessions/{sessionId}/messages")
    public Result<List<ConsultationMessageResponseVO>> messages(@PathVariable Long sessionId) {
        return Result.ok(consultationSessionService.listMessages(
                sessionId, JwtTokenUtil.extractUserId()));
    }

    /**
     * 删除当前用户的咨询会话
     *
     * @param sessionId 会话ID
     * @return 操作结果
     */
    @DeleteMapping("/psychological-chat/sessions/{sessionId}")
    public Result<Void> delete(@PathVariable Long sessionId) {
        consultationSessionService.removeByUser(sessionId, JwtTokenUtil.extractUserId());
        return Result.ok();
    }

    /**
     * 查询咨询会话情绪分析结果
     *
     * @param sessionId 会话ID，格式为session_数字或数字
     * @return 情绪分析结果
     */
    @GetMapping("/psychological-chat/session/{sessionId}/emotion")
    public Result<Object> emotion(@PathVariable String sessionId) {
        Long id = ConsultationSessionIdUtil.parse(sessionId);
        String analysis = consultationSessionService.getEmotionAnalysis(
                id, JwtTokenUtil.extractUserId());
        return Result.ok(analysis == null ? java.util.Map.of() : JSONUtil.parseObj(analysis));
    }

}
