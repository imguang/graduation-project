package com.imguang.demo.web.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imguang.demo.web.common.exception.BaseException;
import com.imguang.demo.web.common.vo.Response;


public class BaseController {

	private static Logger log = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 基于 @ExceptionHandler 的异常统一处理
	 */
	@ExceptionHandler
	@ResponseBody
	public Response exp(HttpServletRequest request, Exception ex) {
		Response response = new Response();

		// 异常类型捕获与对应处理
		if (ex instanceof BaseException) {
			BaseException e = (BaseException) ex;
			String errMsg = null;
			if (errMsg == null)
				errMsg = getErrMsgChain(ex);
			response.failure(e.getCode(), errMsg);
			log.trace(getErrMsgChain(ex));
			log.trace("捕获的异常", ex);
		} else if (ex instanceof Exception) {
			response.failure("1", "未知异常");
			log.error("未捕获的异常", ex);
		}

		log.trace(response.toString());

		return response;
	}

	/**
	 * 取出 异常链 的信息
	 * @param ex
	 * @return
	 */
	private String getErrMsgChain(Exception ex) {
		Throwable throwable = ex;
		StringBuffer errMsgSbf = new StringBuffer();
		errMsgSbf.append(ex.getMessage());
		while (throwable.getCause() != null) {
			throwable = throwable.getCause();
			errMsgSbf.append("-");
			errMsgSbf.append(throwable.getMessage());
		}
		return errMsgSbf.toString();
	}
}
