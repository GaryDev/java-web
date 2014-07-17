package org.kratos.kracart.core.modules.access;

import org.kratos.kracart.core.modules.BaseAccess;

public class Articles extends BaseAccess {
	
	public Articles() {
		super();
		module = "administrators";
		group = "tools";
		icon = "people.png";
		sortOrder = 100;
		//$this->_title = lang('access_articles_title');
	}

}
