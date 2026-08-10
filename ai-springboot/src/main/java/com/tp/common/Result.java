package com.tp.common;

import lombok.Data;

/**
 * 包名称：com.tp.common
 * 类名称：Result
 * 类描述：通用结果类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 9:20
 */
@Data
public class Result<T> {

    /**
     * 消息码
     */
    private String code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 结果
     */
    private T data;

    /**
     * 消息成功
     *
     * @param data 结果数据
     * @param <T>  结果数据类型
     * @return 结果
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<T>();
        result.setData(data);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        return result;
    }

    /**
     * 消息成功
     *
     * @param <T> 结果数据类型
     * @return 结果
     */
    public static <T> Result<T> ok() {
        return ok(null);
    }

    /**
     * 消息失败
     *
     * @param code 结果码
     * @param msg  结果消息
     * @param data 结果数据
     * @param <T>  结果数据类型
     * @return 结果
     */
    public static <T> Result<T> error(String code, String msg, T data) {
        Result<T> result = new Result<T>();
        result.setMsg(msg);
        result.setCode(code);
        result.setData(data);
        return result;
    }

    /**
     * 消息失败
     *
     * @param <T> 结果数据类型
     * @return 结果
     */
    public static <T> Result<T> error() {
        return error(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMsg(), null);
    }

    /**
     *
     * @param msg 结果消息
     * @param <T> 结果数据类型
     * @return 结果
     */
    public static <T> Result<T> error(String msg) {
        return error(ResultCode.FAIL.getCode(), msg, null);
    }

    /**
     * 消息失败
     *
     * @param code 结果码
     * @param msg  结果消息
     * @param <T>  结果数据类型
     * @return 结果
     */
    public static <T> Result<T> error(String code, String msg) {
        return error(code, msg, null);
    }

    /**
     * 消息失败
     *
     * @param data 结果数据
     * @param <T>  结果数据类型
     * @return 结果
     */
    public static <T> Result<T> error(T data) {
        return error(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMsg(), data);
    }
}