/**
 * 
 */
package com.juxtapose.example.ch06.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.juxtapose.example.ch06.CreditBill;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-8-16下午03:30:08
 */
public class CreditBillRowMapper implements RowMapper<CreditBill> {

	public CreditBill mapRow(ResultSet rs, int rowNum) throws SQLException {
		CreditBill bill = new CreditBill();
		bill.setAccountID(rs.getString("ACCOUNTID"));
		bill.setAddress(rs.getString("ADDRESS"));
		bill.setAmount(rs.getDouble("AMOUNT"));
		bill.setDate(rs.getString("DATE"));
		bill.setName(rs.getString("NAME"));
		return bill;
	}
}
