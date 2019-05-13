package edu.oswego.csc445.simpleServices.echoServices;

import java.io.*;
import java.net.*;

public class UDPEchoServiceThread extends Thread {
   int port;

	public UDPEchoServiceThread(int port){
		super();
		this.port = port;
	}

   public void run() {

      try {
         byte[] inBuf;

         DatagramSocket serverSocket = null;
         System.out.println("UDP echo service started at " + new InetSocketAddress(InetAddress.getLocalHost(), port));
         serverSocket = new DatagramSocket(port);
         for (;;) {
            inBuf = new byte[1025];
            DatagramPacket inPacket = new DatagramPacket(inBuf, inBuf.length);
            serverSocket.receive(inPacket);
            serverSocket.send(inPacket);
         }
      } catch (IOException ex) {
         ex.printStackTrace();
         System.exit(-1);
      }
      System.out.println("UDP echo service terminated");
   }
}