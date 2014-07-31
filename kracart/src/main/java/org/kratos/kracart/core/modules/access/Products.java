package org.kratos.kracart.core.modules.access;

import org.kratos.kracart.core.modules.BaseAccess;
import org.kratos.kracart.core.modules.ModuleSubGroup;

public class Products extends BaseAccess {

	public Products() {
		super();
		module = "products";
		group = "content";
		icon = "products.png";
		sortOrder = 200;		
		ModuleSubGroup group = new ModuleSubGroup();
		group.setId("products");
		group.setIconCls("icon-products-win");
		group.setShortcutIconCls("icon-products-shortcut");
		group.setIdentifier("products-win");
		subGroup.add(group);
	}

}
