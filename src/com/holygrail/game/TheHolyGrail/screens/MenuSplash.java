package com.holygrail.game.TheHolyGrail.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuSplash implements Screen {
	
	private Game HolyGrailGame;
	private SpriteBatch batch;
	private Sprite splash;
	
	public MenuSplash(Game g)
	{
		HolyGrailGame = g;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(splash, 0, 50);
		batch.end();
		
		if(Gdx.input.isKeyPressed(Keys.ENTER))
		{
			HolyGrailGame.setScreen(new Level1());
		}		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		Texture splashTexture = new Texture(Gdx.files.internal("img/holygrailTitle1.png"));
		splash = new Sprite(splashTexture);
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		splash.getTexture().dispose();
	}
	

}
