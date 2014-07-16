package org.kratos.kracart.core.bean;

import org.springframework.util.StringUtils;

public class DesktopLauncher {
	
	private static final String DEFAULT_VALUE = "[]";
	
	private String autorun;
	private String contextmenu;
	private String quickstart;
	private String shortcut;
	
	public DesktopLauncher() {
		
	}

	public String getAutorun() {
		return StringUtils.hasLength(autorun) ? autorun : DEFAULT_VALUE;
	}

	public void setAutorun(String autorun) {
		this.autorun = autorun;
	}

	public String getContextmenu() {
		return StringUtils.hasLength(contextmenu) ? contextmenu : DEFAULT_VALUE;
	}

	public void setContextmenu(String contextmenu) {
		this.contextmenu = contextmenu;
	}

	public String getQuickstart() {
		return StringUtils.hasLength(quickstart) ? quickstart : DEFAULT_VALUE;
	}

	public void setQuickstart(String quickstart) {
		this.quickstart = quickstart;
	}

	public String getShortcut() {
		return StringUtils.hasLength(shortcut) ? shortcut : DEFAULT_VALUE;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

}
