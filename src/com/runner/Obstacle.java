package com.runner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Obstacle {

	private float x;
	private int windowHeight;
	private int width;
	private int height;
	private Image image;

	public final float SPEED = .2f;

	public Obstacle(Graphics graphics, int windowWidth, int windowHeight, int width, int height, Settings settings) {
		this.windowHeight = windowHeight;
		x = windowWidth - width;
		this.width = width;
		this.height = height;
		image = settings.getObstacleImage();
	}

	public Obstacle(Graphics graphics, GameContainer gc, Settings settings) {
		this(graphics, gc.getWidth(), gc.getHeight(), (int) (Math.random() * 30 + 40), (int) (Math.random() * 70 + 40),
				settings);
	}

	public void render() {
		image.draw(x, windowHeight * .75f - height, width, height);
	}

	public void update(int delta) {
		x -= SPEED * delta;
	}

	public boolean isOffScreen() {
		return x < -width;
	}

	public boolean isTouching(float otherX, float otherY, float otherWidth, float otherHeight) {
		return otherX + otherWidth >= x + 8 && otherX <= x + width - 8 && otherY <= windowHeight * .75f
				&& otherY + otherHeight >= windowHeight * .75f - height + 10;
	}
}
