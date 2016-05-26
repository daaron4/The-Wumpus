package Users;

import java.io.Serializable;

public class User implements Serializable {
	private int gamesLost;
	private int gamesWon;
	private int wumpiiSlain;
	private int timesRunIntoWumpus;
	private int timesShootSelf;
	private int deathByPits;
	private int stepsTaken;
	private String userName;
	private String userPassword;
	
	public User(String name, String pass) {
		setGamesLost(0);
		setGamesWon(0);
		setWumpiiSlain(0);
		setTimesRunIntoWumpus(0);
		setTimesShootSelf(0);
		setDeathByPits(0);
		setStepsTaken(0);
		setUserName(name);
		setUserPassword(pass);
	}

	public int getGamesLost() {
		return gamesLost;
	}

	public void setGamesLost(int gamesLost) {
		this.gamesLost = gamesLost;
	}

	public int getGamesWon() {
		return gamesWon;
	}

	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}

	public int getWumpiiSlain() {
		return wumpiiSlain;
	}

	public void setWumpiiSlain(int wumpiiSlain) {
		this.wumpiiSlain = wumpiiSlain;
	}

	public int getTimesRunIntoWumpus() {
		return timesRunIntoWumpus;
	}

	public void setTimesRunIntoWumpus(int timesRunIntoWumpus) {
		this.timesRunIntoWumpus = timesRunIntoWumpus;
	}

	public int getTimesShootSelf() {
		return timesShootSelf;
	}

	public void setTimesShootSelf(int timesShootSelf) {
		this.timesShootSelf = timesShootSelf;
	}

	public int getDeathByPits() {
		return deathByPits;
	}

	public void setDeathByPits(int deathByPits) {
		this.deathByPits = deathByPits;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public boolean matches(char[] entered){ 
		return new String(entered).equals(userPassword); 
	}

	public int getStepsTaken() {
		return stepsTaken;
	}

	public void setStepsTaken(int stepsTaken) {
		this.stepsTaken = stepsTaken;
	}
	
	
}
