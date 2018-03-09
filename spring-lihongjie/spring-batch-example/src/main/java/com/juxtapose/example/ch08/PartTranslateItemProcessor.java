/**
 * 
 */
package com.juxtapose.example.ch08;

import org.springframework.batch.item.ItemProcessor;


/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-30上午10:34:34
 */
public class PartTranslateItemProcessor implements
		ItemProcessor<CreditBill, CreditBill> {

	public CreditBill process(CreditBill bill) throws Exception {
		bill.setAddress(bill.getAddress() + "," + bill.getName());
		return bill;
	}
}
