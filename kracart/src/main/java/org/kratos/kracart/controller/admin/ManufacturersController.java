package org.kratos.kracart.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kratos.kracart.controller.CommonController;
import org.kratos.kracart.core.config.ConfigConstant;
import org.kratos.kracart.core.config.DesktopConstant;
import org.kratos.kracart.service.ManufacturerService;
import org.kratos.kracart.vo.manufacturers.ManufacturerGeneralVO;
import org.kratos.kracart.vo.manufacturers.ManufacturerMetaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ManufacturersController extends CommonController {
	
	@Autowired
	private ManufacturerService manufacturerService;

	@RequestMapping("/admin/manufacturers/list-manufacturers")
	@ResponseBody
	public Map<String, Object> listManufacturers(
			@RequestParam(defaultValue="", required=false) String start, 
			@RequestParam(defaultValue="", required=false) String limit, HttpServletRequest request) {
		
		start = StringUtils.hasLength(start) ? start : "0";
		limit = StringUtils.hasLength(limit) ? limit : configurationService.getConfigurationValue(ConfigConstant.KEY_MAX_DISPLAY_SEARCH_RESULTS);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_TOTAL, manufacturerService.getTotal());
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, manufacturerService.getManufacturers(start, limit));
		return response;
	}
	
	@RequestMapping(value = "/admin/manufacturers/save-manufacturer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveManufacturer(
			ManufacturerGeneralVO general, 
			ManufacturerMetaVO meta, HttpServletRequest request) {
		boolean success = true;
		String feedback = success ? getMessage(request, "ms_success_action_performed") : getMessage(request, "ms_error_action_not_performed");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		response.put("feedback", feedback);
		return response;
	}
	
}
