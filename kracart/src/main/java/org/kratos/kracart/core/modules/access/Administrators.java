package org.kratos.kracart.core.modules.access;

import org.kratos.kracart.core.modules.BaseAccess;

public class Administrators extends BaseAccess {
	
	public Administrators() {
		super();
		module = "administrators";
		group = "tools";
		icon = "people.png";
		sortOrder = 100;
	}

}
