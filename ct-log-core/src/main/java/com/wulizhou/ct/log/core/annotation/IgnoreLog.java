package com.wulizhou.ct.log.core.annotation;

import java.lang.annotation.*;

/**
 * 忽略操作日志记录标识<br/>
 * 在类上增加此注解时，类中所有的方法调用不进行日志记录<br/>
 * 在方法上增加此注解时，该方法调用不进行日志记录
 * @author Administrator
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface IgnoreLog {

}
