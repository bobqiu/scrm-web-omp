package net.wildpig.base.common.conf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @FileName SystemConfig.java
 * @Description: 
 *
 * @Date Sep 17, 2015 
 * @author YangShengJun
 * @version 1.0
 * 
 */
public abstract class SystemConfig {

	protected final Log LOGGER = LogFactory.getLog(getClass());
	public final static String SYSTEM_KEY = "system"; // 系统ID

	private static SystemConfig INSTANCE;

	public static void setInstance(SystemConfig instance) {
		SystemConfig.INSTANCE = instance;
	}

	protected static synchronized SystemConfig getInstance() {
		if (INSTANCE == null) {
			throw new RuntimeException("SystemConfig.INSTANCE 未初始化");
		}
		return SystemConfig.INSTANCE;
	}

	/**
	 * 获得当前系统ID
	 * 
	 * @return String
	 * @see 参考的JavaDoc
	 */
	public static String getSystemId() {
		return getInstance().getConfigSystemId();
	}

	/**
	 * 获得系统配置属性，允许属性嵌套
	 * 
	 * @param name
	 *            ：属性名称
	 * @return String
	 * @see 参考的JavaDoc
	 */
	public static String getProperty(String name) {
		return getInstance().getConfigProperty(name);
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
	public static String getProperty(String name, String defaultValue) {
		return getInstance().getConfigProperty(name, defaultValue);
	}

	/**
	 * 获得int型的系统配置属性，没有则返回0
	 * 
	 * @param name
	 *            ：属性名称
	 * @return int
	 * @see 参考的JavaDoc
	 */
	public static int getIntProperty(String name) {
		return getInstance().getConfigIntProperty(name);
	}

	public static int getIntProperty(String name, int defaultValue) {
		return getInstance().getConfigIntProperty(name, defaultValue);
	}

	public static long getLongProperty(String name, long defaultValue) {
		return getInstance().getConfigLongProperty(name, defaultValue);
	}

	/**
	 * 获得long型的系统配置属性，没有则返回0
	 * 
	 * @param name
	 *            ：属性名称
	 * @return long
	 * @see 参考的JavaDoc
	 */
	public static long getLongProperty(String name) {
		return getInstance().getConfigLongProperty(name);
	}

	public abstract String getConfigSystemId();

	/**
	 * 获得系统配置属性，允许属性嵌套
	 * 
	 * @param name
	 *            ：属性名称
	 * @return String
	 * @see 参考的JavaDoc
	 */
	public String getConfigProperty(String name) {
		return getConfigProperty(name, null);
	}

	/**
	 * 获得String型的系统配置属性，如果没有，则返回默认值
	 * 
	 * @param name
	 *            属性名称
	 * @param defaultValue
	 *            默认值
	 * @return String
	 * @see 参考的JavaDoc
	 */
	public String getConfigProperty(String name, String defaultValue) {
		String value = getProperty(name);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	/**
	 * 获得int型的系统配置属性，没有则返回0
	 * 
	 * @param name
	 *            属性名称
	 * @return int
	 * @see 参考的JavaDoc
	 */
	public int getConfigIntProperty(String name) {
		return getIntProperty(name, 0);
	}

	/**
	 * 获得int型的系统配置属性，如果没有，则返回默认值
	 * 
	 * @param name
	 *            属性名称
	 * @param defaultValue
	 *            默认值
	 * @return int
	 * @see 参考的JavaDoc
	 */
	public int getConfigIntProperty(String name, int defaultValue) {
		String value = getProperty(name);
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
	 * 获得long型的系统配置属性，没有则返回0
	 * 
	 * @param name
	 *            ：属性名称
	 * @return long
	 * @see 参考的JavaDoc
	 */
	public long getConfigLongProperty(String name) {
		return getLongProperty(name, 0L);
	}

	/**
	 * 获得long型的系统配置属性，如果没有，则返回默认值
	 * 
	 * @param name
	 *            属性名称
	 * @param defaultValue
	 *            默认值
	 * @return long
	 * @see 参考的JavaDoc
	 */
	public long getConfigLongProperty(String name, long defaultValue) {
		String value = getProperty(name);
		if (value == null) {
			return defaultValue;
		} else {
			try {
				return Long.parseLong(value);
			} catch (NumberFormatException e) {
				LOGGER.error("Value [" + value + "] of Property [" + name + "] must be long;Use defaultValue[" + defaultValue + "]");
				return defaultValue;
			}
		}
	}
}
