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
import com.mauersu.util.Constant;
import com.mauersu.util.RedisApplication;

@Controller
@RequestMapping("/hash")
public class HashConroller extends RedisApplication implements Constant {
	
	@Autowired
	private HashService hashService;
	
	@RequestMapping(value="/delHashField", method=RequestMethod.POST)
	@ResponseBody
	public Object delHashField(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String serverName, 
			@RequestParam int dbIndex,
			@RequestParam String key,
			@RequestParam String dataType,
			@RequestParam String field) {
		
		hashService.delHashField(serverName, dbIndex, key, field);
		
		return WorkcenterResponseBodyJson.custom().build();
	}
	
	@RequestMapping(value="/updateHashField", method=RequestMethod.POST)
	@ResponseBody
	public Object updateHashField(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String serverName, 
			@RequestParam int dbIndex,
			@RequestParam String key,
			@RequestParam String dataType,
			@RequestParam String field,
			@RequestParam String value) {
		
		hashService.updateHashField(serverName, dbIndex, key, field, value);
		
		return WorkcenterResponseBodyJson.custom().build();
	}
	
}
