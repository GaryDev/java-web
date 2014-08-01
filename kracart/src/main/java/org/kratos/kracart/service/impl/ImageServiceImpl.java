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
import org.kratos.kracart.utility.ImageUtils;
import org.kratos.kracart.vo.images.ImageCounterVO;
import org.kratos.kracart.vo.images.ImageGroupVO;
import org.kratos.kracart.vo.images.ImageToolVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("imageService")
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ProductImageModel productImageModel;
	
	private String originalImageGroup;
	
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
					counter.put(title, data);
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
	
	public List<ImageCounterVO> resizeImages(List<String> groups, boolean overwrite, String rootPath, int languageId) {
		List<ImageCounterVO> records = new ArrayList<ImageCounterVO>();
		Map<String, Integer> counter = new HashMap<String, Integer>();
		List<ProductImage> productImages = productImageModel.getImages();
		List<ProductImageGroup> productImageGroups = productImageModel.getImageGroups(languageId);
		if(productImages != null) {
			for (ProductImage image : productImages) {
				for (ProductImageGroup group : productImageGroups) {
					if(group.getId() != 1 && groups.contains(String.valueOf(group.getId()))) {
						String title = group.getTitle();
						Integer count = null;
						if(counter.containsKey(title)) {
							count = counter.get(title);
						} else {
							count = 0;
						}
						File file = new File(rootPath + "/assets/images/products/" + group.getCode() + "/" + image.getImage());
						if(overwrite || !file.exists()) {
							if(resizeImage(group, rootPath, image.getImage(), "products")) {
								count = count.intValue() + 1;
							}
						}
						counter.put(title, count);
					} else if(group.getId() == 1) {
						originalImageGroup = group.getCode();
					}
				}
			}
		}
		if(counter.size() > 0) {
			for (String key : counter.keySet()) {
				ImageCounterVO data = new ImageCounterVO();
				data.setGroup(key);
				data.setCount(Integer.toString(counter.get(key).intValue()));
				records.add(data);
			}
		}
		return records;
	}
	
	private boolean resizeImage(ProductImageGroup group, String rootPath, String image, String type) {
		if(!StringUtils.hasLength(type)) {
			type = "products";
		}
		String path = rootPath + "/assets/images/" + type + "/";
		File dir = new File(path + group.getCode());
		if(!dir.exists()) {
			dir.mkdir();
		}
		String originalPath = path + originalImageGroup + "/" + image;
		String destPath = path + group.getCode() + "/" + image;
		if(new File(originalPath).exists()) {
			try {
				return ImageUtils.resizeImage(originalPath, destPath, group.getSizeWidth(), group.getSizeHeight(), false);
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
	
}
