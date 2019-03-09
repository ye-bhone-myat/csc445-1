package edu.oswego.csc445;

import edu.oswego.csc445.simpleServices.ackServices.TCPAckServiceThread;
import edu.oswego.csc445.simpleServices.ackServices.UDPAckServiceThread;
import edu.oswego.csc445.simpleServices.echoServices.TCPEchoServiceThread;
import edu.oswego.csc445.simpleServices.echoServices.UDPEchoServiceThread;
import edu.oswego.csc445.simpleServices.throughputServices.TCPThroughputServiceThread;

public class Main {

	public static void main(String args[]) {
		if (args.length == 1) {
			switch (args[0]) {
				case "1":
					new Thread(new TCPEchoServiceThread()).start();
					new Thread(new UDPEchoServiceThread()).start();
					break;
				case "2":
					new Thread(new TCPThroughputServiceThread()).start();
					break;
				case "3":
					new Thread(new TCPAckServiceThread()).start();
					new Thread(new UDPAckServiceThread()).start();
					break;
			}
		} else {
			System.out.println("Usage:");
			System.out.println("[Measurement]");
			System.out.println();
			System.out.println("Measurement:");
			System.out.println("	1	measure rtt)");
			System.out.println("	2	measure throughout (TCP only)");
			System.out.println("	3	measure interaction between message size and number of messages");
		}
//		Thread udpService = new Thread(new UDPAckServiceThread());
//		// Thread udpService = new Thread(new UDPEchoServiceThread());
//		udpService.start();
//		// udpService.start();
	}

}