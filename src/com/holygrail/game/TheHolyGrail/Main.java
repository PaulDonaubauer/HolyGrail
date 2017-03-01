package com.holygrail.game.TheHolyGrail;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "TheHolyGrail2";
		cfg.useGL30 = true;
		cfg.width = 900;
		cfg.height = 600;
		
		new LwjglApplication(new HolyGrailGame(), cfg);
	}
}
