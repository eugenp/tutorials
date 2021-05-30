package com.baeldung.pattern.hexagonal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.pattern.hexagonal.domain.model.MobileDetails;
import com.baeldung.pattern.hexagonal.domain.services.MobileStoreService;

@RestController
@RequestMapping("/mobiles")
public class MobileStoreController {
	
	@Autowired
	MobileStoreService mobileStoreService;

	@GetMapping
	public List<MobileDetails> getMobiles()
	{
		return mobileStoreService.getMobiles();
	}
	
	@PostMapping
	public void addMobile(@RequestBody MobileDetails mobileDetails)
	{
		mobileStoreService.addMobile(mobileDetails);
	}
	
	@PutMapping(path = "/{modelNumber}")
	public void modifyMobileDetails(@PathVariable("modelNumber") int modelNumber, @RequestBody MobileDetails mobileDetails)
	{
		mobileStoreService.modifyMobileDetails(modelNumber, mobileDetails);
	}
	
	@DeleteMapping(path = "/{modelNumber}")
	public void removeMobileDetails(@PathVariable("modelNumber") int modelNumber)
	{
		mobileStoreService.removeMobile(modelNumber);
	}
}
