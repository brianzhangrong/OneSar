package com.ihomefnt.module;

import java.net.URL;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ihomefnt.module.common.api.Module;
import com.ihomefnt.module.common.api.ModuleConfig;
import com.ihomefnt.module.common.api.ModuleLoader;
import com.ihomefnt.module.common.api.ModuleManager;
import com.ihomefnt.module.loader.DirClassLoader;

/**
 * Hello world!
 */
@RestController
public class IHomeController {

	@Resource
	ModuleManager moduleManager;
	@Resource
	ModuleLoader moduleLoader;

	// module/{moduleName}/{actionName}/process.json
	@RequestMapping(value = "module", method = { RequestMethod.GET, RequestMethod.POST })
	public Object process(HttpServletRequest request, HttpServletResponse response, String moduleName,
			String actionName, String actionRequest) {
		return moduleManager.find(moduleName).doAction(actionName, actionRequest);
	}

	@RequestMapping(value = "all", method = { RequestMethod.GET, RequestMethod.POST })
	public Object all(HttpServletRequest request, HttpServletResponse response, String moduleName) {

		return moduleManager.getModules();
	}

	@RequestMapping(value = "register", method = { RequestMethod.GET, RequestMethod.POST })
	public String register(String name) throws Exception {
		ModuleConfig moduleConfig = new ModuleConfig();
		moduleConfig.setEnabled(true);
		DirClassLoader loader = new DirClassLoader("lib");
		URL[] urls = loader.getUrls();
		moduleConfig.setModuleUrl(Lists.newArrayList(urls));
		moduleConfig.setName(name);
		moduleConfig.addScanPackage("com.ihomefnt");
		Map<String, Object> properties = Maps.newHashMap();
		properties.put("name", "hey");
		moduleConfig.setProperties(properties);

		moduleConfig.setVersion("1.0");
		Module module = moduleLoader.load(moduleConfig);
		moduleManager.register(module);
		return "ok";
	}

}
