package org.school.fee.support.utils;

public class Result {
	private String msg;
	private Object data;
	private String code;

	public Result() {
	}

	public Result(String msg, String code) {
		this.msg = msg;
		this.code = code;
	}

	public Result(String code, Object data) {
		this.code = code;
		this.data = data;
	}

	public Result(String msg, String code, Object data) {
		this.msg = msg;
		this.data = data;
		this.code = code;
	}

	public Result(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
