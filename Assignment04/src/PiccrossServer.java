
/**
 * @author Belent Patrus 
 * Student Number: 041015657 
 * Due Date: 2022-02-20
 * Description: runs the server for Piccross clients
 * Professor Name: Daniel Comier Assessment: Assignment 4
 *
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class PiccrossServer {

	/**
	 * holds all the thread connections to the server
	 */
	Vector<ThreadHandler> myConnections = new Vector<>();
	/**
	 * stores all the names of the clients connected to the server
	 */
	ArrayList<String> userNameList = new ArrayList<>();
	/**
	 * stores all the scores played on the server game board
	 */
	ArrayList<Score> highScoreList = new ArrayList<>();
	/**
	 * server game board played to add to highscore
	 */
	ServerBoard serverBoard = new ServerBoard();

	public static void main(String[] args) {
		new PiccrossServer();
	}

	/**
	 * Constructor for PiccrossServer
	 */
	public PiccrossServer() {
		try {
			final int PORT = 61001;
			ServerSocket s = new ServerSocket(PORT);
			serverBoard = new ServerBoard();

			System.out.println("Waiting for clients..");
			while (true) {
				Socket incoming = s.accept();
				System.out.println("Client connected from  " + s.getLocalSocketAddress());
				ThreadHandler teh = new ThreadHandler(incoming, this);
				myConnections.add(teh);
				Runnable r = teh;
				Thread t = new Thread(r);
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * sends a message to all clients connect to server
	 * 
	 * @param msg message to be sent to all clients
	 */
	public void broadcast(String msg) {
		for (ThreadHandler teh : myConnections) {
			teh.sendMessage(msg, false);
		}
	}

	/**
	 * adds user to userNameList arrayList
	 * 
	 * @param s socket of name to be added
	 * @throws IOException if name is not given
	 */
	public void addUserName(Socket s) throws IOException {
		Scanner input = new Scanner(s.getInputStream());
		String userName = input.nextLine();
		userNameList.add(userName);
		broadcast(userName + " Enter the server");
	}

	/**
	 * sends a list of all users in the userNameList
	 * 
	 * @return String of all usernames connected to server
	 */
	public String getUserList() {
		String s = "UserList:\n";
		for (String name : userNameList) {
			s += name + "\n";
		}
		return s;
	}

	/**
	 * changes clients name
	 * 
	 * @param original current name of client
	 * @param newName  name to be changed too
	 * @return int to indicate success -1 means original name not in userNameList
	 */
	public int changeUserName(String original, String newName) {

		int index = userNameList.indexOf(original);
		if (index == -1) {
			return -1;
		}
		userNameList.set(index, newName);
		return 1;

	}

	/**
	 * adds score to highScoreList
	 * 
	 * @param newScore to be added
	 */
	public void addScore(Score newScore) {
		highScoreList.add(newScore);
		Collections.sort((List<Score>) highScoreList, Collections.reverseOrder());

	}

	/**
	 * uploads a game from client to server in the serverBoard field
	 * 
	 * @param boardBin the gamebaord from the client
	 */
	public void uploadGame(String boardBin) {
		if (!serverBoard.isEmpty()) { // if there is already a board uploaded, cannot overwrite. future feature to add
			return;
		}

		serverBoard.setBoard(boardBin);

	}

}
