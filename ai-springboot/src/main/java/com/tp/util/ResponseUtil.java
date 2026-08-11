package com.tp.util;

import cn.hutool.json.JSONUtil;
import com.tp.common.Result;
import com.tp.common.ResultCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 包名称：com.tp.util
 * 类名称：ResponseUtil
 * 类描述：响应工具类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/11 13:18
 */
public class ResponseUtil {

    /**
     * 写入错误响应
     *
     * @param response   HttpServletResponse
     * @param resultCode ResultCode
     */
    public static void writeError(HttpServletResponse response, ResultCode resultCode) {
        // 根据不用的resultCode设置响应状态码和消息
        int statusCode = switch (resultCode) {
            case UNAUTHORIZED, ACCESS_UNAUTHORIZED, TOKEN_INVALID, TOKEN_EXPIRED, TOKEN_BLOCKED ->
                    HttpStatus.UNAUTHORIZED.value();
            case TOKEN_ACCESS_FORBIDDEN -> HttpStatus.FORBIDDEN.value();
            default -> HttpStatus.BAD_REQUEST.value();
        };
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (PrintWriter out = response.getWriter()) {
            out.print(JSONUtil.toJsonStr(Result.error(resultCode.getCode(), resultCode.getMsg())));
            // 刷新缓冲区
            out.flush();
        } catch (IOException e) {
            System.out.println("写入错误响应失败");
        }
    }
}