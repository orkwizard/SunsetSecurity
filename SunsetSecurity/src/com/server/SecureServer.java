package com.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.shell.Shell;
import io.vertx.ext.shell.ShellServer;
import io.vertx.ext.shell.command.CommandResolver;
import io.vertx.ext.shell.term.Term;
import io.vertx.ext.shell.term.TermServer;

public class SecureServer extends AbstractVerticle {
	
	Vertx vertx;
	
	public SecureServer(){
		vertx = Vertx.vertx();
		
		
	}

}
