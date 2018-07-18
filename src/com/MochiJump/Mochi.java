package com.MochiJump;


import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.geom.Line2D;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;




public class Mochi{
	JumpInterface jump = new StandardJump();
	CollisionInterface collide = new StandardCollision();

	float x;
	float y;
	double keepHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/768;
	double keepWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/1336;
	double reSizer = .9;
	
	public void reSize() {
		x = (float) (x*reSizer*keepWidth);
		y = (float)(y*reSizer*keepHeight);
		sH = (float)(sH*reSizer*keepWidth);
		sW = (float)(sW*reSizer*keepHeight);
		// speed/gravity adjustment needs to be affected by size, otherwise making mochi larger he feels slower
	}


	float speedX = 0;
	float speedY = 3;
	float sH = 14;
	float sW = 21;
	boolean mRestR;
	boolean mRestL;
	boolean mRunR;
	boolean mRunL;
	boolean mJumpR = true;
	boolean mJumpL;
	boolean uJump;
	boolean midJump;
	int jTime = 0;
	
	LevelMap levelMap = new LevelMap();
	
	public void setActionToFalse () {
		mRestR = false;
		mRestL = false;
		mRunR = false;
		mRunL = false;
		mJumpR = false;
		mJumpL = false;
	}
//Chu means middle of in Japanese
	boolean jumpChu = true;		

// this is defined later in set boundaries so it is not necessary to have the arguments here.	
 	Rectangle mochi = new Rectangle((int)(x), (int)(y), (int)(sH), (int)(sW));
 	Line2D.Float mright = new Line2D.Float(x+sW, y, x+sW, y+sH);
	Line2D.Float mleft = new Line2D.Float(x, y, x, y+sH);
	Line2D.Float mtop = new Line2D.Float(x,y,x+sW,y);
	Line2D.Float mbottom = new Line2D.Float(x, y+sH, x+sW, y+sH);
	

	public float getSpeedY(){
			return this.speedY;
	}
	
	

	public float getSpeedX() {
		return this.speedX;
	}
	
	public float getX() {
			return this.x;
		}
	public float getY() {
			return this.y;
		}
	public void setY(float y) {
		this.y = y;
	}
	public float getsH() {
		return this.sH;
	}
	public float getsW() {
		return this.sW;
	}
	
	public boolean getmRunR() {
		return this.mRunR;
	}
	
	public void setRunR(boolean r) {
		mRunR = r;
	}
	
	
	public boolean getJumpChu() {
		return this.jumpChu;
	}
	public void setJumpChu(boolean jumpChu) {
		this.jumpChu = jumpChu;
	}
	
	public void setUJump(boolean uJump) {
		this.uJump = uJump;
	}
	
	public void setMidJump(boolean midJump) {
		this.midJump = midJump;
	}
	
	public void setJumpR(boolean mJumpR) {
		this.mJumpR = mJumpR;
	}
	
	public void setJumpL(boolean mJumpL) {
		this.mJumpL = mJumpL;
	}
	public void setJTime (int jTime) {
		this.jTime = jTime;
	}
	
	public void setRunL (boolean r) {
		mRunL = r;
	}
	
	public void setRestR (boolean r) {
		mRestR = r;
	}
	
	public void setRestL (boolean r) {
		mRestL = r;
	}
	
// let see if putting this in a method and then calling that method inside boundaryRules updates it.
// okay that didn't fix it. wait maybe we need to add the rectangle mochi in here:
// I think I'm on the right track but I've got a nullPointerException when i try to run this now.
	public void mBoundaries () {
		// the y axis here needs to be trimmed for the right and left or the intersection will always call this first!
			mright.setLine(x+sW, y+5, x+sW, y+sH-5);
			mleft.setLine(x, y+5, x, y+sH-5);
			// trim the x axis here for the same effect
			mtop.setLine(x+5,y,x+sW-5,y);
			mbottom.setLine(x+5, y+sH, x+sW-5, y+sH);
			mochi.setRect((int)(x), (int)(y), (int)(sH), (int)(sW));
		}

	public void landing (){
		if (mJumpR == true) {
			setActionToFalse();
			mRestR=true;
		}
		else if (mJumpL == true) {
			setActionToFalse();
			mRestL=true;
		}
	}
	
	// weirdly changing from a speedY parameter to just changing the Y works just fine.
	// not sure if I like having the mochijs2 as part of the jump animation. but i got it in there.
	public void mJumpHandler () {	
		jump.jump(this);
	}
	
	// Collision detection happens here
	public void boundaryRules () {
		collide.collide(this);
	}

// from playing around with an example of KeyBindings I can tell that it is possible setup and call keybindings from one class into
// another. 

	public JLabel keyInputs () {
		// okay it looks like I've been forgetting to create reference variables for the move classes!
		JLabel MochiL = new JLabel("Mochi Jump");
		
		MoveRightAct MoveRightAct = new MoveRightAct();
		RestRight RestRight = new RestRight();
		MoveLeftAct MoveLeftAct = new MoveLeftAct();
		RestLeft RestLeft = new RestLeft();
		JumpAct JumpAct = new JumpAct();

		InputMap im = MochiL.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = MochiL.getActionMap();	
		// How would this work if I moved "im" to another class?
		im.put(KeyStroke.getKeyStroke("RIGHT"), "MoveRightAct");
		am.put("MoveRightAct", MoveRightAct);
		// For keybinding I'm going to have to add an event for when a key is released for < & >.
		im.put(KeyStroke.getKeyStroke("released RIGHT"), "RestRight");
		am.put("RestRight", RestRight);
		im.put(KeyStroke.getKeyStroke("LEFT"), "MoveLeftAct");
		am.put("MoveLeftAct", MoveLeftAct);
		im.put(KeyStroke.getKeyStroke("released LEFT"), "RestLeft");
		am.put("RestLeft", RestLeft);
		im.put(KeyStroke.getKeyStroke("UP"), "JumpAct" );
		am.put("JumpAct", JumpAct);
		// I think I'll need this so that I can just call this method in the DogLogic Class
		return MochiL;
		
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
			x +=2;
			if (speedX >=-1) {
				speedX++;
			}
			if (speedX >= 1) {
				speedX = 1;
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
			x -= 2;
			if (speedX <=1) {
				speedX --;
			}
			if (speedX <= -1) {
				speedX = -1;
			}

			
		}
		
	}
	class JumpAct extends AbstractAction{
		public void actionPerformed (ActionEvent jr) {
			getJumpChu();
			if (jumpChu == false) {
				jumpChu = true;
				jTime = 1;
				mJumpHandler();
				
			}
		}
	}
		
	class RestRight extends AbstractAction{
		public void actionPerformed (ActionEvent rr) {
			if (speedX >0) {
				speedX --;
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
				speedX ++;
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

	


}