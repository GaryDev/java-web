package org.kratos.kracart.model;

import java.util.List;
import java.util.Map;

import org.kratos.kracart.entity.ProductVariant;

public interface ProductVariantModel {
	
	public List<ProductVariant> getVariantGroups(Map<String, Object> criteria);
	public int getTotalEntries(int groupsId);
	public List<ProductVariant> getVariantEntries(Map<String, Object> criteria);

}
