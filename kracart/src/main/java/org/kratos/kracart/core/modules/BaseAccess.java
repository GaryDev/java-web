package org.kratos.kracart.core.modules;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BaseAccess {

	protected String module;
	protected String group = "misc";
	protected List<ModuleSubGroup> subGroup = new ArrayList<ModuleSubGroup>();
	protected String icon = "configure.png";
	protected Integer sortOrder = 0;
	protected String title;
	protected boolean enabled = true;
	
	public String getGroup() {
		return group;
	}
	
	public String getModule() {
		return module;
	}
	
	public List<ModuleSubGroup> getSubGroup() {
		return subGroup;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public boolean getEnabled() {
		return enabled;
	}

	public static BaseAccessComparator getBaseAccessComparator() {
		return new BaseAccessComparator();
	}

}

class BaseAccessComparator implements Comparator<BaseAccess> {
	@Override
	public int compare(BaseAccess o1, BaseAccess o2) {
		if(o1.sortOrder > o2.sortOrder) {
			return 1;
		} else if(o1.sortOrder < o2.sortOrder) {
			return -1;
		}
		return 0;
	}
}
