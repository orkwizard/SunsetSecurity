package tools;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class Jobber  {

	Scheduler scheduler;
	JobKey jobKey;
	JobDetail jobDetail;
	Trigger trigger;
	Job job;
	
	public Jobber() throws SchedulerException{
		
		scheduler = StdSchedulerFactory.getDefaultScheduler();	
		scheduler.start();
	}
	
	public static void main(String[] args){
		
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
