package dmp.RabbitMQ.Spring;

import java.io.IOException;  

import com.rabbitmq.client.Channel;  
import com.rabbitmq.client.Connection;  
import com.rabbitmq.client.ConnectionFactory;  
import com.rabbitmq.client.ConsumerCancelledException;  
import com.rabbitmq.client.QueueingConsumer;  
import com.rabbitmq.client.QueueingConsumer.Delivery;  
import com.rabbitmq.client.ShutdownSignalException;  
 
public class MQListener{
	
    public void onMessage() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {


        ConnectionFactory connFac = new ConnectionFactory() ;  
        
        //RabbitMQ-Server info 
        connFac.setHost("10.10.4.105");  
        connFac.setUsername("guest");
        connFac.setPassword("guest");
        connFac.setPort(5672);
          
        //创建一个连接  
        Connection conn = connFac.newConnection() ;  
          
        //创建一个渠道  
        Channel channel = conn.createChannel() ;  
          
        //定义Queue名称  
        String queueName = "dmp_url_queue_test" ;  
          
        //为channel定义queue的属性，queueName为Queue名称  
        channel.queueDeclare( queueName , false, false, false, null) ;  
//        channel.exchangeDeclare("dmp_url_exchange_test", "direct");
          
        //配置好获取消息的方式  
        QueueingConsumer consumer = new QueueingConsumer(channel) ;  
        channel.basicConsume(queueName, true, consumer) ;  
          
        //循环获取消息  
        while(true){  
              
            //获取消息，如果没有消息，这一步将会一直阻塞  
            Delivery delivery = consumer.nextDelivery() ;  
              
            String msg = new String(delivery.getBody()) ;    
              
            System.out.println("received message[" + msg + "] from " + queueName);  
        }
    }
}