package org.kratos.kracart.core.modules;

public class BaseAccess {

	protected String module;
	protected String group = "misc";
	protected String subGroup;
	protected String icon = "configure.png";
	protected Integer sortOrder = 0;
	protected String title;
	
	public String getGroup() {
		return group;
	}

}
