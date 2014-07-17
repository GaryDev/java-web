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

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

public abstract class CommonUtils {
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		System.out.println(listToString(list, ","));
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
	
	public static String ucFirst(String str) {
		if(StringUtils.hasLength(str)) {
			String first = str.substring(0, 1);
			return str.replace(first, first.toUpperCase());
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
	
	private static String md5Encrypt(String plain) {
		return DigestUtils.md5DigestAsHex(plain.getBytes());
		
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
		if(source == null || map == null) {
			return null;
		}
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
                if (!key.equals("class")) {  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(source);
                    if(value != null) {
	                    if(value instanceof String || value instanceof Integer || value instanceof Boolean) {
	                    	map.put(key, value);  
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
