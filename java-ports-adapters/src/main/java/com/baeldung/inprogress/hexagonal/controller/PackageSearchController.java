package com.baeldung.inprogress.hexagonal.controller;

import com.baeldung.inprogress.hexagonal.core.backend.channel.IChannelPackageSearchService;
import com.baeldung.inprogress.hexagonal.core.domain.PackageSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PackageSearchController {

	@Autowired
	private IChannelPackageSearchService defaultChannelPackageSearchService;

	@RequestMapping(value = "/searchPackage", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity searchPackage(@RequestBody PackageSearchCriteria packageSearchCriteria) {

		try {
			return ResponseEntity.ok(defaultChannelPackageSearchService.searchPackage(packageSearchCriteria));

		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex);
		}
	}
}
