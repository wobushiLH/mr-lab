package dmp.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzManager {

	private static String JOB_GROUP_NAME = "group1";
	private static String TRIGGER_GROUP_NAME = "trigger1";
	private static String JOB_NAME = "job1";

	public void run (Class<? extends Job> jobClass,String sch) {
		try {
			// 创建Scheduler
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// 创建Job的实例
			JobDetail jobIns = JobBuilder.newJob(jobClass)
					.withIdentity(JOB_NAME, JOB_GROUP_NAME).build();

			// 创建Trigger
			CronScheduleBuilder builder = CronScheduleBuilder
					.cronSchedule(sch);
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(TRIGGER_GROUP_NAME, JOB_GROUP_NAME)
					.startNow().withSchedule(builder).build();

			// 调度执行
			scheduler.scheduleJob(jobIns, trigger);
			scheduler.start();

			try {
				// 当前线程等待1000秒
				Thread.sleep(1000L * 1000L);
			} catch (Exception e) {

			}
			scheduler.shutdown(true);
			System.err.println("停止运行");

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
