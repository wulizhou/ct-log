package com.wulizhou.ct.log.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Log {

	/**
	 * 操作说明，默认指方法名
	 * @return
	 */
	String value() default "";
	
	/**
	 * 操作类型
	 * @return
	 */
	String type() default "";
	
	/**
	 * 不做记录参数
	 * @return
	 */
	String[] ignores() default {};
	
}
