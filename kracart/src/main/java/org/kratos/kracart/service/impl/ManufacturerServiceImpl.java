package org.kratos.kracart.service.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.kratos.kracart.entity.Manufacturer;
import org.kratos.kracart.entity.ManufacturerInfo;
import org.kratos.kracart.model.ManufacturerModel;
import org.kratos.kracart.service.ManufacturerService;
import org.kratos.kracart.vo.manufacturers.ManufacturerGeneralVO;
import org.kratos.kracart.vo.manufacturers.ManufacturerGridVO;
import org.kratos.kracart.vo.manufacturers.ManufacturerMetaVO;
import org.kratos.kracart.vo.manufacturers.ManufacturerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service("manufacturerService")
public class ManufacturerServiceImpl implements ManufacturerService {
	
	@Autowired
	private ManufacturerModel manufacturerModel;

	@Override
	public List<ManufacturerGridVO> getManufacturers(String start, String limit) {
		List<ManufacturerGridVO> manufacturers = new ArrayList<ManufacturerGridVO>();
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("start", start == null ? null : Integer.parseInt(start));
		criteria.put("limit", limit == null ? null : Integer.parseInt(limit));
		List<Manufacturer> manufacturerList = manufacturerModel.getManufacturers(criteria);
		if(manufacturerList != null && manufacturerList.size() > 0) {
			for (Manufacturer manufacturer : manufacturerList) {
				ManufacturerGridVO record = new ManufacturerGridVO();
				record.setManufacturerId(manufacturer.getId());
				record.setManufacturerName(manufacturer.getName());
				record.setUrlClicked(manufacturerModel.getSumClicks(manufacturer.getId()));
				manufacturers.add(record);
			}
		}
		return manufacturers;
	}

	@Override
	public int getTotal() {
		return getManufacturers(null, null).size();
	}

	@Override
	public boolean saveManufacturer(ManufacturerVO data) {
		MultipartFile image = data.getGeneral().getManufacturerImage();
		String imageName = null;
		if(!image.isEmpty()) {
			imageName = image.getOriginalFilename();
			try {
				FileUtils.copyInputStreamToFile(image.getInputStream(), new File(data.getImagePath(), imageName));
			} catch (IOException e) {
				return false;
			}
		}
		Timestamp current = new Timestamp(new Date().getTime());
		Manufacturer newManufacturer = new Manufacturer();
		newManufacturer.setName(data.getGeneral().getManufacturerName());
		if(StringUtils.hasLength(imageName)) {
			newManufacturer.setImage(imageName);
		}
		newManufacturer.setDateUpdated(current);
		Integer id = data.getManufacturerId();
		if(id == null || id.intValue() == 0) {
			newManufacturer.setDateAdded(current);
			manufacturerModel.insertManufacturer(newManufacturer);
		} else {
			newManufacturer.setId(id);
			manufacturerModel.updateManufacturer(newManufacturer);
		}
		Map<Integer, ManufacturerMetaVO> metaInfo = data.getMeta();
		for (Integer languageId : metaInfo.keySet()) {
			ManufacturerMetaVO item = metaInfo.get(languageId);
			ManufacturerInfo newManufacturerInfo = new ManufacturerInfo();
			newManufacturerInfo.setId(newManufacturer.getId());
			newManufacturerInfo.setLanguageId(languageId);
			newManufacturerInfo.setUrl(data.getGeneral().getManufacturerUrl().get(languageId));
			newManufacturerInfo.setFriendlyUrl(item.getManufacturerFriendlyUrl());
			newManufacturerInfo.setPageTitle(item.getPageTitle());
			newManufacturerInfo.setMetaKeywords(item.getMetaKeywords());
			newManufacturerInfo.setMetaDescription(item.getMetaDescription());
			if(id == null || id.intValue() == 0) {
				manufacturerModel.insertManufacturerInfo(newManufacturerInfo);
			} else {
				manufacturerModel.updateManufacturerInfo(newManufacturerInfo);
			}
		}
		return true;
	}
	
	@Override
	public int deleteManufacturer(String[] idArray) {
		if(idArray != null && idArray.length > 0) {
			for (String element : idArray) {
				int id = Integer.parseInt(element);
				manufacturerModel.deleteManufacturer(id);
				manufacturerModel.deleteManufacturerInfo(id);
			}
			return 1;
		}
		return 0;
	}
	
	@Override
	public Map<String, Object> loadManufacturer(int manufacturerId) {
		ManufacturerVO manufacturer = getManufacturer(manufacturerId);
		return convertManufacturerBean(manufacturer);
	}

	private ManufacturerVO getManufacturer(int manufacturerId) {
		ManufacturerVO data = new ManufacturerVO();
		Manufacturer manufacturer = manufacturerModel.getManufacturerData(manufacturerId);
		data.setManufacturerId(manufacturer.getId());
		data.setManufacturerImage(manufacturer.getImage());
		ManufacturerGeneralVO general = new ManufacturerGeneralVO();
		general.setManufacturerName(manufacturer.getName());
		Map<Integer, String> manufacturerUrl = new HashMap<Integer, String>();
		Map<Integer, ManufacturerMetaVO> metaInfo = new HashMap<Integer, ManufacturerMetaVO>();
		List<ManufacturerInfo> manufacturerInfo = manufacturer.getInfos();
		if(manufacturerInfo != null && manufacturerInfo.size() > 0) {
			for (ManufacturerInfo info : manufacturerInfo) {
				manufacturerUrl.put(info.getLanguageId(), info.getUrl());
				ManufacturerMetaVO meta = new ManufacturerMetaVO();
				meta.setManufacturerFriendlyUrl(info.getFriendlyUrl());
				meta.setMetaDescription(info.getMetaDescription());
				meta.setMetaKeywords(info.getMetaKeywords());
				meta.setPageTitle(info.getPageTitle());
				metaInfo.put(info.getLanguageId(), meta);
			}
		}
		general.setManufacturerUrl(manufacturerUrl);
		data.setGeneral(general);
		data.setMeta(metaInfo);
		return data;
	}
	
	private Map<String, Object> convertManufacturerBean(ManufacturerVO source) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());  
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
	        for (PropertyDescriptor property : propertyDescriptors) {  
	            String key = property.getName();  
	            if (key.equals("class")) {  
	            	continue;
	            }
	            Method getter = property.getReadMethod();  
	            Object value = getter.invoke(source);
	            if(value instanceof String || value instanceof Integer) {
	            	map.put(key, value);
	            } else {
	            	if("general".equals(key)) {
	            		map.putAll(convertManufacturerBean(((ManufacturerGeneralVO) value)));
	            	} else if("meta".equals(key)) {
	            		Map meta = (Map) value;
	            		for (Object index : meta.keySet()) {
	            			String metaKey = "meta[" + index + "]";
	            			map.putAll(convertManufacturerBean(metaKey, (ManufacturerMetaVO) meta.get(index)));
	            		}
	            	}
	            }
	        }
		} catch(Exception e) {
			
		}
		return map;
	}
	
	private Map<String, Object> convertManufacturerBean(ManufacturerGeneralVO source) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());  
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
	        for (PropertyDescriptor property : propertyDescriptors) {  
	            String key = property.getName();  
	            if (key.equals("class")) {  
	            	continue;
	            }
	            Method getter = property.getReadMethod();  
	            Object value = getter.invoke(source);
	            if(value instanceof String) {
	            	map.put("general." + key, value);
	            } else if(value instanceof Map) {
	            	Map url = (Map) value;
	            	for (Object index : url.keySet()) {
	            		map.put("general." + key + "[" + index + "]", url.get(index));
					}
	            }
	        }
		} catch(Exception e) {
			
		}
		return map;
	}
	
	private Map<String, Object> convertManufacturerBean(String metaKey, ManufacturerMetaVO source) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());  
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
	        for (PropertyDescriptor property : propertyDescriptors) {  
	            String key = property.getName();  
	            if (key.equals("class")) {  
	            	continue;
	            }
	            Method getter = property.getReadMethod();  
	            Object value = getter.invoke(source);
	            map.put(metaKey + "." + key, value);
	        }
		} catch(Exception e) {
			
		}
		return map;
	}
}
