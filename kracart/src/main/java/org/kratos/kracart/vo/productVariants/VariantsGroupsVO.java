package org.kratos.kracart.vo.productVariants;

import java.util.Map;

public class VariantsGroupsVO {
	
	private int groupsId;
	private Map<Integer, String> groupsName;
	
	public int getGroupsId() {
		return groupsId;
	}
	public void setGroupsId(int groupsId) {
		this.groupsId = groupsId;
	}
	public Map<Integer, String> getGroupsName() {
		return groupsName;
	}
	public void setGroupsName(Map<Integer, String> groupsName) {
		this.groupsName = groupsName;
	}
}
