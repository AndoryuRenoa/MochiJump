import java.awt.*;

import javax.swing.ImageIcon;

public class Animation {
	// this is the resting mochi sprite:
	Image ms = new ImageIcon("mochirs.png").getImage();
// now let's do the reverse for when he faces the other way;
	Image msr = new ImageIcon("mochirsr.png").getImage();

// okay let's import the walking image to splice in the middle so he looks like he's walking:
	Image mws = new ImageIcon("mochiws.png").getImage();
	
// and the reverse
	Image mwsr = new ImageIcon ("mochiwsr.png").getImage();


	Image mjc1 = new ImageIcon ("mochijs1.png").getImage();
	Image mjc2 = new ImageIcon ("mochijs2.png").getImage();
	Image mjc3 = new ImageIcon ("mochijs3.png").getImage();

	Image mjc1r = new ImageIcon ("mochijs1r.png").getImage();
	Image mjc2r = new ImageIcon ("mochijs2r.png").getImage();
	Image mjc3r = new ImageIcon ("mochijs3r.png").getImage();

	Image currentSprite;
		
	int aniTime = 1;
	int sW = 21;
	int sH = 14;
	Mochi mochi = new Mochi();
	int x = (int) mochi.getX();
	int y = (int) mochi.getY();



	int speedY = (int) mochi.getSpeedY();
	int speedX = (int) mochi.getSpeedX();
	boolean mRestR = mochi.mRestR;
	boolean mRestL = mochi.mRestL;
	boolean mRunR = mochi.mRunR;
	boolean mRunL = mochi.mRunL;
	boolean mJumpR = mochi.mJumpR;
	boolean mJumpL = mochi.mJumpL;
	
	
	// this belongs in mochi class, but I'm putting it here now for testing purposes:
	// and for some reason it only works here?
	public void intertia() {
		y = speedY+y;
		x = speedX+x;
	}
	

	public void draw (Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setClip(x, y, sW, sH);
		g2.drawImage(currentSprite, x, y, sW, sH, null);
	}
	
	// okay now I just need something to call on this code and I'm feeling that it should be a thread...
	public void setCurrentSprite (){
		if (mRestR == true) {
			currentSprite = ms;
		}
		if (mRestL == true) {
			currentSprite = msr;
		}
		if (mRunR == true) {
			// can I leave this as an if statement? will that work? is it better that way as a while statement could get stuck?
			// would a while statement get stuck?
			if (aniTime <= 5) {
				currentSprite = mws;
				aniTime ++;
			}else if (aniTime <= 10) {
				currentSprite = ms;
				aniTime++;
			}else  {
				aniTime = 1;
			}
		}
		// why does this at least run when mRunL is set to true, but mRunR causes everything to lock up?
		// why is it only the
		// unless all three while statements are present nothing is painted, and then only the msr image is called... so strange
		// adding a new local variable here also does nothing to change the situation.
		// the crazy part is the wrong the sprite is what is shown when I try to run this, its the msr that is drawn not the mwsr...
		if (mRunL== true) {
			if (aniTime <= 5) {  
				currentSprite = mwsr;
				aniTime ++;
			}
			else if (aniTime <= 10) {
				currentSprite = msr;
				aniTime ++;
			} 
			else{
				aniTime = 1;
			}
				
			}
		// okay let's add the jump animations here:
			if (mJumpR == true) {
				// this should be done via y speed instead of a timer
				 if (speedY < 0) {
					 currentSprite = mjc1;
				 }
				 else if (speedY == 0) {
					 currentSprite = mjc2;
				 // maybe could add if else statement here to allow mochi to switch directions during jump
				 }
				 else if (speedY > 0){
					 currentSprite = mjc3;
				 }
				 else {
						System.out.println("something has gone wrong");
					}
			}
			// I have no idea why but the animation disappears when mJumpL is set to true and speedY is set to anything but
			// 0
			if (mJumpL == true) {
				// remeber y is inverted in java
				if (speedY < 0 ) {
					currentSprite = mjc1r;
				 }
				else if (speedY == 0) {
					currentSprite = mjc2r;
				 // maybe could add if else statement here to allow mochi to switch directions during jump
				 }
				else if (speedY > 0) {
					
					 currentSprite = mjc3r;
				 }
				else {
					System.out.println("something has gone wrong");
				}
				
			}
		
		}	
	
}


// forgot to use == instead of = in if statements. there is a a better way of declaring this.
