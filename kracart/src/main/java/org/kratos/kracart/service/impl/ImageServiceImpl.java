package org.kratos.kracart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.kratos.kracart.service.ImageService;
import org.kratos.kracart.vo.ImageVO;
import org.springframework.stereotype.Service;

@Service("imageService")
public class ImageServiceImpl implements ImageService {
	
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
	
}
