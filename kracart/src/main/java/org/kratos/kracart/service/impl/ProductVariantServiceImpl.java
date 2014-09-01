package org.kratos.kracart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kratos.kracart.entity.ProductVariant;
import org.kratos.kracart.model.ProductVariantModel;
import org.kratos.kracart.service.ProductVariantService;
import org.kratos.kracart.utility.CommonUtils;
import org.kratos.kracart.vo.productVariants.VariantsEntriesGridVO;
import org.kratos.kracart.vo.productVariants.VariantsEntriesVO;
import org.kratos.kracart.vo.productVariants.VariantsGroupsGridVO;
import org.kratos.kracart.vo.productVariants.VariantsGroupsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("productVariantService")
public class ProductVariantServiceImpl implements ProductVariantService {
	
	@Autowired
	private ProductVariantModel productVariantModel;

	@Override
	public List<VariantsGroupsGridVO> getVariantsGroups(int languageId,
			String start, String limit) {
		List<VariantsGroupsGridVO> groups = new ArrayList<VariantsGroupsGridVO>();
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("langId", languageId);
		criteria.put("start", start == null ? null : Integer.parseInt(start));
		criteria.put("limit", limit == null ? null : Integer.parseInt(limit));
		List<ProductVariant> groupList = productVariantModel.getVariantGroups(criteria);
		if(groupList != null && groupList.size() > 0) {
			for (ProductVariant group : groupList) {
				VariantsGroupsGridVO record = new VariantsGroupsGridVO();
				record.setGroupsId(group.getId());
				record.setGroupsName(group.getName());
				record.setTotalEntries(productVariantModel.getTotalEntries(group.getId()));
				groups.add(record);
			}
		}
		return groups;
	}

	@Override
	public List<VariantsEntriesGridVO> getVariantsEntries(int groupsId, int languageId) {
		List<VariantsEntriesGridVO> entries = new ArrayList<VariantsEntriesGridVO>();
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("groupsId", groupsId);
		criteria.put("langId", languageId);
		List<ProductVariant> entryList = productVariantModel.getVariantEntries(criteria);
		if(entryList != null && entryList.size() > 0) {
			for (ProductVariant entry : entryList) {
				VariantsEntriesGridVO record = new VariantsEntriesGridVO();
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

	@Override
	public Map<String, Object> loadProductVariant(int groupsId) {
		List<ProductVariant> groups = productVariantModel.getVariantGroup(groupsId);
		VariantsGroupsVO record = new VariantsGroupsVO();
		Map<Integer, String> groupsName = new HashMap<Integer, String>();
		for (ProductVariant group : groups) {
			record.setGroupsId(group.getId());
			groupsName.put(group.getLanguageId(), group.getName());
		}
		record.setGroupsName(groupsName);
		return CommonUtils.beanToMap(new HashMap<String, Object>(), record);
	}

	@Override
	public Map<String, Object> loadProductEntry(int valuesId) {
		List<ProductVariant> values = productVariantModel.getVariantValue(valuesId);
		VariantsEntriesVO record = new VariantsEntriesVO();
		Map<Integer, String> valuesName = new HashMap<Integer, String>();
		for (ProductVariant value : values) {
			record.setValuesId(value.getId());
			valuesName.put(value.getLanguageId(), value.getName());
		}
		record.setValuesName(valuesName);
		return CommonUtils.beanToMap(new HashMap<String, Object>(), record);
	}

	@Override
	public boolean saveProductVariant(VariantsGroupsVO data) {
		int groupId = data.getGroupsId();
		if(groupId == 0) {
			groupId = productVariantModel.getMaxVariantGroupId();
			groupId ++;
		}
		Map<Integer, String> groupNames = data.getGroupsName();
		for (Integer languageId : groupNames.keySet()) {
			ProductVariant pv = new ProductVariant();
			pv.setId(groupId);
			pv.setLanguageId(languageId);
			pv.setName(groupNames.get(languageId));
			if(data.getGroupsId() > 0) {
				productVariantModel.updateVariantGroup(pv);
			} else {
				productVariantModel.insertVariantGroup(pv);
			}
		}
		return true;
	}

	@Override
	public boolean saveProductVariantEntry(int groupsId, VariantsEntriesVO data) {
		int entryId = data.getValuesId();
		if(entryId == 0) {
			entryId = productVariantModel.getMaxVariantValueId();
			entryId ++;
		}
		Map<Integer, String> valueNames = data.getValuesName();
		for (Integer languageId : valueNames.keySet()) {
			ProductVariant pv = new ProductVariant();
			pv.setId(entryId);
			pv.setLanguageId(languageId);
			pv.setName(valueNames.get(languageId));
			if(data.getValuesId() > 0) {
				productVariantModel.updateVariantValue(pv);
			} else {
				productVariantModel.insertVariantValue(pv);
			}
		}
		Map<String, Integer> relationship = new HashMap<String, Integer>();
		relationship.put("groupsId", groupsId);
		relationship.put("valuesId", entryId);
		productVariantModel.insertVariantValueToGroup(relationship);
		return true;
	}

}
