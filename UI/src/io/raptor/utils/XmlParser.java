package io.raptor.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//poi-examples-3.8xx.jar
import org.apache.poi.hssf.usermodel.examples.NewLinesInCells;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.openqa.selenium.support.ui.LoadableComponent;

public class XmlParser {

	private Document document;
		
	public XmlParser(String path){
		load(path);
	}
	
	private void load(String path){
		SAXReader reader = new SAXReader();
		File file =  new File(path);
		if(file.exists()){
			try {
				this.document = reader.read(file);
				
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("待解析的xml "+path+" 文件不存在");
		}
	}
	
	public Boolean isExist(String xpath){
		Element element = getElement(xpath);
		if(element!=null){
			return true;
		}else{
			return false;
		}
	}
	
	public Element getElement(String xpath){
		return (Element) document.selectSingleNode(xpath);
	}
	
	public String getElementText(String xpath){
		Element element = (Element) document.selectSingleNode(xpath);
		if(element==null){
			System.out.println("查找对象"+xpath+"不存在");
			return null;
		}else{
			return element.getText();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Element> getElements(String xpath){
		return document.selectNodes(xpath);
	}
	
	public List<String> getElementsText(String xpath){
		List<String> resultList = new ArrayList<String>();
		@SuppressWarnings("unchecked")
		List<Element> list = document.selectNodes(xpath);		
		for (Element element : list) {
			if(element!=null){
				resultList.add(element.getText());
			}
		}
		return resultList;
		
	}
	
	public Map<String, String> getChildElementInfo(String xapth){
		Map<String, String> result = new LinkedHashMap<String,String>();	
		List<Element> list = getElement(xapth).elements();
		for (Element element : list) {
			result.put(element.getName(), element.getText());
		}		
		return result;
	}
	
	public Map<String, String> getChildElementInfo(Element element){
		Map<String, String> result = new LinkedHashMap<String,String>();
		List<Element> list = element.elements();
		for (Element e : list) {
			result.put(e.getName(), e.getText());
		}		
		return result;
		
	}
	
	public Map<String, String> mapMerge(Map<String, String> map1, Map<String, String> map2){
		for (String key : map1.keySet()) {
			if(!map2.containsKey(key)){
				map2.put(key, map1.get(key));
			}
		}
		return map2;
	}
	
	
	
}
