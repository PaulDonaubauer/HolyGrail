package com.holygrail.game.TheHolyGrail;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {

	// the movement velocity 
	public Vector2 velocity = new Vector2();

	// some physics constants
	private float speed = 95 * 3, gravity = 110 * 1.8f, animationTime = 0, increment;
	
	private boolean canJump;
	private boolean facingRight;
	private boolean facingLeft;

	private Animation idleleft, idleright, left, right, jumpright, jumpleft, attackright, attackleft;
	private TiledMapTileLayer collisionLayer;

	private String blockedKey = "blocked";

	// creates the player's animations/dimensions
	public Player(Animation idleleft, Animation idleright, Animation left, Animation right, Animation jumpright, Animation jumpleft, Animation attackright, Animation attackleft, TiledMapTileLayer collisionLayer) {
		this.idleleft = idleleft;
		this.idleright = idleright;
		this.left = left;
		this.right = right;
		this.jumpright = jumpright;
		this.jumpleft = jumpleft;
		this.attackright = attackright;
		this.attackleft = attackleft;
		this.collisionLayer = collisionLayer;
		setSize(collisionLayer.getWidth() / 14, collisionLayer.getHeight() );
	}

	@Override
	public void draw(Batch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}

	public void update(float delta) {
		// applies gravity
		velocity.y -= gravity * delta;

		// clamps velocity
		if(velocity.y > speed)
			velocity.y = speed;
		else if(velocity.y < -speed)
			velocity.y = -speed;

		// saves old position
		float oldX = getX(), oldY = getY();
				
		boolean collisionX = false, collisionY = false;

		// move left/right
		setX(getX() + velocity.x * delta);

		// calculate the increment for step in collidesLeft() and collidesRight()
		increment = collisionLayer.getTileWidth();
		increment = getWidth() < increment ? getWidth() / 2 : increment / 2;

		if(velocity.x < 0) // colliding from the left
			collisionX = collidesLeft();
		else if(velocity.x > 0) // colliding from the right
			collisionX = collidesRight();

		// reaction for any x-based collision
		if(collisionX) {
			setX(oldX);
			velocity.x = 0;
		}

		// move up/down
		setY(getY() + velocity.y * delta * 5f);

		// calculate the increment for step in collidesBottom() and collidesTop()
		increment = collisionLayer.getTileHeight();
		increment = getHeight() < increment ? getHeight() / 2 : increment / 2;

		if(velocity.y < 0) // colliding from the bottom
			canJump = collisionY = collidesBottom();
		else if(velocity.y > 0) // colliding from the top
			collisionY = collidesTop();

		// reaction for any y-based collision
		if(collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
		
		
		// update animations
		animationTime += delta;
		
		setRegion( velocity.x < 0 && velocity.y == 0 ? left.getKeyFrame(animationTime) : velocity.x > 0 && velocity.y == 0 ? right.getKeyFrame(animationTime) : 
			facingLeft && velocity.y != 0 ? jumpleft.getKeyFrame(animationTime) : facingRight && velocity.y != 0 ? jumpright.getKeyFrame(animationTime) : 
			facingRight && canAttack() ? attackright.getKeyFrame(animationTime) : facingLeft ? idleleft.getKeyFrame(animationTime) : 
			facingLeft && canAttack() ? attackleft.getKeyFrame(animationTime) :  idleright.getKeyFrame(animationTime) );
	
	}

	
	// allow for attack animation (F key)
	public boolean canAttack(){
		if(Gdx.input.isKeyPressed(Keys.F)){
			return true;
		}
		return false;
	}
	

	// check if cell contains a tile with the "blocked" property
	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
	}

	private boolean isCellExit(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("exit");
	}

	public boolean collidesRight() {
		for(float step = 0; step <= getHeight(); step += increment)
			if(isCellBlocked(getX() + getWidth(), getY() + step))
				return true;
		return false;
	}

	public boolean collidesLeft() {
		for(float step = 0; step <= getHeight(); step += increment)
			if(isCellBlocked(getX(), getY() + step))
				return true;
		return false;
	}

	public boolean collidesTop() {
		for(float step = 0; step <= getWidth(); step += increment)
			if(isCellBlocked(getX() + step, getY() + getHeight()))
				return true;
		return false;

	}

	public boolean collidesBottom() {
		for(float step = 0; step <= getWidth(); step += increment)
			if(isCellBlocked(getX() + step, getY()))
				return true;
		return false;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.SPACE:
			if(canJump) {
				velocity.y = speed / 1.8f;
				canJump = false;
			}
			case Keys.W:
				if(canJump) {
					velocity.y = speed / 1.8f;
					canJump = false;
			}
			break;
		case Keys.A:
			velocity.x = -speed;
			animationTime = 0;
			facingRight = false;
			facingLeft = true;
		case Keys.LEFT:
			velocity.x = -speed;
			animationTime = 0;
			facingRight = false;
			facingLeft = true;
			break;
		case Keys.RIGHT:
			velocity.x = speed;
			animationTime = 0;
			facingRight = true;
			facingLeft = false;
		case Keys.D:
			velocity.x = speed;
			animationTime = 0;
			facingRight = true;
			facingLeft = false;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.A:
		case Keys.D:
		case Keys.LEFT:
		case Keys.RIGHT:
			velocity.x = 0;
			animationTime = 0;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
