/**
 * 
 */
package com.juxtapose.example.ch11.multithread;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-11-17上午07:35:23
 */
public class DestCreditBillItemPreparedStatementSetter implements
		ItemPreparedStatementSetter<DestinationCreditBill> {
	
	public void setValues(DestinationCreditBill item, PreparedStatement ps)
			throws SQLException {
		ps.setString(1, item.getId());
		ps.setString(2, item.getAccountID());
		ps.setString(3, item.getName());
		ps.setDouble(4, item.getAmount());
		ps.setString(5, item.getDate());
		ps.setString(6, item.getAddress());
	}
}
