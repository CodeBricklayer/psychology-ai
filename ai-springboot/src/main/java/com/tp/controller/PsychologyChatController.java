package com.tp.controller;

import com.tp.common.Result;
import com.tp.entity.dto.ConsultationSessionCreateDTO;
import com.tp.entity.vo.response.StreamChatSession;
import com.tp.service.PsychologyService;
import com.tp.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 包名称：com.tp.controller
 * 类名称：PsychologyChatController
 * 类描述：心理咨询会话控制器
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 15:43
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/psychological-chat")
public class PsychologyChatController {

    private final PsychologyService psychologyService;

    /**
     * 创建咨询会话
     *
     * @param consultationSessionCreate 咨询会话创建DTO
     * @return 咨询会话
     */
    @PostMapping("/session/start")
    public Result<StreamChatSession> startSession(@Valid @RequestBody ConsultationSessionCreateDTO consultationSessionCreate) {
        return Result.ok(psychologyService.startSession(JwtTokenUtil.extractUserId(), consultationSessionCreate));
    }
}