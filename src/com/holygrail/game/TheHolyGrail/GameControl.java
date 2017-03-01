package com.holygrail.game.TheHolyGrail;

import com.holygrail.game.TheHolyGrail.screens.Level1;
import com.holygrail.game.TheHolyGrail.screens.Level2;



public class GameControl {
	
	HolyGrailGame holygrail;
	
	public enum _Level {
		level1, level2
	}
		
	public GameControl(HolyGrailGame holyGrailGame) {
		holygrail = holyGrailGame;
	}

	public void load_level(_Level l)
	{
		switch (l)
		{ 
		case level1:
			holygrail.setScreen(new Level1());
			break;
		case level2:
			holygrail.setScreen(new Level2());
			break;
		default:
			break;
		}
	}
	

}
