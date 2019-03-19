package com.wulizhou.ct.log.core.handle;


import com.wulizhou.ct.log.core.handle.bean.LogInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志处理器
 * Created by lizho on 2019/1/7.
 */
public interface LogHandle {

    /**
     * 日志通知，每次日志系统监听到的操作信息都会通知
     * @param log
     * @param request
     */
    public void notice(LogInfo log, HttpServletRequest request);

}
