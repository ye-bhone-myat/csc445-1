package edu.oswego.csc445.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class TCPClient {

	private byte[] host;
	private int port;

	public TCPClient(byte[] host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * Measures round-trip latency of messages using TCP
	 *
	 * @param messageSize size of message to be sent, in B
	 * @return the round-trip time
	 */
	public long t1(int messageSize) {
		System.out.println("t1");
		long rtt = 0;
		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			echoSocket = new Socket(InetAddress.getByAddress(host), port);
			System.out.println("Socket created");
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			System.out.println("sockets and streams created");
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " +
					host[0] + "." + host[0] + "." + host[0] + "." + host[0] + ".");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for TCP connection.");
			e.printStackTrace();
			System.exit(1);
		}

		try {
			// create message with given size
			byte[] b = new byte[messageSize];
			Arrays.fill(b, (byte) 65);
			// start timer and transmit
			System.out.println("Outbound message of size " + messageSize + " bytes to "
					+ echoSocket.getInetAddress().getCanonicalHostName());
			String message = new String(b);
			long start = System.nanoTime();
			out.println(message);
			String received = in.readLine();
			rtt = (System.nanoTime() - start);
//            System.out.println("Inbound message:");
//            received = received.length() > 128 ? received.substring(0, 124) + "..." : received;
//			System.out.println(received);
			// close everything
			out.close();
			in.close();
			echoSocket.close();
		} catch (IOException ex) {
			System.err.println("IO failure.");
			ex.printStackTrace();
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

		try {
			echoSocket = new Socket(InetAddress.getByAddress(host), port);
			// out = new BufferedWriter(echoSocket.getOutputStream());
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " +
					host[0] + "." + host[0] + "." + host[0] + "." + host[0] + ".");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for TCP connection.");
			e.printStackTrace();
			System.exit(1);
		}

		try {
			// BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			// while ((userInput = stdIn.readLine()) != null) {
			byte[] message = new byte[messageSize * 1024];
			Arrays.fill(message, (byte) 65);
			long transmit = System.nanoTime();
			out.println(new String(message, "US-ASCII"));
			// System.out.println("bleh");
//             String received = in.readLine();
			rtt = (System.nanoTime() - transmit);
			String received = in.readLine();
			long size = messageSize * 1024 * 8;
			double throughput = (size * 1000) / rtt;
			System.out.println("message size: " + message.length);
			System.out.println("throughput: " + throughput + " Mbps");
			// }

			out.close();
			in.close();
			// stdIn.close();
			echoSocket.close();
		} catch (IOException ex) {
			System.err.println("IO failure.");
			ex.printStackTrace();
		}
		return (1024 * messageSize) / rtt;
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
			echoSocket = new Socket(InetAddress.getByAddress(host), port);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " +
					host[0] + "." + host[0] + "." + host[0] + "." + host[0] + ".");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for TCP connection.");
			e.printStackTrace();
			System.exit(1);
		}

		try {
			byte[] message = new byte[messageSize];
			Arrays.fill(message, (byte) 65);
			System.out.println("Sending " + nMessages + " messages of size " + messageSize + " bytes to "
					+ echoSocket.getInetAddress().getCanonicalHostName());
			long transmit = System.nanoTime();
			for (int i = 0; i < nMessages; ++i) {
				out.println(new String(message, "US-ASCII"));
				in.readLine();
			}
			rtt = (System.nanoTime() - transmit);

			out.close();
			in.close();
			// stdIn.close();
			echoSocket.close();
		} catch (IOException ex) {
			System.err.println("IO failure.");
			ex.printStackTrace();
		}
		return rtt;
	}
}
