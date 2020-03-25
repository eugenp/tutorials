package com.baeldung.hexagonalexample.adapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonalexample.domain.Material;
import com.baeldung.hexagonalexample.ports.PricingService;

@RestController
@RequestMapping(value = "/materials")
public class PricingControllerAdapter {

	@Autowired
	private PricingService pricingService;

	@PostMapping
	public Material saveMaterial(@RequestBody Material material) {
		return pricingService.persistMaterial(material);
	}

	@PutMapping
	public Material updateMaterial(@RequestBody Material material) {
		return pricingService.updateMaterial(material);
	}

	@GetMapping
	public List<Material> getAllMaterial() {
		return pricingService.findAllMaterials();
	}

}
