package org.kratos.kracart.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

public class CommonController {
	
	protected String getMessage(HttpServletRequest request, String code) {
		return getMessage(request, code, null);
	}
	
	protected String getMessage(HttpServletRequest request, String code, Object[] args) {
		return getContext(request).getMessage(code, args, getLocale(request));
	}
	
	protected Locale getLocale(HttpServletRequest request) {
		return RequestContextUtils.getLocale(request);
	}
	
	protected WebApplicationContext getContext(HttpServletRequest request) {
		return RequestContextUtils.getWebApplicationContext(request);
	}

}
