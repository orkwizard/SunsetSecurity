package tools;

import java.io.IOException;

import org.apache.commons.mail.EmailException;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

public class JobScreenTaker implements InterruptableJob {
	
	private ScreenTaker screen;
	
	
	public JobScreenTaker(){
		screen = new ScreenTaker(null);
	}
	
	
	 public JobScreenTaker(Config conf) {
		// TODO Auto-generated constructor stub
		 screen = new ScreenTaker(conf);
	}
	
	

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			screen.createScreenshot();
			screen.buildEmail();
		} catch (IOException | EmailException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		// TODO Auto-generated method stub
		
	}

}
