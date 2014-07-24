package org.kratos.kracart.controller;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.kratos.kracart.entity.Language;
import org.kratos.kracart.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

public class CommonController {
	
	@Autowired
	protected ConfigurationService configurationService;
	
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
	
	protected ServletContext getServletContext(HttpServletRequest request) {
		return getContext(request).getServletContext();
	}
	
	protected ResourceBundle getResourceBundle(String code, HttpServletRequest request) {
		String path = "lang.";
		if(StringUtils.hasLength(code)) {
			path += code + ".";
		}
		return ResourceBundle.getBundle(path + "messages", getLocale(request));
	}
	
	protected Language getCurrentLanguage(HttpServletRequest request) {
		return (Language) request.getAttribute("lang");
	}

}
