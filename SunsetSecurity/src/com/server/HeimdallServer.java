package com.server;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
import io.vertx.ext.shell.command.CommandRegistry;
import io.vertx.ext.shell.term.SSHTermOptions;
import io.vertx.ext.sql.SQLConnection;
import tools.Config;
import tools.Jobber;

public class HeimdallServer extends AbstractVerticle {

	private Vertx vertx;
	private JDBCClient jdbc;
	
	private Command scatter;
	private Command configure;
	private boolean isConfigured;
	
	private Jobber jobs;
	private String heimdallWelcome=""+
"#  #     # ####### ### #     # ######     #    #       #       \n"+
"#  #     # #        #  ##   ## #     #   # #   #       #       \n"+
"#  #     # #        #  # # # # #     #  #   #  #       #       \n"+
"#  ####### #####    #  #  #  # #     # #     # #       #       \n"+
"#  #     # #        #  #     # #     # ####### #       #       \n"+
"#  #     # #        #  #     # #     # #     # #       #       \n"+
"#  #     # ####### ### #     # ######  #     # ####### ####### \n\n\n";
	
	private  String Asgard="localhost";
	
	private Config config;
	
	
	private String ip;
	private JDBCAuth auth;
	private ShellService service;
	
	
	
	
	@SuppressWarnings("unused")
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
		callAsgard();
		jdbc = JDBCClient.createShared(vertx,new JsonObject()
				.put("url","jdbc:mysql://localhost:3306/Heimdall?useSSL=false")
				.put("driver_class","com.mysql.jdbc.Driver")
				.put("user", "root")
				.put("password", "sys73xrv"));
		auth = JDBCAuth.create(vertx, jdbc);
		
		//defaultdbuser();	
	}
	
	
	private void callAsgard() {
		// Call DB and obtain configuration
		
		
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
		case "stop":   response = stop_();break;
		
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
			response.setSresult("Invalid Command -> Please check help for more info  \n");
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
		// TODO Auto-generated method stub
		ParserResponse response = new ParserResponse();
		if(jobs==null || !jobs.isRunning()){
			response.setResult(false);
			response.setSresult("Scatter is not running ... \n");
		}else{
			response.setResult(true);
			response.setSresult("Scatter is running ...  \n");
		}
		return response;
	}

	private ParserResponse stop_() {
		ParserResponse response = new ParserResponse();
		if(jobs==null){
			response.setSresult("Not running yet \n");
			response.setResult(false);
		}else{
			if(jobs.isRunning()){
				try {
					jobs.stop();
					response.setSresult("Scatter stoped successfully \n");
					response.setResult(true);
					
				} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
			
		return response;
		// TODO Auto-generated method stub
		
	}

	private ParserResponse start(String next) {
		System.out.println("START with : ->" + next);
		ParserResponse response = new ParserResponse();
		try{
			int interval = Integer.parseInt(next);
			System.out.println("Interval : ->" + interval);
			if(jobs == null || !jobs.isRunning()){
				jobs = new Jobber(interval);
				jobs.run();
			}else{
				if(jobs.isRunning())
					response.setSresult("Already running... Please stop the scatter first");
				return response;
			}
			response.setResult(true);
			response.setSresult("Running Scatter sucessfully \n");
		}
		catch(NumberFormatException | SchedulerException  e){
			response.setResult(false);
			response.setSresult("An error happened : \n");
			response.setComments(e.toString()+"\n");
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

	
	
	private ParserResponse parse(String string) {
		// Try to connect to the Asgard DB and get all the configuration Data
		// 
		ParserResponse response = new ParserResponse();
		
		
		
		return response;
	}
	
	private void configure_register(){
		configure = CommandBuilder.command("configure").processHandler(process ->{
			List<String> args = process.args();
			if(args.isEmpty())
				process.write("Usage: configure <URL>");
			else{
				ParserResponse response = parse(args.get(0));
				process.write(response.getSresult());
			}
			process.end();
		}).build(vertx);
	}
	
	
	

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
			                    .put("url", "jdbc:mysql://localhost:3306/Heimdall?useSSL=false")
			                    .put("driver_class", "com.mysql.jdbc.Driver"))
			            )
			    ).setWelcomeMessage(heimdallWelcome)
			);
	}
	
	@Override
	public void start(){
		scatt_register();
		CommandRegistry.getShared(vertx).registerCommand(scatter);	
		service.start(ar -> {
		      if (!ar.succeeded()) {
		          ar.cause().printStackTrace();
		        }
		});
	}
	
	
	public static void main(String[] args){
		int len = args.length;
		ArrayList<String> array = new ArrayList<String>();
		for(int i=0;i<len;i++)
			array.add(args[i].toString());
		
		HeimdallServer server = new HeimdallServer();
		System.out.println(array.toString());
		server.initServer();
		
		
			try {
				server.start();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Server initialized");
			
	}


}
