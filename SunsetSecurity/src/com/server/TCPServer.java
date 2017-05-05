package com.server;

import io.vertx.core.AbstractVerticle;

public class TCPServer extends AbstractVerticle {

	
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
		TCPServer server = new TCPServer();
		try {
			server.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
