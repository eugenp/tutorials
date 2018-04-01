/**
 * 
 */
package test.com.juxtapose.example.ch06;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.juxtapose.example.ch06.CreditBill;


/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-1-6下午09:18:11
 */
public class JobLaunchJMS {
	
	/**
	 * 
	 * @param jobPath
	 * @return
	 */
	public static ApplicationContext getContext(String jobPath){
		return new ClassPathXmlApplicationContext(jobPath);
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static JmsTemplate getJmsTemplate(ApplicationContext context){
		JmsTemplate jmsTemplate = (JmsTemplate)context.getBean("jmsTemplate");
		return jmsTemplate;
	}
	
	/**
	 * 
	 * @param jmsTemplate
	 * @param creditBill
	 */
	public static void sendMessage(JmsTemplate jmsTemplate, final CreditBill creditBill){
		jmsTemplate.send(new MessageCreator() {
		    public Message createMessage(Session session)
		                                    throws JMSException {
		        ObjectMessage message = session.createObjectMessage();
		        message.setObject(creditBill);
		        return message;
		    }
		});
	}
	
	/**
	 * 执行批处理作业.<br>
	 * @param context
	 * @param jobName
	 * @param builder
	 */
	public static void executeJob(ApplicationContext context, String jobName, JobParametersBuilder builder) {
		JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(jobName);
		try {
			JobExecution result = launcher.run(job, builder.toJobParameters());
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = getContext("ch06/job/job-jms.xml");
		JmsTemplate jmsTemplate = getJmsTemplate(context);
		sendMessage(jmsTemplate, new CreditBill("4047390012345678","tom",100.00,"2013-2-2 12:00:08","Lu Jia Zui road"));
		sendMessage(jmsTemplate, new CreditBill("4047390012345678","tom",320,"2013-2-3 10:35:21","Lu Jia Zui road"));
		sendMessage(jmsTemplate, new CreditBill("4047390012345678","tom",360.00,"2013-2-11 11:12:38","Longyang road"));
		executeJob(context, "jmsReadJob",
				new JobParametersBuilder().addDate("date", new Date()));
	}
}
