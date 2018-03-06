/**
 * 
 */
package com.juxtapose.example.ch08.listener;

import org.springframework.batch.core.ItemProcessListener;

import com.juxtapose.example.ch08.CreditBill;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-30下午11:59:52
 */
public class SystemOutItemProcessListener implements ItemProcessListener<CreditBill, CreditBill> {

	@Override
	public void beforeProcess(CreditBill item) {
		System.out.println("SystemOutItemProcessListener.beforeProcess()");
	}

	@Override
	public void afterProcess(CreditBill item, CreditBill result) {
		System.out.println("SystemOutItemProcessListener.afterProcess()");
	}

	@Override
	public void onProcessError(CreditBill item, Exception e) {
		System.out.println("SystemOutItemProcessListener.onProcessError()");
	}

}
