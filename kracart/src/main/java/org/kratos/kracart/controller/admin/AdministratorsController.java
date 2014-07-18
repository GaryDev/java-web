package org.kratos.kracart.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kratos.kracart.controller.CommonController;
import org.kratos.kracart.core.config.ConfigConstant;
import org.kratos.kracart.core.config.DesktopConstant;
import org.kratos.kracart.entity.Administrator;
import org.kratos.kracart.service.AdminService;
import org.kratos.kracart.service.ConfigurationService;
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
	private ConfigurationService configurationService;
	
	@RequestMapping("/admin/administrators/list-administrators")
	@ResponseBody
	public Map<String, Object> listAdministrators(
				@RequestParam(defaultValue="", required=false) String start, 
				@RequestParam(defaultValue="", required=false) String limit) {
		
		start = StringUtils.hasLength(start) ? start : "0";
		limit = StringUtils.hasLength(limit) ? limit : configurationService.getConfigurationValue(ConfigConstant.KEY_MAX_DISPLAY_SEARCH_RESULTS);
		List<Administrator> admins = adminService.getAdministartors(start, limit);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_TOTAL, adminService.getTotal());
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, admins);
		return response;
	}
	
	@RequestMapping("/admin/administrators/get-accesses")
	@ResponseBody
	public Map<String, Object> getAccesses(
				@RequestParam(defaultValue="", required=false) String aID, 
				@RequestParam(defaultValue="", required=false) String global) {
		int id = StringUtils.hasLength(aID) ? Integer.parseInt(aID) : 0;
		if(id > 0) {
			
		} else {
			boolean isGlobal = StringUtils.hasLength(global) && "on".equals(global);
			
		}
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		return response;
	}

}
