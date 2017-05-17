package tools;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class Jobber  {

	Scheduler scheduler;
	JobKey jobKey;
	JobDetail jobDetail;
	Trigger trigger;
	boolean isRunning;
	
	
	public Jobber(int interval) throws SchedulerException{
		scheduler = StdSchedulerFactory.getDefaultScheduler();	
		scheduler.start();
		jobKey = new JobKey("JobScreenTaker","group");
		jobDetail = JobBuilder.newJob(JobScreenTaker.class).withIdentity(jobKey).build();
		trigger = TriggerBuilder.newTrigger().withIdentity("trigger","group")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval).repeatForever()).build();	
	}
	
	public void run() throws SchedulerException{
		scheduler.scheduleJob(jobDetail,trigger);
		isRunning=true;
	}
	
	public boolean isRunning(){
		return isRunning;
	}
	
	public void stop() throws SchedulerException{
		System.out.println("Stoping Jobber");
		scheduler.shutdown();
		isRunning=false;
	}
	
	public static void main(String[] args){
		try {
			Jobber jobscreen = new Jobber(60);
			jobscreen.run();
			try {
				Thread.sleep(10000);
				jobscreen.stop();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	Scheduler scheduler;
	JobDetail job;
	Trigger trigger;
	int interval;
	public Jobber(int inter){
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			interval = inter;
			job = JobBuilder.newJob(Jobber.class).build();
			trigger = TriggerBuilder.newTrigger().withIdentity(JobKey)
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval).repeatForever()).build();
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
}
