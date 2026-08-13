package com.tp.common;

import com.tp.exception.BusinessException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
@Slf4j
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
     * 处理请求参数约束异常
     *
     * @param e 异常信息
     * @return 异常结果
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMsg = e.getConstraintViolations().iterator().next().getMessage();
        return Result.error(ResultCode.PARAM_ERROR.getCode(), ResultCode.PARAM_ERROR.getMsg(), errorMsg);
    }

    /**
     * 处理请求参数格式异常
     *
     * @param e 异常信息
     * @return 异常结果
     */
    @ExceptionHandler({HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class})
    public Result<?> handleRequestParameterException(Exception e) {
        return Result.error(ResultCode.PARAM_INVALID.getCode(), ResultCode.PARAM_INVALID.getMsg());
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
     * 处理访问权限异常
     *
     * @param e 异常信息
     * @return 异常结果
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<?> handleAccessDeniedException(AccessDeniedException e) {
        return Result.error(ResultCode.ACCESS_UNAUTHORIZED.getCode(),
                ResultCode.ACCESS_UNAUTHORIZED.getMsg());
    }

    /**
     * 系统异常
     *
     * @param e 异常信息
     * @return 异常结果
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error(ResultCode.SYSTEM_ERROR.getCode(), ResultCode.SYSTEM_ERROR.getMsg());
    }
}
