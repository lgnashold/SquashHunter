package com.runner;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver extends BasicGameState {
	public static final int ID = 1;
	// time elapsed in milliseconds
	private int timeElapsed;
	private Settings settings;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		timeElapsed = 0;
		try {
			settings = new Settings();
		} catch (Exception e) {
			gc.getGraphics().setBackground(Color.white);
			gc.getGraphics().setColor(Color.red);
			gc.getGraphics().drawString("Problem with the Settings File. Unable to load game.", 0, 0);
			System.out.println("ERROR LOADING SETTINGS");
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Score scores = Score.loadScore(GameState.SCORE_FILE);
		g.setColor(new Color(settings.getTextColor()));
		String text = "GAME OVER.";
		g.drawString(text, gc.getWidth() / 2 - gc.getDefaultFont().getWidth(text) / 2, gc.getHeight() / 2 - 16);
		text = "Your Score: " + scores.getCurrentScore() + " High Score: " + scores.getHighScore();
		g.drawString(text, gc.getWidth() / 2 - gc.getDefaultFont().getWidth(text) / 2, gc.getHeight() / 2);
		text = "Press Enter to Play again, or Q to quit";
		g.drawString(text, gc.getWidth() / 2 - gc.getDefaultFont().getWidth(text) / 2, gc.getHeight() / 2 + 16);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		timeElapsed += delta;
		if (timeElapsed > 30000)
			sbg.enterState(0);
		if (gc.getInput().isKeyDown(Input.KEY_ENTER)) {
			sbg.enterState(0);
		}
		if (gc.getInput().isKeyDown(Input.KEY_Q)) {
			gc.exit();
			System.exit(-1);
		}
	}

	@Override
	public int getID() {
		return GameOver.ID;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		timeElapsed = 0;
	}
}
