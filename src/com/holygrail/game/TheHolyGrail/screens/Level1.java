package com.holygrail.game.TheHolyGrail.screens;

import com.badlogic.gdx.Gdx;
import com.holygrail.game.TheHolyGrail.GameControl._Level;
import com.holygrail.game.TheHolyGrail.HolyGrailGame;


public class Level1 extends Level{
	
	public Level1(){
		super();
		Gdx.gl.glClearColor(0, 0, 0, 1);
	}

	@Override
	public void loadNextLevel() {
		HolyGrailGame.control.load_level(_Level.level2);
	}

	@Override
	String get_level() {
		return "Level1";
	}

	@Override
	void fallDeath() {
		
		
		// SHOULD BE CHANGED BACK TO (_Level.level1) ;  IS AT level2 FOR TESTING PURPOSES ONLY
		if(player.getY() < 0){
			HolyGrailGame.control.load_level(_Level.level2);
		}
	}
		
		
}
