package com.runner;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SquashHunter extends StateBasedGame {

	// Game state identifiers
	public static final int GAME = 0;
	public static final int GAMEOVER = 1;

	// Application Properties
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int FPS = 240;
	public static final double VERSION = 1.0;

	// Class Constructor
	public SquashHunter(String appName) {
		super(appName);
	}

	// Initialize your game states (calls init method of each gamestate, and
	// set's the state ID)
	public void initStatesList(GameContainer gc) throws SlickException {
		// The first state added will be the one that is loaded first, when the
		// application is launched
		this.addState(new GameState());
		this.addState(new GameOver());

	}

	public static void main(String[] args) {
		try {
			System.out.println("Main");
			AppGameContainer app = new AppGameContainer(new SquashHunter("Squash Hunter"));
			app.setDisplayMode(WIDTH, HEIGHT, true);
			app.setTargetFrameRate(FPS);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
