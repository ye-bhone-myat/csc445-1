package edu.oswego.csc445.simpleServices.echoServices;

import java.io.*;
import java.net.*;

public class TCPEchoServiceThread extends Thread {

    int port;
    
    public TCPEchoServiceThread(int port){
		super();
		this.port = port;
	}

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("TCP echo service started at " +
					new InetSocketAddress(InetAddress.getLocalHost(), port));

            for (;;) {
                Socket client = serverSocket.accept();

                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String cmd = in.readLine();
                out.println(cmd);

                out.close();
                in.close();
                client.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        System.out.println("TCP echo service terminated");
    }
}
