package edu.oswego.csc445.clients;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class TCPClient {

	// private static final int ATTEMPTS = 100;
	private String host;
	private int port;
	// private String path;

	public TCPClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * Measures round-trip latency of messages using TCP
	 *
	 * @param messageSize size of message to be sent, in B
	 * @return the round-trip time
	 * @throws InterruptedException
	 */
	public long t1(int messageSize){
		long rtt = 0;
		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			echoSocket = new Socket(InetAddress.getByName(host), port);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + host);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for TCP connection.");
			// e.printStackTrace();
			// System.exit(1);
			return t1(messageSize);
		}

		try {
			// create message with given size
			byte[] b = new byte[messageSize];
			Arrays.fill(b, (byte) 65);
			String message = new String(b);
			// start timer and transmit
			long start = System.nanoTime();
			out.println(message);
			in.readLine();
			rtt = System.nanoTime() - start;
			// close everything
			out.close();
			in.close();
			echoSocket.close();
		} catch (IOException ex) {
			System.err.println("IO failure.");
			return t1(messageSize);
			// ex.printStackTrace();
		}
		return rtt;
	}

	/**
	 * Measures the throughput of TCP messages by sending a message of messageSize
	 *
	 * @param messageSize size of message to be sent, in KB
	 * @return
	 */
	public double t2(int messageSize) {
		long rtt = 0;
		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		double throughput = 0;

		try {
			echoSocket = new Socket(InetAddress.getByName(host), port);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + host);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for TCP connection.");
			return t2(messageSize);
		}

		try {
			byte[] bytes = new byte[messageSize * 1024];
			Arrays.fill(bytes, (byte) 65);
			String message = new String(bytes, "US-ASCII");
			long transmit = System.nanoTime();
			out.println(message);
			rtt = System.nanoTime() - transmit;
			double size = bytes.length * 8;
			throughput = (size * 1000) / rtt;
			out.close();
			in.close();
			echoSocket.close();
		} catch (IOException ex) {
			System.err.println("IO failure.");
			return t2(messageSize);
		}
		return throughput;
	}

	/**
	 * Measures the time taken to send nMessages of size messageSize
	 *
	 * @param messageSize the size of each message, in bytes
	 * @param nMessages   number of messages to be sent
	 * @return
	 */
	public long t3(int messageSize, int nMessages) {
		messageSize = messageSize < 1 ? 1 : messageSize;
		nMessages = nMessages < 1 ? 1 : nMessages;
		long rtt = 0;
		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			echoSocket = new Socket(InetAddress.getByName(host), port);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + host);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for TCP connection.");
			return t3(messageSize, nMessages);
		}

		try {
			byte[] bytes = new byte[messageSize];
			Arrays.fill(bytes, (byte) 65);
			String message = new String(bytes, "US-ASCII");
			long transmit = System.nanoTime();
			for (int i = 0; i < nMessages; ++i) {
				out.println(message);
				in.readLine();
			}
			rtt = (System.nanoTime() - transmit);
			out.close();
			in.close();
			echoSocket.close();
		} catch (IOException ex) {
			System.err.println("IO failure.");
			return t3(messageSize, nMessages);
		}

		return rtt;
	}
}
