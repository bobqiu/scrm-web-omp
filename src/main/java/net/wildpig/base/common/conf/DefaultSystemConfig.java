package net.wildpig.base.common.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @FileName DefaultSystemConfig.java
 * @Description: 
 *
 * @Date Sep 17, 2015 
 * @author YangShengJun
 * @version 1.0
 * 
 */
public class DefaultSystemConfig extends SystemConfig {

	private final static Log LOGGER = LogFactory.getLog(DefaultSystemConfig.class);
	private final static String ENV_KEY = "env"; // 当前属于哪个环境

	final static String SYSTEM_ID;
	private static Properties properties = new Properties();
	private static VariableExpander variableExpander = new VariableExpander(properties);
	static {
		// 顺序检查加载
		// config.properties
		// config.xml
		// config-{evn}.properties
		// config-{env}.xml
		String configFilePrefix = "config";
		System.out.println("\n\n\n================\n\n\n");
		InputStream propertiesEnv = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFilePrefix + ".properties");
		if (propertiesEnv != null) {
			try {
				properties.load(propertiesEnv);
				LOGGER.info("Load SystemConfig[" + configFilePrefix + ".properties] Success");
			} catch (IOException e) {
				LOGGER.error("Error When Load SystemConfig[" + configFilePrefix + ".properties]", e);
			}
		} else {
			LOGGER.info("SystemConfig[" + configFilePrefix + ".properties] Not Found!");
		}

		InputStream xmlIS = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFilePrefix + ".xml");
		if (xmlIS != null) {
			try {
				properties.loadFromXML(xmlIS);
				LOGGER.info("Load SystemConfig[" + configFilePrefix + ".xml] Success");
			} catch (IOException e) {
				LOGGER.error("Error When Load SystemConfig[" + configFilePrefix + ".xml]", e);
			}
		} else {
			LOGGER.info("SystemConfig[" + configFilePrefix + ".xml] Not Found!");
		}

		String env = System.getenv(ENV_KEY);
		if (env == null || env.length() == 0) {
			env = System.getProperty(ENV_KEY);
		}

		if (env == null) {
			throw new IllegalArgumentException("environment variable(env) is miss!");
		} else {
			configFilePrefix = "config-" + env;
		}

		InputStream propertiesEnvIS = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFilePrefix + ".properties");
		if (propertiesEnvIS != null) {
			try {
				properties.load(propertiesEnvIS);
				LOGGER.info("Load SystemConfig[" + configFilePrefix + ".properties] Success");
			} catch (IOException e) {
				LOGGER.error("Error When Load SystemConfig[" + configFilePrefix + ".properties]", e);
			}
		} else {
			LOGGER.info("SystemConfig[" + configFilePrefix + ".properties] Not Found!");
		}

		InputStream xmlEnvIS = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFilePrefix + ".xml");
		if (xmlEnvIS != null) {
			try {
				properties.loadFromXML(xmlEnvIS);
				LOGGER.info("Load SystemConfig[" + configFilePrefix + ".xml] Success");
			} catch (IOException e) {
				LOGGER.error("Error When Load SystemConfig[" + configFilePrefix + ".xml]", e);
			}
		} else {
			LOGGER.info("SystemConfig[" + configFilePrefix + ".xml] Not Found!");
		}

		String systemId = properties.getProperty(SYSTEM_KEY);
		if (systemId == null || systemId.length() == 0) {
			systemId = System.getenv(SYSTEM_KEY);
		}

		if (systemId == null || systemId.length() == 0) {
			systemId = System.getProperty(SYSTEM_KEY);
		}

		SYSTEM_ID = systemId;

		SystemConfig.setInstance(new DefaultSystemConfig());

		LOGGER.info("SystemId: " + SYSTEM_ID);
	}

	public String getConfigSystemId() {
		return SYSTEM_ID;
	}

	public String getConfigProperty(String name) {
		return getConfigProperty(name, null);
	}

	/**
	 * 获得int型的系统配置属性，如果没有，则返回默认值
	 * 
	 * @param name
	 *            ：属性名称
	 * @param defaultValue
	 *            ：默认值
	 * @return int
	 * @see 参考的JavaDoc
	 */
	public int getConfigIntProperty(String name, int defaultValue) {
		String value = getConfigProperty(name);
		if (value == null) {
			return defaultValue;
		} else {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				LOGGER.error("Value [" + value + "] of Property [" + name + "] must be integer;Use defaultValue[" + defaultValue + "]");
				return defaultValue;
			}
		}
	}

	/**
	 * 获得系统配置属性，如果没有，则返回默认值
	 * 
	 * @param name
	 *            ：属性名称
	 * @param defaultValue
	 *            ：默认值
	 * @return String
	 * @see 参考的JavaDoc
	 */
	public String getConfigProperty(String name, String defaultValue) {
		String value = System.getProperty(name);
		if (value == null) {
			value = System.getenv(name);
		}

		if (value == null) {
			value = variableExpander.getValue(name);
		}

		if (value == null || value.length() == 0) {
			return defaultValue;
		} else {
			return value;
		}
	}

	public static Properties getProperties() {
		return properties;
	}

	public static void setProperties(Properties properties) {
		DefaultSystemConfig.properties = properties;
	}
}
