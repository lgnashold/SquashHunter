package com.runner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Score implements Serializable {

	private static final long serialVersionUID = 1L;
	private int currentScore;
	private int highScore;
	
	public Score() {
		currentScore = 0;
		highScore = 0;
		System.out.println("No scores could be loaded. Scores reset to 0.");
	}
	
	public int getCurrentScore(){
		return currentScore;
	}
	
	public int getHighScore() {
		return highScore;
	}
	
	/**
	 *Checks to see if the high score is greater than the current score.
	 *If so, updates the high score to match currentscore
	 * @return if a new high score was created.
	 */
	public boolean updateHighScore(){
		if( currentScore > highScore) {
			highScore = currentScore;
			return true;
		}
		return false;
	}
	
	public void increment(){
		currentScore++;
	}
	
	public void reset(){
		currentScore=0;
	}
	
	public void saveScore(String filename) {
		//FileOutputStream fout;
		try{
			FileOutputStream fout = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(this);
			out.flush();
			out.close();
		}catch(IOException e){
			System.out.println("ERROR: Could not save high score");
		}
	}
	
	public static Score loadScore(String filename) {
		try{
			FileInputStream fin = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fin);
			Score score= (Score)in.readObject();
			in.close();
			return score;
		}catch(Exception e){
			System.out.println("Could not load highscore. Highscore reset to 0.");
		}
		return new Score();
	}
	
	public void render(GameContainer gc, Graphics g, Settings settings){		
		g.setColor(new Color(settings.getTextColor()));
		g.drawString("Score: " + currentScore, 5, 5);
		g.drawString("High Score: " + highScore, 5, 20);
	}
}
