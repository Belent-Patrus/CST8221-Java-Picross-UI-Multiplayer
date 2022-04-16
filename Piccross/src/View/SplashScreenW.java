package View;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import java.awt.*;
import javax.swing.*;

public class SplashScreenW {
	JWindow window = new JWindow();
	JProgressBar bar;
	
	
	public SplashScreenW()  {
		
		bar = new JProgressBar();
		window.getContentPane().add(
	    new JLabel(new ImageIcon("./A3_Graphics/A3_Graphics/pcLogo.png"), SwingConstants.CENTER), BorderLayout.CENTER);
		window.setBounds(0, 0, 300, 300);
		window.getContentPane().add(bar, BorderLayout.SOUTH);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		try {
		    progressBar();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		window.dispose();
		
	}
	
	private void progressBar() throws InterruptedException {
		bar.setValue(10);
		bar.setStringPainted(true);
		int counter = 1;
		while(counter <= 100) {
			bar.setValue(counter++);
			Thread.sleep(1); //COMABACVK
		}
	}

}
