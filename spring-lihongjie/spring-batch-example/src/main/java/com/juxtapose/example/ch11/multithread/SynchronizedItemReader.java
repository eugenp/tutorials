/**
 * 
 */
package com.juxtapose.example.ch11.multithread;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-11-17上午08:19:50
 */
public class SynchronizedItemReader implements ItemReader<CreditBill>, ItemStream{

	private ItemReader<CreditBill> delegate;

	public synchronized CreditBill read() throws Exception {
		CreditBill creditBill = delegate.read();
		return creditBill;
	}

	public ItemReader<CreditBill> getDelegate() {
		return delegate;
	}

	public void setDelegate(ItemReader<CreditBill> delegate) {
		this.delegate = delegate;
	}

	public void close() throws ItemStreamException {
		if (this.delegate instanceof ItemStream) {
			((ItemStream)this.delegate).close();
		}
	}

	public void open(ExecutionContext context) throws ItemStreamException {
		if (this.delegate instanceof ItemStream) {
			((ItemStream)this.delegate).open(context);
		}
	}

	public void update(ExecutionContext context) throws ItemStreamException {
		if (this.delegate instanceof ItemStream) {
			((ItemStream)this.delegate).update(context);
		}
	}
}
