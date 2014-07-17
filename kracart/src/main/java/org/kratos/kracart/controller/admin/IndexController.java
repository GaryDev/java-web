package org.kratos.kracart.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.kratos.kracart.controller.CommonController;
import org.kratos.kracart.core.config.ConfigConstant;
import org.kratos.kracart.entity.Administrator;
import org.kratos.kracart.entity.Language;
import org.kratos.kracart.service.AdminAccessService;
import org.kratos.kracart.service.AdminService;
import org.kratos.kracart.service.ConfigurationService;
import org.kratos.kracart.service.DesktopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
public class IndexController extends CommonController {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private AdminAccessService adminAccessService;
	@Autowired
	private DesktopService desktopService;
	@Autowired
	private ConfigurationService configurationService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping({"/admin", "/admin/index"})
	public ModelAndView index(@RequestParam(defaultValue="", required=false) String module, 
							  @RequestParam(defaultValue="", required=false) String action,
							  HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		if(adminService.IsLogon()) {
			data.put("pageSize", configurationService.getConfigurationValue(ConfigConstant.KEY_MAX_DISPLAY_SEARCH_RESULTS));
			desktopService.loadDesktopConstants(data);
			desktopService.loadLanguages(data, (List<Language>) request.getAttribute("langs"));
			Locale locale = RequestContextUtils.getLocale(request);
			ResourceBundle desktopBundle = ResourceBundle.getBundle("messages_desktop", locale);
			desktopService.loadLanguageDefinition(data, desktopBundle);
		} else {
			return new ModelAndView("redirect:/admin/login");
		}
		return new ModelAndView("admin/index", data);
	}
	
	@RequestMapping("/admin/index/desktop")
	public ModelAndView desktop() {
		Administrator admin = new Administrator();
		admin.setName("admin");	// TODO: From session
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		desktopService.initialize(admin);
		data.put("username", admin.getName());
		data.put("launchers", desktopService.getLaunchers());
		data.put("styles", desktopService.getStyles());
		
		adminAccessService.initialize(admin.getName());
		data.put("modules", adminAccessService.getModules());
		data.put("output", adminAccessService.getOutputModule());
		return new ModelAndView("admin/desktop", data);
	}
	
}
