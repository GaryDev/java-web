package org.kratos.kracart.vo.manufacturers;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ManufacturerGeneralVO {
	
	private String manufacturerName;
	private MultipartFile manufacturerImage;
	private List<String> manufacturerUrl;
	
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public MultipartFile getManufacturerImage() {
		return manufacturerImage;
	}
	public void setManufacturerImage(MultipartFile manufacturerImage) {
		this.manufacturerImage = manufacturerImage;
	}
	public List<String> getManufacturerUrl() {
		return manufacturerUrl;
	}
	public void setManufacturerUrl(List<String> manufacturerUrl) {
		this.manufacturerUrl = manufacturerUrl;
	}

}
