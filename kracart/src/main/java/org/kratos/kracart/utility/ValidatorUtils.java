package org.kratos.kracart.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ValidatorUtils {
	
	public static boolean validateEmail(String email) {
		String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern pattern = Pattern.compile(expr);
		Matcher matcher = pattern.matcher(email);
		if(matcher.find()) {
			return true;
		}
		return false;
	}

}
