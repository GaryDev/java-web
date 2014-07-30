package org.kratos.kracart.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class NewsletterLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String email;
	private Timestamp dateSent;
	
	public NewsletterLog() {
		
	}
	
	public NewsletterLog(int id, String email) {
		this.id = id;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getDateSent() {
		return dateSent;
	}
	public void setDateSent(Timestamp dateSent) {
		this.dateSent = dateSent;
	}
	
	
}
