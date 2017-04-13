package tools;

import java.awt.image.BufferedImage;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class ScreenTaker {
	
	private String IP;
	private String HostName;
	
	
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
		
		
		return null;
	}
}
