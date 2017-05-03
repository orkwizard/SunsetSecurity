package com.server;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class SecureServer {
	HttpServer server;
	Vertx vertx;
	
	public SecureServer(){
		vertx = Vertx.vertx();
		server = vertx.createHttpServer();
		
	}
}
