package org.kratos.kracart.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kratos.kracart.controller.CommonController;
import org.kratos.kracart.core.bean.AdminModuleParent;
import org.kratos.kracart.core.config.ConfigConstant;
import org.kratos.kracart.core.config.DesktopConstant;
import org.kratos.kracart.entity.Administrator;
import org.kratos.kracart.service.AdminAccessService;
import org.kratos.kracart.service.AdminService;
import org.kratos.kracart.utility.JsonUtils;
import org.kratos.kracart.vo.administrators.AdministratorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdministratorsController extends CommonController {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private AdminAccessService adminAccessService;
	
	@RequestMapping("/admin/ajax/administrators/list-administrators")
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
	
	@RequestMapping("/admin/ajax/administrators/get-accesses")
	@ResponseBody
	public List<AdminModuleParent> getAccesses(
				@RequestParam(defaultValue="", required=false) String aID, 
				@RequestParam(defaultValue="", required=false) String global,
				HttpServletRequest request) {
		List<AdminModuleParent> response = new ArrayList<AdminModuleParent>();
		adminAccessService.setResouceBundle(getResourceBundle("access", request));
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
	
	@RequestMapping("/admin/ajax/administrators/load-administrator")
	@ResponseBody
	public Map<String, Object> loadAdminstrator(@RequestParam String aID) {
		int id = StringUtils.hasLength(aID) ? Integer.parseInt(aID) : 0;
		Administrator admin = adminService.getAdministratorById(id);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", true);
		response.put("data", admin);
		return response;
	}
	
	@RequestMapping("/admin/ajax/administrators/save-administrator")
	@ResponseBody
	public Map<String, Object> save(AdministratorVO voAdmin, HttpServletRequest request) {
		String feedback = "";
		int result = adminService.saveAdministrator(voAdmin);
		boolean success = (result == 1);
		switch (result) {
		case 1:
			feedback = getMessage(request, "ms_success_action_performed");
			break;
		case -1:
			feedback = getMessage(request, "ms_error_action_not_performed");
			break;
		case -2:
			feedback = getMessage(request, "ms_error_username_already_exists");
			break;
		case -3:
			feedback = getMessage(request, "ms_error_email_format");
			break;
		case -4:
			feedback = getMessage(request, "ms_error_email_already_exists");
			break;
		}
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		response.put("feedback", feedback);
		return response;
	}
	
	@RequestMapping("/admin/ajax/administrators/delete-administrator")
	@ResponseBody
	public Map<String, Object> delete(String adminId, HttpServletRequest request) {
		return deleteAdministratorById(adminId, false, request);
	}
	
	@RequestMapping("/admin/ajax/administrators/delete-administrators")
	@ResponseBody
	public Map<String, Object> deleteMultiple(String batch, HttpServletRequest request) {
		return deleteAdministratorById(batch, true, request);
	}
	
	private Map<String, Object> deleteAdministratorById(String id, boolean isJson, HttpServletRequest request) {
		Object[] idArray = isJson ? JsonUtils.convertJsonStringToList(id).toArray() : new String[] {id};
		int result = adminService.deleteAdministrator(idArray);
		boolean success = (result == 1);
		String feedback = success ? getMessage(request, "ms_success_action_performed") : getMessage(request, "ms_error_action_not_performed");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		response.put("feedback", feedback);
		return response;
	}

}
