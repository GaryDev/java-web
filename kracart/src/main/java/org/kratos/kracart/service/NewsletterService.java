package org.kratos.kracart.service;

import java.util.List;
import java.util.ResourceBundle;

import org.kratos.kracart.vo.BasicVO;
import org.kratos.kracart.vo.NewsletterVO;

public interface NewsletterService {
	
	public List<NewsletterVO> getNewsletters(String contextName, String start, String limit);
	public int getTotal();
	public List<BasicVO> getNewsletterModules(ResourceBundle bundle);
	public boolean saveNewsletter(NewsletterVO data);

}
