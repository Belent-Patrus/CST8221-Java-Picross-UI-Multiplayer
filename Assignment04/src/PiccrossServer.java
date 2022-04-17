import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This program implements a multithreaded server that listens to port 8189 and
 * echoes back all client input.
 */
public class PiccrossServer {

	Vector<ThreadedEchoHandler> myConnections = new Vector<>();
	ArrayList<String> userNameList = new ArrayList<>();

	public PiccrossServer() {
		try {
			int i = 1;
			final int PORT = 1255;
			ServerSocket s = new ServerSocket(PORT);

			System.out.println("Waiting for clients..");
			while (true) {
				Socket incoming = s.accept();
				System.out.println("Client connected from  " + s.getLocalSocketAddress());
				ThreadedEchoHandler teh = new ThreadedEchoHandler(incoming, this);
				myConnections.add(teh);
//				addUserName(incoming);
				Runnable r = teh;
				Thread t = new Thread(r);
				t.start();
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void broadcast(String msg) {
		for (ThreadedEchoHandler teh : myConnections) {
			teh.sendMessage(msg,false);
		}
	}
	
	public void addUserName(Socket s) throws IOException{
		Scanner input = new Scanner(s.getInputStream());
		String userName = input.nextLine();
		userNameList.add(userName);
		broadcast(userName + " Enter the server");
	}
	
	

	public static void main(String[] args) {
		new PiccrossServer();
	}
}

/**
 * This class handles the client input for one server socket connection.
 */
class ThreadedEchoHandler implements Runnable {

	Scanner in;
	PrintWriter out;
	PiccrossServer server;
	String name;

	/**
	 * Constructs a handler.
	 * 
	 * @param i the incoming socket
	 * @param c the counter for the handlers (used in prompts)
	 */
	public ThreadedEchoHandler(Socket i, PiccrossServer s) {
		incoming = i;
		server = s;
	}

	public void sendMessage(String msg, boolean option) {
		if(!option) {
			
			out.println(msg);
			return;
		}
		out.println("Broadcast: " + msg);

	}
	
	public void helpMenu() {
		out.println("HELP:");
		out.println("/help: this message");
		out.println("/bye: disconnect");
		out.println("/who: show who is connected to server");
		out.println("/name (name): to rename yourself");
		
	}

	public void run() {
		try {
			try {
				InputStream inStream = incoming.getInputStream();
				OutputStream outStream = incoming.getOutputStream();

				in = new Scanner(inStream);
				out = new PrintWriter(outStream, true /* autoFlush */);
				server.addUserName(incoming);
				name = server.userNameList.get(server.userNameList.size() - 1);

//				out.println("Hello! Enter BYE to exit.");

				// echo client input
				boolean done = false;
				while (!done && in.hasNextLine()) {
					String line = in.nextLine();
					if("/help".compareTo(line.trim()) == 0) {
						helpMenu();
					}
//					out.println("Echo: " + line);
					server.broadcast(name + ": " + line);
					System.out.println(name + ": " + line);
					if (line.trim().equals("BYE"))
						done = true;
				}
			} finally {
				incoming.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Socket incoming;
}
