package org.kratos.kracart.vo;

public class NewsletterVO {
	
	private int newsletterId;
	private String title;
	private int size;
	private String module;
	private String content;
	private String sent;
	private String actionClass;
	
	public int getNewsletterId() {
		return newsletterId;
	}
	public void setNewsletterId(int newsletterId) {
		this.newsletterId = newsletterId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSent() {
		return sent;
	}
	public void setSent(String sent) {
		this.sent = sent;
	}
	public String getActionClass() {
		return actionClass;
	}
	public void setActionClass(String actionClass) {
		this.actionClass = actionClass;
	}
}
