package org.kratos.kracart.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.mail.internet.MimeMessage;

import org.kratos.kracart.entity.Customer;
import org.kratos.kracart.entity.Newsletter;
import org.kratos.kracart.entity.NewsletterLog;
import org.kratos.kracart.model.CustomerModel;
import org.kratos.kracart.model.NewsletterModel;
import org.kratos.kracart.service.NewsletterService;
import org.kratos.kracart.utility.HtmlOutputUtils;
import org.kratos.kracart.utility.ValidatorUtils;
import org.kratos.kracart.vo.BasicVO;
import org.kratos.kracart.vo.NewsletterLogVO;
import org.kratos.kracart.vo.NewsletterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("newsletterService")
@Transactional
public class NewsletterServiceImpl implements NewsletterService {
	
	@Autowired
	private NewsletterModel newsletterModel;
	@Autowired
	private CustomerModel customerModel;
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
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
	
	@Override
	public int getTotal() {
		return getNewsletters("", null, null).size();
	}
	
	@Override
	public List<NewsletterLogVO> getNewsletterLogs(String contextName, int newsletterId, String start, String limit) {
		List<NewsletterLogVO> logs = new ArrayList<NewsletterLogVO>();
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("id", newsletterId);
		criteria.put("start", start == null ? null : Integer.parseInt(start));
		criteria.put("limit", limit == null ? null : Integer.parseInt(limit));
		List<NewsletterLog> logList = newsletterModel.getNewsletterLogs(criteria);
		if(logList != null && logList.size() > 0) {
			HtmlOutputUtils.setContextName(contextName);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			for (NewsletterLog log : logList) {
				String sentIcon = log.getDateSent() != null ? HtmlOutputUtils.icon("checkbox_ticked.gif") : HtmlOutputUtils.icon("checkbox_crossed.gif");
				NewsletterLogVO record = new NewsletterLogVO();
				record.setEmail(log.getEmail());
				record.setSentIcon(sentIcon);
				record.setSentDate(dateFormat.format(log.getDateSent()));
				logs.add(record);
			}
		}
		return logs;
	}
	
	@Override
	public int getTotalLogs(int newsletterId) {
		return getNewsletterLogs("", newsletterId, null, null).size();
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
	
	@Override
	public Newsletter loadNewsletter(int newsletterId) {
		return newsletterModel.getNewsletterById(newsletterId);
	}
	
	@Override
	public List<Customer> getNewsletterRecipients(int newsletterId) {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("id", newsletterId);
		criteria.put("newsletter", "1");
		criteria.put("customers", null);
		return customerModel.queryRecipients(criteria);
	}
	
	@Override
	public List<Customer> getEmailRecipients(int newsletterId, List<String> customerId) {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("id", newsletterId);
		criteria.put("newsletter", null);
		if(!customerId.contains("***")) {
			criteria.put("customers", customerId);
		} else {
			criteria.put("customers", null);
		}
		return customerModel.queryRecipients(criteria);
	}
	
	@Override
	public String buildConfirmationMessage(Newsletter email, String totalMessage) {
		StringBuilder confirmation = new StringBuilder();
		confirmation.append("<p style=\"margin: 10px;\"><font color=\"#ff0000\"><b>");
		confirmation.append(totalMessage);
		confirmation.append("</b></font></p>");
		confirmation.append("<p style=\"margin: 10px;\"><b>");
		confirmation.append(email.getTitle());
		confirmation.append("</b></p>");
		confirmation.append("<p style=\"margin: 10px;\">");
		confirmation.append(email.getContent());
		confirmation.append("</p>");
		return confirmation.toString();
	}
	
	@Override
	public List<BasicVO> getEmailsAudience(ResourceBundle bundle) {
		List<BasicVO> audience = new ArrayList<BasicVO>();
		audience.add(new BasicVO("***", bundle.getString("newsletter_email_all_customers")));
		List<Customer> customers = customerModel.getCustomers();
		for (Customer customer : customers) {
			String id = String.valueOf(customer.getId());
			String text = customer.getLastName() + ", " + customer.getFirstName() + " (" + customer.getEmail() + ")";
			audience.add(new BasicVO(id, text));
		}
		return audience;
	}
	
	@Override
	public boolean sendEmails(int newsletterId, List<String> customerId) {
		Newsletter email = newsletterModel.getNewsletterById(newsletterId);
		List<Customer> customers = null;
		if(customerId == null) {
			customers = getNewsletterRecipients(newsletterId);
		} else {
			customers = getEmailRecipients(newsletterId, customerId);
		}
		return doSend(email, customers);
	}
	
	private boolean doSend(Newsletter email, List<Customer> customers) {
		List<String> recipients = new ArrayList<String>();
		if(customers != null && customers.size() > 0) {
			for (Customer customer : customers) {
				String address = customer.getEmail();
				if(ValidatorUtils.validateEmail(address)) {
					recipients.add(address);
				}
			}
			if(sendEmail(email, recipients)) {
				modifyNewsletter(email.getId(), recipients);
				return true;
			}
		}
		return false;
	}
	
	private boolean sendEmail(Newsletter email, List<String> recipients) {
		boolean result = false;
		String[] mailTo = recipients.toArray(new String[0]);
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			messageHelper.setTo(mailTo);
			//messageHelper.setFrom(mailFrom);
			messageHelper.setSubject(email.getTitle());
			messageHelper.setText(email.getContent(), true);
			javaMailSender.send(mimeMessage);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	private void modifyNewsletter(int newsletterId, List<String> recipients) {
		Timestamp date = new Timestamp(new java.util.Date().getTime());
		for (String recipient : recipients) {
			NewsletterLog log = new NewsletterLog(newsletterId, recipient);
			log.setDateSent(date);
			newsletterModel.insertNewsletterLog(log);
		}
		Newsletter newsletter = new Newsletter();
		newsletter.setId(newsletterId);
		newsletter.setDateSent(date);
		newsletter.setStatus(1);
		newsletterModel.updateNewsletter(newsletter);
	}
	
}
