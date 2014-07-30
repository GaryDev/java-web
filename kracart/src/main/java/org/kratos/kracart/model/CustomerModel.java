package org.kratos.kracart.model;

import java.util.List;
import java.util.Map;

import org.kratos.kracart.entity.Customer;

public interface CustomerModel {
	
	public List<Customer> queryRecipients(Map<String, Object> criteria);
	public List<Customer> getCustomers();

}
