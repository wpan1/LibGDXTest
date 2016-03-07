package com.birrypan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Player {
	/** Rotation speed, in radians per ms. */
	private static final double ROTATE_SPEED = 0.004 * 7;
	/** Acceleration while the player is driving, in px/ms^2. */
	private static final double ACCELERATION = 0.0005 * 80;

	private TiledMapTileLayer collisionsLayer;

	/** The Sprite of the player. */
	private Sprite img;

	/** The X coordinate of the player (pixels). */
	private double x;
	/** The Y coordinate of the player (pixels). */
	private double y;
	/**
	 * The angle the player is currently facing. Note: This is in neither
	 * degrees nor radians -- the Angle class allows angles to be manipulated
	 * without worrying about units, and the angle value can be extracted in
	 * either degrees or radians.
	 */
	private Angle angle;
	/** The player's current velocity (px/ms). */
	private double velocity;

	/**
	 * Creates a new Player.
	 * 
	 * @param x
	 *            The player's initial X location (pixels).
	 * @param y
	 *            The monster's initial Y location (pixels).
	 * @param angle
	 *            The player's initial angle.
	 */

	public Player(double x, double y, Angle angle, TiledMapTileLayer collisionsLayer) {
		this.collisionsLayer = collisionsLayer;
		Texture spriteTexture = new Texture("karts/donkey.png");
		this.img = new Sprite(spriteTexture);
		this.img.setScale(Gdx.graphics.getWidth()/Game.RENDER_WIDTH, Gdx.graphics.getHeight()/Game.RENDER_HEIGHT);
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.velocity = 0;
	}

	/** The X coordinate of the player (pixels). */
	public Sprite getSprite() {
		return img;
	}

	/** The X coordinate of the player (pixels). */
	public double getX() {
		return x;
	}

	/** The Y coordinate of the player (pixels). */
	public double getY() {
		return y;
	}

	/** The angle the player is facing. */
	public Angle getAngle() {
		return angle;
	}

	/**
	 * The player's current velocity, in the direction the player is facing
	 * (px/ms).
	 */
	public double getVelocity() {
		return velocity;
	}

	/**
	 * Update the player for a frame. Adjusts the player's angle and velocity
	 * based on input, and updates the player's position. Prevents the player
	 * from entering a blocking tile.
	 * 
	 * @param rotate_dir
	 *            The player's direction of rotation (-1 for anti-clockwise, 1
	 *            for clockwise, or 0).
	 * @param move_dir
	 *            The player's movement in the car's axis (-1, 0 or 1).
	 * @param delta
	 *            Time passed since last frame (milliseconds).
	 * @param world
	 *            The world the player is on (to get friction and blocking).
	 */
	public void update(double rotate_dir, double move_dir) {
		// Modify the player's angle
		Angle rotateamount = new Angle(ROTATE_SPEED * rotate_dir);
		angle = angle.add(rotateamount);
		// Determine the friction of the current location
		double friction = Double.parseDouble(
				collisionsLayer.getCell
				((int) ((x / collisionsLayer.getTileWidth())),
				(int) ((y / collisionsLayer.getTileHeight())))
				.getTile().getProperties().get("friction").toString());
		// Modify the player's velocity. First, increase due to acceleration.
		velocity += ACCELERATION * move_dir;
		// Then, reduce due to friction (this has the effect of creating a
		// maximum velocity for a given coefficient of friction and
		// acceleration)
		velocity *= (1 - friction*5);

		// Modify the position, based on velocity
		// Calculate the amount to move in each direction
		double amount = velocity;
		// Compute the next position, but don't move there yet
		double next_x = x + angle.getXComponent(amount);
		double next_y = y + angle.getYComponent(amount);
		// If the intended destination is a blocking tile, do not move there
		// (crash) -- instead, set velocity to 0	
		if (Double.parseDouble(collisionsLayer
				.getCell((int) (next_x / collisionsLayer.getTileWidth()),
						(int) (next_y / collisionsLayer.getTileHeight()))
				.getTile().getProperties().get("friction").toString()) == 1) {
			velocity = 0;
		} else {
			// Actually move to the intended destination
			x = next_x;
			y = next_y;
		}
	}

	/**
	 * Draw the player to the screen at the correct place.
	 * 
	 * @param g
	 *            The current Graphics context.
	 * @param camera
	 *            The camera to draw relative to.
	 */
	public void render(SpriteBatch batch, OrthographicCamera camera) {
		// Calculate the player's on-screen location from the camera
		int screen_x = (int) (Gdx.graphics.getWidth()/2 - this.img.getWidth()/2);
		int screen_y = (int) (Gdx.graphics.getHeight()/2 - this.img.getHeight()/2);

		img.setRotation((float) angle.getDegrees());

		batch.draw(this.getSprite(), screen_x, screen_y, this.getSprite().getOriginX(), this.getSprite().getOriginY(),
				this.getSprite().getWidth(), this.getSprite().getHeight(), this.getSprite().getScaleX(),
				this.getSprite().getScaleY(), this.getSprite().getRotation());
	}
}
