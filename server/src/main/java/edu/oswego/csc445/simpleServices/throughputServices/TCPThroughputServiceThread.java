package edu.oswego.csc445.simpleServices.throughputServices;

import java.io.*;
import java.net.*;

public class TCPThroughputServiceThread extends Thread {
    static final int PORT = 2701;

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("TCP ack service started on port " + PORT);

            for (;;) {
                Socket client = serverSocket.accept();

                PrintWriter out = new PrintWriter(client.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                long start = System.nanoTime();
                String cmd = in.readLine();
                long rtt = System.nanoTime() - start;
                rtt = (rtt < 1) ? (-1 * rtt) : rtt;

                String reply = cmd;
                int 
                double throughput = ((cmd.length() * 1000) / rtt) * (1000000 / 1024);

                System.out.println("TCP server received message of length " + cmd.length() + " bytes");
                System.out.println("Throughput: " + throughput + " KB/s");
                System.out.println("sender: " + client.getInetAddress().getCanonicalHostName());
                out.println(reply);

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
