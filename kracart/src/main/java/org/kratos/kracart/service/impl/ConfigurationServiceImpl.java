package org.kratos.kracart.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.kratos.kracart.core.config.ConfigConstant;
import org.kratos.kracart.entity.Configuration;
import org.kratos.kracart.model.ConfigurationModel;
import org.kratos.kracart.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("configurationService")
public class ConfigurationServiceImpl implements ConfigurationService {

	private ConfigurationModel configurationModel;
	
	private volatile long time = 0L;
	private volatile boolean refresh = true;
	private Map<String, Configuration> configCache = new ConcurrentHashMap<String, Configuration>();
	
	@Autowired
	public ConfigurationServiceImpl(ConfigurationModel configurationModel) {
		this.configurationModel = configurationModel;
		refreshConfigCache();
		time = System.currentTimeMillis();
	}
	
	public Configuration getConfigurationByKey(String key) {
		long current = System.currentTimeMillis();
		if(refresh && isTimeOut(current)) {
			synchronized (this) {
				if(refresh) {
					timeOutRefresh(current);
				}
			}
		}
		return configCache.get(key);
	}
	
	public String getConfigurationValue(String key) {
		return getConfigurationByKey(key).getValue();
	}
	
	private void timeOutRefresh(long current) {
		refresh = false;
		try {
			refreshConfigCache();
			time = current;
		} catch (Exception e) {
			
		} finally {
			refresh = true;
		}
	}
	
	private void refreshConfigCache() {
		List<Configuration> configs = configurationModel.getConfigs();
		for (Configuration config : configs) {
			configCache.put(config.getKey(), config);
		}
	}
	
	private boolean isTimeOut(long current) {
		return (current - time >= ConfigConstant.CACHE_TIMEOUT);
	}

}
