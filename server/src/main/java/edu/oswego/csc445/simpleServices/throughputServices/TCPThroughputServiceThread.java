package edu.oswego.csc445.simpleServices.throughputServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPThroughputServiceThread extends Thread {
	static final int PORT = 2701;

	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			System.out.println("TCP ack service started at " +
					new InetSocketAddress(InetAddress.getLocalHost(), PORT));

			for (; ; ) {
				Socket client = serverSocket.accept();

				PrintWriter out = new PrintWriter(client.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				String cmd = in.readLine();
				long start = System.nanoTime();
				out.println(cmd);
				long rtt = System.nanoTime() - start;
				rtt = (rtt < 1) ? (-1 * rtt) : rtt;

				long size = 8 * cmd.length();
//				System.out.println((size * 1000 / rtt));
				double throughput = (size * 1000) / rtt;

				System.out.println("TCP server received message of length " + cmd.length() + " bytes");
				System.out.println("Throughput: " + throughput + " Mbps");
				System.out.println("sender: " + client.getInetAddress().getCanonicalHostName());
				out.println(cmd);

				out.close();
				in.close();
				client.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		System.out.println("TCP server thread terminated");
	}
}
