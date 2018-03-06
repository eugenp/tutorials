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

import com.mauersu.service.ListService;
import com.mauersu.service.StringService;
import com.mauersu.util.Constant;
import com.mauersu.util.RedisApplication;

@Controller
@RequestMapping("/string")
public class StringConroller extends RedisApplication implements Constant {
	
	@Autowired
	private StringService stringService;
	
	@RequestMapping(value="/delValue", method=RequestMethod.POST)
	@ResponseBody
	public Object delValue(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String serverName, 
			@RequestParam int dbIndex,
			@RequestParam String key,
			@RequestParam String dataType) {
		
		stringService.delValue(serverName, dbIndex, key);
		
		return WorkcenterResponseBodyJson.custom().build();
	}
	
	@RequestMapping(value="/updateValue", method=RequestMethod.POST)
	@ResponseBody
	public Object updateValue(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String serverName, 
			@RequestParam int dbIndex,
			@RequestParam String key,
			@RequestParam String dataType,
			@RequestParam String value) {
		
		stringService.updateValue(serverName, dbIndex, key, value);
		
		return WorkcenterResponseBodyJson.custom().build();
	}
	
}
