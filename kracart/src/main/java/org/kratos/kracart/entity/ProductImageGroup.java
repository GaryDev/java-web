package org.kratos.kracart.entity;

import java.io.Serializable;

public class ProductImageGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String title;
	private String code;
	private Integer sizeWidth;
	private Integer sizeHeight;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getSizeWidth() {
		return sizeWidth;
	}
	public void setSizeWidth(Integer sizeWidth) {
		this.sizeWidth = sizeWidth;
	}
	public Integer getSizeHeight() {
		return sizeHeight;
	}
	public void setSizeHeight(Integer sizeHeight) {
		this.sizeHeight = sizeHeight;
	}
}
