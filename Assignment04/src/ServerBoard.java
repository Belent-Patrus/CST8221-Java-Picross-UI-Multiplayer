
/**
 * @author Belent Patrus 
 * Student Number: 041015657 
 * Due Date: 2022-02-20
 * Description: represents a piccross gameboard.
 * Professor Name: Daniel Comier Assessment: Assignment 4
 *
 */
import java.util.Arrays;

public class ServerBoard {

	/**
	 * represent the game board sent from the client in binary
	 */
	private String[] gameBoardBinary;
	/**
	 * boolean to represent weather the gameBoardBinary field is empty or not
	 */
	private boolean empty;

	/**
	 * default Constructor
	 */
	public ServerBoard() {
		empty = true;
		gameBoardBinary = new String[5];
	}

	/**
	 * indicates if gameBoardBianry field is empty
	 * 
	 * @return empty
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * assigns the gameBoardBinary field to the clients gameboard in binary rep.
	 * 
	 * @param bBin the gameboard from client
	 */
	public void setBoard(String bBin) {
		// TODO Auto-generated method stub
		String tmp = bBin.substring(1, bBin.length() - 1);

		gameBoardBinary = tmp.split(", ");

//		System.out.println("This is the board saved to the server..");
//		for(String s : gameBoardBinary) {
//			System.out.println(s);
//		}

	}

	/**
	 * @return a string representation of the gameBoardBinary array field
	 */
	public String getGameBaordBin() {
		return Arrays.toString(gameBoardBinary);
	}

}
