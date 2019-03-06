package edu.oswego.csc445.simpleServices.echoServices;

import java.io.*;
import java.net.*;

public class TCPEchoServiceThread extends Thread {
    static final int PORT = 2701;

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("TCP echo service started on port " + PORT);

            for (;;) {
                Socket client = serverSocket.accept();

                PrintWriter out = new PrintWriter(client.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String cmd = in.readLine();

                String reply = cmd;

                System.out.println("TCP server received message of length " + cmd.length() + " bytes");
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
        System.out.println("TCP echo service terminated");
    }
}
