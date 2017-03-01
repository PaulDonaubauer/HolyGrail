package com.holygrail.game.TheHolyGrail.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.holygrail.game.TheHolyGrail.Player;

public abstract class Level implements Screen{

	private Sprite bgsprite;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	Player arthur;
	private TextureAtlas playerAtlas;
	protected Player player;
	SpriteBatch spriteBatch;

	private int[] foreground = new int[] {0}, background = new int[] {1}, background2 = new int[] {2}, background3 = new int[] {3};
	
	abstract String get_level();
	abstract public void loadNextLevel();

	private ShapeRenderer sr;
	
	int tileCount = 3;

	private Batch bgbatch = new SpriteBatch();
	
	Level(){
		super();

	}
	
	@Override
	public void render(float delta) {
		
		//clears the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//draw the background
		bgbatch.setProjectionMatrix(camera.projection);
		bgbatch.begin();
		bgsprite.draw(bgbatch);
		bgbatch.end();
		
		
	

		//causes camera to follow the player
		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 50, 0);
		camera.update();

		renderer.setView(camera);
		
		renderer.render(background3);
		renderer.render(background2);
		renderer.render(background);
		renderer.render(foreground);
		   
		renderer.getSpriteBatch().begin();
		player.draw(renderer.getSpriteBatch());
    	renderer.getSpriteBatch().end();
    	
		
    	fallDeath();

	}

	abstract void fallDeath();
	
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width * 1.5f;
		camera.viewportHeight = height * 1.5f;
	}

	@Override
	public void show() {
		
		//loads the TiledMap file
		map = new TmxMapLoader().load("img/" + get_level() + ".tmx");
		
		//renders the map as Orthogonal
		renderer = new OrthogonalTiledMapRenderer(map);
		sr = new ShapeRenderer();

		//sets the camera in a 2D sidescrolling fashion
		camera = new OrthographicCamera();

		//builds the player (textures, animation modes/speeds)
		playerAtlas = new TextureAtlas("img/Arthur.txt");
		Animation idleleft, idleright, left, right, jumpright, jumpleft, attackright, attackleft;
		idleleft = new Animation(1 / 2f, playerAtlas.findRegions("idleleft"));
		idleright = new Animation(1 / 2f, playerAtlas.findRegions("idleright"));
		left = new Animation(1 / 6f, playerAtlas.findRegions("walkleft"));
		right = new Animation(1 / 6f, playerAtlas.findRegions("walkright"));
		jumpright = new Animation(1 / 6f, playerAtlas.findRegions("jumpright"));
		jumpleft = new Animation(1 / 6f, playerAtlas.findRegions("jumpleft"));
		attackright = new Animation(1 / 10f, playerAtlas.findRegions("attackright"));
		attackleft = new Animation(1 / 10f, playerAtlas.findRegions("attackleft"));
		idleleft.setPlayMode(Animation.PlayMode.LOOP);
		idleright.setPlayMode(Animation.PlayMode.LOOP);
		left.setPlayMode(Animation.PlayMode.LOOP);
		right.setPlayMode(Animation.PlayMode.LOOP);
		jumpright.setPlayMode(Animation.PlayMode.NORMAL);
		jumpleft.setPlayMode(Animation.PlayMode.NORMAL);
		attackright.setPlayMode(Animation.PlayMode.LOOP);
		attackleft.setPlayMode(Animation.PlayMode.LOOP);

		//instantiates the player
		player = new Player(idleleft, idleright, left, right, jumpright, jumpleft, attackright, attackleft, (TiledMapTileLayer) map.getLayers().get(1));
		
		//sets the player's starting position
		player.setPosition( player.getCollisionLayer().getTileWidth() * 12, (player.getCollisionLayer().getHeight() / 2) * player.getCollisionLayer().getTileHeight());
		
		//allows control over the player
		Gdx.input.setInputProcessor(player);

		//set up the background for rendering
		bgbatch = new SpriteBatch();
		Texture bgtexture = new Texture(Gdx.files.internal("img/background.png"));
	    bgtexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    TextureRegion region = new TextureRegion(bgtexture, 0, 0, 4924, 1278);
        bgsprite = new Sprite(region);
        bgsprite.setPosition(-2000, -650);
	    
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		sr.dispose();
		playerAtlas.dispose();
	}

	
}
