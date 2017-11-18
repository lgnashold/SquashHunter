package com.runner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Player {
	private float y;
	private float yVelocity;
	private int windowHeight;
	private boolean isJumping;
	private Image image;
	private int width;
	private int height;

	public final float X_POSITION = 20;
	public final float ACCELERATION = .0014f;
	public final float INITIAL_VELOCITY = -0.8f;
	public final float SCALE;

	public Player(Graphics graphics, GameContainer gc, Settings settings) {
		image = settings.getPlayerImage();
		SCALE = 80f / image.getWidth();
		width = (int) (image.getWidth() * SCALE);
		height = (int) (image.getHeight() * SCALE);
		this.windowHeight = gc.getHeight();
		gc.getWidth();
		y = windowHeight * .75f - height;
	}

	public void render() {
		image.draw(X_POSITION, y, SCALE);
	}

	public void update(int delta) {
		// Updates position
		if (isJumping) {
			yVelocity += ACCELERATION * delta;
			y += yVelocity * delta;
			if (y > windowHeight * .75f - height) {
				isJumping = false;
				y = windowHeight * .75f - height;
			}
		}
	}

	public void jump() {
		if (!isJumping) {
			yVelocity = INITIAL_VELOCITY;
			isJumping = true;
		}
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
