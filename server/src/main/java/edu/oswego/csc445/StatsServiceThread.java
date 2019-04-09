package edu.oswego.csc445;

import edu.oswego.csc445.utils.Records;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class StatsServiceThread extends Thread{
	private static final int PORT = 4701;
	private String dataDir;


	public StatsServiceThread(String dataDir){
		this.dataDir = dataDir;
	}

	public void run(){

		File[] data = new File(dataDir).listFiles();
		Records[] records = new Records[data.length];

		for (int i = 0; i < data.length; ++i){
			records[i] = new Records(data[i].getPath());
			records[i].loadRecords();
		}



		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init();

		Template template = velocityEngine.getTemplate("assgn1Site.html");
		VelocityContext context = new VelocityContext();
		context.put("tst", "Success");
		context.put("fr", records[0]);
		context.put("sr", records[1]);
		context.put("tr", records[2]);

		StringWriter writer = new StringWriter();
		template.merge( context, writer );


		try {

			String reply = writer.toString();

//			String reply = new BufferedReader(new FileReader("assgn1Site.html"))
//					.lines().reduce(String::concat).get();



			ServerSocket serverSocket = new ServerSocket(PORT);

			for (;;) {
				Socket client = serverSocket.accept();

				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in =
						new BufferedReader(new InputStreamReader(client.getInputStream()));

				String cmd = in.readLine();

				System.out.println(client.getInetAddress().getHostAddress());

				int len = reply.length();

				out.println("HTTP/1.0 200 OK");
				out.println("Content-Length: " + len);
				out.println("Content-Type: text/html\n");
				out.println(reply);

				out.close();
				in.close();
				client.close();
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
