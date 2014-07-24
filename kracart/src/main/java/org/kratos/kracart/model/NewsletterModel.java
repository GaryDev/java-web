package org.kratos.kracart.model;

import java.util.List;
import java.util.Map;

import org.kratos.kracart.entity.Newsletter;

public interface NewsletterModel {
	
	public List<Newsletter> getNewsletters(Map<String, Object> criteria);
	public void updateNewsletter(Newsletter newsletter);
	public void insertNewsletter(Newsletter newsletter);

}
