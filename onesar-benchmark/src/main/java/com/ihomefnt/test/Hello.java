package com.ihomefnt.test;

import org.springframework.stereotype.Service;

import com.ihomefnt.module.common.api.Action;

/**
 * Hello world!
 *
 */
@Service
public class Hello implements Action<String, String> {
	public String hello(String name) {
		String ret = "##########" + name;
		return ret;
	}

	@Override
	public String execute(String actionRequest) {
		return hello(actionRequest);
	}

	@Override
	public String getActionName() {
		return "hello";
	}
}
