/**
 * TODO
 * 
 */
package com.rabbitmq.lihongjie.routing;

/**
 * @author hushuang
 *
 */
import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveLogsDirect1 {
	// ����������
	private static final String EXCHANGE_NAME = "direct_logs";
	// ·�ɹؼ���
	private static final String[] routingKeys = new String[]{"info" ,"warning", "error"};
	
	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
//		����������
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
//		��ȡ������������
		String queueName = channel.queueDeclare().getQueue();
//		����·�ɹؼ��ֽ��ж��ذ�
		for (String severity : routingKeys) {
			channel.queueBind(queueName, EXCHANGE_NAME, severity);
			System.out.println("ReceiveLogsDirect1 exchange:"+EXCHANGE_NAME+", queue:"+queueName+", BindRoutingKey:" + severity);
		}
		System.out.println("ReceiveLogsDirect1 [*] Waiting for messages. To exit press CTRL+Recv");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}
}
