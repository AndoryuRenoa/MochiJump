import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;


// Platform creation, animation, collision, and Keybindings all are operational. 
// I've fixed the jump and inertia issue, but changing the Y value directly instead of speedY and it works fine.
// Jump animation currently includes mochijsp2, not sure if I liked not having it there better or not.
// I finished what I set out to do when I first started the project at this state.

// This current version may not be stable as I am working on implimenting a Start and Pause screen.***
// Mochi, Animation, LevelMap Classes will not be affected while implimenting start/pause screen

// MochiJump V 0.1 02/04/2018

public class MochiJump {

	
	
	 public static void main(String[] args) {
		 
		 		DogLogic dogLogic = new DogLogic();
		 //		StartPause sP = new StartPause();
		 // I think the switch to add a new thread and pain for pause start maybe should be in DogLogic only
		 // and not here in the main class
		 //		boolean isStart = sP.getIsStart();
		 //		boolean isPause = sP.getIsPause();
		 		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			         public void run() {
			        
				 JFrame frame = new JFrame ("Mochi Jump");
				 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				 frame.getContentPane().add(dogLogic, BorderLayout.CENTER);
				 frame.setExtendedState(JFrame.MAXIMIZED_BOTH);	
				 frame.pack();
				 frame.setVisible(true);
				 frame.repaint();
					 
			         }
	
		 		});
	 }
}
