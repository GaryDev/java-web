package org.kratos.kracart.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class ManufacturerInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String url;
	private String friendlyUrl;
	private String pageTitle;
	private String metaKeywords;
	private String metaDescription;
	private int urlClicked;
	private Timestamp dateLastClicked;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFriendlyUrl() {
		return friendlyUrl;
	}
	public void setFriendlyUrl(String friendlyUrl) {
		this.friendlyUrl = friendlyUrl;
	}
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
	public int getUrlClicked() {
		return urlClicked;
	}
	public void setUrlClicked(int urlClicked) {
		this.urlClicked = urlClicked;
	}
	public Timestamp getDateLastClicked() {
		return dateLastClicked;
	}
	public void setDateLastClicked(Timestamp dateLastClicked) {
		this.dateLastClicked = dateLastClicked;
	}

}
