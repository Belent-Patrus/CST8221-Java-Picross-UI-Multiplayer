import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This program implements a multithreaded server that listens to port 8189 and
 * echoes back all client input.
 */
public class PiccrossServer {

	Vector<ThreadedEchoHandler> myConnections = new Vector<>();

	public PiccrossServer() {
		try {
			int i = 1;
			ServerSocket s = new ServerSocket(1255);

			while (true) {
				System.out.println("Waiting for socket..");
				Socket incoming = s.accept();
				System.out.println("Spawing a server thread " + i);
				ThreadedEchoHandler teh = new ThreadedEchoHandler(incoming, this);
				myConnections.add(teh);
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
			teh.sendMessage(msg);
		}
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

	public void sendMessage(String msg) {
		out.println("Received message: " + msg);

	}

	public void run() {
		try {
			try {
//				InputStream inStream = incoming.getInputStream();
//				OutputStream outStream = incoming.getOutputStream();
//
//				in = new Scanner(inStream);
//				out = new PrintWriter(outStream, true /* autoFlush */);
//
//				out.println("Hello! Enter BYE to exit.");
//				
//
//				// echo client input
//				boolean done = false;
//				while (!done && in.hasNextLine()) {
//					System.out.println("im in threadhandler");
//					String line = in.nextLine();
//					out.println("Echo: " + line);
//					System.out.println("Client: " + line);
////					server.broadcast(line);
//					if (line.trim().equals("BYE"))
//						System.out.println("disconnected");
//						done = true;
//				}
//				

				InputStream inStream = incoming.getInputStream();
				OutputStream outStream = incoming.getOutputStream();

				in = new Scanner(inStream);
				out = new PrintWriter(outStream, true /* autoFlush */);

				out.println("Hello! Enter BYE to exit.");

				// echo client input
				boolean done = false;
				while (!done && in.hasNextLine()) {
					System.out.println("yes");
					String line = in.nextLine();
					out.println("Echo: " + line);
					server.broadcast(line);
					System.out.println("Client: " + line);
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

//
//
//import java.io.*;
//import java.net.*;
//import java.util.*;
//
///**
//   This program implements a multithreaded server that listens to port 8189 and echoes back 
//   all client input.
//*/
//public class PiccrossServer
//{  
//   public static void main(String[] args )
//   {  
//      try
//      {  
//         int i = 1;
//         ServerSocket s = new ServerSocket(1254);
//
//         while (true)
//         {  
//            Socket incoming = s.accept();
//            System.out.println("Spawing a server thread " + i);
//            Runnable r = new ThreadedEchoHandler(incoming);
//            Thread t = new Thread(r);
//            t.start();
//            i++;
//         }
//      }
//      catch (IOException e)
//      {  
//         e.printStackTrace();
//      }
//   }
//}
//
///**
//   This class handles the client input for one server socket connection. 
//*/
//class ThreadedEchoHandler implements Runnable
//{ 
//   /**
//      Constructs a handler.
//      @param i the incoming socket
//      @param c the counter for the handlers (used in prompts)
//   */
//   public ThreadedEchoHandler(Socket i)
//   { 
//      incoming = i; 
//   }
//
//   public void run()
//   {  
//      try
//      {  
//         try
//         {
//            InputStream inStream = incoming.getInputStream();
//            OutputStream outStream = incoming.getOutputStream();
//            
//            Scanner in = new Scanner(inStream);         
//            PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);
//            
//            out.println( "Hello! Enter BYE to exit." );
//            
//            // echo client input
//            boolean done = false;
//            while (!done && in.hasNextLine())
//            {  
//            	System.out.println("yes");
//               String line = in.nextLine();            
//               out.println("Echo: " + line);   
//               System.out.println("Client: "+line);
//               if (line.trim().equals("BYE"))
//                  done = true;
//            }
//         }
//         finally
//         {
//            incoming.close();
//         }
//      }
//      catch (IOException e)
//      {  
//         e.printStackTrace();
//      }
//   }
//
//   private Socket incoming;
//}
