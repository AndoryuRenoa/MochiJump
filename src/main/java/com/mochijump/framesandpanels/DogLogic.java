package com.mochijump.framesandpanels;

import com.mochijump.levels.LevelMap;
import com.mochijump.collision.NoCollideFactory;
import com.mochijump.animation.AnimationFactory;
import com.mochijump.animation.AnimationInterface;
import com.mochijump.characters.GameCharacter;
import com.mochijump.characters.NonPlayerCharacter;
import com.mochijump.characters.PlayerCharacter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.*;

/**
 * This class is responsible for tying together all of the objects working to create the actual game interactions
 *
 * @author Andrew Lenoir
 */
public class DogLogic extends JPanel {
	private final Logger LOG = LogManager.getLogger(DogLogic.class);
	private int currentPanel;
	public LevelMap levelMap;
	private AnimationFactory animationFactory = new AnimationFactory();
	public ArrayList <Rectangle> plat = new ArrayList<Rectangle>();
	private ArrayList <AnimationInterface> animation = new ArrayList <AnimationInterface>();
	public ArrayList <GameCharacter> gameCharacters = new ArrayList <GameCharacter>();
	private int refreshRate = 40;
	private Rectangle background = new Rectangle (0,0,10000,10000);
	private Color skyblue = new Color (102, 204, 255);
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public PanelSwitcher switcher;
	private NoCollideFactory noCollideFactory = new NoCollideFactory (this);
	//TODO make resizeValue configurable
	public int resizeValue = 7;
	private boolean setupCamera = false;
	private Rectangle screenRect;
	private double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public boolean runAway = false;
	

	
	public DogLogic (PanelSwitcher s) {
		levelMap = new LevelMap(this);
		JPanel dogPain = new JPanel();
		add(dogPain);
		gameStart();
		switcher = s;
		screenRect = new  Rectangle (0, 0,
				(int) screenWidth,
				(int) screenHeight);
	}
	
	public void setupCamera() {
		if (!setupCamera) {
			float startingYDiff = (float)(gameCharacters.get(gameCharacters.size()-1).keepHeight*786/2)-
					gameCharacters.get(gameCharacters.size()-1).y;
			
				if (gameCharacters.get(gameCharacters.size()-1).y> 
				(gameCharacters.get(gameCharacters.size()-1).keepHeight*786/2)) {
							
					for (int i=0; i<gameCharacters.size(); i++) {
						gameCharacters.get(i).y = (float) (gameCharacters.get(i).y+startingYDiff);
					}
					for (int i = 0; i<plat.size() ; i++) {
						plat.get(i).y = (int) (plat.get(i).y+startingYDiff);
					}
				}
				if (gameCharacters.get(gameCharacters.size()-1).y< 
				(gameCharacters.get(gameCharacters.size()-1).keepHeight*786/2.5)) {
					for (int i=0; i<gameCharacters.size(); i++) {
						gameCharacters.get(i).y = (float) (gameCharacters.get(i).y-startingYDiff);
					}
					for (int i = 0; i<plat.size() ; i++) {
						plat.get(i).y = (int) (plat.get(i).y-startingYDiff);
					}
				}
				
				float startingXDiff = (float)(gameCharacters.get(gameCharacters.size()-1).keepWidth*1336/2)-
						gameCharacters.get(gameCharacters.size()-1).x;
				
				if (gameCharacters.get(gameCharacters.size()-1).x> 
				(gameCharacters.get(gameCharacters.size()-1).keepWidth*1336/1.5)) {
					for (int i=0; i<gameCharacters.size(); i++) {
						gameCharacters.get(i).x = (float) (gameCharacters.get(i).x+startingXDiff);
					}
					for (int i = 0; i<plat.size() ; i++) {
						plat.get(i).x = (int) (plat.get(i).x+startingXDiff);
					}
				}
				setupCamera = true;
			
		}
	}
		
			
		
	public void addGameCharacter (GameCharacter character, int startingX, int startingY) {
		character.posInGameCharacter = gameCharacters.size();
		gameCharacters.add(character);
		character.x = startingX;
		character.y = startingY;
		character.reSize(resizeValue);
		if (character instanceof PlayerCharacter) {
			PlayerCharacter playerCharacter = (PlayerCharacter) character;
			playerCharacter.keyInputs();
		}
		animation.add(animationFactory.makeAnimation(character));
	}
	
	public void setCurrentPanel (int option) {
		this.currentPanel = option;
	}

	public void gameStart() {
		Thread gameThread = new Thread() {
			public void run() {
				while (currentPanel==2) {
					gameUpdate();
					repaint();
					try {
						Thread.sleep(1000/refreshRate);
					}catch (InterruptedException e) {
						LOG.error("An error has occured in game's main thread: {}", e.getLocalizedMessage());
						throw new IllegalStateException(e);
					}
				}
			
			}
		}; 
		gameThread.start(); 
	}

	public void gameUpdate () {
		plat = levelMap.getPlat();
		for (int i=0; i<animation.size(); i++) {
			animation.get(i).setCurrentSprite();
		}
		for (int i=0; i<gameCharacters.size(); i++) {
			if (gameCharacters.get(i) instanceof NonPlayerCharacter) {
				NonPlayerCharacter npc = (NonPlayerCharacter)gameCharacters.get(i);
				npc.aIInputs();
			}
			gameCharacters.get(i).boundaryRules();
		}
		for (int i=0; i<animation.size(); i++) {
			animation.get(i).aniVarUpdate(this.gameCharacters.get(i));
		}
		
		if (runAway == true) {
			gameCharacters.get(gameCharacters.size()-1).runOffScreen(.9);
		}
		setupCamera();
		updateCamera();
	}
	
	public void updateCamera() {
		if (gameCharacters.get(gameCharacters.size()-1).y> 
		(gameCharacters.get(gameCharacters.size()-1).keepHeight*786/2)) {
			for (int i=0; i<gameCharacters.size(); i++) {
				gameCharacters.get(i).y = (float) (gameCharacters.get(i).y-(int)(2.9*resizeValue));
			}
			for (int i = 0; i<plat.size() ; i++) {
				plat.get(i).y = (int) (plat.get(i).y-(int)(2.9*resizeValue));
			}
		}
		if (gameCharacters.get(gameCharacters.size()-1).y< 
		(gameCharacters.get(gameCharacters.size()-1).keepHeight*786/2.5)) {
			for (int i=0; i<gameCharacters.size(); i++) {
				gameCharacters.get(i).y = (float) (gameCharacters.get(i).y+(int)(2.9*resizeValue));
			}
			for (int i = 0; i<plat.size() ; i++) {
				plat.get(i).y = (int) (plat.get(i).y+(int)(2.9*resizeValue));
			}
		}
		
		if (gameCharacters.get(gameCharacters.size()-1).x> 
		(gameCharacters.get(gameCharacters.size()-1).keepWidth*1336/1.95)) {
			for (int i=0; i<gameCharacters.size(); i++) {
				gameCharacters.get(i).x = (float) (gameCharacters.get(i).x-(int)(3.25*resizeValue));
			}
			for (int i = 0; i<plat.size() ; i++) {
				plat.get(i).x = (int) (plat.get(i).x-(int)(3.25*resizeValue));
			}
		}
		if (gameCharacters.get(gameCharacters.size()-1).x< 
		(gameCharacters.get(gameCharacters.size()-1).keepWidth*1336)/2.25) {
			for (int i=0; i<gameCharacters.size(); i++) {
				gameCharacters.get(i).x = (float) (gameCharacters.get(i).x+(int)(3.25*resizeValue));
			}
			for (int i = 0; i<plat.size() ; i++) {
				plat.get(i).x = (int) (plat.get(i).x+(int)(3.25*resizeValue));
			}
		}
		
	}
	
	public void turnToNoCollide (GameCharacter toBeChanged, int currentX, int currentY) {
		for (int i=0; i<gameCharacters.size(); i++) {
			if (gameCharacters.get(i) == toBeChanged) {
				gameCharacters.set(i, noCollideFactory.swapToNoCollide(toBeChanged));
				gameCharacters.get(i).x= currentX;
				// trying to avoid new no-collide from jumping around.
				gameCharacters.get(i).y= currentY-(10*resizeValue);
				if (LOG.isDebugEnabled()){
					LOG.debug("Creating new \"no collide\" character. Coordinates, x: {}. y: {}",
							gameCharacters.get(i).x, gameCharacters.get(i).y);
				}
				gameCharacters.get(i).sH = (int)gameCharacters.get(i).sH* resizeValue;
				gameCharacters.get(i).sW = (int) gameCharacters.get(i).sW*resizeValue;
				animation.set(i, animationFactory.makeAnimation(gameCharacters.get(i)));
			}
		}
		
	}
	

		@Override
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			// 1st objects drawn will be in background so I can create a background by doing this:
			// an animated background could work here as well.
			g2.setColor(skyblue);
			g2.fill(background);
			g2.draw(background);
			for (Rectangle next: plat) {
				if (next.intersects(screenRect) || screenRect.contains(next)) {
					g2.setColor( new Color (130, 87, 27));
					g2.fill(next);
					g2.draw(next);
				}
			}
			for (int i=0; i<animation.size(); i++) {
				if(gameCharacters.get(i).mochi.intersects(screenRect) ||
						screenRect.contains(gameCharacters.get(i).mochi))
					animation.get(i).draw(g2);
			}
		}
		
	@Override
	public Dimension getPreferredSize() {
			return screenSize;
	}
	
}
