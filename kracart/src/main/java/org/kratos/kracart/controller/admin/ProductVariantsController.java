package org.kratos.kracart.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kratos.kracart.controller.CommonController;
import org.kratos.kracart.core.config.ConfigConstant;
import org.kratos.kracart.core.config.DesktopConstant;
import org.kratos.kracart.service.ProductVariantService;
import org.kratos.kracart.utility.CommonUtils;
import org.kratos.kracart.utility.JsonUtils;
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
	
	@RequestMapping("/admin/ajax/product-variants/delete-product-variant")
	@ResponseBody
	public Map<String, Object> deleteProductVariant(String groupsId, HttpServletRequest request) {
		boolean success = true;
		String feedback = "";
		int gId = StringUtils.hasLength(groupsId) ? Integer.parseInt(groupsId) : 0;
		int totalProduct = productVariantService.getProductVariantCount(gId);
		if(totalProduct > 0) {
			success = false;
			feedback = getMessage(request, "delete_error_variant_group_in_use", new Object[]{ totalProduct });
		} else {
			success = productVariantService.deleteProductVariant(gId);
			feedback = success ? getMessage(request, "ms_success_action_performed") : getMessage(request, "ms_error_action_not_performed");
		}
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
	
	@RequestMapping("/admin/ajax/product-variants/delete-product-variants-entry")
	@ResponseBody
	public Map<String, Object> deleteProductVariantEntry(String groupsId, String valuesId, HttpServletRequest request) {
		return deleteProductVariantEntryById(groupsId, valuesId, false, request);
	}
	
	@RequestMapping("/admin/ajax/product-variants/delete-product-variants-entries")
	@ResponseBody
	public Map<String, Object> deleteProductVariantEntries(String groupsId, String batch, HttpServletRequest request) {
		return deleteProductVariantEntryById(groupsId, batch, true, request);
	}
	
	private Map<String, Object> deleteProductVariantEntryById(String groupsId, String id, boolean isJson, HttpServletRequest request) {
		boolean success = true;
		String feedback = "";
		int gId = StringUtils.hasLength(groupsId) ? Integer.parseInt(groupsId) : 0;
		String[] idArray = isJson ? JsonUtils.convertJsonStringToList(id).toArray(new String[0]) : new String[] {id};
		int languageId = getCurrentLanguage(request).getId();
		List<String> entryData = productVariantService.getEntryData(idArray, languageId);
		if(entryData.size() > 0) {
			success = false;
			if(isJson) {
				feedback = getMessage(request, "batch_delete_error_group_entries_in_use");
				feedback += "<p>" + CommonUtils.listToString(entryData, ",", true) + "</p>";
			} else {
				feedback = getMessage(request, "delete_error_group_entry_in_use", new Object[]{ entryData.size() });
			}
		} else {
			success = productVariantService.deleteProductVariantEntry(gId, idArray);
			feedback = success ? getMessage(request, "ms_success_action_performed") : getMessage(request, "ms_error_action_not_performed");
		}
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("success", success);
		response.put("feedback", feedback);
		return response;
	}
	
}
