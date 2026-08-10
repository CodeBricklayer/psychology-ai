package com.tp.exception;

import com.tp.common.ResultCode;
import lombok.Getter;

/**
 * 业务异常类
 *
 * @author tanpeng
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 消息码
     */
    private final String code;

    /**
     * 消息
     *
     */
    private final String message;

    /**
     * 数据
     *
     */
    private final Object data;

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
        this.data = null;
    }
}
