package org.kratos.kracart.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.kratos.kracart.core.config.DesktopConstant;
import org.kratos.kracart.entity.Language;
import org.kratos.kracart.service.DesktopService;
import org.springframework.stereotype.Service;

@Service("desktopService")
public class DesktopServiceImpl implements DesktopService {

	public void loadDesktopConstants(Map<String, Object> data) {
		data.put("steps", String.valueOf(DesktopConstant.EXT_GRID_STEPS));
		data.put("jsonReaderRoot", DesktopConstant.EXT_JSON_READER_ROOT);
		data.put("jsonReaderTotal", DesktopConstant.EXT_JSON_READER_TOTAL);
	}
	
	public void loadLanguages(Map<String, Object> data, List<Language> langs) {
		List<String> jsonLang = new ArrayList<String>();
		for (Language lang : langs) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				jsonLang.add(mapper.writeValueAsString(lang));
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		data.put("jsonLang", jsonLang);
	}
	
	public void loadLanguageDefinition(Map<String, Object> data, ResourceBundle desktopBundle) {
		String jsonLangDef = "";
		if(desktopBundle != null) {
			Map<String, String> prop = new HashMap<String, String>();
			ObjectMapper mapper = new ObjectMapper();
			Enumeration<String> keys = desktopBundle.getKeys();
			while(keys.hasMoreElements()) {
				String key = keys.nextElement();
				prop.put(key, desktopBundle.getString(key));
			}
			try {
				jsonLangDef = mapper.writeValueAsString(prop);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		data.put("jsonLangDef", jsonLangDef);
	}
}
