/**
 * 
 */
package com.juxtapose.example.ch07;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;


/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-29下午02:27:52
 */
public class DummyCreditItemReader implements ItemReader<CreditBill> {
	private static List<CreditBill> list = new ArrayList<CreditBill>();
	static{
		list.add(new CreditBill("1","tom",100.00,"2013-2-2 12:00:08","Lu Jia Zui road"));
		list.add(new CreditBill("2","tom",320,"2013-2-3 10:35:21","Lu Jia Zui road"));
		list.add(new CreditBill("3","tom",360.00,"2013-2-11 11:12:38","Longyang road"));
	}
	
	public DummyCreditItemReader(){
	}

	@Override
	public CreditBill read() throws Exception, UnexpectedInputException, ParseException,
			NonTransientResourceException {
		if (!list.isEmpty()) {
            return list.remove(0);
        }
		return null;
	}
}
