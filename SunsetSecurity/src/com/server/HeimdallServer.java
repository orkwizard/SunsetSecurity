package com.server;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;

import javax.sql.DataSource;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.auth.AuthOptions;
import io.vertx.ext.auth.jdbc.JDBCAuth;
import io.vertx.ext.auth.jdbc.JDBCAuthOptions;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.shell.ShellService;
import io.vertx.ext.shell.ShellServiceOptions;
import io.vertx.ext.shell.command.Command;
import io.vertx.ext.shell.command.CommandBuilder;
import io.vertx.ext.shell.command.CommandRegistry;
import io.vertx.ext.shell.term.HttpTermOptions;
import io.vertx.ext.shell.term.SSHTermOptions;
import io.vertx.ext.shell.term.TelnetTermOptions;
import io.vertx.ext.shell.term.TermServer;
import tools.Jobber;

public class HeimdallServer extends AbstractVerticle {

	private Vertx vertx;
	private TermServer server;
	
	private JDBCClient jdbc;
	
	
	private Command scatter;
	private Command scatterstop;
	
	private Jobber jobs;
	
	private String heimdallWelcome=""+
"#  #     # ####### ### #     # ######     #    #       #       \n"+
"#  #     # #        #  ##   ## #     #   # #   #       #       \n"+
"#  #     # #        #  # # # # #     #  #   #  #       #       \n"+
"#  ####### #####    #  #  #  # #     # #     # #       #       \n"+
"#  #     # #        #  #     # #     # ####### #       #       \n"+
"#  #     # #        #  #     # #     # #     # #       #       \n"+
"#  #     # ####### ### #     # ######  #     # ####### ####### \n\n\n";
	
	
	private String ip;
	
	private JDBCAuth auth;
	
	
	public HeimdallServer(){
		super();
		try {
			ip = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//lookforAsgard();
		//Creating vertx
		vertx = Vertx.vertx();
		
		//Creating DataBase Connection 
		//Asgard will return this data!!
		
		jdbc = JDBCClient.createShared(vertx,new JsonObject()
				.put("url","jdbc:mysql://localhost:3306/Heimdall")
				.put("driver_class","com.mysql.jdbc.Driver")
				.put("user", "root")
				.put("password", "sys73xrv"));
		
		
		auth = JDBCAuth.create(vertx, jdbc);
		
		
		
		
	}
	
	
	
	private void scatt_register(){
		scatter = CommandBuilder.command("scatter").processHandler(process ->{
			
			/*
			 * Only valid are:
			 * start <interval>
			 * status
			 * stop
			 * 
			 */
			List<String> args = process.args();
			
			if(args.isEmpty()){
				process.write("Usage: start <interval> | stop | status | register | update <interval> \n");
				process.end();
			}else{
				int size = args.size();
				String command = args.get(0);
				System.out.println(command);
				
				switch(command){
				case "stop":{
					try {
						jobs.stop();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					process.end();
					
				}break;
				case "status":{
					if(jobs!=null)
						process.write(jobs.isRunning()+"");
					else
						process.write("Scatter not running \n\n");
					process.end();
					
				}break;
				case "start":{
					
					
					boolean valid = startCommand(args);
					
					
					if(!(size>1)){
						process.write("Sintaxis: scatter start <interval> \n");
						break;
					}
					int interval;
					try{
						interval = Integer.parseInt(args.get(1));
					}catch(NumberFormatException ex){
						process.write("An integer was expected");
						process.end();
						break;
					}
					try {
						jobs = new Jobber(interval);
						jobs.run();
						process.write("Running Scatter at : " + interval +"seconds");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					process.end();
				}break;
				}
				
			}
			
			}).build(vertx);
	};
	
	
	private boolean startCommand(List<String> args) {
		// TODO Auto-generated method stub
		// Valida que se cumplan los valores
		int size = args.size();
		int interval;
		if(size > 1)
			try{
				interval = Integer.parseInt(args.get(1));
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
			
		
		return false;
	}
	@Override
	
	
	public void start()throws Exception{
		
		scatt_register();
		
		
		
		
		
		/*
		
		ShellService service = ShellService.create(vertx,
				new ShellServiceOptions().setSSHOptions(
						new SSHTermOptions().
						setHost(ip).
						setPort(5500).setAuthOptions(new JDBCAuthOptions().)
								
		*/						
								
								
								/*new ShiroAuthOptions().
								setType(ShiroAuthRealmType.PROPERTIES).
								setConfig(new JsonObject().
										put("properties_path","")
										)
								)
						);*/
										
		
		
		
		
		
		ShellService service = ShellService.create(vertx, new ShellServiceOptions().setTelnetOptions(
		        new TelnetTermOptions().setHost(ip).setPort(3000)
		    ).setWelcomeMessage(heimdallWelcome));
		
		
		
		CommandRegistry.getShared(vertx).registerCommand(scatter);	
		service.start(ar -> {
		      if (!ar.succeeded()) {
		          ar.cause().printStackTrace();
		        }
		});
		
	}
	
	
	
	/*
	@Override
	public void start() throws Exception{
		Vertx vertx = Vertx.vertx();
		
		NetServer server = vertx.createNetServer();
		
		server.connectHandler(new Handler<NetSocket>() {

            @Override
            public void handle(NetSocket netSocket) {
                System.out.println("Incoming connection!");
                
                netSocket.handler(new Handler<Buffer>() {
					
					@Override
					public void handle(Buffer buffer) {
						// TODO Auto-generated method stub
						
						
						System.out.println("command: "+buffer.toString());
						
						ScreenTaker screen = new ScreenTaker(null);
						BufferedImage image = null ; //screen.capture();
						
                        //buffer.getString(0,buffer.length());
						System.out.println("ScreenSize Image "+ image.getHeight()+":"+image.getWidth());
						
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        
                        try {
							ImageIO.write(image,"jpg",baos);
							 baos.flush();
							 
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                       
                        byte[] imageInByte = baos.toByteArray();
                        System.out.println("Size Bytes (Image in Bytes) " + imageInByte.length);
                        
                        Buffer outBuffer = Buffer.buffer(imageInByte);
                        
                        netSocket.write(outBuffer);
                        
                        System.out.println("Sent Image");
					}
				});
            }
        });
		server.listen(3000);
	}
	
	
*/
	public static void main(String[] args){
		HeimdallServer server = new HeimdallServer();
		try {
			server.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
