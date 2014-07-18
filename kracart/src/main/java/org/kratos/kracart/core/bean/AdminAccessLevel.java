package org.kratos.kracart.core.bean;

import java.util.List;

import org.kratos.kracart.core.modules.BaseAccess;

public class AdminAccessLevel {
	
	private String group;
	private List<BaseAccess> modules;
	
	public AdminAccessLevel() {
		
	}
	
	public AdminAccessLevel(String group) {
		this.group = group;
	}
	
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminAccessLevel other = (AdminAccessLevel) obj;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		return true;
	}
	
}
