package org.kratos.kracart.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kratos.kracart.controller.CommonController;
import org.kratos.kracart.core.config.ConfigConstant;
import org.kratos.kracart.core.config.DesktopConstant;
import org.kratos.kracart.entity.Customer;
import org.kratos.kracart.entity.Newsletter;
import org.kratos.kracart.service.NewsletterService;
import org.kratos.kracart.utility.JsonUtils;
import org.kratos.kracart.vo.newsletters.NewsletterVO;
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
	
	@RequestMapping("/admin/newsletters/list-log")
	@ResponseBody
	public Map<String, Object> listNewsletterLogs(
			@RequestParam(defaultValue="", required=false) String start, 
			@RequestParam(defaultValue="", required=false) String limit, 
			String newsletterId, HttpServletRequest request) {
		
		start = StringUtils.hasLength(start) ? start : "0";
		limit = StringUtils.hasLength(limit) ? limit : configurationService.getConfigurationValue(ConfigConstant.KEY_MAX_DISPLAY_SEARCH_RESULTS);
		int id = StringUtils.hasLength(newsletterId) ? Integer.parseInt(newsletterId) : 0;
		String contextName = getServletContext(request).getServletContextName();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_TOTAL, newsletterService.getTotalLogs(id));
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, newsletterService.getNewsletterLogs(contextName, id, start, limit));
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
	public Map<String, Object> saveNewsletter(NewsletterVO data, HttpServletRequest request) {
		boolean success = newsletterService.saveNewsletter(data);
		String feedback = success ? getMessage(request, "ms_success_action_performed") : getMessage(request, "ms_error_action_not_performed");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		response.put("feedback", feedback);
		return response;
	}
	
	@RequestMapping("/admin/newsletters/load-newsletter")
	@ResponseBody
	public Map<String, Object> loadNewsletter(String newsletterId, HttpServletRequest request) {
		int id = StringUtils.hasLength(newsletterId) ? Integer.parseInt(newsletterId) : 0;
		Newsletter data = newsletterService.loadNewsletter(id);
		boolean success = (data != null);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		response.put("data", data);
		return response;
	}
	
	@RequestMapping("/admin/newsletters/delete-newsletter")
	@ResponseBody
	public Map<String, Object> delete(String newsletterId, HttpServletRequest request) {
		return deleteNewsletterById(newsletterId, false, request);
	}
	
	@RequestMapping("/admin/newsletters/delete-newsletters")
	@ResponseBody
	public Map<String, Object> deleteMultiple(String batch, HttpServletRequest request) {
		return deleteNewsletterById(batch, true, request);
	}
	
	private Map<String, Object> deleteNewsletterById(String id, boolean isJson, HttpServletRequest request) {
		String[] idArray = isJson ? JsonUtils.convertJsonStringToList(id).toArray(new String[0]) : new String[] {id};
		int result = newsletterService.deleteNewsletter(idArray);
		boolean success = (result == 1);
		String feedback = success ? getMessage(request, "ms_success_action_performed") : getMessage(request, "ms_error_action_not_performed");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		response.put("feedback", feedback);
		return response;
	}
	
	@RequestMapping("/admin/newsletters/get-newsletters-confirmation")
	@ResponseBody
	public Map<String, Object> getNewslettersConfirmation(String newsletterId, HttpServletRequest request) {
		int id = StringUtils.hasLength(newsletterId) ? Integer.parseInt(newsletterId) : 0;
		Newsletter email = newsletterService.loadNewsletter(id);
		List<Customer> recipients = newsletterService.getNewsletterRecipients(id);
		String totalMessage = getMessage(request, "newsletter_newsletter_total_recipients", new Object[]{ recipients.size() });
		String confirmation = newsletterService.buildConfirmationMessage(email, totalMessage);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", true);
		response.put("execute", recipients.size() > 0);
		response.put("confirmation", confirmation);
		return response;
	}
	
	@RequestMapping("/admin/newsletters/get-emails-confirmation")
	@ResponseBody
	public Map<String, Object> getEmailsConfirmation(String newsletterId, String batch, HttpServletRequest request) {
		int id = StringUtils.hasLength(newsletterId) ? Integer.parseInt(newsletterId) : 0;
		List<String> customerId = JsonUtils.convertJsonStringToList(batch);
		String confirmation = "";
		if(customerId.size() > 0) {
			Newsletter email = newsletterService.loadNewsletter(id);
			List<Customer> customers = newsletterService.getEmailRecipients(id, customerId);
			String totalMessage = getMessage(request, "newsletter_newsletter_total_recipients", new Object[]{ customers.size() });
			confirmation = newsletterService.buildConfirmationMessage(email, totalMessage);
		}
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", true);
		response.put("confirmation", confirmation);
		return response;
	}
	
	@RequestMapping("/admin/newsletters/get-emails-audience")
	@ResponseBody
	public Map<String, Object> getEmailsAudience(HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, 
				newsletterService.getEmailsAudience(getResourceBundle("newsletters", request)));
		return response;
	}
	
	@RequestMapping("/admin/newsletters/send-newsletters")
	@ResponseBody
	public Map<String, Object> sendNewsletters(String newsletterId, HttpServletRequest request) {
		return sendAction(newsletterId, null, request);
	}
	
	@RequestMapping("/admin/newsletters/send-emails")
	@ResponseBody
	public Map<String, Object> sendEmails(String newsletterId, String batch, HttpServletRequest request) {
		return sendAction(newsletterId, batch, request);
	}
	
	private Map<String, Object> sendAction(String newsletterId, String batch, HttpServletRequest request) {
		int id = StringUtils.hasLength(newsletterId) ? Integer.parseInt(newsletterId) : 0;
		List<String> customerId = null;
		if(StringUtils.hasLength(batch)) {
			customerId = JsonUtils.convertJsonStringToList(batch);
		}
		boolean success = newsletterService.sendEmails(id, customerId);
		String feedback = success ? getMessage(request, "ms_success_action_performed") : getMessage(request, "ms_error_action_not_performed");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		response.put("feedback", feedback);
		return response;
	}

}
