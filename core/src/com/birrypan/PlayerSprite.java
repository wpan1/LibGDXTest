package com.birrypan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PlayerSprite extends Sprite {

	// Movement Velocity
	private double velocity = 0;

	private Angle angle;

	/** Rotation speed, in radians per ms. */
	private static final double ROTATE_SPEED = 0.004;
	/** Acceleration while the player is driving, in px/ms^2. */
	private static final double ACCELERATION = 0.0005;

	public PlayerSprite(Sprite sprite, double x, double y) {
		super(sprite);
		setX((float) x);
		setY((float) y);
		angle = new Angle(0);
	}
	
	public void draw(Batch spriteBatch, double rotate_dir, double move_dir) {
		update(Gdx.graphics.getDeltaTime(), rotate_dir, move_dir);
		super.draw(spriteBatch);
	}

	public void update(float delta, double rotate_dir, double move_dir) {
		// Modify the player's angle
		Angle rotateamount = new Angle(ROTATE_SPEED * rotate_dir);
		angle = angle.add(rotateamount);
		this.setRotation((float) angle.getDegrees());
		// Determine the friction of the current location
		// double friction = world.frictionAt((int) x, (int) y);
		// Modify the player's velocity. First, increase due to acceleration.
		velocity += ACCELERATION * move_dir;
		// Then, reduce due to friction (this has the effect of creating a
		// maximum velocity for a given coefficient of friction and
		// acceleration)
		// velocity *= (1 - friction);

		// Modify the position, based on velocity
		// Calculate the amount to move in each direction
		double amount = velocity;
		// Compute the next position, but don't move there yet
		double next_x = getX() + angle.getXComponent(amount);
		double next_y = getY() + angle.getYComponent(amount);
		// If the intended destination is a blocking tile, do not move there
		// (crash) -- instead, set velocity to 0
		// world.blockingAt((int) next_x, (int) next_y
		if (false) {
			velocity = 0;
		} else {
			// Actually move to the intended destination
			setX((float) next_x);
			setY((float) next_y);
		}
	}
}
