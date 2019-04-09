package edu.oswego.csc445.simpleServices.throughputServices;

import edu.oswego.csc445.utils.Record2;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPThroughputServiceThread extends Thread {
	static final int PORT = 2701;
	int attempts = 100;
	long avgRtt;

	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			System.out.println("TCP ack service started at " + new InetSocketAddress(InetAddress.getLocalHost(), PORT));

			for (;;) {
				Socket client = serverSocket.accept();

				PrintWriter out = new PrintWriter(client.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				String cmd = in.readLine();
				long start = System.nanoTime();
				out.println(cmd);
				long rtt = System.nanoTime() - start;
				rtt = (rtt < 1) ? (-1 * rtt) : rtt;
				avgRtt += rtt;
				attempts--;
				long size = 8 * cmd.length();

				// out.println(cmd);
				System.out.println("TCP server received message of length " + cmd.length() + " bytes");
				// System.out.println("Throughput: " + throughput + " Mbps");
				System.out.println("sender: " + client.getInetAddress().getCanonicalHostName());
				if (attempts == 1) {
					double throughput = (size * 100000) / avgRtt;
					File file = new File("data" + File.separator + InetAddress.getLocalHost().getHostAddress());
					file.mkdirs();
					String path = file.getPath() + File.separator + "TCP-2-server" + cmd.length() + ".ser";

					ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(path));
					objOut.writeObject(new Record2(InetAddress.getLocalHost().getHostAddress(), PORT, throughput,
							cmd.length() / 1024));
					objOut.close();
				}
				out.close();
				in.close();
				// client.close();
			}
		} catch (

		IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		System.out.println("TCP server thread terminated");
	}
}
