package com.client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

public class TCPClient  extends AbstractVerticle {
	
	public void start() {
		
		Vertx vertx = Vertx.vertx();
        NetClient tcpClient = vertx.createNetClient();
       
        tcpClient.connect(3000, "localhost",
                new Handler<AsyncResult<NetSocket>>(){
                @Override
                public void handle(AsyncResult<NetSocket> result) {
                    NetSocket socket = result.result();
                    socket.write("GET Image");
                    
                    socket.handler(new Handler<Buffer>(){
                    	ByteArrayOutputStream result = new ByteArrayOutputStream();
                    	
                        @Override
                        public void handle(Buffer buffer) {
                            System.out.println("Received data: " + buffer.length());
                            
                            byte[] array =  buffer.getBytes();
                            try {
								result.write(array);
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                            System.out.println("Result Array size :" + result.size());
                            //System.out.println("Buffer size(client):"+ array.length);
                            BufferedImage image = createImageFromBytes(result.toByteArray());                       
                            try {
								ImageIO.write(image, "JPG", new File("//Users//EdgarOsorio//screenScatter.jpg"));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                           // System.out.println(buffer.getString(0, buffer.length()));
                        }
                       
                    });
                
                }
            });
    }

	
	
	private BufferedImage createImageFromBytes(byte[] imageData) {
	    ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
	    try {
	        return ImageIO.read(bais);
	        
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}
	public static void main(String args[]){
		TCPClient client = new TCPClient();
		client.start();
	}
}
