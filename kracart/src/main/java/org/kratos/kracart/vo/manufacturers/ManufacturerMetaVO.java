package org.kratos.kracart.vo.manufacturers;

import org.kratos.kracart.core.annotation.JsonFlatter;

@JsonFlatter("meta")
public class ManufacturerMetaVO {
	
	private String pageTitle;
	private String metaKeywords;
	private String metaDescription;
	private String manufacturerFriendlyUrl;
	
	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	public String getMetaKeywords() {
		return metaKeywords;
	}
	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}
	public String getMetaDescription() {
		return metaDescription;
	}
	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}
	public String getManufacturerFriendlyUrl() {
		return manufacturerFriendlyUrl;
	}
	public void setManufacturerFriendlyUrl(String manufacturerFriendlyUrl) {
		this.manufacturerFriendlyUrl = manufacturerFriendlyUrl;
	}
}
