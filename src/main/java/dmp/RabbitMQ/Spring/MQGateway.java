package dmp.RabbitMQ.Spring;

import java.io.IOException;

import com.rabbitmq.client.Channel;  
import com.rabbitmq.client.Connection;  
import com.rabbitmq.client.ConnectionFactory;  

public class MQGateway {  
  
    public void sendMessage() throws IOException {  
    	
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
//        channel.exchangeBind(destination, source, routingKey)
          
        String msg1 = "aaaaa!";  
        String msg2 = "bbbbb!";  
        String msg3 = "ccccc!";  
          
        //发送消息  
        channel.basicPublish("", queueName , null , msg1.getBytes());  
        channel.basicPublish("",queueName , null , msg2.getBytes()); 
        channel.basicPublish("", queueName , null , msg3.getBytes()); 
        
//        System.out.println("send message[" + msg + "] to "+ queueName +" success!");  
          
        channel.close();   
        conn.close();   
    }  
  

}  