package com.wulizhou.ct.log.autoconfigue.aop;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wulizhou.ct.log.autoconfigue.annotation.Log;
import com.wulizhou.ct.log.autoconfigue.config.LogConfig;
import com.wulizhou.ct.log.autoconfigue.handle.LogHandle;
import com.wulizhou.ct.log.autoconfigue.handle.bean.LogInfo;
import com.wulizhou.ct.log.autoconfigue.util.CollectionUtil;
import com.wulizhou.ct.log.autoconfigue.util.ObjectUtils;
import com.wulizhou.ct.log.autoconfigue.util.WebUtil;

@Aspect
public class LogCenter {

	private LogConfig logConfig;

	private LogHandle logHandle;

	private Executor executor;
	
	/**
	 * 排除用户自定义不进行操作记录的类或方法
	 */
	@Pointcut("!(@within(com.wulizhou.ct.log.autoconfigue.annotation.IgnoreLog) || @annotation(com.wulizhou.ct.log.autoconfigue.annotation.IgnoreLog))")
	public void needLog(){}
	
	/**
	 * 核心实现方法
	 * 
	 * @param point
	 * @param mapping
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(mapping) && needLog()")
	public Object doAroundAdvice(ProceedingJoinPoint point, RequestMapping mapping) throws Throwable {
		return operationHandle(point);
	}
	
	@Around("@annotation(mapping) && needLog()")
	public Object doAroundAdvice(ProceedingJoinPoint point, GetMapping mapping) throws Throwable {
		return operationHandle(point);
	}
	
	@Around("@annotation(mapping) && needLog()")
	public Object doAroundAdvice(ProceedingJoinPoint point, PostMapping mapping) throws Throwable {
		return operationHandle(point);
	}
	
	@Around("@annotation(mapping) && needLog()")
	public Object doAroundAdvice(ProceedingJoinPoint point, PutMapping mapping) throws Throwable {
		return operationHandle(point);
	}
	
	@Around("@annotation(mapping) && needLog()")
	public Object doAroundAdvice(ProceedingJoinPoint point, DeleteMapping mapping) throws Throwable {
		return operationHandle(point);
	}
	
	@Around("@annotation(mapping) && needLog()")
	public Object doAroundAdvice(ProceedingJoinPoint point, PatchMapping mapping) throws Throwable {
		return operationHandle(point);
	}
	
	private Object operationHandle(ProceedingJoinPoint point) throws Throwable {

		long start = System.currentTimeMillis(); 	// 时间统计
		Object proceed = point.proceed(); 			// 执行方法
		long end = System.currentTimeMillis();

		// request必须在当前线程方能获取
		HttpServletRequest request = WebUtil.getRequest();
		// 记录信息
		executor.execute(() -> {

			// 设置为get方法不记录且当前为get方法
			String methodType = request.getMethod();
			if (RequestMethod.GET.name().equals(methodType) && logConfig.isIgnoreGet()) {
				return;
			}

			// 获取方法相关信息
			LogInfo info = new LogInfo();
			info.setCreateDate(new Date());
			info.setExecTime(end - start);
			MethodSignature signature = (MethodSignature) point.getSignature();

			// 获取参数信息
			String[] params = signature.getParameterNames();
			Object[] args = point.getArgs();
			Map<String, Object> paramInfo = new LinkedHashMap<>();
			for (int i = 0; i < params.length; i++) { 	// 参数名参数值对应
				// 忽略特殊参数
				if (args[i] instanceof HttpServletRequest
						|| args[i] instanceof HttpServletResponse) {
					continue;
				}
				paramInfo.put(params[i], args[i]); 		// args[i]可能为null
			}

			// 获取自定义日志信息
			Log log = signature.getMethod().getAnnotation(Log.class);
			if (null != log) {
				if (StringUtils.isNotBlank(log.value())) { 		// 自定义methodName
					info.setMethodName(log.value());
				}
				if (StringUtils.isNotBlank(log.type())) { 		// 自定义methodType
					info.setMethodType(log.type());
				}
				if (CollectionUtil.isNotEmpty(log.ignores())) { // 设置了不记录参数，进行过滤
					info.setParam(ObjectUtils.object2String(paramInfo, log.ignores()));
				}
			}

			// 未自定义方法名
			if (StringUtils.isBlank(info.getMethodName())) {
				// 获取方法名
				String methodName = signature.getName();
				if (!logConfig.isShortMethodName()) { 	// 获取全限定方法名
					methodName = signature.getDeclaringTypeName() + "." + methodName;
				}
				info.setMethodName(methodName);
			}

			// 未自定义方法类型
			if (StringUtils.isBlank(info.getMethodType())) {
				info.setMethodType(methodType);
			}
			
			// 未自定义记录参数信息
			if (StringUtils.isBlank(info.getParam())) {
				info.setParam(ObjectUtils.object2String(paramInfo, null));
			}
			
			// 未自定义记录结果信息
			if (StringUtils.isBlank(info.getResult())) {
				info.setResult(ObjectUtils.object2String(proceed, null));
			}

			logHandle.notice(info, request);
		});

		return proceed;
	}

	public LogConfig getLogConfig() {
		return logConfig;
	}

	public void setLogConfig(LogConfig logConfig) {
		this.logConfig = logConfig;
	}

	public LogHandle getLogHandle() {
		return logHandle;
	}

	public void setLogHandle(LogHandle logHandle) {
		this.logHandle = logHandle;
	}

	public Executor getExecutor() {
		return executor;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}
	
}
