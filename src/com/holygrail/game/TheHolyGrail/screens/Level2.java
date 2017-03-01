package com.holygrail.game.TheHolyGrail.screens;

import com.badlogic.gdx.Gdx;
import com.holygrail.game.TheHolyGrail.HolyGrailGame;
import com.holygrail.game.TheHolyGrail.GameControl._Level;

public class Level2 extends Level {
	
	public Level2(){
		super();
		Gdx.gl.glClearColor(0, 0, 0, 1);
	}

	@Override
	public void loadNextLevel() {
		HolyGrailGame.control.load_level(_Level.level2);
	}

	@Override
	String get_level() {
		return "Level2" ;
	}

	@Override
	void fallDeath() {
		if(player.getY() < 0){
			HolyGrailGame.control.load_level(_Level.level2);
		}
		
	}

}
