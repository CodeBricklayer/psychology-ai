package com.tp.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 包名称：com.tp.common
 * 枚举名称：ResultCode
 * 枚举描述：通用结果码枚举
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/10 9:30
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /**
     * 操作成功
     */
    SUCCESS("200", "操作成功"),

    /**
     * 操作失败
     */
    FAIL("-1", "操作失败"),

    /**
     * 暂未登录或token已经过期
     */
    UNAUTHORIZED("401", "暂未登录或token已经过期"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR("500", "系统错误"),

    // 参数相关错误
    /**
     * 参数错误
     */
    PARAM_ERROR("400", "参数错误"),
    /**
     * 缺少必要参数
     */
    PARAM_MISSING("4001", "缺少必要参数"),
    /**
     * 参数格式不正确
     */
    PARAM_INVALID("4002", "参数格式不正确"),

    // 文件操作相关错误
    /**
     * 文件不存在
     */
    FILE_NOT_FOUND("5001", "文件不存在"),
    /**
     * 文件上传失败
     */
    FILE_UPLOAD_FAILED("5002", "文件上传失败"),
    /**
     * 文件删除失败
     */
    FILE_DELETE_FAILED("5003", "文件删除失败"),
    /**
     * 文件大小超过限制
     */
    FILE_SIZE_EXCEEDED("5004", "文件大小超过限制"),
    /**
     * 不支持的文件类型
     */
    FILE_TYPE_NOT_SUPPORTED("5005", "不支持的文件类型"),
    /**
     * 文件名不合法
     */
    FILE_NAME_INVALID("5006", "文件名不合法"),
    /**
     * 文件内容不合法
     */
    FILE_CONTENT_INVALID("5007", "文件内容不合法"),
    /**
     * 文件保存失败
     */
    FILE_SAVE_FAILED("5008", "文件保存失败"),

    // 业务相关错误
    /**
     * 业务处理失败
     */
    BUSINESS_ERROR("6000", "业务处理失败"),
    /**
     * 用户名已存在
     */
    ACCOUNT_SAME("6001", "用户名已存在"),
    /**
     * 邮箱已存在
     */
    EMAIL_SAME("6002", "邮箱已存在"),
    /**
     * 用户或密码错误
     */
    USER_OR_PASSWORD_ERROR("6003", "用户或密码错误"),
    /**
     * 密码不一致
     */
    PASSWORD_NOT_MATCH("6004", "密码不一致"),
    /**
     * 用户类型无效
     */
    USER_TYPE_INVALID("6005", "用户类型无效"),
    /**
     * 用户不存在
     */
    USER_NOT_FOUND("6006", "用户不存在"),

    // token相关错误
    /**
     * token无效
     */
    TOKEN_INVALID("A0230", "token无效"),
    /**
     * token已过期
     */
    TOKEN_EXPIRED("A0231", "token已过期"),
    /**
     * token已加入黑名单
     */
    TOKEN_BLOCKED("A0232", "token已加入黑名单"),
    /**
     * token已被禁止访问
     */
    TOKEN_ACCESS_FORBIDDEN("A0233", "token已被禁止访问"),
    /**
     * 访问权限异常
     */
    AUTHORIZED_ERROR("A0300", "访问权限异常"),
    /**
     * 访问未授权
     */
    ACCESS_UNAUTHORIZED("A0301", "访问未授权");

    /**
     * 结果码
     */
    private final String code;

    /**
     * 结果消息
     */
    private final String msg;
}
