package edu.oswego.csc445;

import java.io.IOException;

import edu.oswego.csc445.simpleServices.throughputServices.*;

public class Main {

	public static void main(String args[]) throws IOException {
		Thread tcpService = new Thread(new TCPThroughputServiceThread());
		// Thread udpService = new Thread(new UDPEchoServiceThread());
		tcpService.start();
		// udpService.start();
	}

}