package edu.oswego.csc445.clients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Arrays;
import java.util.Random;

public class UDPClient {

	private String host;
	private int port;
	// private String path;

	public UDPClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * Measures round-trip latency of messages using UDP
	 * <p>
	 * The minimum size of message sent is 1 byte.
	 *
	 * @param messageSize size of message to send, in bytes
	 * @return the round-trip time
	 */
	public long t1(int messageSize) {
		long rtt = 0;
		DatagramSocket echoSocket = null;
		InetAddress echoServerAddress = null;
		try {
			echoSocket = new DatagramSocket(port);
			echoSocket.setSoTimeout(30000);
			echoServerAddress = InetAddress.getByName(host);

		} catch (SocketException e) {
			System.err.println("Failed to open DatagramSocket at port " + port);
			return t1(messageSize);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + host);
			e.printStackTrace();
			System.exit(1);
		}

		try {
			// create message with given size
			byte[] message = new byte[messageSize];
			Arrays.fill(message, (byte) (new Random().nextInt(57) + 65));
			DatagramPacket sendPacket = new DatagramPacket(message, messageSize, echoServerAddress, port);
			DatagramPacket rcvePacket = new DatagramPacket(new byte[messageSize + 1], messageSize + 1,
					echoServerAddress, port);
			// start timer and transmit
			long transmit = System.nanoTime();
			echoSocket.send(sendPacket);
			echoSocket.receive(rcvePacket);
			rtt = System.nanoTime() - transmit;
			echoSocket.close();
		} catch (IOException e) {
			echoSocket.close();
			System.err.println("IO failure.");
			e.printStackTrace();
			return t1(messageSize);
		}
		return rtt;
	}

	/**
	 * Sends nMessages of size messageSize using UDP
	 * <p>
	 * The minimum size of message sent is 1 byte
	 *
	 * @param messageSize the size of message to be sent, in bytes
	 * @param nMessages   the number of messages of size messageSize to be sent
	 * @return
	 */
	public long t3(int messageSize, int nMessages) {
		messageSize = messageSize < 1 ? 1 : messageSize;
		nMessages = nMessages < 1 ? 1 : nMessages;
		long rtt = 0;
		DatagramSocket echoSocket = null;
		InetAddress echoServerAddress = null;
		try {
			echoSocket = new DatagramSocket(port);
			echoSocket.setSoTimeout(30000);
			echoServerAddress = InetAddress.getByName(host);
		} catch (SocketException e) {
			System.err.println("Failed to open DatagramSocket at port " + port);
			return t3(messageSize, nMessages);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + host);
			e.printStackTrace();
			System.exit(1);
		}
		try {
			byte[] message = new byte[messageSize];
			Arrays.fill(message, (byte) 65);
			DatagramPacket sendPacket = new DatagramPacket(message, message.length, echoServerAddress, port);
			long transmit = System.nanoTime();
			for (int i = 0; i < nMessages; ++i) {
				echoSocket.send(sendPacket);
				echoSocket.receive(sendPacket);
			}
			rtt = System.nanoTime() - transmit;
			echoSocket.close();

		} catch (IOException e) {
			System.err.println("IO failure.");
			echoSocket.close();
			return t3(messageSize, nMessages);
		}
		return rtt;
	}

}