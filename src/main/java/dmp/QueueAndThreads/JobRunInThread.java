package dmp.QueueAndThreads;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JobRunInThread implements Runnable{

	private int jobID;
	private LinkedBlockingQueue<QueueJob> queue;
	private long timeLong = 0;// how long thread running will cost  
    private TimeUnit timeUnit;
    private QueueJob queueJob;
	
	public JobRunInThread(int jobID, LinkedBlockingQueue<QueueJob> queue, long milli, TimeUnit timeUnit){
		this.jobID = jobID;
		this.queue = queue;
		this.timeLong = milli;
		this.timeUnit = timeUnit;
		this.queueJob = queue.poll();				
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Queue value: "+ queueJob.getURL() + " starts");
		
		try {
			if(queueJob.getURL().equals("URL2") || queueJob.getURL().equals("URL3")){
				System.out.println("Queue value: "+ queueJob.getURL() +"  sleeps");
				Thread.currentThread().sleep(2000);
				System.out.println("Queue value: "+ queueJob.getURL() +"  is awake");
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.err.println("Queue value: "+ queueJob.getURL() +" got interrupted");
			try {
				if (queueJob.isRerunnable()){
					System.err.println("Queue value: "+ queueJob.getURL() + "'s job count " + queueJob.getJobCount() + " is smaller than or equal to 2, add back to queue");
//					queueJob.incrementJobCount();
					queue.put(queueJob);
				}else
					System.out.println("Queue value: "+ queueJob.getURL() + "'s job count " + queueJob.getJobCount() + " is greater than 2, job dismissed");
					
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}  
		System.out.println("Queue value: "+ queueJob.getURL() + " ends");
	}

}
