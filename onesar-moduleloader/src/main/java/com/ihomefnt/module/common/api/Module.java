/*
 *
 *  * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.ihomefnt.module.common.api;

import java.util.Date;
import java.util.Map;

/**
 * JarsLink模块接口
 */
public interface Module {

  /**
   * 查找处理请求的Action
   */
  <R, T> Action<R, T> getAction(String actionName);

  /**
   * 获取全部的Action
   *
   * @return action名称和Action对象
   */
  Map<String, Action> getActions();

  /**
   * 查找处理请求的Action
   */
  <R, T> T doAction(String actionName, R actionRequest);

  /**
   * 模块名
   */
  String getName();

  /**
   * 模块版本号
   */
  String getVersion();

  /**
   * 模块的创建时间
   */
  Date getCreation();

  /**
   * 销毁
   */
  void destroy();

  /**
   * 获取spring上下
   */
  ClassLoader getChildClassLoader();

  /**
   * 获取模块配置
   */
  ModuleConfig getModuleConfig();

}
