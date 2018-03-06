package com.mauersu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.workcenter.common.response.WorkcenterResponseBodyJson;

import com.mauersu.service.HashService;
import com.mauersu.service.SetService;
import com.mauersu.service.ZSetService;
import com.mauersu.util.Constant;
import com.mauersu.util.RedisApplication;

@Controller
@RequestMapping("/zset")
public class ZSetConroller extends RedisApplication implements Constant {
	
	@Autowired
	private ZSetService zsetService;
	
	@RequestMapping(value="/delZSetValue", method=RequestMethod.POST)
	@ResponseBody
	public Object delZSetValue(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String serverName, 
			@RequestParam int dbIndex,
			@RequestParam String key,
			@RequestParam String dataType,
			@RequestParam String member) {
		
		zsetService.delZSetValue(serverName, dbIndex, key, member);
		
		return WorkcenterResponseBodyJson.custom().build();
	}
	
	@RequestMapping(value="/updateZSetValue", method=RequestMethod.POST)
	@ResponseBody
	public Object updateZSetValue(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String serverName, 
			@RequestParam int dbIndex,
			@RequestParam String key,
			@RequestParam String dataType,
			@RequestParam double score,
			@RequestParam String member) {
		
		zsetService.updateZSetValue(serverName, dbIndex, key, score, member);
		
		return WorkcenterResponseBodyJson.custom().build();
	}
	
}
