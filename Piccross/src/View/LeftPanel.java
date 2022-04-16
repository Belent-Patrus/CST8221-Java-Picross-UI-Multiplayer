package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Belent Patrus Student Number: 041015657 Due Date: 2022-02-20
 *         Description: builds the left panel for our GameFrame Lab Professor
 *         Name: Daniel Comier Assessment: Assignment 2
 *
 */
public class LeftPanel extends JPanel {

	private JPanel rowHints;

	/**
	 * Constructor: generates leftpanel for piccross GameFrame
	 */
	public LeftPanel() {
		rowHints = new JPanel();

		rowHints.setLayout(new GridLayout(5, 3));
		this.setLayout(new BorderLayout());

		this.add(rowHints, BorderLayout.EAST);
	}

	public void setHints(int[] bHintsRow) {
		rowHints.removeAll();
		for (int i = 0; i < 15; i++) {
			if (bHintsRow[i] == 0) {

				rowHints.add(new JLabel("  "));
			} else {
				rowHints.add(new JLabel(Integer.toString(bHintsRow[i]) + "  "));
			}
		}
	}

}
