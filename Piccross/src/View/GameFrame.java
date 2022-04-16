package View;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import java.awt.BorderLayout;

/**
 * @author Belent Patrus Student Number: 041015657 Due Date: 2022-02-20
 *         Description: creates our JFrame where we will store all our game
 *         components Lab Professor Name: Daniel Comier Assessment: Assignment 2
 *
 */

public class GameFrame extends JFrame {

	/**
	 * Constructor create the JFrame for out piccross window
	 */

	/**
	 * The JMenuItem to indicate weather the user wants to see the solution to the current match
	 */
	private JMenuItem solution;
	/**
	 * The JMenuItem to indicate weather the user wants to start a new game
	 */
	private JMenuItem nw;
	/**
	 * The JMenuItem to indicate weather the user wants to run debug senario 1 of the program
	 */
	private JMenuItem debugSenario01;
	/**
	 * The JMenuItem to indicate weather the user wants to run debug senario 2 of the program
	 */
	private JMenuItem debugSenario03;
	/**
	 * The JMenuItem to indicate weather the user wants to run debug senario 3 of the program
	 */
	private JMenuItem debugSenario02;
	/**
	 * The JMenuItem to indicate weather the user wants to run debug senario 3 of the program
	 */
	private JMenuItem newConnect;
	/**
	 * The JMenuItem to indicate weather the user wants to run debug senario 3 of the program
	 */
	private JMenuItem disconnect;


	/**
	 * The shortcut key for the solution JMenuItem (ALT+S)
	 */
	private final KeyStroke keySolution = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK);
	/**
	 * The shortcut key for the new JMenuItem (CRTL+N)
	 */
	private final KeyStroke keyNew = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
	
	/**
	 * Icon image for nw JMenuItem
	 */
	private ImageIcon newFileImg;
	
	/**
	 * Icon image for exit JMenuItem
	 */
	private ImageIcon extFileImg;
	
	/**
	 * Icon image for solution JMenuItem
	 */
	private ImageIcon solFileImg;
	
	/**
	 * The main menu bar for the user
	 */
	private JMenuBar menuBar;
	
	/**
	 * menu item to display programmer information
	 */
	private JMenuItem about;
	
	/**
	 * menu item to exit application
	 */
	private JMenuItem exit;

	/**
	 * Constructor
	 */
	public GameFrame() {
		this.setTitle("Piccross");

		try {
			newFileImg = new ImageIcon("./A3_Graphics/A3_Graphics/piciconnew.gif");
			extFileImg = new ImageIcon("./A3_Graphics/A3_Graphics/piciconext.gif");
			solFileImg = new ImageIcon("./A3_Graphics/A3_Graphics/piciconsol.gif");

		} catch (Exception e) {
			System.out.println(e);
			newFileImg = null;
			extFileImg = null;
			solFileImg = null;

		}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setResizable(false);
		this.getContentPane().setBackground(new Color(255, 255, 255));
		this.setLayout(new BorderLayout());
		this.setVisible(true);

		menuBar = createJMenuBar();

		this.setJMenuBar(menuBar);

		this.setLocationRelativeTo(null);

	}
	
	/**
	 * creates the menu bar for the frame
	 * @return JMenuBar menu
	 */
	private JMenuBar createJMenuBar() {
		JMenuBar menu = new JMenuBar();
		
		nw = new JMenuItem("new");
		nw.setAccelerator(keyNew);
		nw.setMnemonic(KeyEvent.VK_N);
		nw.setActionCommand("New");
		nw.setIcon(newFileImg);
		
		newConnect = new JMenuItem("New Connection");
		disconnect = new JMenuItem("Disconnect");
		
		disconnect.setEnabled(false);
		
		
		exit = new JMenuItem("exit");
		exit.setIcon(extFileImg);
		
		JMenu debug = new JMenu("debug");
		debugSenario01 = new JMenuItem("Senario 1: All Edges Filled");
		debugSenario02 = new JMenuItem("Senario 2: Max number of column hints");
		debugSenario03 = new JMenuItem("Senario 3: Max number of row hints");
		debug.add(debugSenario01);
		debug.add(debugSenario02);
		debug.add(debugSenario03);
		

		solution = new JMenuItem("Solution");
		solution.setAccelerator(keySolution);
		solution.setIcon(solFileImg);
		
		about = new JMenuItem("About");
		
		
		
		JMenu game = new JMenu("Game");
		JMenu network = new JMenu("Network");
		
		network.add(newConnect);
		network.add(disconnect);
		
		game.add(nw);
		game.add(debug);
		game.add(exit);
		
		JMenu help = new JMenu("Help");
		help.add(solution);
		help.add(about);
		
		menu.add(game);
		menu.add(network);
		menu.add(help);
		
		return menu;
		
	}
	
	/**
	 * @return exit
	 */
	public JMenuItem getExitItem() {
		return exit;
	}
	
	/**
	 * @return about
	 */
	public JMenuItem getAboutItem() {
		return about;
	}

	/**
	 * @return solution
	 */
	public JMenuItem getSolutionItem() {
		return solution;
	}

	/**
	 * @return nw
	 */
	public JMenuItem getNwItem() {
		return nw;
	}
	
	/**
	 * @return nw
	 */
	public JMenuItem getNewConnectItem() {
		return newConnect;
	}
	
	/**
	 * @return nw
	 */
	public JMenuItem getDisconnectItem() {
		return disconnect;
	}
	
	/**
	 * returns a specific debug senario MenuItem based on given int (1,2,3)
	 * @param i : the number to indicate which senario
	 * @return JMenuItem debugSenario0(i)
	 */
	public JMenuItem getDebugSenario(int i) {
		switch(i) {
			case 1:
				return debugSenario01;
			case 2:
				return debugSenario02;
			case 3:
				return debugSenario03;
			default:
				return null;
		}
	}

	/**
	 * close application
	 */
	public void close() {
		System.exit(0);
		
	}


}
