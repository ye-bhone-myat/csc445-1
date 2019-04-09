package edu.oswego.csc445.clients;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class TCPClient {

	// private static final int ATTEMPTS = 100;
	private byte[] host;
	private int port;
	// private String path;

	public TCPClient(byte[] host, int port) {
		this.host = host;
		this.port = port;
		// path = "data" + File.separatorChar +
				// (host[0] & 0xFF) + "." + (host[1] & 0xFF) + "." + (host[2] & 0xFF) + "." + (host[3] & 0xFF);
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
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " +
					(host[0] & 0xFF) + "." + (host[1] & 0xFF) + "." + (host[2] & 0xFF) + "." + (host[3] & 0xFF)+ ".");
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
			// for (int i = 0; i < ATTEMPTS; ++i) {
				out.println(message);
				in.readLine();
			// }
			rtt = (System.nanoTime() - start);
			// new File(path).mkdirs();
			// path +=  File.separatorChar + "TCP-1-" + messageSize + ".ser";
			// close everything
			out.close();
			in.close();
			echoSocket.close();
		} catch (IOException ex) {
			System.err.println("IO failure.");
			ex.printStackTrace();
		}

		// ObjectOutputStream objOut;
		// try {
		// 	objOut = new ObjectOutputStream(new FileOutputStream(path));
		// 	objOut.writeObject(new Record1(
		// 			(host[0] & 0xFF) + "." + (host[1] & 0xFF) + "." + (host[2] & 0xFF) + "." + (host[3] & 0xFF),
		// 			port, "TCP", rtt, messageSize));
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }

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
			echoSocket = new Socket(InetAddress.getByAddress(host), port);
			// out = new BufferedWriter(echoSocket.getOutputStream());
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " +
					host[0] + "." + host[1] + "." + host[2] + "." + host[3] + ".");
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
			byte[] bytes = new byte[messageSize * 1024];
			Arrays.fill(bytes, (byte) 65);
			String message = new String(bytes, "US-ASCII");
			long transmit = System.nanoTime();
			for(int i = 0; i < ATTEMPTS; ++i){
				out.println(message);
			}
			rtt = (System.nanoTime() - transmit) / ATTEMPTS;
			String received, s;
			received = "";
			// while((s  = in.readLine()) != null){
			// 	received += s;
			// }
			long size = messageSize * 1024 * 8;
			throughput = (size * 10) / rtt;
			System.out.println("message size: " + message.length());
			System.out.println("throughput: " + throughput + " Mbps");
			// }

			// new File(path).mkdirs();
			// path += File.separator + "TCP-2-" + messageSize + ".ser";

			out.close();
			in.close();
			// stdIn.close();
			echoSocket.close();
		} catch (IOException ex) {
			System.err.println("IO failure.");
			ex.printStackTrace();
		}

		long size = messageSize * 1024 * 8;
		throughput = (size * 1000) / rtt;

		// ObjectOutputStream objOut;
		// try {
		// 	objOut = new ObjectOutputStream(new FileOutputStream(path));
		// 	objOut.writeObject(new Record2(
		// 			(host[0] & 0xFF) + "." + (host[1] & 0xFF) + "." + (host[2] & 0xFF) + "." + (host[3] & 0xFF),
		// 			port, throughput, messageSize));
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }
		return ( 1000 * messageSize) / rtt;
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
					host[0] + "." + host[1] + "." + host[2] + "." + host[3] + ".");
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

			// new File(path).mkdirs();
			// path += File.separator + "TCP-3-" + messageSize + ".ser";
			out.close();
			in.close();
			// stdIn.close();
			echoSocket.close();
		} catch (IOException ex) {
			System.err.println("IO failure.");
			ex.printStackTrace();
		}

		// ObjectOutputStream objOut;
		// try{
		// 	objOut = new ObjectOutputStream(new FileOutputStream(path));
		// 	objOut.writeObject(new Record3(
		// 			(host[0] & 0xFF) + "." + (host[1] & 0xFF) + "." + (host[2] & 0xFF) + "." + (host[3] & 0xFF),
		// 			port, "TCP", rtt, messageSize));
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }
		return rtt;
	}
}
