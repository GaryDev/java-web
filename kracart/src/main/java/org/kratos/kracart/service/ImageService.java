package org.kratos.kracart.service;

import java.util.List;
import java.util.ResourceBundle;

import org.kratos.kracart.vo.ImageCounterVO;
import org.kratos.kracart.vo.ImageToolVO;
import org.kratos.kracart.vo.ImageGroupVO;

public interface ImageService {
	
	public List<ImageToolVO> getImageList(ResourceBundle bundle);
	public List<ImageCounterVO> countImages(String rootPath, int languageId);
	public  List<ImageGroupVO> getProductImageGroup(int languageId);
	public List<ImageCounterVO> resizeImages(List<String> groups, boolean overwrite, String rootPath, int languageId);
}
