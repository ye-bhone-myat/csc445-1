package edu.oswego.csc445;

import edu.oswego.csc445.clients.TCPClient;
import edu.oswego.csc445.clients.UDPClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String args[]) throws IOException {


		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		if (args.length == 3) {
			String arg = args[0] + " " + args[1] + " " + args[2];
			Matcher matcher = Pattern.compile("(-[tu]) (\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3}) ([1-3])")
					.matcher(arg);
			matcher.matches();
			String protocol = matcher.group(1);
			String measurement = matcher.group(6);
			byte[] host = {(byte) Integer.parseInt(matcher.group(2)), (byte) Integer.parseInt(matcher.group(3)),
					(byte) Integer.parseInt(matcher.group(4)), (byte) Integer.parseInt(matcher.group(5))};
			int port;
			int messageSize = 0;
			int nMessages = 0;
			switch (protocol) {
				case "-t":
					port = 2701;
					System.out.println("Using TCP");
					switch (measurement) {
						case "1":
							System.out.println("Measuring round-trip latency");
							System.out.print("Enter message size in bytes: ");
							try {
								messageSize = Integer.parseInt(in.readLine());
							} catch (NumberFormatException ex) {
								System.out.println("Please enter a number");
								System.exit(1);
							}
							new TCPClient(host, port).t1(messageSize);
							break;
						case "2":
							System.out.println("Measuring throughput ");
							System.out.print("Enter message size in kilobytes: ");
							try {
								messageSize = Integer.parseInt(in.readLine());
							} catch (NumberFormatException ex) {
								System.out.println("Please enter a number");
								System.exit(1);
							}
							new TCPClient(host, port).t2(messageSize);
							break;
						case "3":
							System.out.println("Measuring interaction between message size and number of messages ");
							System.out.println("Enter message size in bytes");
							System.out.println("and number of messages, separated by a space [x x]: ");
							matcher = Pattern.compile("(\\d{1,4}),? (\\d{1,4})").matcher(in.readLine());
							matcher.matches();
							try {
								messageSize = Integer.parseInt(matcher.group(1));
								nMessages = Integer.parseInt(matcher.group(2));
							} catch (NumberFormatException ex) {
								System.out.println("Please enter a number");
								System.exit(1);
							}
							new TCPClient(host, port).t3(messageSize, nMessages);
							break;
						default:
							System.out.println("Measurement:");
							System.out.println("	1	measure rtt)");
							System.out.println("	2	measure throughout (-t only)");
							System.out.println("	3	measure interaction between message size and number of messages");
							System.exit(0);
					}
					break;
				case "-u":
					port = 3701;
					System.out.println("Using UDP");
					switch (measurement) {
						case "1":
							System.out.println("Measuring round-trip latency");
							System.out.print("Enter message size in bytes: ");
							try {
								messageSize = Integer.parseInt(in.readLine());
							} catch (NumberFormatException ex) {
								System.out.println("Please enter a number");
								System.exit(1);
							}
							new UDPClient(host, port).t1(messageSize);
							break;
						case "2":
							System.out.println("Throughput measurement not available for UDP");
							break;
						case "3":
							System.out.println("Measuring interaction between message size and number of messages ");
							System.out.println("Enter message size in kilobytes");
							System.out.println("and number of messages, separated by a space [x x]: ");
							matcher = Pattern.compile("(\\d{1,4}),? (\\d{1,4})").matcher(in.readLine());
							matcher.matches();
							try {
								messageSize = Integer.parseInt(matcher.group(1));
								nMessages = Integer.parseInt(matcher.group(2));
							} catch (NumberFormatException ex) {
								System.out.println("Please enter a number");
								System.exit(1);
							}
							new UDPClient(host, port).t3(messageSize, nMessages);
							break;
						default:
							System.out.println("Measurement:");
							System.out.println("	1	measure rtt)");
							System.out.println("	2	measure throughout (-t only)");
							System.out.println("	3	measure interaction between message size and number of messages");
							System.exit(0);
					}
					break;
			}
			System.exit(0);
		}
		System.out.println("Usage:");
		System.out.println("[Protocol] [Target] [Measurement]");
		System.out.println();
		System.out.println("Protocol:");
		System.out.println("	-t	TCP");
		System.out.println("	-u	UDP");
		System.out.println();
		System.out.println("Target:");
		System.out.println("	IPv4 address of target machine (xxx.xxx.xxx.xxx)");
		System.out.println("Measurement:");
		System.out.println("	1	measure rtt)");
		System.out.println("	2	measure throughout (-t only)");
		System.out.println("	3	measure interaction between message size and number of messages");


	}

}