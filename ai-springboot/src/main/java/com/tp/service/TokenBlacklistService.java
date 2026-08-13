package com.tp.service;

import java.util.Date;

/**
 * 包名称：com.tp.service
 * 接口名称：TokenBlacklistService
 * 接口描述：Token黑名单服务接口
 *
 * @author tanpeng
 * @version V4.0
 * @since 2026/8/13
 */
public interface TokenBlacklistService {

    /**
     * 将Token加入黑名单
     *
     * @param token     JWT Token
     * @param expiresAt Token过期时间
     */
    void block(String token, Date expiresAt);

    /**
     * 判断Token是否已加入黑名单
     *
     * @param token JWT Token
     * @return 是否已加入黑名单
     */
    boolean isBlocked(String token);
}
