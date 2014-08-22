package org.kratos.kracart.service;

import java.util.List;
import java.util.Map;

import org.kratos.kracart.vo.productVariants.VariantsEntriesGridVO;
import org.kratos.kracart.vo.productVariants.VariantsGroupsGridVO;


public interface ProductVariantService {
	
	public List<VariantsGroupsGridVO> getVariantsGroups(int languageId, String start, String limit);
	public List<VariantsEntriesGridVO> getVariantsEntries(int groupsId, int languageId);
	public int getTotal(int languageId);
	public Map<String, Object> loadProductVariant(int groupsId);
	public Map<String, Object> loadProductEntry(int valuesId);
}
