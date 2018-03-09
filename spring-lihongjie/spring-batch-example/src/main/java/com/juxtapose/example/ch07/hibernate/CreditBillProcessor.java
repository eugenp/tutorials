/**
 * 
 */
package com.juxtapose.example.ch07.hibernate;

import org.springframework.batch.item.ItemProcessor;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-1-6下午09:55:38
 */
public class CreditBillProcessor implements
		ItemProcessor<CreditBill, DestinationCreditBill> {

	public DestinationCreditBill process(CreditBill bill) throws Exception {
		System.out.println(bill.toString());
		DestinationCreditBill destCreditBill = new DestinationCreditBill();
		destCreditBill.setAccountID(bill.getAccountID());
		destCreditBill.setAddress(bill.getAddress());
		destCreditBill.setAmount(bill.getAmount());
		destCreditBill.setDate(bill.getDate());
		destCreditBill.setId(bill.getId());
		destCreditBill.setName(bill.getName());
		return destCreditBill;
	}
}
