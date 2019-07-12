package org.hexagonal.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hexagonal.dao.AssetDao;
import org.hexagonal.domain.model.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AssetDaoImpl implements AssetDao{

	@Autowired
	NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public List<Asset> getAssetList() {
		String sql = "select asset_id as assetId, asset_type_id as assetTypeId, asset_name as AssetName, purchase_Date as dateOfPurchase,"
				+ "sell_date as dateOfSell, manufacturer, status from asset";
		return namedJdbcTemplate.query(sql, new BeanPropertyRowMapper<Asset>(Asset.class));
	}

	@Override
	public void addAsset(Asset newAsset) {
		final String INSERT_QUERY = "insert into asset (asset_type, manufacturer, status, purchase_date, sell_date ) "
				+ "values (:assetType, :manufacturer, :status, :purchasedDate, :soldDate)";
		// Creating map with all required params
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("assetType", newAsset.getAssetTypeId());
        paramMap.put("manufacturer", newAsset.getManufacturer());
        paramMap.put("status", newAsset.getStatus());
        paramMap.put("purchasedDate", newAsset.getDateOfPurchase());
        paramMap.put("soldDate", newAsset.getDateOfSell());
        // Passing map containing named params
        namedJdbcTemplate.update(INSERT_QUERY, paramMap);
		
	}

}
