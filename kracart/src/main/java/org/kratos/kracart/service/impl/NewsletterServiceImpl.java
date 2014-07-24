package org.kratos.kracart.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.kratos.kracart.entity.Newsletter;
import org.kratos.kracart.model.NewsletterModel;
import org.kratos.kracart.service.NewsletterService;
import org.kratos.kracart.utility.HtmlOutputUtils;
import org.kratos.kracart.vo.BasicVO;
import org.kratos.kracart.vo.NewsletterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("newsletterService")
public class NewsletterServiceImpl implements NewsletterService {
	
	@Autowired
	private NewsletterModel newsletterModel;
	
	public List<NewsletterVO> getNewsletters(String contextName, String start, String limit) {
		List<NewsletterVO> newsletters = new ArrayList<NewsletterVO>();
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("start", start == null ? null : Integer.parseInt(start));
		criteria.put("limit", limit == null ? null : Integer.parseInt(limit));
		List<Newsletter> newsletterList = newsletterModel.getNewsletters(criteria);
		if(newsletterList != null && newsletterList.size() > 0) {
			HtmlOutputUtils.setContextName(contextName);
			for (Newsletter newsletter : newsletterList) {
				String sent = newsletter.getStatus() == 1 ? HtmlOutputUtils.icon("checkbox_ticked.gif") : HtmlOutputUtils.icon("checkbox_crossed.gif");
				String actionClass = newsletter.getStatus() == 1 ? "icon-log-record" : "icon-send-email-record";
				NewsletterVO record = new NewsletterVO();
				record.setNewsletterId(newsletter.getId());
				record.setTitle(newsletter.getTitle());
				record.setSize(newsletter.getContent().length());
				record.setModule(newsletter.getModule());
				record.setSent(sent);
				record.setActionClass(actionClass);
				newsletters.add(record);
			}
		}
		return newsletters;
	}
	
	public int getTotal() {
		return getNewsletters("", null, null).size();
	}

	@Override
	public List<BasicVO> getNewsletterModules(ResourceBundle bundle) {
		BasicVO[] modules = new BasicVO[]{
			new BasicVO("email", bundle.getString("newsletter_email_title")),
			new BasicVO("newsletter", bundle.getString("newsletter_newsletter_title"))
		};
		List<BasicVO> newsletterModules = new ArrayList<BasicVO>();
		for (BasicVO module : modules) {
			newsletterModules.add(module);
		}
		return newsletterModules;
	}

	@Override
	public boolean saveNewsletter(NewsletterVO data) {
		try {
			Newsletter newsletter = new Newsletter();
			newsletter.setTitle(data.getTitle());
			newsletter.setContent(data.getContent());
			newsletter.setModule(data.getModule());
			if(data.getNewsletterId() > 0) {
				newsletter.setId(data.getNewsletterId());
				newsletterModel.updateNewsletter(newsletter);
			} else {
				newsletter.setDateAdded(new Timestamp(new java.util.Date().getTime()));
				newsletter.setStatus(0);
				newsletterModel.insertNewsletter(newsletter);
			}
		} catch(Exception e) {
			return false;
		}
		return true;
	}

}
