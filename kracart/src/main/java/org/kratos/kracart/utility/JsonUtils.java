package org.kratos.kracart.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.springframework.util.StringUtils;

public abstract class JsonUtils {

	private final static ObjectMapper mapper = new ObjectMapper().enableDefaultTyping();
	
	public static String convertToJsonString(Object target) {
		return convertToJsonString(target, null);
	}
	
	public static String convertToJsonString(Object target, String defaultValue) {
		if(!StringUtils.hasLength(defaultValue)) {
			defaultValue = "{}";
		}
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
	
	@SuppressWarnings("unchecked")
	public static <T> T convertJsonStringToList(String jsonString, Class<?> collectionClass, Class<?> elementClasses) {
		JavaType javaType = getCollectionType(collectionClass, elementClasses);
		try {
			return (T) mapper.readValue(jsonString, javaType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	}
	
	public static void main(String[] args) {
		String json = "[\"\u56fe\u7247\u5de5\u5177\",\"\u7ba1\u7406\u5458\"]";
		List<String> list = convertJsonStringToList(json, ArrayList.class, String.class);
		System.out.println(list);
	}
	
}
