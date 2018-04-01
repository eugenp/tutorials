/**
 * 
 */
package com.juxtapose.example.ch08;

import org.springframework.batch.item.ItemProcessor;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-30下午02:42:12
 */
public class FilterItemProcessor implements ItemProcessor<CreditBill, CreditBill> {

	@Override
	public CreditBill process(CreditBill item) throws Exception {
		if(item.getAmount()> 500){
			return null;
		}else{
			return item;
		}
	}
}
