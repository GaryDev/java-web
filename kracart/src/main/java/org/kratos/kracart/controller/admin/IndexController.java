package org.kratos.kracart.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.kratos.kracart.controller.CommonController;
import org.kratos.kracart.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController extends CommonController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping({"/admin", "/admin/index"})
	public ModelAndView index(@RequestParam(defaultValue="", required=false) String module, 
							  @RequestParam(defaultValue="", required=false) String action,
							  HttpServletRequest request) {
		if(adminService.IsLogon()) {
			
		} else {
			return new ModelAndView("redirect:/admin/login");
		}
		return new ModelAndView("admin/index");
	}
	
}
