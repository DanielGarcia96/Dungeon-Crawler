package HighScore;

import java.io.Serializable;

public class User implements Serializable{
	private String Name;
	private int Score, Rank;
	
	public User(String Name, int Score){
		setRank(1);
		setName(Name);
		setScore(Score);
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getScore() {
		return Score;
	}
	public void setScore(int score) {
		Score = score;
	}
	public int getRank() {
		return Rank;
	}
	public void setRank(int rank) {
		Rank = rank;
	}
	
}
