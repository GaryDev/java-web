package org.kratos.kracart.model;

import java.util.List;
import java.util.Map;

import org.kratos.kracart.entity.ProductVariant;

public interface ProductVariantModel {
	
	public List<ProductVariant> getVariantGroups(Map<String, Object> criteria);
	public List<Integer> getVariantEntriesByGroup(int groupsId);
	public List<ProductVariant> getVariantEntries(Map<String, Object> criteria);
	public List<ProductVariant> getVariantGroup(int groupsId);
	public List<ProductVariant> getVariantValue(int valuesId);
	public int getMaxVariantGroupId();
	public void insertVariantGroup(ProductVariant data);
	public void updateVariantGroup(ProductVariant data);
	public int getMaxVariantValueId();
	public void insertVariantValue(ProductVariant data);
	public void updateVariantValue(ProductVariant data);
	public void insertVariantValueToGroup(Map<String, Integer> relationship);
	public List<String> getVariantValueName(Map<String, Object> criteria);
	public void deleteVariantValue(int valuesId);
	public void deleteVariantValueToGroup(Map<String, Object> criteria);
	public int getProductVariantCount(int groupsId);
	public void deleteProductVariant(int groupsId);
}
