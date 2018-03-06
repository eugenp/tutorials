/**
 * 
 */
package com.juxtapose.example.ch08.listener;

import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.OnProcessError;

import com.juxtapose.example.ch08.CreditBill;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-10-1上午12:01:33
 */
public class SystemOutAnnotation {
	@BeforeProcess
	public void beforeProcess(CreditBill item) {
		System.out.println("SystemOutAnnotation.beforeProcess()");
	}

	@AfterProcess
	public void afterProcess(CreditBill item, CreditBill result) {
		System.out.println("SystemOutAnnotation.afterProcess()");
	}

	@OnProcessError
	public void onProcessError(CreditBill item, Exception e) {
		System.out.println("SystemOutAnnotation.onProcessError()");
	}
}
