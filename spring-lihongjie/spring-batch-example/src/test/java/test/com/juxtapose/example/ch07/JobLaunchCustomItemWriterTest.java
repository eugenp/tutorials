/**
 * 
 */
package test.com.juxtapose.example.ch07;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;

import com.juxtapose.example.ch07.CreditBill;
import com.juxtapose.example.ch07.cust.itemwriter.CustomCreditBillItemWriter;
import com.juxtapose.example.ch07.cust.itemwriter.RestartableCustomCreditBillItemWriter;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-9-29下午12:36:33
 */
public class JobLaunchCustomItemWriterTest {
	
	/**
	 * 测试自定义ItemWriter
	 * @throws Exception
	 */
	@Test
	public void testCustItemReader() throws Exception{
		List<CreditBill> list = new ArrayList<CreditBill>();
		list.add(new CreditBill("1","tom",100.00,"2013-2-2 12:00:08","Lu Jia Zui road"));
		list.add(new CreditBill("2","tom",320,"2013-2-3 10:35:21","Lu Jia Zui road"));
		list.add(new CreditBill("3","tom",360.00,"2013-2-11 11:12:38","Longyang road"));
		CustomCreditBillItemWriter writer = new CustomCreditBillItemWriter();
		writer.write(list);
		Assert.assertEquals(3, writer.getResult().size());
	}
	
	/**
	 * 测试可重启的自定义ItemWriter
	 * @throws Exception
	 */
	@Test
	public void testRestartableCustItemReader() throws Exception{
		List<CreditBill> list = new ArrayList<CreditBill>();
		list.add(new CreditBill("1","tom",100.00,"2013-2-2 12:00:08","Lu Jia Zui road"));
		
		RestartableCustomCreditBillItemWriter writer = new RestartableCustomCreditBillItemWriter();
		ExecutionContext executionContext = new ExecutionContext();
		((ItemStream)writer).open(executionContext);
		writer.write(list);
		Assert.assertEquals(1, writer.getResult().size());
		((ItemStream)writer).update(executionContext);
		
		list.add(new CreditBill("2","tom",320,"2013-2-3 10:35:21","Lu Jia Zui road"));
		list.add(new CreditBill("3","tom",360.00,"2013-2-11 11:12:38","Longyang road"));
		writer = new RestartableCustomCreditBillItemWriter();
		((ItemStream)writer).open(executionContext);
		writer.write(list);
		Assert.assertEquals(2, writer.getResult().size());
		((ItemStream)writer).update(executionContext);
	}
	
	
}
