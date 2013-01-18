package com.force.aus.outboundMessage;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppProperties {

	public static String DEV_DBASE_URL = "dev.dbase.url";
	public static String PROPS_FILE = "app.properties";
	public static String MESSAGE_TIMEZONE = "dbase.date.timezone";
	private static Logger logger;
	
	public static Properties props;
	
	public static void loadProperties() {
		logger = LoggerFactory.getLogger(AppProperties.class);
		try {
			props = new Properties();
			props.load(AppProperties.class.getClassLoader().getResourceAsStream(PROPS_FILE));
			
			logger.info("Loaded application properties");
			logger.info("{} - {}",DEV_DBASE_URL, getPropValue(DEV_DBASE_URL));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static String getPropValue(String prop) {
		return props.getProperty(prop);
	}
}
