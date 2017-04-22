package com.imguang.demo.web.common.vo;

public class Response {

	private String code;

	private String msg;

	private Object data;

	public Response success() {
		code = "0";
		return this;
	}

	public Response success(BaseVO vo) {
		return success(vo.toData());
	}

	public Response success(Object data) {
		code = "0";
		this.data = data;
		return this;
	}

	public Response failure() {
		return failure("1");
	}

	public Response failure(String code) {
		return failure(code, "");
	}

	public Response failure(String code, String msg) {
		this.code = code;
		this.msg = msg;
		return this;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public Object getData() {
		return data;
	}

	@Override
	public String toString() {
		return "Response [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

}
