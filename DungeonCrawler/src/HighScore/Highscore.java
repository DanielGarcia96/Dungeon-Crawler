package HighScore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;

public class Highscore{
	private ArrayList <User>UserList;
	private int HighscoreSize;
	
	public Highscore(){
		UserList = new ArrayList <User>();
		HighscoreSize = 10;
	}
	@SuppressWarnings({ "resource", "unchecked" })
	public void ReadHighScoreFile(String Filepath){
		try {
			ObjectInputStream ObjectStream = new ObjectInputStream(new FileInputStream(Filepath));
			try {
				UserList = (ArrayList<User>)ObjectStream.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SuppressWarnings("resource")
	public void WriteHighScoreFile(String Filepath){
		try {
			ObjectOutputStream ObjectStream = new ObjectOutputStream(new FileOutputStream(Filepath));
			ObjectStream.writeObject(UserList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		for(int i = 0; i < UserList.size(); i++){
			System.out.println(UserList.get(i).getName() + " " + UserList.get(i).getScore());
		}
		*/
	}
	public int AddUser(User NewUser){
		if(UserList.size() < HighscoreSize){
			UserList.add(NewUser);
			SortUserList();
			for(int i = 0; i < UserList.size(); i++){
				UserList.get(i).setRank(i+1);
			}
			return NewUser.getRank();
		}
		
		boolean NewUserToAdd = false;
		for( User U1 : UserList){
			if(NewUser.getScore() > U1.getScore()){
				NewUserToAdd = true;
				break;
			}
		}
		if(NewUserToAdd == true){
			UserList.add(NewUser);
			SortUserList();
			UserList.remove(UserList.size() - 1);
			for(int i = 0; i < UserList.size(); i++){
				UserList.get(i).setRank(i+1);
			}
			return NewUser.getRank();
		}
		return -1;
	}
	
	public User getUserRank(int index){
		index--;
		if(index < 0 || index >= UserList.size()){
			return null;
		}
		return UserList.get(index);
	}
	public boolean CanAddUser(int Score){
		if(UserList.size() < HighscoreSize){
			return true;
		}
		for( User U1 : UserList){
			if(Score > U1.getScore()){
				return true;
			}
		}
		return false;
	}
	public ArrayList<User> getUserList(){
		return UserList;
	}
	private void SortUserList(){
		UserList.sort(new Comparator<User>(){
			@Override
			public int compare(User User1, User User2) {
				// TODO Auto-generated method stub
				if(User1.getScore() > User2.getScore()){
					return -1;
				}
				if(User1.getScore() < User2.getScore()){
					return 1;
				}
				return 0;
			}
		});
	}
}
