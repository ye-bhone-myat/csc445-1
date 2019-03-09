package edu.oswego.csc445.simpleServices.echoServices;

import java.io.*;
import java.net.*;

public class UDPEchoServiceThread extends Thread {
   static final int PORT = 3701;

   public void run() {

      try {
         byte[] inBuf;

         System.out.println("UDP echo service started at " +
				 new InetSocketAddress(InetAddress.getLocalHost(), PORT));

         for (;;) {
         	//
			 DatagramSocket serverSocket = new DatagramSocket(PORT);
            inBuf = new byte[1025];
            // receive packet
            DatagramPacket inPacket = new DatagramPacket(inBuf, inBuf.length);
            serverSocket.receive(inPacket);
            // trim received packet
            // String.trim() worked too
			String received = new String(inPacket.getData(), "US-ASCII").substring(0, inPacket.getLength() - 1);
            System.out.println("UDP server received message of length " + received.length() + " bytes");
            System.out.println("sender: " + inPacket.getAddress());
            // echo packet
            InetAddress senderAddress = inPacket.getAddress();
            DatagramPacket outPacket = new DatagramPacket(received.getBytes(), received.length(), senderAddress, PORT);
            serverSocket.send(outPacket);
//            serverSocket.close();
         }
      } catch (IOException ex) {
         ex.printStackTrace();
         System.exit(-1);
      }
      System.out.println("UDP echo service terminated");
   }
}