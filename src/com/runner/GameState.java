package com.runner;

import java.util.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.*;

public class GameState extends BasicGameState {
	public static final int ID = 0;
	private Player player;
	private List<Obstacle> obstacles;
	private List<Squash> squash;
	private Score score;
	private int timeUntilNextObstacle;
	private int timeUntilNextSquash;
	private Settings settings;

	// Declaring Constant values
	public final static String SCORE_FILE = "scores.dat";
	private final int MIN_TIME_BETWEEN_OBSTACLES = 1000;
	private final int MAX_TIME_BETWEEN_OBSTACLES = 2000;
	private final int MIN_TIME_BETWEEN_SQUASH = 2000;
	private final int MAX_TIME_BETWEEN_SQUASH = 4000;

	public GameState() {
		super();
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		setupNewGame(gc, sbg);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (gc.getInput().isKeyDown(Input.KEY_SPACE)) {
			player.jump();
		}
		player.update(delta);
		for (int j = obstacles.size() - 1; j >= 0; j--) {
			obstacles.get(j).update(delta);
			if (obstacles.get(j).isTouching(player.X_POSITION, player.getY(), player.getWidth(), player.getHeight()))
				endGame(gc, sbg);
			if (obstacles.get(j).isOffScreen()) {
				obstacles.remove(j);
			}
		}
		for (int j = squash.size() - 1; j >= 0; j--) {
			squash.get(j).update(delta);
			if (squash.get(j).isTouching(player.X_POSITION, player.getY(), player.getWidth(), player.getHeight())) {
				score.increment();
				score.updateHighScore();
				squash.remove(j);
			} else if (squash.get(j).isOffScreen()) {
				squash.remove(j);
			}
		}
		timeUntilNextObstacle -= delta;
		if (timeUntilNextObstacle <= 0) {
			Obstacle toAdd = new Obstacle(gc.getGraphics(), gc, settings);
			Squash lastSquash = null;
			if (squash.size() != 0)
				lastSquash = squash.get(squash.size() - 1);
			if (lastSquash == null
					|| !toAdd.isTouching(lastSquash.getX(), lastSquash.getY(), lastSquash.WIDTH, lastSquash.HEIGHT)) {
				obstacles.add(new Obstacle(gc.getGraphics(), gc, settings));
				timeUntilNextObstacle = (int) (Math.random() * MIN_TIME_BETWEEN_OBSTACLES + MAX_TIME_BETWEEN_OBSTACLES
						- MIN_TIME_BETWEEN_OBSTACLES);
			}
		}
		timeUntilNextSquash -= delta;
		if (timeUntilNextSquash <= 0) {
			Squash toAdd = new Squash(gc.getGraphics(), gc, settings);
			if (!obstacles.get(obstacles.size() - 1).isTouching(toAdd.getX(), toAdd.getY(), toAdd.WIDTH, toAdd.HEIGHT))
				squash.add(toAdd);
			timeUntilNextSquash = (int) (Math.random() * MIN_TIME_BETWEEN_SQUASH + MAX_TIME_BETWEEN_SQUASH
					- MIN_TIME_BETWEEN_SQUASH);
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(new Color(settings.getGroundColor()));
		Rectangle rect = new Rectangle(0f, .75f * gc.getHeight(), gc.getWidth(), .25f * gc.getHeight());
		g.fill(rect);
		player.render();
		for (Obstacle o : obstacles) {
			o.render();
		}
		for (Squash s : squash) {
			s.render(gc, g);
		}
		score.render(gc, g, settings);
	}

	public void endGame(GameContainer gc, StateBasedGame sbg) {
		score.saveScore(SCORE_FILE);
		sbg.enterState(1);
	}

	public void setupNewGame(GameContainer gc, StateBasedGame sbg) {
		try {
			settings = new Settings();
		} catch (Exception e) {
			gc.getGraphics().setBackground(Color.white);
			gc.getGraphics().setColor(Color.red);
			gc.getGraphics().drawString("Problem with the Settings File. Unable to load game.", 0, 0);
			System.out.println("ERROR LOADING SETTINGS");
		}
		score = Score.loadScore(SCORE_FILE);
		gc.getGraphics().clear();
		gc.getGraphics().setBackground(new Color(settings.getBackgroundColor()));
		player = new Player(gc.getGraphics(), gc, settings);
		obstacles = new ArrayList<Obstacle>();
		squash = new ArrayList<Squash>();
		score.reset();
		timeUntilNextObstacle = 0;
		timeUntilNextSquash = 1000;
	}

	@Override
	public int getID() {
		return GameState.ID;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		setupNewGame(gc, sbg);
	}
}