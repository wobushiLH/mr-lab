package dmp.RabbitMQ.Spring;

import java.io.IOException;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

public class Main {

	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		// TODO Auto-generated method stub
		MQGateway gateway = new MQGateway();
		MQListener listener = new MQListener();
		
		gateway.sendMessage();
		
		listener.onMessage();
		
	}

}
