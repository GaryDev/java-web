package org.kratos.kracart.interceptor;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kratos.kracart.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

public class LocaleCustomizeInterceptor extends HandlerInterceptorAdapter {

	private String paramName;
	
	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	@Autowired
	private LanguageService languageService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		languageService.initialize();
		if(paramName != null) {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			if(localeResolver == null) {
				throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
			}
			Locale locale = null;
			String newLocale = request.getParameter(paramName);
			if(newLocale == null) {
				locale = localeResolver.resolveLocale(request);
				if(locale == null) {
					locale = Locale.getDefault();
				}
				newLocale = locale.toString();
			} else {
				locale = StringUtils.parseLocaleString(newLocale);
			}
			localeResolver.setLocale(request, response, locale);
			request.setAttribute("lang", languageService.getLanguage(newLocale));
			request.setAttribute("langs", languageService.getAllLanguage());
		}
		return true;
	}
	
	

}
