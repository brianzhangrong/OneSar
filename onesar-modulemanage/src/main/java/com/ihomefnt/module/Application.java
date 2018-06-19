package com.ihomefnt.module;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ihomefnt.module.common.api.Module;
import com.ihomefnt.module.common.api.ModuleConfig;
import com.ihomefnt.module.common.api.ModuleLoader;
import com.ihomefnt.module.common.api.ModuleManager;
import com.ihomefnt.module.config.ModuleBootConfig;
import com.ihomefnt.module.loader.DirClassLoader;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

  public static void main(String[] args) throws Exception {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
        ModuleBootConfig.class);
    ModuleManager moduleManager = context.getBean(ModuleManager.class);
    ModuleLoader moduleLoader = context.getBean(ModuleLoader.class);

    DirClassLoader loader = new DirClassLoader(
        "/Users/zhangrong/Documents/workspace/ihomefnt/irayproxy/irayproxy/target");
    URL[] urls = loader.getUrls();
    for (URL url : urls) {
      String jarName = url.getFile().substring(url.getFile().lastIndexOf("/") + 1)
          .replace(".jar", "");
      System.out.println("#######" + jarName);
      ModuleConfig moduleConfig = new ModuleConfig();
      moduleConfig.setEnabled(true);
      moduleConfig.addScanPackage("com.ihomefnt");
      moduleConfig.setOverridePackages(Lists.newArrayList("com.ihomefnt"));
      moduleConfig.setModuleUrl(Lists.newArrayList(url));
      moduleConfig.setName(jarName);
      Map<String, Object> properties = Maps.newHashMap();
      properties.put("server.port", "7777");
      moduleConfig.setProperties(properties);
      moduleConfig.setVersion("1.0");
      Module module = moduleLoader.load(moduleConfig);

      moduleManager.register(module);
    }
    List<Module> modules = moduleManager.getModules();
    Object doAction = moduleManager.find("irayproxy").doAction("start", "server.port=2222");

    System.out.println("end msg:" + doAction);

    // SpringApplication.run(Application.class, args);
  }
}
