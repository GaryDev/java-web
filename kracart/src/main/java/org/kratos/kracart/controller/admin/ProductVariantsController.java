package org.kratos.kracart.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kratos.kracart.controller.CommonController;
import org.kratos.kracart.core.config.ConfigConstant;
import org.kratos.kracart.core.config.DesktopConstant;
import org.kratos.kracart.service.ProductVariantService;
import org.kratos.kracart.vo.productVariants.VariantsEntriesVO;
import org.kratos.kracart.vo.productVariants.VariantsGroupsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductVariantsController extends CommonController {
	
	@Autowired
	private ProductVariantService productVariantService;
	
	@RequestMapping("/admin/ajax/product-variants/list-product-variants")
	@ResponseBody
	public Map<String, Object> listProductVariants(
			@RequestParam(defaultValue="", required=false) String start, 
			@RequestParam(defaultValue="", required=false) String limit, HttpServletRequest request) {
		
		start = StringUtils.hasLength(start) ? start : "0";
		limit = StringUtils.hasLength(limit) ? limit : configurationService.getConfigurationValue(ConfigConstant.KEY_MAX_DISPLAY_SEARCH_RESULTS);
		int languageId = getCurrentLanguage(request).getId();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_TOTAL, productVariantService.getTotal(languageId));
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, productVariantService.getVariantsGroups(languageId, start, limit));
		return response;
	}
	
	@RequestMapping("/admin/ajax/product-variants/list-product-variants-entries")
	@ResponseBody
	public Map<String, Object> listProductVariantsEntries(String groupsId, HttpServletRequest request) {
		int id = StringUtils.hasLength(groupsId) ? Integer.parseInt(groupsId) : 0;
		int languageId = getCurrentLanguage(request).getId();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, productVariantService.getVariantsEntries(id, languageId));
		return response;
	}
	
	@RequestMapping("/admin/ajax/product-variants/load-product-variant")
	@ResponseBody
	public Map<String, Object> loadProductVariant(String groupsId, HttpServletRequest request) {
		int id = StringUtils.hasLength(groupsId) ? Integer.parseInt(groupsId) : 0;
		Map<String, Object> data = productVariantService.loadProductVariant(id);
		boolean success = (data != null);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		response.put("data", data);
		return response;
	}
	
	@RequestMapping("/admin/ajax/product-variants/load-product-variants-entry")
	@ResponseBody
	public Map<String, Object> loadProductVariantEntry(String valuesId, HttpServletRequest request) {
		int id = StringUtils.hasLength(valuesId) ? Integer.parseInt(valuesId) : 0;
		Map<String, Object> data = productVariantService.loadProductEntry(id);
		boolean success = (data != null);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		response.put("data", data);
		return response;
	}
	
	@RequestMapping("/admin/ajax/product-variants/save-product-variant")
	@ResponseBody
	public Map<String, Object> saveProductVariant(VariantsGroupsVO data, HttpServletRequest request) {
		boolean success = productVariantService.saveProductVariant(data);
		String feedback = success ? getMessage(request, "ms_success_action_performed") : getMessage(request, "ms_error_action_not_performed");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		response.put("feedback", feedback);
		return response;
	}
	
	@RequestMapping("/admin/ajax/product-variants/save-product-variants-entry")
	@ResponseBody
	public Map<String, Object> saveProductVariantEntry(String groupsId, VariantsEntriesVO data, HttpServletRequest request) {
		int id = StringUtils.hasLength(groupsId) ? Integer.parseInt(groupsId) : 0;
		boolean success = productVariantService.saveProductVariantEntry(id, data);
		String feedback = success ? getMessage(request, "ms_success_action_performed") : getMessage(request, "ms_error_action_not_performed");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		response.put("feedback", feedback);
		return response;
	}
	
}
