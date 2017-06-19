package io.raptor.web;

import io.raptor.utils.XmlParser;

public class Config {

	public static int objectTimeOut;
	public static int pageLoadTimeout;
	public static int retryTimes;
	public static String objectRespository;
	
	static{
		XmlParser xp = new XmlParser("config.xml");
		objectTimeOut = Integer.parseInt(xp.getElementText("/config/objectTimeOut"));
		pageLoadTimeout = Integer.parseInt(xp.getElementText("/config/pageLoadTimeout"));
		retryTimes = Integer.parseInt(xp.getElementText("/config/retryTimes"));
		objectRespository = xp.getElementText("/config/objectRespository");
	}
	
}
