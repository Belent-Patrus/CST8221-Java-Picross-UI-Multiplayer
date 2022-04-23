
/**
 * @author Belent Patrus 
 * Student Number: 041015657 
 * Due Date: 2022-02-20
 * Description: handles the threads for the server
 * Professor Name: Daniel Comier Assessment: Assignment 4
 *
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class ThreadHandler implements Runnable {

	Scanner in;
	PrintWriter out;
	PiccrossServer server;
	String name;
	private Socket incoming;
	private ServerBoard gameBoard;

	private final String SCOREACCESSCODE = "CODEX82F3";
	private final String UPLOADGAMEBOARDACCESSCODE = "CODEX82F4";
	private final String SENDGAMEBOARDACCESSCODE = "CODEX82F5";

	/**
	 * Constructs a handler.
	 * 
	 * @param i the incoming socket
	 * @param s the server
	 */
	public ThreadHandler(Socket i, PiccrossServer s) {
		incoming = i;
		server = s;
		gameBoard = s.serverBoard;
	}

	/**
	 * Sends a message to the current client.
	 * 
	 * @param msg    message to be sent
	 * @param option boolean if true attaches broadcast string to msg
	 */
	public void sendMessage(String msg, boolean option) {
		if (!option) {

			out.println(msg);
			return;
		}
		out.println("Broadcast: " + msg);

	}

	/**
	 * Display the highscore to this client
	 */
	private void displayHighScore() {
		out.println("PLAYER\tSCORE\tTIME");
		out.println("================================");
		for (Score score : server.highScoreList) {

			out.printf("%7s\t%2d\t%2d\n", score.getName(), score.getPoints(), score.getTime());
		}

	}

	/**
	 * Sends the server board to the client
	 */
	private void sendBoard() {
		out.println(SENDGAMEBOARDACCESSCODE + " " + server.serverBoard.getGameBaordBin());

	}

	/**
	 * sends the display menu to the client
	 */
	public void helpMenu() {
		out.println("HELP:");
		out.println("/help: this message");
		out.println("/bye: disconnect");
		out.println("/who: show who is connected to server");
		out.println("/name (name): to rename yourself");

	}

	/**
	 * run method trigger when thread is started
	 * 
	 */
	@Override
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
					String[] lineArgs = line.split(" "); // breaks up the send in command for easier parse
//					for(int i =0; i < lineArgs.length; i++) {
//						System.out.println(lineArgs[i]);
//					}
					if ("/help".compareTo(line.trim()) == 0) { // if user types help command
						helpMenu();
					} else if ("/who".compareTo(lineArgs[0].trim()) == 0) { // displays all users
						sendMessage(server.getUserList(), false);
					} else if ("/name".compareTo(lineArgs[0].trim()) == 0) { // ability to change name
						if (server.changeUserName(name, lineArgs[1].trim()) != -1) { // returns -1 if name is not found
																						// on servers list

							name = line.split(" ")[1].trim();
							sendMessage(line, false);
						}
					} else if ("/hs".compareTo(lineArgs[0].trim()) == 0) { // display high score to client
						displayHighScore();
					} else if ("/get".compareTo(lineArgs[0].trim()) == 0) { // get the servers board to the client
						sendBoard();
					} else if (SCOREACCESSCODE.compareTo(lineArgs[0].trim()) == 0) { // score sent int
//						System.out.println(line);
						int score = Integer.parseInt(lineArgs[1]);
						int time = Integer.parseInt(lineArgs[2]);
						String clientGameBin = line.split(" ", 4)[3]; // client board game in binary rep. sent as 4th
																		// argument
//						System.out.println("----- " + clientGameBin);
//						System.out.println("im here");
//						System.out.println(gameBoard.getGameBaordBin());
						// will only add the score to the high score list of the following demands are
						// met
						// must be playing same board game as one stored in server.
						// or there must be no board game on the server
						if (server.highScoreList.size() == 0
								|| clientGameBin.compareTo(gameBoard.getGameBaordBin()) == 0) {
							if (server.highScoreList.size() == 0) {
								server.uploadGame(clientGameBin);
							}
							Score newScore = new Score(score, time, name);
							server.addScore(newScore);

						}
					} else if (UPLOADGAMEBOARDACCESSCODE.compareTo(lineArgs[0].trim()) == 0) { // uploads the sent game
																								// to the server
//						System.out.println(line);
						String binArr = line.split(" ", 2)[1];
//						System.out.println("you are in the upload game section this is your board: " + binArr);
						server.uploadGame(binArr);
//						int score = Integer.parseInt(lineArgs[1]);
//						int time = Integer.parseInt(lineArgs[2]);
//						Score newScore = new Score(score, time);
//						server.addScore(newScore);
					} else { // just a normal chat message
						server.broadcast(name + ": " + line);

					}
//					out.println("Echo: " + line);
//					System.out.println(name + ": " + line);
					if (line.trim().equals("/bye"))
						done = true;
				}
			} finally {
				server.broadcast("Disconnecting...");
				incoming.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
