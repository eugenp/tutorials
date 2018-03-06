/**
 * 
 */
package com.juxtapose.example.ch11.partition.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.juxtapose.example.ch11.partition.CreditBill;


/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2014-1-11下午03:10:28
 */
public class CreditBillRowMapper implements RowMapper<CreditBill> {

	public CreditBill mapRow(ResultSet rs, int rowNum) throws SQLException {
		CreditBill bill = new CreditBill();
		bill.setId(rs.getString("ID"));
		bill.setAccountID(rs.getString("ACCOUNTID"));
		bill.setAddress(rs.getString("ADDRESS"));
		bill.setAmount(rs.getDouble("AMOUNT"));
		bill.setDate(rs.getString("DATE"));
		bill.setName(rs.getString("NAME"));
		return bill;
	}
}
