package tools;

import java.awt.AWTException;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class ScreenTaker {
	
	private String IP;
	private String HostName;
	private Robot robot;

	
	
	public ScreenTaker(){
		try {
			IP = Inet4Address.getLocalHost().getHostAddress();
			HostName = Inet4Address.getLocalHost().getHostName();
			
		} catch (UnknownHostException e) {
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
		 ScreenTaker screen = new ScreenTaker();
		 System.out.println(screen.HostName);
		 System.out.print(screen.getIP());
		 System.out.println("Test");
		 
		 
	 }
	
}
