package View;

import java.awt.Color;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import Model.GameRules;
import Model.GameRules.BoxStatusE;
import Model.GameRules.BoxTypeE;
import Model.GameRules.ButtonData;

/**
 * @author Belent Patrus Student Number: 041015657 Due Date: 2022-02-20
 *         Description: builds the center panel for our GameFrame Lab Professor
 *         Name: Daniel Comier Assessment: Assignment 2
 *
 */
public class CenterPanel extends JPanel {

	/**
	 * array of buttons user can click
	 */
	private JButton[] buttons = new JButton[25];

	/**
	 * Constructor creates the gridlayout of clickable buttons
	 */
	public CenterPanel() {

		setLayout(new GridLayout(5, 5));
		setBackground(new Color(150, 150, 150));
		for (int i = 0; i < 25; i++) {
			buttons[i] = new JButton();
			buttons[i].setBackground(new Color(150, 150, 150));
			add(buttons[i]);

		}

	}

	/**
	 * @param i : index in the buttons array
	 * @return buttons : JButton array of clickable buttons
	 */
	public JButton getButtons(int i) {
		return buttons[i];
	}

	/**
	 * Changes the image of the box based on both button type and ref.mark
	 * 
	 * @param i      : int, The index of the button in the array named buttons
	 * @param button : ButtonData, The characteristics of the button that was
	 *               played
	 * @param ref    : GameRules, game state object
	 */
	public void changeButtonState(int i, ButtonData button, GameRules ref) {
		if (button == null) {
			return;
		}
		ImageIcon xIcon = new ImageIcon("./A3_Graphics/A3_Graphics/xmark.png");
		Color filledBox = new Color(129, 218, 245);
		Color emptyBox = new Color(221, 221, 221);
		if (button.getType() == GameRules.BoxTypeE.FILLED) {

			if (ref.getMark()) { // got it wrong
				buttons[i].setIcon(xIcon);
				buttons[i].setBackground(filledBox);
			}

			buttons[i].setBackground(filledBox);

		} else if (button.getType() == GameRules.BoxTypeE.EMPTY) {
			buttons[i].setBackground(emptyBox);
			if (!ref.getMark()) {

				buttons[i].setIcon(xIcon);
			}

		}

	}

	/**
	 * Resets the buttons array
	 */
	public void resetField() {
		for (int i = 0; i < 25; i++) {
			buttons[i].setIcon(null);
			buttons[i].setBackground(new Color(150, 150, 150));
		}
	}

	
	/**
	 * displays the valid squares in yellow
	 * @param ref the GameRules object for this particular game
	 */
	public void displaySolution(GameRules ref) {
		for(int i =0; i < 25; i++) {
			if(ref.getButtonData(i).getType() == BoxTypeE.FILLED && ref.getButtonData(i).getStatus() == BoxStatusE.UNCHECKED) {
				buttons[i].setBackground(Color.YELLOW);
			}
		}
		
	}

}
