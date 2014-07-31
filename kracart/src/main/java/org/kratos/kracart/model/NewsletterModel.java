package org.kratos.kracart.model;

import java.util.List;
import java.util.Map;

import org.kratos.kracart.entity.Newsletter;
import org.kratos.kracart.entity.NewsletterLog;

public interface NewsletterModel {
	
	public List<Newsletter> getNewsletters(Map<String, Object> criteria);
	public List<NewsletterLog> getNewsletterLogs(Map<String, Object> criteria);
	public Newsletter getNewsletterById(int newsletterId);
	public void updateNewsletter(Newsletter newsletter);
	public void insertNewsletter(Newsletter newsletter);
	public void insertNewsletterLog(NewsletterLog log);
	public void deleteNewsletter(int newsletterId);
	public void deleteNewsletterLog(int newsletterId);
}
