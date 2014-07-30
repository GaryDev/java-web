package org.kratos.kracart.service;

import java.util.List;
import java.util.ResourceBundle;

import org.kratos.kracart.entity.Customer;
import org.kratos.kracart.entity.Newsletter;
import org.kratos.kracart.vo.BasicVO;
import org.kratos.kracart.vo.NewsletterLogVO;
import org.kratos.kracart.vo.NewsletterVO;

public interface NewsletterService {
	
	public List<NewsletterVO> getNewsletters(String contextName, String start, String limit);
	public List<NewsletterLogVO> getNewsletterLogs(String contextName, int newsletterId, String start, String limit);
	public int getTotal();
	public int getTotalLogs(int newsletterId);
	public List<BasicVO> getNewsletterModules(ResourceBundle bundle);
	public List<BasicVO> getEmailsAudience(ResourceBundle bundle);
	public boolean saveNewsletter(NewsletterVO data);
	public Newsletter loadNewsletter(int newsletterId);
	public List<Customer> getNewsletterRecipients(int newsletterId);
	public List<Customer> getEmailRecipients(int newsletterId, List<String> customerId);
	public String buildConfirmationMessage(Newsletter email, String totalMessage);
	public boolean sendEmails(int newsletterId, List<String> customerId);

}
