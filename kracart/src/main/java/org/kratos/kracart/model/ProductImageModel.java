package org.kratos.kracart.model;

import java.util.List;

import org.kratos.kracart.entity.ProductImage;
import org.kratos.kracart.entity.ProductImageGroup;

public interface ProductImageModel {
	
	public List<ProductImageGroup> getImageGroups(int langId);
	public List<ProductImage> getImages();

}
