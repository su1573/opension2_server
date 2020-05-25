package com.chinare.opension2.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
class WebConfigurer extends WebMvcConfigurerAdapter {
	@Autowired
	GeneratorConfig generatorConfig;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/files/**").addResourceLocations("file:///"+generatorConfig.getUploadPath());
	}

}
