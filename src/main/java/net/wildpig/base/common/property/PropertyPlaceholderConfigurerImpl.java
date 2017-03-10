package net.wildpig.base.common.property;

import java.util.Enumeration;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyPlaceholderConfigurerImpl extends PropertyPlaceholderConfigurer {

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
		Enumeration<Object> keys = props.keys();

		PropertyPlaceHelper pHelper = new PropertyPlaceHelper(props);
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			System.setProperty(key, pHelper.getProperty(key));
		}
		super.processProperties(beanFactory, props);
	}
}
