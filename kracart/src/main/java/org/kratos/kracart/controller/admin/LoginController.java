package org.kratos.kracart.controller.admin;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kratos.kracart.controller.CommonController;
import org.kratos.kracart.service.AdminService;
import org.kratos.kracart.utility.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
public class LoginController extends CommonController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/admin/login")
	public ModelAndView index() {
		return new ModelAndView("admin/login");
	}
	
	@RequestMapping("/admin/login/process")
	public void process() {
		
	}
	
	@RequestMapping("/admin/login/get-password")
	@ResponseBody
	public Map<String, Object> getPassword(@RequestParam String emailAddress, HttpServletRequest request) {
		boolean error = false;
		String feedback = "";
				
		WebApplicationContext ctx = RequestContextUtils.getWebApplicationContext(request);
		Locale locale = RequestContextUtils.getLocale(request);
		Map<String, Object> body = new HashMap<String, Object>();
		
		if(!ValidatorUtils.validateEmail(emailAddress)) {
			error = true;
			feedback = ctx.getMessage("ms_error_wrong_email_address", null, locale);
		} else if(!adminService.validateEmail(emailAddress)) {
			error = true;
			feedback = ctx.getMessage("ms_error_email_not_exist", null, locale);
		}
		
		if(!error) {
			if(!adminService.resetPassword(emailAddress, request.getRemoteAddr(), locale)) {
				error = true;
				feedback = ctx.getMessage("ms_error_email_send_failure", null, locale);
			}
		}
		
		if(!error) {
			body.put("success", true);
			body.put("feedback", ctx.getMessage("ms_success_action_performed", null, RequestContextUtils.getLocale(request)));
		} else {
			body.put("success", false);
			body.put("feedback", feedback);
		}
		
		return body;
	}

}
