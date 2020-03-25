package com.baeldung.hexagonalexample.adapters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonalexample.domain.Material;
import com.baeldung.hexagonalexample.ports.PricingRepo;

@Repository
public class InMemoryPricingRepoImplAdapter implements PricingRepo {

	private Map<String, Material> materialMap = new HashMap<>();

	@Override
	public Material persistMaterial(Material material) {
		String id = UUID.randomUUID().toString();
		material.setId(id);
		materialMap.put(id, material);
		return material;
	}

	@Override
	public Material updateMaterial(Material material) {
		Material localMaterial = materialMap.get(material.getId());
		localMaterial.setPrice(material.getPrice());
		return material;
	}

	@Override
	public List<Material> getAllMaterials() {
		return materialMap.values().stream().collect(Collectors.toList());
	}

}
