package com.mochijump.actions;

import com.mochijump.characters.GameCharacter;

public class GooseJump implements JumpInterface {
	
	public void jump(GameCharacter implementer) {
		
		if (implementer.jumpChu) {
			implementer.speedY = 0;
			if (implementer.mRunR == true || implementer.mRestR == true) {
				implementer.setActionToFalse();
				implementer.mJumpR = true;	
			}
			if (implementer.mRunL == true || implementer.mRestL == true) {
				implementer.setActionToFalse();
				implementer.mJumpL = true;
			}
		}
		
	}
	

}
