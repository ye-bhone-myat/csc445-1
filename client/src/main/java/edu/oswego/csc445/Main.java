package edu.oswego.csc445;

import java.io.IOException;

import edu.oswego.csc445.clients.*;

public class Main {

	public static void main(String args[]) throws IOException {

		String host = "kamikaze-grande";
		int port = 2701;
		int messageSize = 1024;
		int nMessages = 1024;

		double rtt = new TCPClient(host, port).t2(messageSize);
		System.out.println("RTT: " + rtt );

		// if (args.length > 1) {
		// 	String host = args[1];
		// 	int port = Integer.parseInt(args[2]);
		// 	int messageSize = Integer.parseInt(args[3]);
		// 	int nMessages = Integer.parseInt(args[4]);
		// 	switch (args[0]) {
		// 	case "-t":
		// 		new TCPClient(host, port).rttLatency(messageSize,nMessages);
		// 		break;
		// 	case "-u":
		// 	new UDPClient(host, port).rttLatency(messageSize,nMessages);
		// 		break;
		// 	default:
		// 		System.exit(0);
		// 	}
		// }

	}

}