package org.kratos.kracart.vo.productVariants;

import java.util.Map;

public class VariantsEntriesVO {
	
	private int valuesId;
	private Map<Integer, String> valuesName;
	
	public int getValuesId() {
		return valuesId;
	}
	public void setValuesId(int valuesId) {
		this.valuesId = valuesId;
	}
	public Map<Integer, String> getValuesName() {
		return valuesName;
	}
	public void setValuesName(Map<Integer, String> valuesName) {
		this.valuesName = valuesName;
	}
	
}
