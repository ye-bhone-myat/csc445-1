package edu.oswego.csc445;

import edu.oswego.csc445.clients.TCPClient;
import edu.oswego.csc445.clients.UDPClient;
import edu.oswego.csc445.utils.Constants;

import java.io.*;

public class Main implements Constants {

	public static void main(String args[]) {
		String host = args[0];
		tcp(host);
		udp(host);
	}

	private static void tcp(String host) {
		File f = new File("data" + File.separator + host);
		f.mkdirs();
//		tcp1(host);
//		tcp2(host);
		tcp3(host);
	}

	private static void udp(String host) {
		File f = new File("data" + File.separator + host);
		f.mkdirs();
		udp1(host);
		udp3(host);
	}

	private static void write(String path, String host, int port, String protocol,
							  int task, float stat, int size) throws IOException {

		PrintWriter printWriter = new PrintWriter(
				new FileOutputStream(new File(path + protocol + "-" + task + "-" + size + ".json"))
				, true);
		printWriter.println("{" +
				"\"host\": " + "\"" + host + "\"" +
				",\"port\": " + port +
				",\"protocol\": " + "\"" + protocol + "\"" +
				",\"stat\": " + stat +
				",\"size\": " + size +
				"}");
		printWriter.close();

	}

	private static void tcp1(String host) {
		String protocol = "TCP";
		float m1, m64, m1024;
		int port = TCP_ECHO_PORT;
		String path = "data" + File.separator + host + File.separator;
		TCPClient tcp = new TCPClient(host, port);
		long rtt = 0;
		ObjectOutputStream objOut = null;

		for (int i = 0; i < ATTEMPTS; ++i) {
			rtt += tcp.t1(1);
		}
		m1 = ((float) (rtt / ATTEMPTS)) / 1000000;
		System.out.println("RTT for 1 byte using " + protocol + ": " + m1 + " ms");

		rtt = 0;
		for (int i = 0; i < ATTEMPTS; ++i) {
			rtt += tcp.t1(64);
		}
		m64 = ((float) (rtt / ATTEMPTS)) / 1000000;
		System.out.println("RTT for 64 bytes using " + protocol + ": " + m64 + " ms");
		// System.out.println(((float) (rtt / ATTEMPTS))/1000000);
		rtt = 0;
		for (int i = 0; i < ATTEMPTS; ++i) {
			rtt += tcp.t1(1024);
		}
		m1024 = ((float) (rtt / ATTEMPTS)) / 1000000;
		System.out.println("RTT for 1024 bytes using " + protocol + ": " + m1024 + " ms");

		try {
			write(path, host, port, protocol, 1, m1, 1);
			write(path, host, port, protocol, 1, m64, 64);
			write(path, host, port, protocol, 1, m1024, 1024);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void udp1(String host) {
		String protocol = "UDP";
		int port = UDP_ECHO_PORT;
		float m1, m64, m1024;
		String path = "data" + File.separator + host + File.separator;
		UDPClient udp = new UDPClient(host, port);
		long rtt = 0;

		for (int i = 0; i < ATTEMPTS; ++i) {
			rtt += udp.t1(1);
		}
		m1 = ((float) (rtt / ATTEMPTS)) / 1000000;
		System.out.println("RTT for 1 byte using " + protocol + ": " + m1 + " ms");

		rtt = 0;
		for (int i = 0; i < ATTEMPTS; ++i) {
			rtt += udp.t1(64);
		}
		m64 = ((float) (rtt / ATTEMPTS)) / 1000000;
		System.out.println("RTT for 64 bytes using " + protocol + ": " + m64 + " ms");
		// System.out.println(((float) (rtt / ATTEMPTS))/1000000);
		rtt = 0;
		for (int i = 0; i < ATTEMPTS; ++i) {
			rtt += udp.t1(1024);
		}
		m1024 = ((float) (rtt / ATTEMPTS)) / 1000000;
		System.out.println("RTT for 1024 bytes using " + protocol + ": " + m1024 + " ms");

		try {
			write(path, host, port, protocol, 1, m1, 1);
			write(path, host, port, protocol, 1, m64, 64);
			write(path, host, port, protocol, 1, m1024, 1024);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void tcp2(String host) {
		String protocol = "TCP";
		int port = TCP_THROUGHPUT_PORT;
		int[] sizes = {1, 16, 64, 256, 1024};
		double[] avgThroughputs = new double[sizes.length];
		String path = "data" + File.separator + host + File.separator;
		TCPClient tcp = new TCPClient(host, port);
		ObjectOutputStream objOut = null;

		for (int i = 0; i < avgThroughputs.length; ++i) {
			double throughput = 0;
			for (int j = 0; j < ATTEMPTS; ++j) {
				throughput += tcp.t2(sizes[i]);
			}
			avgThroughputs[i] = throughput / ATTEMPTS;
			System.out.println(sizes[i] + " : " + avgThroughputs[i] + " Mbps");
		}

		try {

			for (int i = 0; i < avgThroughputs.length; ++i) {
				int size = (sizes[i] * 1024);
				write(path, host, port, protocol, 2, (float) avgThroughputs[i], size);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void tcp3(String host) {
		String protocol = "TCP";
		int port = TCP_ACK_PORT;
		int[] sizes = {1024, 512, 256};
		int[] nMessages = new int[sizes.length];
		for (int i = 0; i < sizes.length; ++i) {
			nMessages[i] = 1024 * (1024 / sizes[i]);
		}
		float[] avgRtt = new float[sizes.length];
		String path = "data" + File.separator + host + File.separator;
		TCPClient tcp = new TCPClient(host, port);
		ObjectOutputStream objOut = null;

		for (int i = 0; i < avgRtt.length; ++i) {
			double rtt = 0;
			for (int j = 0; j < ATTEMPTS; ++j) {
				rtt += tcp.t3(sizes[i], nMessages[i]);
			}
			avgRtt[i] = (((float) (rtt / ATTEMPTS)) / 1000000) / 1000;
			System.out.println(sizes[i] + " : " + avgRtt[i] + " s");
		}

		try {

			for (int i = 0; i < avgRtt.length; ++i) {
				int size = (sizes[i] * 1024);
				write(path, host, port, protocol, 3, avgRtt[i], size);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void udp3(String host) {
		String protocol = "UDP";
		int port = UDP_ACK_PORT;
		int[] sizes = {1024, 512, 256};
		int[] nMessages = new int[sizes.length];
		for (int i = 0; i < sizes.length; ++i) {
			nMessages[i] = 1024 * (1024 / sizes[i]);
		}
		float[] avgRtt = new float[sizes.length];
		String path = "data" + File.separator + host + File.separator;
		UDPClient udp = new UDPClient(host, port);
		ObjectOutputStream objOut = null;

		for (int i = 0; i < avgRtt.length; ++i) {
			double rtt = 0;
			for (int j = 0; j < ATTEMPTS; ++j) {
				rtt += udp.t3(sizes[i], nMessages[i]);
			}
			avgRtt[i] = (((float) (rtt / ATTEMPTS)) / 1000000) / 1000;
			System.out.println(sizes[i] + " : " + avgRtt[i] + " s");
		}

		try {

			for (int i = 0; i < avgRtt.length; ++i) {
				int size = (sizes[i] * 1024);
				write(path, host, port, protocol, 2, (float) avgRtt[i], size);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}


}