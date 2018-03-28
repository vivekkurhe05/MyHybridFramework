package com.hybridFramework.helper.Logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.hybridFramework.utility.ResourceHelper;



public class LoggerHelper {

	private static boolean root = false;

	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class clas){
		if (root) {
			return Logger.getLogger(clas);
		}
		//String log4jLOcation = System.getProperty("user.dir")+"/src/main/resources/log4j.properties";
		
		//ResourceHelper is a user defined class
		PropertyConfigurator.configure(ResourceHelper.getResourcePath("/src/main/resources/log4j.properties"));
		root = true;
		return Logger.getLogger(clas);
}
	
}
