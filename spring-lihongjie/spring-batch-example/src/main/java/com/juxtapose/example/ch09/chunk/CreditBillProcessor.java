/**
 * 
 */
package com.juxtapose.example.ch09.chunk;

import org.springframework.batch.item.ItemProcessor;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-19下午01:15:37
 */
public class CreditBillProcessor implements
		ItemProcessor<CreditBill, CreditBill> {

	public CreditBill process(CreditBill bill) throws Exception {
		System.out.println(bill.toString());
		return bill;
	}
}
