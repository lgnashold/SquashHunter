package com.runner;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Settings {
	private Image obstacleImage;
	private Image playerImage;
	private Image squashImage;
	private int backgroundColor;
	private int groundColor;
	private int textColor;

	public final String FILEPATH = "./settings.txt";

	public Settings() throws IOException, SlickException {
		File file = new File(FILEPATH);
		Scanner in = new Scanner(file);
		//Reads in all the data from the settings.txt file and assigns them to local variables.
		while (in.hasNextLine()) {
			String cur = in.nextLine();
			if (cur.startsWith("Obstacle Image:")) {
				obstacleImage = new Image(cur.substring("Obstacle Image:".length()).trim());
			} else if (cur.contains("Player Image:")) {
				playerImage = new Image(cur.substring("Player Image:".length()).trim());
				System.out.println(playerImage);
			} else if (cur.contains("Squash Image:")) {
				squashImage = new Image(cur.substring("Squash Image:".length()).trim());
			} else if (cur.contains("Background Color:")) {
				backgroundColor = Integer.parseInt(cur.substring("Background Color:".length()).trim(), 16);
			} else if (cur.contains("Ground Color:")) {
				groundColor = Integer.parseInt(cur.substring("Ground Color:".length()).trim(), 16);
			} else if (cur.contains("Text Color:")) {
				textColor = Integer.parseInt(cur.substring("Text Color:".length()).trim(), 16);
			}
		}
		in.close();
		//Double checks that all variables have been instantiated
		if (groundColor == 0 || textColor == 0 || backgroundColor == 0 || squashImage == null || playerImage == null
				|| obstacleImage == null || squashImage == null)
			throw new IOException();
	}

	public Image getPlayerImage() {
		return playerImage;
	}

	public Image getSquashImage() {
		return squashImage;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public int getGroundColor() {
		return groundColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public Image getObstacleImage() {
		return obstacleImage;
	}
}
