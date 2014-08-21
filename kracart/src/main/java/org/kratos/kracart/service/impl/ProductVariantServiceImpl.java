package org.kratos.kracart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kratos.kracart.entity.ProductVariant;
import org.kratos.kracart.model.ProductVariantModel;
import org.kratos.kracart.service.ProductVariantService;
import org.kratos.kracart.vo.productVariants.VariantsEntriesVO;
import org.kratos.kracart.vo.productVariants.VariantsGroupsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("productVariantService")
public class ProductVariantServiceImpl implements ProductVariantService {
	
	@Autowired
	private ProductVariantModel productVariantModel;

	@Override
	public List<VariantsGroupsVO> getVariantsGroups(int languageId,
			String start, String limit) {
		List<VariantsGroupsVO> groups = new ArrayList<VariantsGroupsVO>();
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("langId", languageId);
		criteria.put("start", start == null ? null : Integer.parseInt(start));
		criteria.put("limit", limit == null ? null : Integer.parseInt(limit));
		List<ProductVariant> groupList = productVariantModel.getVariantGroups(criteria);
		if(groupList != null && groupList.size() > 0) {
			for (ProductVariant group : groupList) {
				VariantsGroupsVO record = new VariantsGroupsVO();
				record.setGroupsId(group.getId());
				record.setGroupsName(group.getName());
				record.setTotalEntries(productVariantModel.getTotalEntries(group.getId()));
				groups.add(record);
			}
		}
		return groups;
	}

	@Override
	public List<VariantsEntriesVO> getVariantsEntries(int groupsId, int languageId) {
		List<VariantsEntriesVO> entries = new ArrayList<VariantsEntriesVO>();
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("groupsId", groupsId);
		criteria.put("langId", languageId);
		List<ProductVariant> entryList = productVariantModel.getVariantEntries(criteria);
		if(entryList != null && entryList.size() > 0) {
			for (ProductVariant entry : entryList) {
				VariantsEntriesVO record = new VariantsEntriesVO();
				record.setValuesId(entry.getId());
				record.setValuesName(entry.getName());
				entries.add(record);
			}
		}
		return entries;
	}

	@Override
	public int getTotal(int languageId) {
		return getVariantsGroups(languageId, null, null).size();
	}

}
