package com.wulizhou.ct.log.autoconfigue.handle.bean;

import java.util.Date;

/**
 * Created by lizho on 2019/1/7.
 */
public class LogInfo {

    private String methodName;
    private String methodType;
    private String param;
    private String result;
    private long execTime;
    private Date createDate;
    
    public LogInfo() {
		super();
	}

	public LogInfo(String methodName, String methodType, String param, String result, long execTime, Date createDate) {
		super();
		this.methodName = methodName;
		this.methodType = methodType;
		this.param = param;
		this.result = result;
		this.execTime = execTime;
		this.createDate = createDate;
	}

	public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    public long getExecTime() {
		return execTime;
	}

	public void setExecTime(long execTime) {
		this.execTime = execTime;
	}

	public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
