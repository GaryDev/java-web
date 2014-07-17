package org.kratos.kracart.utility;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

public abstract class JsonUtils {

	public static String convertToJsonString(Object target) {
		return convertToJsonString(target, null);
	}
	
	public static String convertToJsonString(Object target, String defaultValue) {
		if(!StringUtils.hasLength(defaultValue)) {
			defaultValue = "{}";
		}
		ObjectMapper mapper = new ObjectMapper();
		String result = defaultValue;
		if(target != null) {
			try {
				result = mapper.writeValueAsString(target);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
