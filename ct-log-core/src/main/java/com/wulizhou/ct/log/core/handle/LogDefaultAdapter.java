package com.wulizhou.ct.log.core.handle;

import com.wulizhou.ct.log.core.handle.bean.LogInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Log处理默认适配器 Created by lizho on 2019/1/7.
 */
public class LogDefaultAdapter implements LogHandle {
	
	private static final Log logger = LogFactory.getLog(LogDefaultAdapter.class);
	
	@Override
	public void notice(LogInfo log, HttpServletRequest request) {
		// 输出日志
		logger.info(String.format("[%s]请求方法(%s)，参数信息：%s，执行时间%sms", log.getMethodType(), log.getMethodName(), log.getParam(), log.getExecTime()));
		// 保存日志
		save(log, request);
	}
	
	protected void save(LogInfo log, HttpServletRequest request) {}

}
