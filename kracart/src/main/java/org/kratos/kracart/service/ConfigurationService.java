package org.kratos.kracart.service;

import org.kratos.kracart.entity.Configuration;

public interface ConfigurationService {
	
	public Configuration getConfigurationByKey(String key);
	public String getConfigurationValue(String key);

}
