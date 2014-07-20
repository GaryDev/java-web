package org.kratos.kracart.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.kratos.kracart.controller.CommonController;
import org.kratos.kracart.core.bean.AdminModuleParent;
import org.kratos.kracart.core.config.ConfigConstant;
import org.kratos.kracart.core.config.DesktopConstant;
import org.kratos.kracart.entity.Administrator;
import org.kratos.kracart.service.AdminAccessService;
import org.kratos.kracart.service.AdminService;
import org.kratos.kracart.service.ConfigurationService;
import org.kratos.kracart.vo.AdministratorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
public class AdministratorsController extends CommonController {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private AdminAccessService adminAccessService;
	@Autowired
	private ConfigurationService configurationService;
	
	@RequestMapping("/admin/administrators/list-administrators")
	@ResponseBody
	public Map<String, Object> listAdministrators(
				@RequestParam(defaultValue="", required=false) String start, 
				@RequestParam(defaultValue="", required=false) String limit) {
		
		start = StringUtils.hasLength(start) ? start : "0";
		limit = StringUtils.hasLength(limit) ? limit : configurationService.getConfigurationValue(ConfigConstant.KEY_MAX_DISPLAY_SEARCH_RESULTS);
		List<Administrator> admins = adminService.getAdministrators(start, limit);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_TOTAL, adminService.getTotal());
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, admins);
		return response;
	}
	
	@RequestMapping("/admin/administrators/get-accesses")
	@ResponseBody
	public List<AdminModuleParent> getAccesses(
				@RequestParam(defaultValue="", required=false) String aID, 
				@RequestParam(defaultValue="", required=false) String global,
				HttpServletRequest request) {
		List<AdminModuleParent> response = new ArrayList<AdminModuleParent>();
		Locale locale = RequestContextUtils.getLocale(request);
		ResourceBundle bundle = ResourceBundle.getBundle("messages_access", locale);
		adminAccessService.setResouceBundle(bundle);
		int id = StringUtils.hasLength(aID) ? Integer.parseInt(aID) : 0;
		if(id > 0) {
			Administrator admin = adminService.getAdministratorById(id);
			response = adminAccessService.getAdminModules(admin.getName());
		} else {
			boolean isGlobal = "on".equals(global);
			response = adminAccessService.getModules(isGlobal);
		}
		return response;
	}
	
	@RequestMapping("/admin/administrators/load-administrator")
	@ResponseBody
	public Map<String, Object> loadAdminstrator(@RequestParam String aID) {
		int id = StringUtils.hasLength(aID) ? Integer.parseInt(aID) : 0;
		Administrator admin = adminService.getAdministratorById(id);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", true);
		response.put("data", admin);
		return response;
	}
	
	@RequestMapping("/admin/administrators/save-administrator")
	@ResponseBody
	public Map<String, Object> save(AdministratorVO voAdmin, String modulesJSON, HttpServletRequest request) {
		WebApplicationContext ctx = RequestContextUtils.getWebApplicationContext(request);
		Locale locale = RequestContextUtils.getLocale(request);
		String feedback = "";
		int result = adminService.saveAdministrator(voAdmin);
		boolean success = (result == 1);
		switch (result) {
		case 1:
			feedback = ctx.getMessage("ms_success_action_performed", null, locale);
			break;
		case -1:
			feedback = ctx.getMessage("ms_error_action_not_performed", null, locale);
			break;
		case -2:
			feedback = ctx.getMessage("ms_error_username_already_exists", null, locale);
			break;
		case -3:
			feedback = ctx.getMessage("ms_error_email_format", null, locale);
			break;
		case -4:
			feedback = ctx.getMessage("ms_error_email_already_exists", null, locale);
			break;
		}
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		response.put("feedback", feedback);
		return response;
	}

}
