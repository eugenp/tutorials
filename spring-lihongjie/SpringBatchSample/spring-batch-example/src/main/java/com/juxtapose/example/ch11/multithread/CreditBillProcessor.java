/**
 * 
 */
package com.juxtapose.example.ch11.multithread;

import java.util.UUID;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-11-17上午07:36:36
 */
public class CreditBillProcessor implements
		ItemProcessor<CreditBill, DestinationCreditBill> {
	JdbcTemplate jdbcTemplate = null;

	public DestinationCreditBill process(CreditBill bill) throws Exception {
		System.out.println(bill.toString());
		System.out.println("Job Process Thread name: " + Thread.currentThread().getName());
		DestinationCreditBill destCreditBill = new DestinationCreditBill();
		destCreditBill.setAccountID(bill.getAccountID());
		destCreditBill.setAddress(bill.getAddress());
		destCreditBill.setAmount(bill.getAmount());
		destCreditBill.setDate(bill.getDate());
		destCreditBill.setId(UUID.randomUUID().toString() +"::" + bill.getId());
		destCreditBill.setName(bill.getName());
		if(null != jdbcTemplate){
			jdbcTemplate.update("update t_credit set flag=? where id=?", "true", bill.getId());
		}
		return destCreditBill;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
