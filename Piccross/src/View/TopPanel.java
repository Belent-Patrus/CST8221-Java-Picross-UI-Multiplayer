package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author Belent Patrus Student Number: 041015657 Due Date: 2022-02-20
 *         Description: builds the top panel for our GameFrame Lab Professor
 *         Name: Daniel Comier Assessment: Assignment 2
 *
 */
public class TopPanel extends JPanel {

	/**
	 * holds image for logo
	 */
	private ImageIcon image;
	/**
	 * panel that will hold the hints
	 */
	private JPanel columnHints;

	/**
	 * label that will hold the logo
	 */
	private JLabel textfield;

	/**
	 * empty filler panel
	 */
	private JPanel empty1;
	/**
	 * empty filler panel
	 */
	private JPanel empty2;

	/**
	 * Constructor for TopPanel
	 */
	public TopPanel() {

		// border layout manager
		this.setLayout(new BorderLayout());

		// fields to be placed in this
		columnHints = new JPanel();
		textfield = new JLabel();
		empty1 = new JPanel();
		empty2 = new JPanel();

//		try { 
//			image = new ImageIcon(getClass().getResource("./piccrossLogo.png"));
//		} catch (Exception e) {
//			System.out.println(e.fillInStackTrace());
//		}

		// creates two empty panels with specificed border dimensions
		empty1 = fillerPanel(25, 0, 25, 260);
		empty2 = fillerPanel(25, 80, 25, 0);

		textfield = new JLabel(image);

		// creating the gridlayout with numbers to specify hints for buttons
		columnHints.setLayout(new GridLayout(3, 5));

		// adding all fields
		this.add(textfield, BorderLayout.NORTH);
		this.add(columnHints, BorderLayout.CENTER);
		this.add(empty2, BorderLayout.WEST);
		this.add(empty1, BorderLayout.EAST);

	}

	public void setHints(int[] bHintsCol) {

		columnHints.removeAll();

		for (int i = 0; i < 15; i++) {
			if (bHintsCol[i] == 0) {
				columnHints.add(new JLabel("  "));
			} else {
				JLabel h = new JLabel(Integer.toString(bHintsCol[i]));
				h.setHorizontalTextPosition(JLabel.CENTER);
				columnHints.add(h);

			}
		}

	}

	// creates filler panels to align components correctly
	private JPanel fillerPanel(int top, int right, int bottom, int left) {

		JPanel tmpPanel = new JPanel();

		tmpPanel.setForeground(new Color(255, 0, 0));
		tmpPanel.setBorder(new EmptyBorder(top, right, bottom, left));

		return tmpPanel;
	}

	public JPanel getHints() {
		return columnHints;
	}

}
