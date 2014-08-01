package org.kratos.kracart.service;

import java.util.List;

import org.kratos.kracart.vo.manufacturers.ManufacturerVO;

public interface ManufacturerService {
	
	public List<ManufacturerVO> getManufacturers(String start, String limit);
	public int getTotal();

}
