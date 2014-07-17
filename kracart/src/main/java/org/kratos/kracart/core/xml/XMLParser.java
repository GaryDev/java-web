package org.kratos.kracart.core.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class XMLParser {
	
	private File file;
	
	public XMLParser(String path) throws Exception {
		this(new File(path));
	}
	
	public XMLParser(File file) throws Exception {
		this.file = file;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T unmarshall(Class<T> clazz) throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		return (T) unmarshaller.unmarshal(file);
	}

}
