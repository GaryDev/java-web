package org.kratos.kracart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kratos.kracart.entity.Manufacturer;
import org.kratos.kracart.model.ManufacturerModel;
import org.kratos.kracart.service.ManufacturerService;
import org.kratos.kracart.vo.manufacturers.ManufacturerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("manufacturerService")
public class ManufacturerServiceImpl implements ManufacturerService {
	
	@Autowired
	private ManufacturerModel manufacturerModel;

	@Override
	public List<ManufacturerVO> getManufacturers(String start, String limit) {
		List<ManufacturerVO> manufacturers = new ArrayList<ManufacturerVO>();
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("start", start == null ? null : Integer.parseInt(start));
		criteria.put("limit", limit == null ? null : Integer.parseInt(limit));
		List<Manufacturer> manufacturerList = manufacturerModel.getManufacturers(criteria);
		if(manufacturerList != null && manufacturerList.size() > 0) {
			for (Manufacturer manufacturer : manufacturerList) {
				ManufacturerVO record = new ManufacturerVO();
				record.setManufacturerId(manufacturer.getId());
				record.setManufacturerName(manufacturer.getName());
				record.setUrlClicked(manufacturerModel.getSumClicks(manufacturer.getId()));
				manufacturers.add(record);
			}
		}
		return manufacturers;
	}

	@Override
	public int getTotal() {
		return getManufacturers(null, null).size();
	}

}
