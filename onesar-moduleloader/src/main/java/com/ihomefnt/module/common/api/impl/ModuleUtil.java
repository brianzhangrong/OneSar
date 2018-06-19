package com.ihomefnt.module.common.api.impl;

import com.ihomefnt.module.common.api.Module;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * 模块工具类
 */
public class ModuleUtil {

  public static final String MODULE_PROPERTY_PLACEHOLDER_CONFIGURER = "modulePropertyPlaceholderConfigurer";
  private static final Logger LOGGER = LoggerFactory.getLogger(ModuleUtil.class);

  /**
   * 将模块的属性配置设置到PropertyPlaceholderConfigurer里，并把PropertyPlaceholderConfigurer注册到上下文中
   */
  static BeanDefinitionRegistry registerModulePropertiesPlaceHolderConfigurer(
      BeanDefinitionRegistry beanFactory,
      Properties properties) {
    //通过GenericBeanDefinition创建bean
    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
    //设置bean资源属性的配置器
    beanDefinition.setBeanClass(PropertyPlaceholderConfigurer.class);
    //配置PropertyPlaceholderConfigurer的properties属性
    beanDefinition.getPropertyValues().add("properties", properties);
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("Register: {}", beanDefinition);
    }
    //注册bean
    beanFactory.registerBeanDefinition(MODULE_PROPERTY_PLACEHOLDER_CONFIGURER, beanDefinition);
    return beanFactory;
  }

  /**
   * 销毁模块，不抛出异常
   */
  static void destroyQuietly(Module module) {
    if (module != null) {
      try {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("Destroy module: {}", module.getName());
        }
        module.destroy();
      } catch (Exception e) {
        LOGGER.error("Failed to destroy module " + module, e);
      }
    }
  }
}
