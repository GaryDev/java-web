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
import org.kratos.kracart.vo.ImageGroupVO;
import org.kratos.kracart.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("imageService")
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ProductImageModel productImageModel;
	
	public List<ImageVO> getImageList(ResourceBundle bundle) {
		ImageVO[] values = new ImageVO[] { 
				new ImageVO(bundle.getString("images_check_title"), "checkimages"), 
				new ImageVO(bundle.getString("images_resize_title"), "resizeimages") };
		List<ImageVO> records = new ArrayList<ImageVO>();
		for (int i = 0; i < values.length; i++) {
			records.add(values[i]);
		}
		return records;
	}
	
	public List<ImageGroupVO> countImages(String rootPath, int languageId) {
		List<ImageGroupVO> records = new ArrayList<ImageGroupVO>();
		Map<String, ImageGroupVO> counter = new HashMap<String, ImageGroupVO>();
		List<ProductImage> productImages = productImageModel.getImages();
		List<ProductImageGroup> productImageGroups = productImageModel.getImageGroups(languageId);
		if(productImages != null) {
			for (ProductImage image : productImages) {
				for (ProductImageGroup group : productImageGroups) {
					String title = group.getTitle();
					ImageGroupVO data = null;
					if(counter.containsKey(title)) {
						data = counter.get(title);
					} else {
						data = new ImageGroupVO();
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
				ImageGroupVO data = new ImageGroupVO();
				data.setGroup(key);
				data.setCount(counter.get(key).getExisting() + "/" + counter.get(key).getRecords());
				records.add(data);
			}
		}
		return records;
	}
	
}
