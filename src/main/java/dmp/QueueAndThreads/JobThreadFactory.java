package dmp.QueueAndThreads;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class JobThreadFactory<T> implements ThreadFactory {
	
	
	
	public LinkedBlockingQueue<T> queue;
    static final AtomicInteger poolNumber = new AtomicInteger(1);  
    final ThreadGroup group;  
    final AtomicInteger threadNumber = new AtomicInteger(1);  
    final String namePrefix; 
	
    
    JobThreadFactory(LinkedBlockingQueue<T> queue){
    	SecurityManager s = System.getSecurityManager();  
        group = (s != null)?s.getThreadGroup():Thread.currentThread().getThreadGroup();  
        namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";  
        this.queue = queue;
    }
    
    
    
	

	@Override
	public Thread newThread(Runnable r) {
		// TODO Auto-generated method stub
		Thread thread = new Thread(r);
		
		if (thread.getPriority() != Thread.NORM_PRIORITY)  
			thread.setPriority(Thread.NORM_PRIORITY);  
		
		return thread;
	}

}
