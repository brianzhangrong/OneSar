/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.ihomefnt.module.common.api.impl;

import com.ihomefnt.module.common.api.Module;
import com.ihomefnt.module.common.api.ToStringObject;
import java.util.concurrent.ConcurrentHashMap;

/**
 * module in runtime
 */
public class RuntimeModule extends ToStringObject {

  /**
   * all version module,key:version
   */
  private final ConcurrentHashMap<String, Module> modules = new ConcurrentHashMap();
  private String name;
  private String defaultVersion;
  /**
   * load module error msg
   */
  private String errorContext;

  public Module getModule(String version) {
    return modules.get(version);
  }

  public Module getDefaultModule() {
    return modules.get(getDefaultVersion());
  }

  public RuntimeModule addModule(Module module) {
    modules.put(module.getVersion(), module);
    return this;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RuntimeModule withName(String name) {
    this.name = name;
    return this;
  }

  public String getDefaultVersion() {
    return defaultVersion;
  }

  public void setDefaultVersion(String defaultVersion) {
    this.defaultVersion = defaultVersion;
  }

  public RuntimeModule withDefaultVersion(String defaultVersion) {
    setDefaultVersion(defaultVersion);
    return this;
  }

  public ConcurrentHashMap<String, Module> getModules() {
    return modules;
  }

  public String getErrorContext() {
    return errorContext;
  }

  public void setErrorContext(String errorContext) {
    this.errorContext = errorContext;
  }

}