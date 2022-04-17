/**
 * @author Belent Patrus 
 * Student Number: 041015657 
 * Due Date: 2022-02-20
 * Description: handles the clients information needs with the server
 * Professor Name: Daniel Comier Assessment: Assignment 4
 *
 */

package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

import Model.GameRules;
import Model.GameRules.BoxTypeE;
import Model.GameRules.ButtonData;

public class ClientHandler implements Runnable {

	/**
	 * The UX of the client
	 */
	private ApplicationEngine picrossUX;
	/**
	 * the clients socket
	 */
	private Socket client;

	/**
	 * clients Inputstream
	 */
	private InputStream inStream;
	/**
	 * clients outputstream
	 */
	private OutputStream outStream;
	private BufferedReader br; // br reading from instream
	PrintWriter out; // out sends data to outstream

	/**
	 * scanner to read tokens
	 */
	private Scanner in;
	/**
	 * name of client
	 */
	private String name;
	/**
	 * port used for client
	 */
	private int port;
	/**
	 * address of client
	 */
	private String address;

	/**
	 * unique code to indicate to server a score is trying to be sent
	 */
	private final String UPLOADSCOREACCESSCODE = "CODEX82F3";

	/**
	 * unique code to indicate to server a gameboard is trying to be sent
	 */
	private final String UPLOADGAMEBOARDACCESSCODE = "CODEX82F4";
	/**
	 * unique code to indicate to client (this) a gameboard is trying to be recieved
	 */
	private final String RECEIVEGAMEBOARDACCESSCODE = "CODEX82F5";

	/**
	 * Constructor for ClientHandler
	 * 
	 * @param s       Socket to connect too
	 * @param p       UX for client
	 * @param address of client
	 * @param port    number of client
	 * @param name    of client
	 */
	public ClientHandler(Socket s, ApplicationEngine p, String address, int port, String name) {

		client = s;
		picrossUX = p;
		this.address = address;
		this.port = port;
		this.name = name;

	}

//	public void setOut(String s) {
//		out.println(s);
//	}

	/**
	 * gets a binary representation of the clients current gameboard
	 * 
	 * @param ref GameRules, current state of game
	 * @return String rep of the gameBoard in binary
	 */
	private String getBin(GameRules ref) {
		String[] gameBoardBinArr = new String[5];
		String row = "";
		int rowNum = 0;
		for (int i = 0; i < 25; i++) {
			ButtonData btn = ref.getBoardIndex(i);
			if (btn.getType() == BoxTypeE.FILLED) {
				row += "1";
			} else {
				row += "0";
			}
			if ((i - 4) % 5 == 0) {
				gameBoardBinArr[rowNum++] = row;
				row = "";
			}
		}

		return Arrays.toString(gameBoardBinArr);

	}

	/**
	 * sends the gameboard to the server
	 * 
	 * @param ref GameRules, current gameState
	 */
	public void sendGame(GameRules ref) {

		System.out.println(UPLOADGAMEBOARDACCESSCODE + " " + getBin(ref));
		out.println(UPLOADGAMEBOARDACCESSCODE + " " + getBin(ref));

	}

	/**
	 * sends the score of the client to the server
	 * 
	 * @param p   points scored
	 * @param t   time elapsed
	 * @param ref GameRules, state of game
	 */
	public void uploadScore(int p, int t, GameRules ref) {
		out.println(UPLOADSCOREACCESSCODE + " " + p + " " + t + " " + getBin(ref));
	}

	/**
	 * run method triggered when thread starts
	 */
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
				String[] lineArgs = line.split(" ");
				if (RECEIVEGAMEBOARDACCESSCODE.compareTo(lineArgs[0].trim()) == 0) {
					String binArr = line.split(" ", 2)[1];
					picrossUX.setBoard(binArr);
//					System.out.println("this is the line in cli hand - " + line);
				}

				else {

					picrossUX.panelRight.setOutput(line);
				}
//				System.out.println("client class - " + line);
			}

		} catch (IOException e) {

		}

	}

}
