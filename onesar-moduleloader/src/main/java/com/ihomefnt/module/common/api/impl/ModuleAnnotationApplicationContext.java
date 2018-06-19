package com.ihomefnt.module.common.api.impl;

import java.util.Properties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring Annotation Application Context
 */
public class ModuleAnnotationApplicationContext extends AnnotationConfigApplicationContext {

  public ModuleAnnotationApplicationContext(Properties properties) {
    Properties springProperties = (properties == null) ? new Properties() : properties;
    ModuleUtil.registerModulePropertiesPlaceHolderConfigurer(this, springProperties);
  }
}
