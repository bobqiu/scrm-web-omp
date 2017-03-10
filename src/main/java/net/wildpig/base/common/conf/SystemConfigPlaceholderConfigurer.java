package net.wildpig.base.common.conf;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


/**
 * @FileName SystemConfigPlaceholderConfigurer.java
 * @Description: 
 *
 * @Date Sep 17, 2015 
 * @author YangShengJun
 * @version 1.0
 * 
 */
public class SystemConfigPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
	
	Properties properties = DefaultSystemConfig.getProperties();
	props.putAll(properties);
	
	super.processProperties(beanFactoryToProcess, props);
    }
}
