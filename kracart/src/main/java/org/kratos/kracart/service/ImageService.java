package org.kratos.kracart.service;

import java.util.List;
import java.util.ResourceBundle;

import org.kratos.kracart.vo.ImageGroupVO;
import org.kratos.kracart.vo.ImageVO;

public interface ImageService {
	
	public List<ImageVO> getImageList(ResourceBundle bundle);
	public List<ImageGroupVO> countImages(String rootPath, int languageId);
}
