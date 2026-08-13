package com.tp.util;

import com.tp.common.PsychologyConstants;
import com.tp.common.ResultCode;
import com.tp.exception.BusinessException;
import org.springframework.util.StringUtils;

/**
 * 包名称：com.tp.util
 * 类名称：ConsultationSessionIdUtil
 * 类描述：咨询会话ID工具类
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/13
 */
public final class ConsultationSessionIdUtil {

    /**
     * 工具类禁止实例化
     */
    private ConsultationSessionIdUtil() {
    }

    /**
     * 解析咨询会话ID
     *
     * @param sessionId 会话ID，格式为session_数字或数字
     * @return 数据库会话ID
     */
    public static Long parse(String sessionId) {
        if (!StringUtils.hasText(sessionId)) {
            throw new BusinessException(ResultCode.PARAM_INVALID);
        }
        String value = sessionId.startsWith(PsychologyConstants.SESSION_ID_PREFIX)
                ? sessionId.substring(PsychologyConstants.SESSION_ID_PREFIX.length()) : sessionId;
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            throw new BusinessException(ResultCode.PARAM_INVALID);
        }
    }
}
