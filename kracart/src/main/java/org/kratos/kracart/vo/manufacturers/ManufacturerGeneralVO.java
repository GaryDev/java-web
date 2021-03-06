package org.kratos.kracart.vo.manufacturers;

import java.util.Map;

import org.kratos.kracart.core.annotation.JsonFlatter;
import org.springframework.web.multipart.MultipartFile;

@JsonFlatter("general")
public class ManufacturerGeneralVO {
	
	private String manufacturerName;
	private MultipartFile manufacturerImage;
	private Map<Integer, String> manufacturerUrl;
	
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
	public Map<Integer, String> getManufacturerUrl() {
		return manufacturerUrl;
	}
	public void setManufacturerUrl(Map<Integer, String> manufacturerUrl) {
		this.manufacturerUrl = manufacturerUrl;
	}
}
