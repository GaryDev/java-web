package org.kratos.kracart.service;

import java.util.List;
import java.util.Map;

import org.kratos.kracart.vo.productVariants.VariantsEntriesGridVO;
import org.kratos.kracart.vo.productVariants.VariantsEntriesVO;
import org.kratos.kracart.vo.productVariants.VariantsGroupsGridVO;
import org.kratos.kracart.vo.productVariants.VariantsGroupsVO;


public interface ProductVariantService {
	
	public List<VariantsGroupsGridVO> getVariantsGroups(int languageId, String start, String limit);
	public List<VariantsEntriesGridVO> getVariantsEntries(int groupsId, int languageId);
	public int getTotal(int languageId);
	public Map<String, Object> loadProductVariant(int groupsId);
	public Map<String, Object> loadProductEntry(int valuesId);
	public boolean saveProductVariant(VariantsGroupsVO data);
	public boolean saveProductVariantEntry(int groupsId, VariantsEntriesVO data);
	public List<String> getEntryData(String[] idArray, int languageId);
	public boolean deleteProductVariantEntry(int groupsId, String[] idArray);
	public int getProductVariantCount(int groupsId);
	public boolean deleteProductVariant(int groupsId);
}
