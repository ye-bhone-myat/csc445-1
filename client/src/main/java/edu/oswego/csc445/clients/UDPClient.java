package edu.oswego.csc445.clients;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Random;

public class UDPClient {

   private String host;
   private int port;

   public UDPClient(String host, int port) {
      this.host = host;
      this.port = port;
   }

   public long rttLatency(int messageSize, int nMessages) {
      nMessages = nMessages < 1 ? 1 : nMessages;
      long rtt = 0;
      DatagramSocket echoSocket = null;
      InetAddress echoServerAddress = null;
      try {
         echoSocket = new DatagramSocket(port);
         echoServerAddress = InetAddress.getByName(host);
      } catch (SocketException e) {
         System.err.println("Failed to open DatagramSocket at port " + port);
         e.printStackTrace();
         System.exit(1);
      } catch (UnknownHostException e) {
         System.err.println("Don't know about host " + host);
         e.printStackTrace();
         System.exit(1);
      }
      // BufferedReader inFromUser = new BufferedReader(new
      // InputStreamReader(System.in));
      try {
         byte[] message = new byte[messageSize];
         Arrays.fill(message, (byte) 65);
         System.out.println("Outbound message to " + echoServerAddress.getHostAddress() + " from port " + port);
         DatagramPacket packet = new DatagramPacket(message, message.length, echoServerAddress, port);
         long transmit = System.nanoTime();
         for (int i = 0; i < nMessages; ++i) {
            // System.out.println(new String(message));
            // out.write(new String(message, "US-ASCII"));
            // out.flush();
            echoSocket.send(packet);
         }
         // echoSocket.connect(echoServerAddress, port);
         // DatagramPacket receivePacket = new DatagramPacket(receiveData,
         // receiveData.length);
         // echoSocket.send(packet);
         // long transmit = System.nanoTime();
         echoSocket.receive(packet);
         rtt = System.nanoTime() - transmit;
         System.out.println("Echo received");
         // String modifiedSentence = new String(packet.getData(), "US-ASCII");
         // System.out.println("FROM SERVER:" + modifiedSentence);
         echoSocket.close();

      } catch (IOException e) {
         System.err.println("IO failure.");
         e.printStackTrace();
      }
      return rtt;
   }
}