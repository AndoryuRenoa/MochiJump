package com.mochijump.characters;

import com.mochijump.actions.JumpInterface;
import com.mochijump.collision.CollisionInterface;
import com.mochijump.collision.GoalCollision;
import com.mochijump.actions.StandardJump;
import com.mochijump.framesandpanels.DogLogic;

public class GoalNPC extends NonPlayerCharacter {
	
	private JumpInterface jump = new StandardJump();
	private CollisionInterface collide = new GoalCollision();
	
	public GoalNPC (DogLogic dl) {
		super(dl);
	}
	
	
	public void mJumpHandler () {	
		jump.jump(this);
	}
	

	public void boundaryRules () {
		collide.collide(this);
	}
	// implement this:
	public void aIInputs() {
		if (jumpChu == false && this.sH <18*dogLogic.resizeValue) {
			jump();
			
		}
	}

}
