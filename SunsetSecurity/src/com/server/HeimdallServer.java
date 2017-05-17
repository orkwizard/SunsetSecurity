package com.server;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.html.HTMLEditorKit.Parser;

import org.quartz.SchedulerException;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.auth.jdbc.JDBCAuth;
import io.vertx.ext.auth.jdbc.JDBCAuthOptions;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.shell.ShellService;
import io.vertx.ext.shell.ShellServiceOptions;
import io.vertx.ext.shell.command.Command;
import io.vertx.ext.shell.command.CommandBuilder;
import io.vertx.ext.shell.command.CommandProcess;
import io.vertx.ext.shell.command.CommandRegistry;
import io.vertx.ext.shell.term.SSHTermOptions;
import io.vertx.ext.sql.SQLConnection;
import tools.Jobber;

public class HeimdallServer extends AbstractVerticle {

	private Vertx vertx;
	private JDBCClient jdbc;
	private Command scatter;
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
	private ShellService service;
	
	
	
	private void  defaultdbuser(){
		String salt = auth.generateSalt();
		String hash = auth.computeHash("sys73xrv",salt);

		 jdbc.getConnection(res -> {
			  if (res.succeeded()) {
				    SQLConnection conn = res.result();
				    conn.updateWithParams("INSERT INTO user VALUES (?, ?, ?)", new JsonArray().add("eosorio").add(hash).add(salt), resa -> {
				    	  if (resa.succeeded()) {
				    	    System.out.println("Added User");
				    	    conn.close();
				    	  }
				 });
			  }
		});
	}
	
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
		jdbc = JDBCClient.createShared(vertx,new JsonObject()
				.put("url","jdbc:mysql://localhost:3306/Heimdall")
				.put("driver_class","com.mysql.jdbc.Driver")
				.put("user", "root")
				.put("password", "sys73xrv"));
		auth = JDBCAuth.create(vertx, jdbc);
		
		//defaultdbuser();	
	}
	
	
	private ParserResponse parse(Iterator<String> args){
		/*
		 * Options:
		 * 
			 * start <interval>
			 * status
			 * stop
			 * update <interval>
			 * 
		 */
		ParserResponse response= new ParserResponse();
		
		String command = args.next();
		System.out.println("Command :-> " + command);
		switch(command){
		case "stop":   response = stop_();				break;
		case "start":{
						if(args.hasNext())
							response = start(args.next());	
						else{
							response.setResult(false);
							response.setSresult("An integer was expected");
						}	
					 }break;
		case "status": response = status();				break;
		case "update": response = update(args.next());	break;
		default:{
			response.setResult(false);
			response.setSresult("Invalid Command");
			response.setComments("Unkown command.. Please check the valid sintaxis");
			}
			
		}
		return response;
	}
	
	
	
	private ParserResponse update(String next) {
		return null;
		// TODO Auto-generated method stub
		
	}



	private ParserResponse status() {
		return null;
		// TODO Auto-generated method stub
		
	}



	private ParserResponse stop_() {
		return null;
		// TODO Auto-generated method stub
		
	}



	private ParserResponse start(String next) {
		System.out.println("START with : ->" + next);
		ParserResponse response = new ParserResponse();
		try{
			int interval = Integer.parseInt(next);
			System.out.println("Interval : ->" + interval);
			jobs = new Jobber(interval);
			jobs.run();
			response.setResult(true);
			response.setComments("Running Scatter sucessfully");
		}
		catch(Exception  e){
			response.setResult(false);
			response.setSresult("An error happened");
			response.setComments(e.toString());
		}
		return response;
	}



	private void scatt_register(){
		scatter = CommandBuilder.command("scatter").processHandler(process ->{
			List<String> args = process.args();
			if(args.isEmpty())
				process.write("Usage: start <interval> | stop | status | register | update <interval> \n");
			else{
				ParserResponse response = parse(args.iterator());
				process.write(response.getSresult());
			}
			process.end();
		}).build(vertx);
		
	}
	
	
	
	/*
	private void scatt_register(){
		scatter = CommandBuilder.command("scatter").processHandler(process ->{
			
			
			 
			
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
	
	*/

	



	private void initServer(){
		service = ShellService.create(vertx,
			    new ShellServiceOptions().setSSHOptions(
			        new SSHTermOptions().
			            setHost(ip).
			            setPort(2020).
			            setKeyPairOptions(new JksOptions().
			                    setPath("keystore.jks").
			                    setPassword("sys73xrv")
			            ).
			            setAuthOptions(new JDBCAuthOptions().setConfig(new JsonObject()
			                    .put("url", "jdbc:mysql://localhost:3306/Heimdall")
			                    .put("driver_class", "com.mysql.jdbc.Driver"))
			            )
			    ).setWelcomeMessage(heimdallWelcome)
			);
	}
	
	@Override
	public void start()throws Exception{
		scatt_register();
		CommandRegistry.getShared(vertx).registerCommand(scatter);	
		service.start(ar -> {
		      if (!ar.succeeded()) {
		          ar.cause().printStackTrace();
		        }
		});
	}
	
	
	public static void main(String[] args){
		HeimdallServer server = new HeimdallServer();
		server.initServer();
		try {
			server.start();
			System.out.println("Server initialized");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
