package org.kratos.kracart.core.modules;

import java.util.Map;

public class ModuleSubGroup {

	private String iconCls;
	private String shortcutIconCls;
	private String title;
	private String identifier;
	private Map<String, Object> params;
	
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getShortcutIconCls() {
		return shortcutIconCls;
	}
	public void setShortcutIconCls(String shortcutIconCls) {
		this.shortcutIconCls = shortcutIconCls;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
