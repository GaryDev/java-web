package org.kratos.kracart.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kratos.kracart.controller.CommonController;
import org.kratos.kracart.core.config.ConfigConstant;
import org.kratos.kracart.core.config.DesktopConstant;
import org.kratos.kracart.service.ProductVariantService;
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
	
}
