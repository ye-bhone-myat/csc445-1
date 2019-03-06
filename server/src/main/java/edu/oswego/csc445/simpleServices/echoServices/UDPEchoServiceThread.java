package edu.oswego.csc445.simpleServices.echoServices;

import java.io.*;
import java.net.*;

public class UDPEchoServiceThread extends Thread {
   static final int PORT = 3701;

   public void run() {
      try {
         byte[] inBuf;
         DatagramSocket serverSocket = new DatagramSocket(PORT);
         System.out.println("UDP echo service started on port " + PORT);

         for (;;) {
            // Socket client = serverSocket.accept();
            inBuf = new byte[1024];
            // receive packet
            DatagramPacket inPacket = new DatagramPacket(inBuf, inBuf.length);
            serverSocket.receive(inPacket);
            String received = new String(inPacket.getData(), "US-ASCII");
            System.out.println("UDP server received message of length " + received.length() + " bytes");
            System.out.println("sender: " + inPacket.getAddress().getCanonicalHostName());
            // echo packet
            InetAddress senderAddress = inPacket.getAddress();
            // System.out.println(senderAddress.getHostAddress());
            DatagramPacket outPacket = new DatagramPacket(received.getBytes(), received.length(), senderAddress, PORT);
            serverSocket.send(outPacket);
         }
      } catch (IOException ex) {
         ex.printStackTrace();
         System.exit(-1);
      }
      System.out.println("UDP echo service terminated");
   }
}