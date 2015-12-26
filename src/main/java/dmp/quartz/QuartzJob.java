package dmp.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		
		
		/*    	CommandExecutor ce = new CommandExecutor("10.10.10.2","hdtreport","WF^h2AkHSaZp");
		// 使用多个命令用分号隔开
    	ce.setCommand("ls");
    	ce.sendCommand();
    	for(String s:ce.getStr())  
        {  
            System.out.println(s);  
        }
        */ 
		
		
		System.out.println("Print from Quartz!");
	}

}
