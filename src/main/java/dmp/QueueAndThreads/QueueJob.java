package dmp.QueueAndThreads;

import java.util.concurrent.atomic.AtomicInteger;

public class QueueJob {
	
	private final static int MAX_RUN_TIME = 2;
	private final static int ZERO = 0;
	
	
	private String url;
	private AtomicInteger jobCount;
	
	
	public QueueJob(String url){
		this.url = url;
//		this.jobCount.set(zero);
		this.jobCount = new AtomicInteger(ZERO);
	}

	public String getURL(){
		return url;
	}
	
	public int getJobCount(){
		return jobCount.get();
	}
	
	public void setJobCount(int count){
		jobCount.set(count);
	}
	
	public void incrementJobCount(){
		jobCount.incrementAndGet();
	}

	public boolean isRerunnable(){
		if(jobCount.getAndIncrement() < MAX_RUN_TIME)
			return true;
		return false;
	}

}
