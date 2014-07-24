package org.kratos.kracart.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kratos.kracart.controller.CommonController;
import org.kratos.kracart.core.config.ConfigConstant;
import org.kratos.kracart.core.config.DesktopConstant;
import org.kratos.kracart.service.NewsletterService;
import org.kratos.kracart.vo.NewsletterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NewslettersController extends CommonController {
	
	@Autowired
	private NewsletterService newsletterService;
	
	@RequestMapping("/admin/newsletters/list-newsletters")
	@ResponseBody
	public Map<String, Object> listNewsletters(
			@RequestParam(defaultValue="", required=false) String start, 
			@RequestParam(defaultValue="", required=false) String limit, HttpServletRequest request) {
		
		start = StringUtils.hasLength(start) ? start : "0";
		limit = StringUtils.hasLength(limit) ? limit : configurationService.getConfigurationValue(ConfigConstant.KEY_MAX_DISPLAY_SEARCH_RESULTS);
		String contextName = getServletContext(request).getServletContextName();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_TOTAL, newsletterService.getTotal());
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, newsletterService.getNewsletters(contextName, start, limit));
		return response;
	}
	
	@RequestMapping("/admin/newsletters/get-modules")
	@ResponseBody
	public Map<String, Object> getModules(HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, 
				newsletterService.getNewsletterModules(getResourceBundle("newsletters", request)));
		return response;
	}
	
	@RequestMapping("/admin/newsletters/save-newsletter")
	@ResponseBody
	public Map<String, Object> save(NewsletterVO data, HttpServletRequest request) {
		boolean success = newsletterService.saveNewsletter(data);
		String feedback = success ? getMessage(request, "ms_success_action_performed") : getMessage(request, "ms_error_action_not_performed");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		response.put("feedback", feedback);
		return response;
	}

}
