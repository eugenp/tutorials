package org.hexagonal.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hexagonal.dao.AssetDao;
import org.hexagonal.dao.AssetTypeDao;
import org.hexagonal.domain.model.Asset;
import org.hexagonal.domain.model.AssetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AssetTypeDaoImpl implements AssetTypeDao{

	@Autowired
	NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public List<AssetType> getAssetTypeList() {
		String sql = "select type_id as typeId, asset_type_name as assetTypeName, asset_type_description as assetTypeDescription,"
				+ " units_for_measurement as unitsForMeasurement from assetType";
		return namedJdbcTemplate.query(sql, new BeanPropertyRowMapper<AssetType>(AssetType.class));
	}

	@Override
	public void addAsset(AssetType newAssetType) {
		final String INSERT_QUERY = "insert into asset_type (asset_type_name, asset_type_description, units_for_measurement ) "
				+ "values (:assetTypeName, :assetTypeDesc, :unitOfMesurement)";
		// Creating map with all required params
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("assetTypeName", newAssetType.getAssetTypeName());
        paramMap.put("assetTypeDesc", newAssetType.getAssetTypeDescription());
        paramMap.put("unitOfMesurement", newAssetType.getUnitsForMeasurement());
        
        // Passing map containing named params
        namedJdbcTemplate.update(INSERT_QUERY, paramMap);
		
	}	
}
