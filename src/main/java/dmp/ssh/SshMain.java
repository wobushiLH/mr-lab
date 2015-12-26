package dmp.ssh;


public class SshMain {
	
	public static void main(String[] args) {
		
    	CommandExecutor executor = new CommandExecutor("10.10.10.2","hdtreport","WF^h2AkHSaZp");
    	
    	
    	executor.connectOpen();
    	
    	
		// 使用多个命令用分号隔开
    	for(String s:executor.sendCommand("ls"))  
        {  
            System.out.println(s);  
        }
         
    	executor.connectClose();

	}
}
