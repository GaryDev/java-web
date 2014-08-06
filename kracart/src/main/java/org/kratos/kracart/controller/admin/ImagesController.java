package org.kratos.kracart.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.kratos.kracart.controller.CommonController;
import org.kratos.kracart.core.config.DesktopConstant;
import org.kratos.kracart.service.ImageService;
import org.kratos.kracart.utility.JsonUtils;
import org.kratos.kracart.vo.images.ImageCounterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImagesController extends CommonController {

	@Autowired
	private ImageService imageService;
	
	@RequestMapping("/admin/ajax/images/list-images")
	@ResponseBody
	public Map<String, Object> listImages(HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, 
				imageService.getImageList(getResourceBundle("images", request)));
		return response;
	}
	
	@RequestMapping("/admin/ajax/images/check-images")
	@ResponseBody
	public Map<String, Object> checkImages(HttpServletRequest request) {
		int languageId = getCurrentLanguage(request).getId();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, imageService.countImages(getRootPath(request), languageId));
		return response;
	}
	
	@RequestMapping("/admin/ajax/images/get-imagegroups")
	@ResponseBody
	public Map<String, Object> getImageGroups(HttpServletRequest request) {
		int languageId = getCurrentLanguage(request).getId();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(DesktopConstant.EXT_JSON_READER_ROOT, imageService.getProductImageGroup(languageId));
		return response;
	}
	
	@RequestMapping("/admin/ajax/images/list-imagesresize-result")
	@ResponseBody
	public List<ImageCounterVO> listImageResizeResult(String groups, String overwrite, HttpServletRequest request) {
		List<String> groupList = JsonUtils.convertJsonStringToList(groups);
		int languageId = getCurrentLanguage(request).getId();
		List<ImageCounterVO> response = imageService.resizeImages(groupList, "1".equals(overwrite), getRootPath(request), languageId);
		return response;
	}
}
