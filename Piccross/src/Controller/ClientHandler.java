package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

	private ApplicationEngine picrossUX;
	private Socket client;
	
	
	private InputStream inStream;
	private OutputStream outStream;
	private BufferedReader br; // br reading from instream
	 PrintWriter out; // out sends data to outstream
	private Scanner in; // 
	private String name;
	private int port;
	private String address;

	public ClientHandler(Socket s, ApplicationEngine p, String address, int port, String name) {

		client = s;
		picrossUX = p;
		this.address = address;
		this.port = port;
		this.name = name;

	}

	public void setOut(String s) {
		out.println(s);
	}

	@Override
	public void run() {
		try {

			inStream = client.getInputStream();
			outStream = client.getOutputStream();
			in = new Scanner(inStream);
			out = new PrintWriter(outStream, true /* autoFlush */);
			out.println(name);
//			BufferedReader buff = new BufferedReader(inStream);
			boolean done = false;
			while (in.hasNextLine() && !done) {
				String line = in.nextLine();
//				System.out.println("client class - " + line);
				picrossUX.panelRight.setOutput(line);
			}
			if(!in.hasNextLine()) {
				System.out.println("null string");
			}

		} catch (IOException e) {

		}
		
	}

}
