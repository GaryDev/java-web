package org.kratos.kracart.entity;

import java.io.Serializable;

public class EmailTemplateDescription implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private EmailTemplate template;
	private Language language;
	private String title;
	private String content;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public EmailTemplate getTemplate() {
		return template;
	}
	public void setTemplate(EmailTemplate template) {
		this.template = template;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
