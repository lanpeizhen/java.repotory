package com.aek.web.adapter.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Utf8;
import com.mb.common.util.StringUtils;
import com.mb.common.util.SystemResource;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 * 免检地址（一批无需会话检查的URL地址,在xml中配置,通过本属性的set方法注入）
	 */
	private List<String> uncheckUrls; 
	
	/**
	 * 请求前置处理器
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 不需要检查会话的请求,直接放行
	    return true;
		
	}
	
	// set方法注入免检地址
	public void setUncheckUrls(List<String> uncheckUrls) {
		this.uncheckUrls = uncheckUrls;
	}

}