package com.baeldung.micronaut.vs.springboot.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.micronaut.vs.springboot.service.ArithmeticService;

@RestController
@RequestMapping("/math")
public class ArithmeticController {
    @Autowired
    private ArithmeticService arithmeticService;
    
    @GetMapping("/sum/{number1}/{number2}")
    public float getSum(@PathVariable("number1") float number1, @PathVariable("number2") float number2) {
    	return arithmeticService.add(number1, number2);
    }
    
    @GetMapping("/subtract/{number1}/{number2}")
    public float getDifference(@PathVariable("number1") float number1, @PathVariable("number2") float number2) {
    	return arithmeticService.subtract(number1, number2);
    }
    
    @GetMapping("/multiply/{number1}/{number2}")
    public float getMultiplication(@PathVariable("number1") float number1, @PathVariable("number2") float number2) {
    	return arithmeticService.multiply(number1, number2);
    }
    
    @GetMapping("/divide/{number1}/{number2}")
    public float getDivision(@PathVariable("number1") float number1, @PathVariable("number2") float number2) {
    	return arithmeticService.divide(number1, number2);
    }
    
    @GetMapping("/memory")
    public String getMemoryStatus() {
    	MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
    	String memoryStats = "";
    	
        String init = String.format(
            "Initial: %.2f GB \n", 
    	    (double)memoryBean.getHeapMemoryUsage().getInit() /1073741824);
    	String usedHeap = String.format("Used: %.2f GB \n", 
            (double)memoryBean.getHeapMemoryUsage().getUsed() /1073741824);
    	String maxHeap = String.format("Max: %.2f GB \n", 
            (double)memoryBean.getHeapMemoryUsage().getMax() /1073741824);
    	String committed = String.format("Committed: %.2f GB \n", 
                (double)memoryBean.getHeapMemoryUsage().getCommitted() /1073741824);
    	memoryStats += init;
    	memoryStats += usedHeap;
    	memoryStats += maxHeap;
    	memoryStats += committed;
    	
    	return memoryStats;
    }
}
