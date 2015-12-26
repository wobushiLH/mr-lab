package dmp.quartz;


public class QuartzMain {

	public static void main(String[] args) {
		
		QuartzManager qm = new QuartzManager();
		
		/**
		 * check urls existence in hbase every 5 seconds  
		 */
		qm.run(QuartzJob.class, "0/5 * * * * ?");
		
		
	}
	
	
}
