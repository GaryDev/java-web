package org.kratos.kracart.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kratos.kracart.controller.CommonController;
import org.kratos.kracart.core.config.DesktopConstant;
import org.kratos.kracart.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImagesController extends CommonController {

	@Autowired
	private ImageService imageService;
	
	@RequestMapping("/admin/images/list-images")
	@ResponseBody
	public Map<String, Object> listImages(HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, 
				imageService.getImageList(getResourceBundle("images", request)));
		return response;
	}
	
	@RequestMapping("/admin/images/check-images")
	@ResponseBody
	public Map<String, Object> checkImages(HttpServletRequest request) {
		String root = getServletContext(request).getRealPath("/");
		int languageId = getCurrentLanguage(request).getId();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, imageService.countImages(root, languageId));
		return response;
	}
	
	@RequestMapping("/admin/images/get-imagegroups")
	@ResponseBody
	public Map<String, Object> getImageGroups(HttpServletRequest request) {
		int languageId = getCurrentLanguage(request).getId();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, imageService.getProductImageGroup(languageId));
		return response;
	}
	
	@RequestMapping("/admin/images/list-imagesresize-result")
	@ResponseBody
	public Map<String, Object> listImageResizeResult(HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, null);
		return response;
	}
}
