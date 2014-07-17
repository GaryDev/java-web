package org.kratos.kracart.core.modules;

import java.util.ArrayList;
import java.util.List;

public class BaseAccess {

	protected String module;
	protected String group = "misc";
	protected List<ModuleSubGroup> subGroup = new ArrayList<ModuleSubGroup>();
	protected String icon = "configure.png";
	protected Integer sortOrder = 0;
	protected String title;
	
	public String getGroup() {
		return group;
	}
	
	public String getModule() {
		return module;
	}
	
	public List<ModuleSubGroup> getSubGroup() {
		return subGroup;
	}

}
