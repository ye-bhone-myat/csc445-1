package edu.oswego.csc445.simpleServices.ackServices;

import java.io.*;
import java.net.*;

public class TCPAckServiceThread extends Thread {

    int port;

    public TCPAckServiceThread (int port ){
        super();
        this.port = port;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("TCP ack service started at " +
					new InetSocketAddress(InetAddress.getLocalHost(), port));

            for (;;) {
                Socket client = serverSocket.accept();

                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				in.readLine();
				out.println("*");

                out.close();
                in.close();
                client.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        System.out.println("TCP server thread terminated");
    }
}
