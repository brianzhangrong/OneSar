package com.ihomefnt.module;

import com.google.common.base.Splitter;
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
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

  public static void main(String[] args) throws Exception {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
        ModuleBootConfig.class);
    ModuleManager moduleManager = context.getBean(ModuleManager.class);
    ModuleLoader moduleLoader = context.getBean(ModuleLoader.class);

    DirClassLoader loader = new DirClassLoader(
        "/Users/zhangrong/Documents/lib");
    Map<String,Module> moduleMap = Maps.newHashMap();
    URL[] urls = loader.getUrls();
    for (URL url : urls) {
      String jarName = url.getFile().substring(url.getFile().lastIndexOf("/") + 1)
          .replace(".jar", "");
      String version = Splitter.on("_").splitToList(jarName).get(1);

      String name = Splitter.on("_").splitToList(jarName).get(0);
      ModuleConfig moduleConfig = new ModuleConfig();
      moduleConfig.setEnabled(true);
      moduleConfig.addScanPackage("com.ihomefnt");
      moduleConfig.setOverridePackages(Lists.newArrayList("com.ihomefnt"));
      moduleConfig.setModuleUrl(Lists.newArrayList(url));
      moduleConfig.setName(name);
      moduleConfig.setVersion(version);
      moduleManager.activeVersion(name, version);
      Module module = moduleLoader.load(moduleConfig);

      Module register = moduleManager.register(module);
      System.out.println("#######name:"+name+",version:"+version+",register:"+module+","+register);
      moduleMap.put(jarName, module);
    }
    Module module5 = moduleManager.find("irayproxy", "5555");
    System.out.println("######module5:"+module5);
   // new RequestFacade(new
   //     Request())
    Object oA = module5.doAction("start", "");
    System.out.println("oA:"+oA);
    Module module7 = moduleManager.find("irayproxy", "7777");
//    System.out.println("module7:"+module7);
//    Object oB = module7.doAction("start", new RequestFacade(new
//        Request()));
//    System.out.println("oB:"+oB);


//    Module irayproxy = moduleManager.remove("irayproxy", "5555");
//    moduleLoader.unload(irayproxy);
//    moduleManager.activeVersion("irayproxy", "7777");
//    System.out.println("remove:"+irayproxy+"active");
  }
}
