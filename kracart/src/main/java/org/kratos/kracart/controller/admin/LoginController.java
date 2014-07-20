package org.kratos.kracart.controller.admin;

import java.util.HashMap;
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
import org.springframework.web.servlet.ModelAndView;

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
		Map<String, Object> body = new HashMap<String, Object>();
		
		if(!ValidatorUtils.validateEmail(emailAddress)) {
			error = true;
			feedback = getMessage(request, "ms_error_wrong_email_address");
		} else if(!adminService.validateEmail(emailAddress)) {
			error = true;
			feedback = getMessage(request, "ms_error_email_not_exist");
		}
		
		if(!error) {
			if(!adminService.resetPassword(emailAddress, request.getRemoteAddr(), getLocale(request))) {
				error = true;
				feedback = getMessage(request, "ms_error_email_send_failure");
			}
		}
		
		if(!error) {
			body.put("success", true);
			body.put("feedback", getMessage(request, "ms_success_action_performed"));
		} else {
			body.put("success", false);
			body.put("feedback", feedback);
		}
		
		return body;
	}

}
