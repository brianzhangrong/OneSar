package com.ihomefnt.module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ihomefnt.module.common.api.impl.ModuleLoaderImpl;
import com.ihomefnt.module.common.api.impl.ModuleManagerImpl;

@Configuration
public class ModuleBootConfig {

	@Bean
	public ModuleLoaderImpl moduleLoaderConfig() {
		return new ModuleLoaderImpl();
	}

	@Bean
	public ModuleManagerImpl moduleManagerConfig() {
		return new ModuleManagerImpl();
	}

}
