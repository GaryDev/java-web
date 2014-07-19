package org.kratos.kracart.core.bean;

import java.util.List;

public class AdminModuleParent {
	
	private int id;
	private String text;
	private List<AdminModuleNode> children;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public List<AdminModuleNode> getChildren() {
		return children;
	}
	public void setChildren(List<AdminModuleNode> children) {
		this.children = children;
	}
	

}
