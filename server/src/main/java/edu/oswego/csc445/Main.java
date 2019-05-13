package edu.oswego.csc445;

import edu.oswego.csc445.simpleServices.ackServices.TCPAckServiceThread;
import edu.oswego.csc445.simpleServices.ackServices.UDPAckServiceThread;
import edu.oswego.csc445.simpleServices.echoServices.TCPEchoServiceThread;
import edu.oswego.csc445.simpleServices.echoServices.UDPEchoServiceThread;
import edu.oswego.csc445.simpleServices.throughputServices.TCPThroughputServiceThread;
import edu.oswego.csc445.utils.Constants;

public class Main implements Constants {

	public static void main(String args[]){

		new Thread(new TCPEchoServiceThread(TCP_ECHO_PORT)).start();
		new Thread(new UDPEchoServiceThread(UDP_ECHO_PORT)).start();

		new Thread(new TCPThroughputServiceThread(TCP_THROUGHPUT_PORT)).start();
		
		new Thread(new UDPAckServiceThread(UDP_ACK_PORT)).start();
		new Thread(new TCPAckServiceThread(TCP_ACK_PORT)).start();
	}

}