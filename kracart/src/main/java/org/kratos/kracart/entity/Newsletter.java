package org.kratos.kracart.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Newsletter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	private String content;
	private String module;
	private Timestamp dateAdded;
	private Timestamp dateSent;
	private int status;
	private int locked;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public Timestamp getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}
	public Timestamp getDateSent() {
		return dateSent;
	}
	public void setDateSent(Timestamp dateSent) {
		this.dateSent = dateSent;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getLocked() {
		return locked;
	}
	public void setLocked(int locked) {
		this.locked = locked;
	}
}
