package edu.oswego.csc445.clients;


import edu.oswego.csc445.utils.Record1;
import edu.oswego.csc445.utils.Record3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Arrays;
import java.util.Random;

public class UDPClient {

	private static final int ATTEMPTS = 100;
	private byte[] host;
	private int port;
	private String path;

	public UDPClient(byte[] host, int port) {
		this.host = host;
		this.port = port;
		path = "data" + File.separator +
				(host[0] & 0xFF) + "." + (host[1] & 0xFF) + "." + (host[2] & 0xFF) + "." + (host[3] & 0xFF) ;
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
		messageSize = messageSize > 1 ? messageSize : 1;
		long rtt = 0;
		DatagramSocket echoSocket = null;
		InetAddress echoServerAddress = null;
		try {
			echoSocket = new DatagramSocket(port);
			echoServerAddress = InetAddress.getByAddress(host);

		} catch (SocketException e) {
			System.err.println("Failed to open DatagramSocket at port " + port);
			e.printStackTrace();
			System.exit(1);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " +
					(host[0] & 0xFF) + "." + (host[1] & 0xFF) + "." + (host[2] & 0xFF) + "." + (host[3] & 0xFF)+ ".");
			e.printStackTrace();
			System.exit(1);
		}

		try {
			// create message with given size
			byte[] message = new byte[messageSize];
			Arrays.fill(message, (byte) (new Random().nextInt(57) + 65));
			DatagramPacket sendPacket = new DatagramPacket(message, messageSize, echoServerAddress, port);
			DatagramPacket rcvePacket =
					new DatagramPacket(new byte[messageSize + 1], messageSize + 1, echoServerAddress, port);
			// start timer and transmit
			System.out.println("Outbound message of size " + messageSize +
					" to " + echoServerAddress.getHostAddress());
			long transmit = System.nanoTime();
			for (int i = 0; i < ATTEMPTS; ++i){
				echoSocket.send(sendPacket);
				echoSocket.receive(rcvePacket);
			}
			rtt = System.nanoTime() - transmit / ATTEMPTS;
			System.out.println("Inbound message:");
			System.out.println(new String(rcvePacket.getData(), "US-ASCII").trim());
			new File(path).mkdirs();
			path += File.separator + "UDP-1-" + messageSize + ".ser";
			echoSocket.close();
		} catch (IOException e) {
			System.err.println("IO failure.");
			e.printStackTrace();
		}

		ObjectOutputStream objOut;
		try {
			objOut = new ObjectOutputStream(new FileOutputStream(path));
			objOut.writeObject(new Record1(
					(host[0] & 0xFF) + "." + (host[1] & 0xFF) + "." + (host[2] & 0xFF) + "." + (host[3] & 0xFF),
					port, "UDP", rtt, messageSize));
		} catch (IOException e) {
			e.printStackTrace();
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
			echoServerAddress = InetAddress.getByAddress(host);
		} catch (SocketException e) {
			System.err.println("Failed to open DatagramSocket at port " + port);
			e.printStackTrace();
			System.exit(1);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " +
					(host[0] & 0xFF) + "." + (host[1] & 0xFF) + "." + (host[2] & 0xFF) + "." + (host[3] & 0xFF) + ".");
			e.printStackTrace();
			System.exit(1);
		}
		// BufferedReader inFromUser = new BufferedReader(new
		// InputStreamReader(System.in));
		try {
			byte[] message = new byte[messageSize];
			Arrays.fill(message, (byte) 65);
			System.out.println("Outbound message to " + echoServerAddress.getHostAddress() + " from port " + port);
			DatagramPacket sendPacket = new DatagramPacket(message, message.length, echoServerAddress, port);
			DatagramPacket rcvePacket = new DatagramPacket(new byte[2], 1, echoServerAddress, port);
			// String s = "";
			long transmit = System.nanoTime();
			for (int i = 0; i < nMessages; ++i) {
				echoSocket.send(sendPacket);
//				System.out.println("yay");
				echoSocket.receive(rcvePacket);
				// s += new String(rcvePacket.getData(), "US-ASCII").trim();
//				System.out.println(new String(sendPacket.getData(), "US-ASCII"));
				// Arrays.fill(message, (byte) ((i % 57) + 65));
			}
//			System.out.println(s.length());
			rtt = System.nanoTime() - transmit;
			new File(path).mkdir();
			path += File.separator + "UDP-3-" + messageSize + ".ser";
			System.out.println("Transfer finished");
			echoSocket.close();

		} catch (IOException e) {
			System.err.println("IO failure.");
			e.printStackTrace();
		}

		ObjectOutputStream objOut;
		try {
			objOut = new ObjectOutputStream(new FileOutputStream(path));
			objOut.writeObject(new Record3(
					(host[0] & 0xFF) + "." + (host[1] & 0xFF) + "." + (host[2] & 0xFF) + "." + (host[3] & 0xFF),
					port, "UDP", rtt, messageSize));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rtt;
	}


}