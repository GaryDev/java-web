package org.kratos.kracart.utility;

public abstract class HtmlOutputUtils {
	
	private static String contextName;
	
	public static void setContextName(String name) {
		contextName = name;
	}
	
	public static String icon(String icon) {
		return icon(icon, "base", "web");
	}
	
	public static String icon(String icon, String template, String type) {
		return "<img src=\"" + "/" + contextName + "/templates/" + template + "/" + type + "/images/icons/16x16/" + icon + "\" />";
	}

}
