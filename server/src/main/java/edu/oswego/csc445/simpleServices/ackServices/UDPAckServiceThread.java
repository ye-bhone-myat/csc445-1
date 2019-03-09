package edu.oswego.csc445.simpleServices.ackServices;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class UDPAckServiceThread extends Thread {
	static final int PORT = 3701;

	public void run() {
		try {
			byte[] inBuf = new byte[1025];
			DatagramSocket serverSocket = new DatagramSocket(PORT);

			System.out.println("UDP ack service started at " + new InetSocketAddress(InetAddress.getLocalHost(), PORT));

			for (; ; ) {
//				serverSocket = new DatagramSocket(PORT);
				// Socket client = serverSocket.accept();
				// receive packet
				DatagramPacket inPacket = new DatagramPacket(inBuf, inBuf.length);

//				StringBuilder received = new StringBuilder();

				serverSocket.receive(inPacket);
//				System.out.println("bleh");
//				int length = inPacket.getLength();
//				String string;
//				string = new String(inPacket.getData(), "US-ASCII");
//				System.out.println(inPacket.getOffset());
//				while (inPacket.getLength() > 0) {
//					string = new String(inPacket.getData(), "US-ASCII");
//					System.out.println(string + "\n" + string.length());
					InetAddress senderAddress = inPacket.getAddress();
				System.out.println(senderAddress.getHostAddress());
					DatagramPacket outPacket = new DatagramPacket("*".getBytes(), 1, senderAddress, PORT);
					serverSocket.send(outPacket);
////					inBuf = new byte[2048];
//					inPacket.setData(inBuf, 0, length);
//					System.out.println("beh");
//					serverSocket.receive(inPacket);
//				}
//				System.out.println( new String(inPacket.getData(), "US-ASCII").substring(0, inPacket.getLength() - 1));
				System.out.println("UDP ack service received message of length " + inPacket.getLength() + " bytes");
				System.out.println("sender: " + inPacket.getAddress().getCanonicalHostName());
//				inBuf = new byte[1025];
				// echo packet
//				serverSocket.close();

			}
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		System.out.println("UDP ack service terminated");
	}
}