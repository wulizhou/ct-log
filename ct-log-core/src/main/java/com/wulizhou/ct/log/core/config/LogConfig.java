package com.wulizhou.ct.log.core.config;

public class LogConfig {

	/**
	 * 忽略Get方法
	 */
	private boolean ignoreGet = true;
	/**
	 * 使用简单方法名
	 */
	private boolean shortMethodName = true;

	public boolean isIgnoreGet() {
		return ignoreGet;
	}

	public void setIgnoreGet(boolean ignoreGet) {
		this.ignoreGet = ignoreGet;
	}

	public boolean isShortMethodName() {
		return shortMethodName;
	}

	public void setShortMethodName(boolean shortMethodName) {
		this.shortMethodName = shortMethodName;
	}

}
