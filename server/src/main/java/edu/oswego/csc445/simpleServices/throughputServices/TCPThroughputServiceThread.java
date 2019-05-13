package edu.oswego.csc445.simpleServices.throughputServices;

import edu.oswego.csc445.utils.Constants;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPThroughputServiceThread extends Thread implements Constants {
	int port;
	double throughputTotal = 0;

	public TCPThroughputServiceThread(int port) {
		super();
		this.port = port;
	}

	public void run() {
		try {
			int attempts = ATTEMPTS;
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println(
					"TCP throughput service started at " + new InetSocketAddress(InetAddress.getLocalHost(), port));

			for (;;) {
				Socket client = serverSocket.accept();

				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				String cmd = in.readLine();
				long start = System.nanoTime();
				out.println(cmd);
				long rtt = System.nanoTime() - start;
				rtt = (rtt < 1) ? (-1 * rtt) : rtt;
				long size = 8 * cmd.length();
				throughputTotal += (double) ((size * 1000) / rtt);
				--attempts;
				if (attempts == 0) {
					double throughput = throughputTotal / ATTEMPTS;
					File file = new File("data" + File.separator + client.getLocalAddress().getHostAddress());
					file.mkdirs();
					String path = file.getPath() + File.separator + "TCP-2-server" + cmd.length() + ".json";

					PrintWriter objOut = new PrintWriter(new FileOutputStream(new File(path)), true);
					objOut.println("{" +
							"\"host\": " + "\"" + InetAddress.getLocalHost().getHostAddress() + "\"" +
							",\"port\": " + port +
							",\"protocol\": " + "\"TCP\"" +
							",\"stat\": " + throughput +
							",\"size\": " + (cmd.length() / 1024) +
							"}");
					objOut.close();
					attempts = ATTEMPTS;
				}
				out.close();
				in.close();
				client.close();
			}
		} catch (
		IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		System.out.println("TCP server thread terminated");
	}
}
