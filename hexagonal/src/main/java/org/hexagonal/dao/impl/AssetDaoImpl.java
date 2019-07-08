package org.hexagonal.dao.impl;

import java.util.Date;
import java.util.List;

import org.hexagonal.dao.AssetDao;
import org.hexagonal.model.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class AssetDaoImpl implements AssetDao{

	@Autowired
	NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public List<Asset> getAssetList() {
		String sql = "select asset_id as assetId, asset_type as assetType, asset_name as AssetName, purchase_Date as dateOfPurchase,"
				+ "sell_date as dateOfSell, manufacturer, asset_description as assetDescription from asset";
		return namedJdbcTemplate.query(sql, new BeanPropertyRowMapper<Asset>(Asset.class));
	}

}
