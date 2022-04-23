package Controller;

import java.awt.*;
import java.awt.event.*;

import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import Model.GameRules;
import View.CenterPanel;
import View.GameFrame;
import View.LeftPanel;
import View.PiccrossNetworkModalVC;
import View.RightPanel;
import View.SplashScreenW;
import View.TopPanel;

/**
 * @author Belent Patrus Student Number: 041015657 Due Date: 2022-02-20
 *         Description: builds the application window for piccross game Lab
 *         Professor Name: Daniel Comier Assessment: Assignment 2
 *
 */

public class ApplicationEngine {

	/**
	 * the Jframe for the application with borderlayout manager
	 */
	private GameFrame frame;
	/**
	 * the top panel for the frame
	 */
	private TopPanel panelTop;
	/**
	 * the center panel for the frame
	 */
	private CenterPanel panelCenter;
	/**
	 * the right panel for the frame
	 */
	public RightPanel panelRight;
	/**
	 * the left panel for the frame
	 */
	private LeftPanel panelLeft;
	/**
	 * the controller for all user input
	 */
	private ActionController actionControl;

	
	private ImageIcon newFileImg;

	private ControllableTimer timer;
	
	private PiccrossNetworkModalVC mod;
	
	ClientHandler client;


	/**
	 * Constructor for piccross application window
	 * 
	 */
	public ApplicationEngine() {

		SplashScreenW splashScreen = new SplashScreenW();

		frame = new GameFrame();
		mod = new PiccrossNetworkModalVC(frame);
		panelCenter = new CenterPanel();
		panelRight = new RightPanel();
		panelLeft = new LeftPanel();
		panelTop = new TopPanel();
		panelCenter.setOpaque(false);
		actionControl = new ActionController();

		panelTop.setHints(actionControl.ref.getColHints()); // sets the hints using array from the ref
		panelLeft.setHints(actionControl.ref.getRowHints());

		createListeners();
		timer = new ControllableTimer(panelRight);
		timer.start();
		timer.setStatus(ControllableTimer.START);
		frame.add(panelTop, BorderLayout.NORTH);
		frame.add(panelCenter, BorderLayout.CENTER);
		frame.add(panelRight, BorderLayout.EAST);
		frame.add(panelLeft, BorderLayout.WEST);

		frame.revalidate();
	}
	

	// adds all the action listeners to all the needed components in our frame
	private void createListeners() {
		
		for (int i = 0; i < 25; i++) {
			panelCenter.getButtons(i).addActionListener(actionControl);
		}
		panelRight.getMark().addActionListener(actionControl);
		panelRight.getResetBTN().addActionListener(actionControl);
		panelRight.getInField().addActionListener(actionControl);
		frame.getSolutionItem().addActionListener(actionControl);
		frame.getNwItem().addActionListener(actionControl);
		frame.getAboutItem().addActionListener(actionControl);
		frame.getExitItem().addActionListener(actionControl);
		frame.getDebugSenario(1).addActionListener(actionControl);
		frame.getDebugSenario(2).addActionListener(actionControl);
		frame.getDebugSenario(3).addActionListener(actionControl);
		frame.getNewConnectItem().addActionListener(actionControl);
		frame.getDisconnectItem().addActionListener(actionControl);

	}

	/**
	 * @author billy Class that acts as controller for all user input inside our
	 *         frame
	 */
	class ActionController implements ActionListener {
		GameRules ref = new GameRules();
		
		/**
		 * performes actions requried for each individual component with actionlistener
		 * added
		 */
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == frame.getExitItem()) {
				frame.close();
			}

			if (e.getSource() == frame.getNwItem()) { // new game
				timer.setStatus(ControllableTimer.RESET); // reset time
				timer.setStatus(ControllableTimer.START); // start time
				panelCenter.resetField(); // reset center panel
				panelRight.resetPanel(); // restart right panel
				ref.newGame();
				panelTop.setHints(actionControl.ref.getColHints());
				panelLeft.setHints(actionControl.ref.getRowHints());
			} else if (e.getSource() == panelRight.getResetBTN()) { // reset button clicked
				timer.setStatus(ControllableTimer.RESET);
				timer.setStatus(ControllableTimer.START);
				panelRight.resetPanel();
				panelCenter.resetField();
				ref.reset();
			} else if (ref.gameOver()) { // game is over
				return;
			} else if (e.getSource() == panelRight.getMark()) { // mark has been toggled
				ref.setMark();
			} else if (e.getSource() == panelRight.getInField()) { // input field used
//				System.out.println("I am the input field and my input has:" + panelRight.getInField().getText());
				client.out.println(panelRight.getInField().getText());
			} else if (e.getSource() == frame.getSolutionItem()) { // solution clicked
				ref.setSolution();
				panelCenter.displaySolution(ref);
				System.out.println("solution is: check");
			} else if (e.getSource() == frame.getDebugSenario(1)) { // senario 1
				ref.Senario1();
				timer.setStatus(ControllableTimer.RESET);
				panelCenter.resetField();
				panelRight.resetPanel();
				panelTop.setHints(ref.getColHints());
				panelLeft.setHints(ref.getRowHints());
			} else if (e.getSource() == frame.getDebugSenario(2)) { //senario 2
				ref.Senario2();
				timer.setStatus(ControllableTimer.RESET);
				panelCenter.resetField();
				panelRight.resetPanel();
				panelTop.setHints(ref.getColHints());
				panelLeft.setHints(ref.getRowHints());
			} else if (e.getSource() == frame.getDebugSenario(3)) { // senario 3
				ref.Senario3();
				timer.setStatus(ControllableTimer.RESET);
				panelCenter.resetField();
				panelRight.resetPanel();
				panelTop.setHints(ref.getColHints());
				panelLeft.setHints(ref.getRowHints());
			} else if (e.getSource() == frame.getAboutItem()) { // about menu item clicked
				JOptionPane.showMessageDialog(null, "Piccross-Assignment\nBy: Belent Patrus\n\nWinter term 2022");
			}else if(e.getSource() == frame.getNewConnectItem()) {
				 
				 mod.setVisible(true);
				 if(mod.pressedConnect()) {
//					 
//					 System.out.println("Address: "+ mod.getAddress());
//					 System.out.println("Port: "+ mod.getPort());
//					 System.out.println("Name: "+ mod.getName());
//					 
					 try {
						client = new ClientHandler(new Socket("localhost",61001),ApplicationEngine.this
								,mod.getAddress(),mod.getPort(),mod.getName());
						Thread t = new Thread(client);
						t.start();

						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				
					 
				 }
				
			}else if(e.getSource() == frame.getDisconnectItem()) {
				
			}
			buttonListener(e); // method for listening to the center panel buttons
			panelRight.setOutput(ref.getUserDisplayMessage()); // message the ref wants to output based on action played

		}

		// helper method to listen to the buttons
		private void buttonListener(ActionEvent e) {
//			
			for (int i = 0; i < 25; i++) {
				JButton btn = panelCenter.getButtons(i);
				if (e.getSource() == btn) {
					panelCenter.changeButtonState(i, ref.updateBox(i), ref);
					panelRight.setPoints(ref.getPoints());
					if (ref.gameOver()) {
						timer.setStatus(ControllableTimer.STOP);
						if (ref.getNumWrong() == 0) { // perfect game
							newFileImg = new ImageIcon("./A3_Graphics/A3_Graphics/gamepicwinner.png");
						} else {
							newFileImg = new ImageIcon("./A3_Graphics/A3_Graphics/gamepicend.png");
						}
						int r =JOptionPane.showOptionDialog(frame, newFileImg, "Winner!",
						         JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION, null,
						         new Object[] { "Yes", "No" }, JOptionPane.YES_NO_OPTION);
						if(r == JOptionPane.YES_OPTION && mod.pressedConnect()) {
							// in order to upload score, must be connected to network
							// highscore size == 0 || currentgame == servergame
							
							
							System.out.println("You selected upload highscore");
							client.uploadScore(ref.getPoints(),panelRight.getTime(),ref);
							client.sendGame(ref);
						}
					}

				}
			}
		}

	}

	public void setBoard(String boardBin) {
		actionControl.ref.pullServerGame(boardBin);
		
	}



}
