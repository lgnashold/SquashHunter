package com.runner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Squash {

	private float x;
	private int windowHeight;
	private int windowWidth;
	public final int WIDTH;
	public final int HEIGHT;
	private Image image;

	private final float SCALE = 1;
	public final float SPEED = .2f;

	public Squash(Graphics graphics, GameContainer gc, Settings settings) {
		image = settings.getSquashImage();
		WIDTH = (int) (image.getWidth() * SCALE);
		HEIGHT = (int) (image.getHeight() * SCALE);
		this.windowHeight = gc.getHeight();
		this.windowWidth = gc.getWidth();
		x = windowWidth - WIDTH;
	}

	public void render(GameContainer gc, Graphics g) {
		image.draw(x, windowHeight * .75f - HEIGHT, SCALE);
	}

	public void update(int delta) {
		x -= SPEED * delta;
	}

	public boolean isOffScreen() {
		return x < -WIDTH;
	}

	public boolean isTouching(float otherX, float otherY, float otherWidth, float otherHeight) {
		return otherX + otherWidth >= x && otherX <= x + WIDTH && otherY <= windowHeight * .75f
				&& otherY + otherHeight >= windowHeight * .75f - HEIGHT;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return windowHeight * .75f - HEIGHT;
	}
}
