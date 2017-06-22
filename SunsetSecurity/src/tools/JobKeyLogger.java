package tools;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

public class JobKeyLogger implements InterruptableJob {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		// TODO Auto-generated method stub
		
	}

}
