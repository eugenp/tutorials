/**
 * 
 */
package com.juxtapose.example.ch06.reuse;

import java.util.ArrayList;
import java.util.List;

import com.juxtapose.example.ch06.CreditBill;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-7下午01:37:54
 */
public class ExistService {
	private static List<CreditBill> list = new ArrayList<CreditBill>();
	static{
		list.add(new CreditBill("4047390012345678","tom",100.00,"2013-2-2 12:00:08","Lu Jia Zui road"));
		list.add(new CreditBill("4047390012345678","tom",320,"2013-2-3 10:35:21","Lu Jia Zui road"));
		list.add(new CreditBill("4047390012345678","tom",360.00,"2013-2-11 11:12:38","Longyang road"));
	}
	/**
	 * 
	 * @return
	 */
	public List<CreditBill> queryAllCreditBill(){
		//business service
		return list;
	}
}
