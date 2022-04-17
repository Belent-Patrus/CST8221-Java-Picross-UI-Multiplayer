package View;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * @author Belent Patrus Student Number: 041015657 Due Date: 2022-02-20
 *         Description: builds the right panel for our GameFrame Lab Professor
 *         Name: Daniel Comier Assessment: Assignment 2
 *
 */

public class RightPanel extends JPanel {

	// Panels to be add to rightPanel
	/**
	 * Jpanel holds the input and output box
	 */
	private JPanel systemLog;
	/**
	 * JPanel holds the timer, time and reset button
	 */
	private JPanel timerPanel;
	/**
	 * JPanel holds the mark checkbox and the points
	 */
	private JPanel southPanel;

	// interactable components
	/**
	 * JTextArea that will display content to user
	 */
	private JTextArea outField;
	/**
	 * JCheckbox which will have some feature for user
	 */
	private JCheckBox mark;
	/**
	 * JTextField for user to input commands to system
	 */
	private JTextField inField;
	/**
	 * JButton which will allow user to reset game
	 */
	private JButton resetBTN;

	private JLabel timeDisplay;
	private JLabel pointCounter;

	// global styling
	private EmptyBorder paddingBorder = new EmptyBorder(20, 25, 20, 25);
	private Border compound = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),
			paddingBorder);

	/**
	 * Constructor for rightPanel
	 */
	public RightPanel() {

		this.setLayout(new BorderLayout());

		southPanel = createSouthPanel();
		timerPanel = createTimerPanel();
		systemLog = createSystemLogPanel();

		this.add(timerPanel, BorderLayout.NORTH);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(systemLog, BorderLayout.EAST);

	}

	// puts together the entire south panel for rightPanel
	private JPanel createSouthPanel() {

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());

		mark = new JCheckBox("Mark");
		mark.setHorizontalAlignment(JLabel.CENTER);

		pointCounter = new JLabel("Points:  0");
		pointCounter.setHorizontalAlignment(JLabel.CENTER);

		southPanel.add(mark, BorderLayout.NORTH);
		southPanel.add(pointCounter, BorderLayout.CENTER);
		southPanel.setBorder(new EmptyBorder(15, 0, 15, 0));

		return southPanel;
	}

	// creates the entire systemlog panel which will be placed on east of rightPanel
	private JPanel createSystemLogPanel() {

		// panel we will store everything in
		JPanel tmpPanel = new JPanel();
		tmpPanel.setLayout(new BorderLayout());

		// scroll pane for output box
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// creates the input box for user
		JPanel inputDiv = SysPanInputBox();

		// initialized the output box for the user
		outField = new JTextArea("System Logs\n");
		outField.setLineWrap(true);
		outField.setWrapStyleWord(true);
		outField.setEditable(false);
		outField.setBorder(new LineBorder(Color.BLACK));

		// adds the output box to the scroll wheel panel
		scrollPane.getViewport().add(outField);

		tmpPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 25, 20, 25),
				BorderFactory.createLineBorder(Color.black)));

		// adding the inputbox and the output box to the panel
		tmpPanel.add(inputDiv, BorderLayout.SOUTH);
		tmpPanel.add(scrollPane, BorderLayout.CENTER);

		return tmpPanel;
	}

	// creates the input box for the user to interact with the settings
	private JPanel SysPanInputBox() {
		JPanel inputDiv = new JPanel();
		inputDiv.setLayout(new BorderLayout());
		JLabel inputLabel = new JLabel("Input");
		inField = new JTextField(20);
		inField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),
				new EmptyBorder(5, 10, 5, 10)));
		inputDiv.add(inputLabel, BorderLayout.WEST);
		inputDiv.add(inField, BorderLayout.CENTER);
		return inputDiv;
	}

	// creates the timer panel to be place on top of rightPanel
	private JPanel createTimerPanel() {
		JPanel tmpPanel = new JPanel();
		tmpPanel.setLayout(new BoxLayout(tmpPanel, BoxLayout.Y_AXIS));

		// label to display title for time
		JLabel timerText = new JLabel();
		timerText.setForeground(new Color(0, 0, 0));
		timerText.setFont(new Font("Monospaced", Font.BOLD, 20));
		timerText.setHorizontalAlignment(JLabel.CENTER);
		timerText.setText("TIMER");
		timerText.setAlignmentX(CENTER_ALIGNMENT);
		timerText.setOpaque(true);

		// label where the time is going to be counting
		timeDisplay = new JLabel();
		timeDisplay.setForeground(new Color(0, 0, 0));
		timeDisplay.setFont(new Font("Monospaced", Font.BOLD, 20));
		timeDisplay.setHorizontalAlignment(JLabel.CENTER);
		timeDisplay.setText("0");
		timeDisplay.setOpaque(true);
		timeDisplay.setAlignmentX(CENTER_ALIGNMENT);

		// reset button to reset the game
		resetBTN = new JButton("Reset");
		resetBTN.setFocusable(false);
		resetBTN.setPreferredSize(new Dimension(25, 25));
		resetBTN.setAlignmentX(CENTER_ALIGNMENT);
		resetBTN.setBounds(70, 80, 100, 30);
		resetBTN.setBorder(compound);

		tmpPanel.add(timerText);
		tmpPanel.add(timeDisplay);
		tmpPanel.add(resetBTN);

		return tmpPanel;

	}

	public void setTime(int elapsed) {
		timeDisplay.setText(Integer.toString(elapsed));
	}

	public void setPoints(int points) {
		pointCounter.setText("Points: " + points);
	}
	
	public int getTime() {
		return Integer.parseInt(timeDisplay.getText());
	}

	public void setOutput(String msg) {
		outField.append(msg+"\n");
	}

	// getters for interactable components
	/**
	 * @return outField
	 */
	public JTextArea getOutField() {
		return outField;
	}

	/**
	 * @return mark
	 */
	public JCheckBox getMark() {
		return mark;
	}

	/**
	 * @return inField
	 */
	public JTextField getInField() {
		return inField;
	}

	/**
	 * @return resetBTN
	 */
	public JButton getResetBTN() {
		return resetBTN;
	}

	public void resetPanel() {
		outField.setText("");
		pointCounter.setText("0");
		mark.setSelected(false);;
	}
}
