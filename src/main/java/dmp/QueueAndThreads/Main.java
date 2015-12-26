package dmp.QueueAndThreads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		long timeout = 1000;
		long timeNeed = 2000;
		TimeUnit timeUnit = TimeUnit.MILLISECONDS;
		LinkedBlockingQueue<QueueJob> givenQueue = new LinkedBlockingQueue<QueueJob>();
		ExecutorService executorService = Executors.newFixedThreadPool(5, new JobThreadFactory(givenQueue));
		
		
		for (int i = 0; i < 8; i++) {
			try {
				givenQueue.put(new QueueJob("URL" + Integer.toString(i)));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		int iter = 0;
		
		
		while(!givenQueue.isEmpty()){
			try {
				timedCall(executorService, new JobRunInThread(iter, givenQueue, timeout, timeUnit), timeout, timeUnit);
				iter++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
//				System.out.println("Thread got interrupted.");
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
			
			
//			executorService.submit(new JobRunInThread(i, givenQueue, timeout, timeUnit));

		executorService.shutdown();
	}
	
	
	private static boolean timedCall(ExecutorService executor, Runnable c,long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException {  
        // FutureTask<?> task = new FutureTask<Object>(c, null);  
        // executor.execute(task);  
        //        
        // task.get(timeout, timeUnit);  
        Future<?> future = executor.submit(c);  
        try {  
            future.get(timeout, timeUnit);  
            return true;  
        } catch (TimeoutException e) {  
            future.cancel(true);// 参数设为true，向执行线程发送中断通知。否则，允许已经启动的线程继续执行直到完成任务。  
            System.err.println("任务执行超时,强制退出");  
            return false;  
        }  
    }  	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
