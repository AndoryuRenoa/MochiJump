package com.MochiJump;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Line2D;

public abstract class GameCharacter {
	JumpInterface jump;
	CollisionInterface collide;
	DogLogic dogLogic;
	LevelMap levelMap;
	
	float x;
	float y;
	double keepHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/768;
	double keepWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/1336;
	double reSizer = 1;
	
	public GameCharacter (DogLogic dl) {
		dogLogic = dl;
		levelMap = dogLogic.levelMap;
	}
	
	public void reSize() {
		x = (float) (x*reSizer*keepWidth);
		y = (float)(y*reSizer*keepHeight);
		sH = (float)(sH*reSizer*keepWidth);
		sW = (float)(sW*reSizer*keepHeight);
	}
	
	public void runOffScreen(double reSizer) {
		x = (float) (x*reSizer*keepWidth);
		y = (float)(y*reSizer*keepHeight);
		sH = (float)(sH*reSizer*keepWidth);
		sW = (float)(sW*reSizer*keepHeight);
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
 	Rectangle mochi = new Rectangle((int)(x), (int)(y), (int)(sW), (int)(sH));
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
			mochi.setRect((int)(x), (int)(y), (int)(sW), (int)(sH));
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
	
	public abstract void mJumpHandler();
	
	public abstract void boundaryRules();


}
