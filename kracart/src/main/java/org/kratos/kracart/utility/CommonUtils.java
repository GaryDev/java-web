package org.kratos.kracart.utility;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.kratos.kracart.core.annotation.JsonFlatter;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

public abstract class CommonUtils {
	
	public static void main(String[] args) {
		String test = "\u56fe\u7247\u5de5\u5177";
		System.out.println(test);
	}

	public static String encryptString(String plain) {
		String password = "";
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			password += String.valueOf(rand.nextInt(9));
		}
		String salt = md5Encrypt(password).substring(0, 2);
		password = md5Encrypt(salt + plain) + ":" + salt;
		return password;
	}
	
	public static String createRandomString(int length) {
		String pattern = "abcdefghijklmnopqrstuvwxyz01234567890";
		String randStr = "";
		Random rand = new Random();
		while(randStr.length() < length) {
			int pos = rand.nextInt(pattern.length());
			randStr += pattern.substring(pos, pos + 1);
		}
		return randStr;
	}
	
	private static String md5Encrypt(String plain) {
		return DigestUtils.md5DigestAsHex(plain.getBytes());
	}
	
	public static String ucFirst(String str) {
		if(StringUtils.hasLength(str)) {
			String first = str.substring(0, 1);
			return first.toUpperCase() + str.substring(1);
		}
		return str;
	}
	
	public static String ucWord(String str) {
		if(StringUtils.hasLength(str)) {
			if(str.indexOf("_") != -1) {
				String[] words = str.split("_");
				str = ucFirst(words[0]) + ucFirst(words[1]); 
			} else {
				str = ucFirst(str);
			}
		}
		return str;
	}
	
	public static String listToString(List<String> list) {
		return listToString(list, ",");
	}
	
	public static String listToString(List<String> list, String delimter) {
		if(list == null || list.size() == 0) {
			return "";
		}
		if(!StringUtils.hasLength(delimter)) {
			delimter = ",";
		}
		StringBuilder result = new StringBuilder();
		for (String item : list) {
			result.append(item);
			result.append(delimter);
		}
		String str = result.toString();
		return str.substring(0, str.length() - 1);
	}
	
	public static void mapToBean(Map<String, Object> map, Object target) {
		try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
                if (map.containsKey(key)) {  
                    Object value = map.get(key);  
                    Method setter = property.getWriteMethod();  
                    setter.invoke(target, value);
                }  
            }  
        } catch (Exception e) {  
        } 
	}
	
	public static Map<String, Object> beanToMap(Map<String, Object> map, Object source) {
		return beanToMap(map, source, null);
	}
	
	public static Map<String, Object> beanToMap(Map<String, Object> map, Object source, Integer index) {
		if(source == null || map == null) {
			return null;
		}
        try {  
        	String flatterKey = null;
        	if(source.getClass().isAnnotationPresent(JsonFlatter.class)) {
        		JsonFlatter flatter = source.getClass().getAnnotation(JsonFlatter.class);
        		flatterKey = flatter.value();
        		if(index != null) {
        			flatterKey = flatterKey + "[" + index + "]";
        		}
        	}
            BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
                if (!key.equals("class")) {
                	if(StringUtils.hasLength(flatterKey)) {
                		key = flatterKey + "." + key;
                	}
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(source);
                    if(value != null) {
	                    if(value instanceof String || value instanceof Integer || value instanceof Boolean) {
	                    	map.put(key, value);
	                    } else if(value instanceof Map) {
	                    	Map mapper = (Map) value;
	                    	for (Object k : mapper.keySet()) {
	                    		Object item = mapper.get(k);
	                    		if(item instanceof String || item instanceof Integer || item instanceof Boolean) {
	                    			map.put(key + "[" + k + "]", item);
	                    		} else if(k instanceof Integer) {
		                    		beanToMap(map, item, (Integer) k);
	                    		}
							}
	                    } else {
	                    	beanToMap(map, value);
	                    }
                    }
                }  
            }  
        } catch (Exception e) {  
        }  
        return map;
	}
	
	public static List<String> getClassName(String packageName, URL root) {
		String packagePath = packageName.replaceAll("\\.", "\\\\");
		String filePath = root.getPath() + packagePath;
		return getClassNameList(filePath, null);
	}
	
	private static List<String> getClassNameList(String filePath, List<String> className) {
		List<String> classNameList = new ArrayList<String>();
		File file = new File(filePath);
		File[] files = file.listFiles();
		for (File f : files) {
			if(f.isDirectory()) {
				classNameList.addAll(getClassNameList(f.getPath(), classNameList));
			} else {
				String path = f.getPath();
				String name = path.substring(path.indexOf("\\classes") + 9, path.lastIndexOf("."));
				classNameList.add(name.replaceAll("\\\\", "\\."));
			}
		}
		return classNameList;
	}
	
}
