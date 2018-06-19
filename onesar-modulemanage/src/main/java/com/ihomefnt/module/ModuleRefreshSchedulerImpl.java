package com.ihomefnt.module;

import java.net.URL;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.ihomefnt.module.common.api.ModuleConfig;
import com.ihomefnt.module.common.api.impl.AbstractModuleRefreshScheduler;

public class ModuleRefreshSchedulerImpl extends AbstractModuleRefreshScheduler {

	@Override
	public List<ModuleConfig> queryModuleConfigs() {
		return ImmutableList.of();// ModuleManagerTest.buildModuleConfig());
	}

	public static ModuleConfig buildModuleConfig() {
		URL demoModule = Thread.currentThread().getContextClassLoader().getResource("lib/irayproxy.jar");
		ModuleConfig moduleConfig = new ModuleConfig();
		moduleConfig.setName("demo");
		moduleConfig.setEnabled(true);
		moduleConfig.setVersion("1.0.0.20170621");
		moduleConfig.setProperties(ImmutableMap.of("server.port", 8888));
		moduleConfig.setModuleUrl(ImmutableList.of(demoModule));
		return moduleConfig;
	}

}
