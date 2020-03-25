package com.baeldung.hexagonalexample.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonalexample.ports.PricingRepo;
import com.baeldung.hexagonalexample.ports.PricingService;

@Service
public class PricingServiceImpl implements PricingService {

	@Autowired
	private PricingRepo pricingRepo;

	@Override
	public Material persistMaterial(Material material) {
		return pricingRepo.persistMaterial(material);
	}

	@Override
	public Material updateMaterial(Material material) {
		return pricingRepo.updateMaterial(material);
	}

	@Override
	public List<Material> findAllMaterials() {
		return pricingRepo.getAllMaterials();
	}

}
