package com.holygrail.game.TheHolyGrail;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.holygrail.game.TheHolyGrail.screens.Level;
import com.holygrail.game.TheHolyGrail.screens.Level1;
import com.holygrail.game.TheHolyGrail.screens.MenuSplash;


public class HolyGrailGame extends Game {
	
	MenuSplash mainMenuScreen;
	Level anotherScreen;
	public static GameControl control;
	com.badlogic.gdx.audio.Music music;

	public HolyGrailGame()
	{
		super();
		control = new GameControl(this);
	}
	
	@Override
	public void create() {	
		mainMenuScreen = new MenuSplash(this);
		anotherScreen = new Level1();
		setScreen(mainMenuScreen);
		
		// TestMusic.mp3 is a mp3 in the assets folder. Easily changeable. 
				music = Gdx.audio.newMusic(Gdx.files.internal("HolyGrail.mp3"));
				//sets music to loop
		        music.setLooping(true);
		        music.play();
	}

	@Override
	public void dispose() {
		super.dispose();
		music.dispose();
	}

	@Override
	public void render() {		
		super.render();
		
		//resets the game if R is pressed
		if(Gdx.input.isKeyPressed(Keys.R))
			try {
				setScreen(getScreen().getClass().newInstance());
			} catch(InstantiationException e) {
				e.printStackTrace();
			} catch(IllegalAccessException e) {
				e.printStackTrace();
			}
		
	
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
