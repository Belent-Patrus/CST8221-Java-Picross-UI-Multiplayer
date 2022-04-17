package Model;

import java.util.Random;

public class GameRules {

	/**
	 * @author billy Contains the types of button that is contained in the gameBoard
	 *         array
	 */
	public enum BoxTypeE {
		FILLED, EMPTY
	}

	/**
	 * @author billy Contains the states of button that is contained in the
	 *         gameBoard array
	 */
	public enum BoxStatusE {
		CHECKED, UNCHECKED
	}

	/**
	 * @author billy Class stores all the data needed to act when a button is
	 *         clicked
	 */
	public class ButtonData {

		/**
		 * Stores the type of button either filled or empty
		 */
		private BoxTypeE boxType;
		/**
		 * Stores the status of the button either checked or unchecked
		 */
		private BoxStatusE boxStatus;

		/**
		 * Constructor for ButtonData
		 * 
		 * @param boxType   = the type of button this will be
		 * @param boxStatus = the status this button is in
		 */
		public ButtonData(BoxTypeE boxType, BoxStatusE boxStatus) {
			this.boxType = boxType;
			this.boxStatus = boxStatus;

		}

		/**
		 * @return boxType
		 */
		public BoxTypeE getType() {
			return boxType;
		}

		public Object getStatus() {
			// TODO Auto-generated method stub
			return boxStatus;
		}

	}

	/**
	 * An array of ButtonData to store the current state of the game view,
	 */
	private ButtonData[] gameBoard;

	/**
	 * An array of int to store the column hints for the gameBoard
	 */
	private int[] arrHintsColumn;
	/**
	 * arrHintsColumn index
	 */
	private int hintsIndexColumn;
	/**
	 * An array of int to store the row hints for the gameBoard
	 */
	private int[] arrHintsRow;
	/**
	 * arrHintsRow index
	 */
	private int hintsIndexRow;
	/**
	 * stores the number of filled buttons
	 */
	private int numValid;

	private int numLost;
	private int numWrong;
	/**
	 * stores the number of filled buttons found
	 */
	private int numFound;
	/**
	 * stores the number of points gained
	 */
	private int points;
	/**
	 * toggles the mark checkbox mode, to enable/disable the marking of empty boxs
	 * as correct
	 */
	private boolean mark;
	/**
	 * flag to represent weather the user wants the solution shown to them
	 */
	private boolean solution;
	/**
	 * The user display message after a users action. varies based on action.
	 */
	private String userDisplayMessage;

	// helper method to generate a random bool T or F
	private boolean randomGen() {
		Random rd = new Random();
		return rd.nextBoolean();
	}

	/**
	 * default Constructor to create a GameRules to monitor a picross game
	 */
	public GameRules() {
		arrHintsColumn = new int[15];
		gameBoard = new ButtonData[25];
		arrHintsRow = new int[15];
		mark = false;
		solution = false;
		hintsIndexColumn = 0;
		hintsIndexRow = 0;
		numValid = 0;
		points = 0;
		numLost = 0;
		numWrong = 0;
		numFound = 0;
		userDisplayMessage = "";
		randomizeBoard();
	}

	/**
	 * executed when the user wants to run a debug senario and chooses option 1.
	 * Senario1: all the edges of the board are valid options.
	 */
	public void Senario1() { // only edges filled
		userDisplayMessage = "";
		mark = false;
		solution = false;
		hintsIndexColumn = 0;
		hintsIndexRow = 0;
		numValid = 0;
		points = 0;
		numLost = 0;
		numWrong = 0;
		numFound = 0;
		for (int i = 0; i < 25; i++) {
			if (i < 5 || i % 5 == 0 || (20 <= i && i <= 25) || i % 5 == 4) { // each condition validates on the of the
																				// sides of the board

				gameBoard[i] = new ButtonData(BoxTypeE.FILLED, BoxStatusE.UNCHECKED);
				numValid++;
			} else {
				gameBoard[i] = new ButtonData(BoxTypeE.EMPTY, BoxStatusE.UNCHECKED);
			}

		}
	}

	/**
	 * executed when the user wants to run a debug senario and chooses option 2.
	 * Senario2: all the column hints are filled
	 */
	public void Senario2() {
		mark = false;
		solution = false;
		hintsIndexColumn = 0;
		hintsIndexRow = 0;
		numValid = 0;
		points = 0;
		numLost = 0;
		numWrong = 0;
		numFound = 0;
		boolean flag = false;
		int flagCounter = 0;
		for (int i = 0; i < 25; i += 1) {
			if (flag) {
				gameBoard[i] = new ButtonData(BoxTypeE.EMPTY, BoxStatusE.UNCHECKED);
				flagCounter--;

			} else {

				gameBoard[i] = new ButtonData(BoxTypeE.FILLED, BoxStatusE.UNCHECKED);
				numValid++;
				flagCounter++;

			}

			if (flagCounter == 5) {
				flag = true;
			} else if (flagCounter == 0) {
				flag = false;
			}

		}
	}

	/**
	 * executed when the user wants to run a debug senario and chooses option 3.
	 * Senario3: all the row hints are filled
	 */
	public void Senario3() {
		mark = false;
		solution = false;
		hintsIndexColumn = 0;
		hintsIndexRow = 0;
		numValid = 0;
		points = 0;
		numLost = 0;
		numWrong = 0;
		numFound = 0;
		boolean flag = false;
		int flagCounter = 0;
		for (int i = 0; i < 25; i++) {
			if (flag) {
				gameBoard[i] = new ButtonData(BoxTypeE.EMPTY, BoxStatusE.UNCHECKED);
				flag = false;

			} else {

				gameBoard[i] = new ButtonData(BoxTypeE.FILLED, BoxStatusE.UNCHECKED);
				numValid++;
				flagCounter++;
				flag = true;

			}

			if (flagCounter == 3) {
				flag = false;
				flagCounter = 0;

			}

		}
	}

	/**
	 * sets the game board gotten from server
	 * 
	 * @param boardBin gameBoard gotten from server
	 */
	public void pullServerGame(String boardBin) {
		String bin = boardBin.substring(1, boardBin.length() - 1).replace(", ", "");
		System.out.println("this is from pull server bin: " + bin);
		userDisplayMessage = "";
		mark = false;
		solution = false;
		hintsIndexColumn = 0;
		hintsIndexRow = 0;
		numValid = 0;
		points = 0;
		numLost = 0;
		numWrong = 0;
		numFound = 0;

		for (int i = 0; i < 25; i++) {
			if (bin.charAt(i) == '1') {

				gameBoard[i] = new ButtonData(BoxTypeE.FILLED, BoxStatusE.UNCHECKED);
				numValid++;
			} else {
				gameBoard[i] = new ButtonData(BoxTypeE.EMPTY, BoxStatusE.UNCHECKED);
			}

		}
	}

	/**
	 * toggles the mark boolean value which represent weather the checkbox is
	 * checked or not
	 */
	public void setMark() {
		mark = !mark;
		if (mark) {
			userDisplayMessage = "\nMark has been enabled - now select EMPTY boxs";
		} else {
			userDisplayMessage = "\nMark has been disabled - now select FILLED boxs";
		}
	}

	/**
	 * sets the solution value to true, which shows the user the solution to the
	 * current gameBoard
	 */
	public void setSolution() {
		solution = true;
	}

	/**
	 * randomly fills the gameBoard up with new buttonData to begin a new game
	 */
	public void randomizeBoard() {
		for (int i = 0; i < 25; i++) {
			if (randomGen() == true) {

				gameBoard[i] = new ButtonData(BoxTypeE.FILLED, BoxStatusE.UNCHECKED);
				numValid++;
			} else {
				gameBoard[i] = new ButtonData(BoxTypeE.EMPTY, BoxStatusE.UNCHECKED);
			}

		}

	}

	/**
	 * Fills the arrHintsColumn array with the correct values based on the gameBoard
	 * arrays buttonData
	 * 
	 * @return the array arrHintsColumn
	 */
	public int[] getColHints() { // column numbers are 0 based
		int colNum = 0;
		while (colNum < 5) { // iterates over each column
			hintsIndexColumn = colNum; // sets the index based on which column it has to iterate down

			boolean[] col = new boolean[5]; // stores how many in the column were filled and were empty by setting them
											// = to true and false respectively
			int counter = 0;

			for (int i = colNum; i <= 20 + colNum; i += 5) { // independent of which column iterating over, the next
																// value in the column has an index difference of 5
				if (gameBoard[i].boxType == BoxTypeE.FILLED) {
					col[counter++] = true;
				} else {
					col[counter++] = false;
				}
			}

			setHintsColumn(col); // private helper method to add the actual hint values to the arrHintsColumn
									// array
			colNum++;
		}

		return arrHintsColumn;

	}

	// helper method adds values to the arrHintsColumn Array
	private void setHintsColumn(boolean[] bHints) {

		int[] hints = new int[3]; // to store this columns hint numbers, only 3 hints maximum

		int counter = 0;
		for (int i = 0; i < 5; i++) { // iterate over the bHints array which stores booleans to represent either valid
										// or invalid
			if (bHints[i]) { // if its true means that its valid
				counter++;
			}
			if (i == 4 && bHints[i]) { // if its on the last value in the column and its a valid value
				for (int j = 0; j < 3; j++) { // iterate over the hints array to find an empty slot
					if (hints[j] == 0) { // if we hit 0 that means its empty
						hints[j] = counter;
						break;
					}
				}
				counter = 0; // reset counter to count next sequence
			} else if (!bHints[i] && counter > 0) { // if the value is invalid and we are in a sequence -> reset counter
													// add sequence to hints
				for (int j = 0; j < 3; j++) { // iterate over the hints array to find an empty slot
					if (hints[j] == 0) { // if we hit 0 that means its empty
						hints[j] = counter;
						break;
					}
				}
				counter = 0; // reset counter to count next sequence
			}
		}

		// To display correctly in the 5x3 grid view, most store values in indexes with
		// a difference of 5 to be in next row in a 1D length 15 array
		arrHintsColumn[hintsIndexColumn] = hints[0];
		arrHintsColumn[hintsIndexColumn + 5] = hints[1];
		arrHintsColumn[hintsIndexColumn + 10] = hints[2];
		hintsIndexColumn++; // increment for next column
	}

	/**
	 * Fills the arrHintsRow array with the correct values based on the gameBoard
	 * arrays buttonData
	 * 
	 * @return the array arrHintsRow
	 */
	public int[] getRowHints() {
		int rowNum = 0;
		while (rowNum < 5) { // iterate over each row
			boolean[] row = new boolean[5]; // stores how many in the row were filled and were empty by setting them =
											// to true and false respectively

			int counter = 0;

			for (int i = rowNum * 5; i <= rowNum * 5 + 4; i++) { // there are 4 values in each row and based on row
																	// number 0,1,2,.. must multiply by 5 to reach index
																	// in gameBoard
																	// array since it is 1D and length 15
				if (gameBoard[i].boxType == BoxTypeE.FILLED) {
					row[counter++] = true;
				} else {
					row[counter++] = false;
				}
			}

			setHintsRow(row); // private helper method to add the actual hint values to the arrHintsRow array
			rowNum++;
		}

		return arrHintsRow;

	}

	// helper method adds values to the arrHintsColumn Array
	private void setHintsRow(boolean[] bHints) {

		int[] hints = new int[3]; // to store this row hint numbers, only 3 hints maximum

		int counter = 0;
		for (int i = 0; i < 5; i++) { // iterate over the bHints array which stores booleans to represent either valid
										// or invalid
			if (bHints[i]) { // if its true means that its valid
				counter++;
			}
			if (i == 4 && bHints[i]) { // if its on the last value in the row and its a valid value
				for (int j = 0; j < 3; j++) { // iterate over the hints array to find an empty slot
					if (hints[j] == 0) { // if we hit 0 that means its empty
						hints[j] = counter;
						break;
					}
				}
				counter = 0; // reset counter to count next sequence
			} else if (!bHints[i] && counter > 0) { // if the value is invalid and we are in a sequence -> reset counter
													// add sequence to hints
				for (int j = 0; j < 3; j++) { // iterate over the hints array to find an empty slot
					if (hints[j] == 0) { // if we hit 0 that means its empty
						hints[j] = counter;
						break;
					}
				}
				counter = 0; // reset counter to count next sequence
			}
		}

		// adds the hints to the correct index in the arrHintsRow
		arrHintsRow[hintsIndexRow++] = hints[0];
		arrHintsRow[hintsIndexRow++] = hints[1];
		arrHintsRow[hintsIndexRow++] = hints[2];

	}

	public ButtonData updateBox(int i) {
		if (gameOver()) {
			return null;
		}
		ButtonData button = gameBoard[i];
		if (button.boxStatus == BoxStatusE.CHECKED)
			return null;
		if (button.boxType == BoxTypeE.FILLED) {
			if (!mark) { // correct
				button.boxStatus = BoxStatusE.CHECKED;
				numFound++;
				points++;
				userDisplayMessage = "\n+1 points - Mark: OFF -> Selected Filled box";
				return button;
			} else { // got it wrong
				button.boxStatus = BoxStatusE.CHECKED;
				numLost++;
				numWrong++;
				userDisplayMessage = "\nOOF, sorry incorrect that box is NOT empty";
				return button;

			}

		} else if (button.boxType == BoxTypeE.EMPTY) {

			if (!mark) { // got it wrong
				numWrong++;
				button.boxStatus = BoxStatusE.CHECKED;
				userDisplayMessage = "\nOOF, sorry incorrect that box IS empty";
			} else {
				button.boxStatus = BoxStatusE.CHECKED;
				points++;
				userDisplayMessage = "\n+1 points - Mark: ON -> Selected empty box";
				return button;
			}

		}
		return button;
	}

	public ButtonData getBoardIndex(int i) {
		if (i < 0 || i >= gameBoard.length)
			return null;

		ButtonData button = gameBoard[i];

		return button;
	}

	/**
	 * @return points
	 */
	public int getPoints() {
		return points;
	}

	public ButtonData getButtonData(int i) {
		return gameBoard[i];
	}

	/**
	 * @return mark
	 */
	public boolean getMark() {
		return mark;
	}

	/**
	 * @return numLost - number of valid boxs out of play
	 */
	public int getNumLost() {
		return numLost;
	}

	/**
	 * @return numWrong - the number of wrong boxs clicked
	 */
	public int getNumWrong() {
		return numWrong;
	}

	/**
	 * @return true if the game is over, game is over if numFound == numValid.
	 */
	public boolean gameOver() {
		if (numFound == (numValid - numLost)) {
			userDisplayMessage = "\nGame over";
		}
		return numFound == (numValid - numLost);
	}

	/**
	 * @return message that should about to the user based on the action taken
	 */
	public String getUserDisplayMessage() {
		String s = userDisplayMessage;
		userDisplayMessage = "";
		return s;
	}

	/**
	 * resets the gameBoard with the same game in play
	 */
	public void reset() {
		userDisplayMessage = "Game has been Reset";
		mark = false;
		solution = false;
		hintsIndexColumn = 0;
		hintsIndexRow = 0;
		numFound = 0;
		numLost = 0;
		points = 0;
		numWrong = 0;
		for (int i = 0; i < 25; i++) {
			gameBoard[i].boxStatus = BoxStatusE.UNCHECKED;
		}
	}

	/**
	 * creates an entirely new game
	 */
	public void newGame() {

		userDisplayMessage = "";
		mark = false;
		solution = false;
		hintsIndexColumn = 0;
		hintsIndexRow = 0;
		numValid = 0;
		points = 0;
		numFound = 0;
		numLost = 0;
		numWrong = 0;
		randomizeBoard();
	}

}
