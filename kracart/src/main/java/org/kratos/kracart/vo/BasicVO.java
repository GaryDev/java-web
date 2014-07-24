package org.kratos.kracart.vo;

public class BasicVO {
	
	public BasicVO() {
		
	}
	
	public BasicVO(String id, String text) {
		this.id = id;
		this.text = text;
	}
	
	private String id;
	private String text;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
