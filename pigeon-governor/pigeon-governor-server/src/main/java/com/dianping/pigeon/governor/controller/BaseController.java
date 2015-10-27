package com.dianping.pigeon.governor.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.dianping.pigeon.governor.util.Constants;
import com.dianping.pigeon.governor.util.IPUtils;

/**
 * 
 * @author chenchongze
 *
 */
public class BaseController {

	public void commonnav(Map<String, Object> map, HttpServletRequest request) {
		String currentUser = (String) request.getSession().getAttribute(Constants.DP_ACCOUNT);
		map.put("currentUser", currentUser);
	}

	public void commonnav(ModelMap modelMap, HttpServletRequest request) {
		String currentUser = (String) request.getSession().getAttribute(Constants.DP_ACCOUNT);
		modelMap.addAttribute("currentUser", currentUser);
	}

	public void verifyIdentity(int agentId) throws Exception{

		if(3 != agentId){
			throw new Exception(String.format("agentId %d is not system level", agentId));
		}

	}

	public void verifyIdentity(HttpServletRequest request, int agentId) throws Exception{
		
		String ip = IPUtils.getUserIP(request);
		if(!"0:0:0:0:0:0:0:1%0".equals(ip)){
			throw new Exception("You have no privilege.");
		}
		
		verifyIdentity(agentId);
		
	}
}