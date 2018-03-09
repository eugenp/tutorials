/**
 * 
 */
package com.juxtapose.example.ch05;

import org.springframework.batch.item.ItemProcessor;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-1-6下午09:55:38
 */
public class CreditBillProcessor implements
		ItemProcessor<CreditBill, CreditBill> {

	public CreditBill process(CreditBill bill) throws Exception {
		System.out.println(bill.toString());
		return bill;
	}
}
