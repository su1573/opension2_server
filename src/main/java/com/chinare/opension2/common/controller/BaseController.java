package com.chinare.opension2.common.controller;

import com.chinare.opension2.common.utils.ShiroUtils;
import com.chinare.opension2.system.domain.UserDO;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}
