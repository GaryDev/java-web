package org.kratos.kracart.model;

import java.util.List;
import java.util.Map;

import org.kratos.kracart.entity.Manufacturer;

public interface ManufacturerModel {
	
	public List<Manufacturer> getManufacturers(Map<String, Object> criteria);
	public int getSumClicks(int manufacturerId);

}
