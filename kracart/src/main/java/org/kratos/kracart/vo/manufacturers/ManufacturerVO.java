package org.kratos.kracart.vo.manufacturers;

import java.util.Map;

public class ManufacturerVO {
	
	private Integer manufacturerId;
	private ManufacturerGeneralVO general;
	private Map<Integer, ManufacturerMetaVO> meta;
	
	private String imagePath;
	
	public Integer getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(Integer manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	public ManufacturerGeneralVO getGeneral() {
		return general;
	}
	public void setGeneral(ManufacturerGeneralVO general) {
		this.general = general;
	}
	public Map<Integer, ManufacturerMetaVO> getMeta() {
		return meta;
	}
	public void setMeta(Map<Integer, ManufacturerMetaVO> meta) {
		this.meta = meta;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
