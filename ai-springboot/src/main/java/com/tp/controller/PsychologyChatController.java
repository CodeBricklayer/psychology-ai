package com.tp.controller;

import cn.hutool.json.JSONUtil;
import com.tp.common.Result;
import com.tp.common.ResultCode;
import com.tp.entity.dto.ConsultationSessionCreateDTO;
import com.tp.entity.dto.ConsultationStreamDTO;
import com.tp.entity.vo.response.StreamChatSession;
import com.tp.service.PsychologyService;
import com.tp.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

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

    /**
     * 咨询流
     *
     * @param stream 咨询流DTO
     * @return 咨询流
     */
    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamChat(@Valid @RequestBody ConsultationStreamDTO stream) {
        Long userId = JwtTokenUtil.extractUserId();
        if (userId == null) {
            return Flux.just(ServerSentEvent.<String>builder()
                    .event("error")
                    .data(JSONUtil.toJsonStr(Result.error(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMsg())))
                    .build());
        }
        return psychologyService.streamChat(userId, stream.getSessionId(), stream.getUserMessage())
                //将咨询流转换为ServerSentEvent
                .map(fragment -> ServerSentEvent.<String>builder().event("message").data(JSONUtil.toJsonStr(Result.ok(Map.of("content", fragment, "type", "normal")))).build())
                //添加完成事件
                .concatWith(Flux.just(ServerSentEvent.<String>builder().event("done").data("{}").build()));
    }
}
