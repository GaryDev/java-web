package org.kratos.kracart.core.bean;

import java.util.List;

import org.kratos.kracart.core.modules.BaseAccess;

public class AdminAccessLevel {
	
	private String group;
	private List<BaseAccess> modules;
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public List<BaseAccess> getModules() {
		return modules;
	}
	public void setModules(List<BaseAccess> modules) {
		this.modules = modules;
	}
	
}
