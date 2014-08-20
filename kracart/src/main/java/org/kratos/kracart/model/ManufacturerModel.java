package org.kratos.kracart.model;

import java.util.List;
import java.util.Map;

import org.kratos.kracart.entity.Manufacturer;
import org.kratos.kracart.entity.ManufacturerInfo;

public interface ManufacturerModel {
	
	public List<Manufacturer> getManufacturers(Map<String, Object> criteria);
	public Manufacturer getManufacturerData(int manufacturerId);
	public int getSumClicks(int manufacturerId);
	public void insertManufacturer(Manufacturer data);
	public void insertManufacturerInfo(ManufacturerInfo data);
	public void updateManufacturer(Manufacturer data);
	public void updateManufacturerInfo(ManufacturerInfo data);
	public void deleteManufacturer(int manufacturerId);
	public void deleteManufacturerInfo(int manufacturerId);
}
