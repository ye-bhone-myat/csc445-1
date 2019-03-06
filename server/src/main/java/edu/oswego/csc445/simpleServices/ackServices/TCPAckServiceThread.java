package edu.oswego.csc445.simpleServices.ackServices;

import java.io.*;
import java.net.*;

public class TCPAckServiceThread extends Thread {
    static final int PORT = 2701;

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("TCP ack service started on port " + PORT);

            for (;;) {
                Socket client = serverSocket.accept();

                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String cmd = "";
                String message = null;
                while ((message = in.readLine()) != null) {
                    cmd += message;
                    out.println("*");
                }

                System.out.println("TCP server received message of length " + cmd.length() + " bytes");
                System.out.println("sender: " + client.getInetAddress().getCanonicalHostName());
                // System.out.println("replying with '*'");

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
