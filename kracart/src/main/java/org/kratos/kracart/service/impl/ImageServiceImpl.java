package org.kratos.kracart.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.kratos.kracart.entity.ProductImage;
import org.kratos.kracart.entity.ProductImageGroup;
import org.kratos.kracart.model.ProductImageModel;
import org.kratos.kracart.service.ImageService;
import org.kratos.kracart.vo.ImageCounterVO;
import org.kratos.kracart.vo.ImageToolVO;
import org.kratos.kracart.vo.ImageGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("imageService")
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ProductImageModel productImageModel;
	
	public List<ImageToolVO> getImageList(ResourceBundle bundle) {
		ImageToolVO[] values = new ImageToolVO[] { 
				new ImageToolVO(bundle.getString("images_check_title"), "checkimages"), 
				new ImageToolVO(bundle.getString("images_resize_title"), "resizeimages") };
		List<ImageToolVO> records = new ArrayList<ImageToolVO>();
		for (int i = 0; i < values.length; i++) {
			records.add(values[i]);
		}
		return records;
	}
	
	public List<ImageCounterVO> countImages(String rootPath, int languageId) {
		List<ImageCounterVO> records = new ArrayList<ImageCounterVO>();
		Map<String, ImageCounterVO> counter = new HashMap<String, ImageCounterVO>();
		List<ProductImage> productImages = productImageModel.getImages();
		List<ProductImageGroup> productImageGroups = productImageModel.getImageGroups(languageId);
		if(productImages != null) {
			for (ProductImage image : productImages) {
				for (ProductImageGroup group : productImageGroups) {
					String title = group.getTitle();
					ImageCounterVO data = null;
					if(counter.containsKey(title)) {
						data = counter.get(title);
					} else {
						data = new ImageCounterVO();
					}
					data.setRecords(data.getRecords() + 1);
					File file = new File(rootPath + "/assets/images/products/" + group.getCode() + "/" + image.getImage());
					if(file.exists()) {
						data.setExisting(data.getExisting() + 1);
					}
					counter.put(group.getTitle(), data);
				}
			}
		}
		if(counter.size() > 0) {
			for (String key : counter.keySet()) {
				ImageCounterVO data = new ImageCounterVO();
				data.setGroup(key);
				data.setCount(counter.get(key).getExisting() + "/" + counter.get(key).getRecords());
				records.add(data);
			}
		}
		return records;
	}
	
	public List<ImageGroupVO> getProductImageGroup(int languageId) {
		List<ImageGroupVO> records = new ArrayList<ImageGroupVO>();
		List<ProductImageGroup> productImageGroups = productImageModel.getImageGroups(languageId);
		for (ProductImageGroup group : productImageGroups) {
			if(group.getId() != 1) {
				ImageGroupVO data = new ImageGroupVO();
				data.setId(group.getId());
				data.setText(group.getTitle());
				records.add(data);
			}
		}
		return records;
	}
	
}
