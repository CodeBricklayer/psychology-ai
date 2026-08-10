package com.tp.common;

import com.tp.exception.BusinessException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 包名称：com.tp.common
 * 类名称：GlobalExceptionHandler
 * 类描述：
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 10:09
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理参数校验异常
     *
     * @param e 异常信息
     * @return 异常结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMsg = e.getFieldErrors().getFirst().getDefaultMessage();
        return Result.error(ResultCode.PARAM_ERROR.getCode(), ResultCode.PARAM_ERROR.getMsg(), errorMsg);
    }

    /**
     * 业务异常
     *
     * @param e 异常信息
     * @return 异常结果
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        // 如果异常携带额外的数据
        if (e.getData() != null) {
            return Result.error(e.getCode(), e.getMessage(), e.getData());
        }

        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 系统异常
     *
     * @param e 异常信息
     * @return 异常结果
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        return Result.error(ResultCode.SYSTEM_ERROR.getCode(), ResultCode.SYSTEM_ERROR.getMsg(), e.getMessage());
    }
}