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
	
	int port;

	public UDPAckServiceThread(int port){
		super();
		this.port = port;
	}

	public void run() {
		DatagramSocket serverSocket;
		try {

			System.out.println("UDP ack service started at " + new InetSocketAddress(InetAddress.getLocalHost(), port));
			for (;;) {
				serverSocket = new DatagramSocket(port);
				byte[] inBuf = new byte[1025];
				DatagramPacket inPacket = new DatagramPacket(inBuf, inBuf.length);
				serverSocket.receive(inPacket);
				inPacket.setLength(1);
				serverSocket.send(inPacket);
				serverSocket.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		System.out.println("UDP ack service terminated");
	}
}