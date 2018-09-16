package com.MochiJump;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;




public class Mochi extends PlayerCharacter{
	
	public Mochi (DogLogic dl){
		super(dl);
	}

	
	JumpInterface jump = new StandardJump();
	CollisionInterface collide = new MochiCollision();


	
	
	public void mJumpHandler () {	
		jump.jump(this);
	}
	

	public void boundaryRules () {
		collide.collide(this);
	}


	public void keyInputs() {
		
		MoveRightAct MoveRightAct = new MoveRightAct();
		RestRight RestRight = new RestRight();
		MoveLeftAct MoveLeftAct = new MoveLeftAct();
		RestLeft RestLeft = new RestLeft();
		JumpAct JumpAct = new JumpAct();
		Escape Escape = new Escape();

		InputMap im = dogLogic.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = dogLogic.getActionMap();	

		im.put(KeyStroke.getKeyStroke("RIGHT"), "MoveRightAct");
		am.put("MoveRightAct", MoveRightAct);
		im.put(KeyStroke.getKeyStroke("released RIGHT"), "RestRight");
		am.put("RestRight", RestRight);
		im.put(KeyStroke.getKeyStroke("LEFT"), "MoveLeftAct");
		am.put("MoveLeftAct", MoveLeftAct);
		im.put(KeyStroke.getKeyStroke("released LEFT"), "RestLeft");
		am.put("RestLeft", RestLeft);
		im.put(KeyStroke.getKeyStroke("UP"), "JumpAct" );
		am.put("JumpAct", JumpAct);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0), "Escape");
		am.put("Escape", Escape);
		
	}
	
	

	class MoveRightAct extends AbstractAction{
		public void actionPerformed (ActionEvent mr) {
			if (jumpChu == false) {
				setActionToFalse();
				mRunR = true;
			}
			if (jumpChu == true) {
				setActionToFalse();
				mJumpR = true;
			}
			x +=2 *dogLogic.resizeValue;
			if (speedX >=-1) {
				speedX += 1 *dogLogic.resizeValue;
			}
			if (speedX >= 1) {
				speedX = 1*dogLogic.resizeValue;
			}
		}
	}
	class MoveLeftAct extends AbstractAction{
		public void actionPerformed (ActionEvent ml) {
			mRunL = true; 
			if (jumpChu == false) {
				setActionToFalse();
				mRunL = true;
			}
			if (jumpChu == true) {
				setActionToFalse();
				mJumpL = true;
			} 
			x -= 2*dogLogic.resizeValue;
			if (speedX <=1) {
				speedX -= 1*dogLogic.resizeValue;
			}
			if (speedX <= -1) {
				speedX = -1*dogLogic.resizeValue;
			}

			
		}
		
	}
	class JumpAct extends AbstractAction{
		public void actionPerformed (ActionEvent jr) {
			if (jumpChu == false) {
				jumpChu = true;
				jump();
				
			}
		}
	}
		
	class RestRight extends AbstractAction{
		public void actionPerformed (ActionEvent rr) {
			if (speedX >0) {
				speedX -= 1*dogLogic.resizeValue;
				if (speedX <0) {
					speedX = 0;
				}
				if (speedX == 0 && jumpChu == false) {
					setActionToFalse();
					mRestR=true;
					
				}
				
			}
		}
	}
	class RestLeft extends AbstractAction{
		public void actionPerformed (ActionEvent rr) {
			if (speedX <0) {
				speedX += 1* dogLogic.resizeValue;
				if (speedX >0) {
					speedX = 0;
				}
				if (speedX == 0 && jumpChu==false) {
					setActionToFalse();
					mRestL=true;
					
				}
				
			}
		}
	}
	class Escape extends AbstractAction{
		public void actionPerformed (ActionEvent es) {
			dogLogic.switcher.leaveDogLogic();
			
		}
	}


	


}