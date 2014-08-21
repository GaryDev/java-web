package org.kratos.kracart.service;

import java.util.List;

import org.kratos.kracart.vo.productVariants.VariantsEntriesVO;
import org.kratos.kracart.vo.productVariants.VariantsGroupsVO;


public interface ProductVariantService {
	
	public List<VariantsGroupsVO> getVariantsGroups(int languageId, String start, String limit);
	public List<VariantsEntriesVO> getVariantsEntries(int groupsId, int languageId);
	public int getTotal(int languageId);

}
