package Classic;

/*
 * First and Last name: Kal Cramer and David Aaron
 * Assignment name: Hunt the Wumpus: Iteration 2
 * Date due:9/19/14
 */
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Scanner;

import javax.swing.JOptionPane;

import RunnerAndCell.Cell;
import RunnerAndCell.Login;
import Users.User;

public class ClassicMap extends Observable {

	private Cell[][] board;
	private Point Hunter;
	private boolean playing;
	private String shootResponse;
	public String gameOverReason;
	private User currentUser;
	
	private boolean hasShoot;

	public ClassicMap(User currentUser) {
		board = new Cell[10][10];
		Hunter = new Point();
		playing = true;
		this.currentUser = currentUser;
		setGame();
	}
	public void playNextGame() {
		playing = true;
		setGame();
		shootResponse = "";
		gameOverReason = shootResponse;
		updateViewers();
	}

	/*
	 * This method sets up the board and visableBoard with random values
	 * following the criteria. It places the pits and Wumpus and uses their set
	 * methods to place the slim/blood/goop.
	 */

	public void setGame() {

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = new Cell(Cell.Type.Nothing);
			}
		}

		int WumpusRow = (int) (Math.random() * 10);
		int WumpusCol = (int) (Math.random() * 10);
		// Makes sure it's empty.
		while (board[WumpusRow][WumpusCol].getType() != Cell.Type.Nothing) {
			WumpusRow = (int) (Math.random() * 10);
			WumpusCol = (int) (Math.random() * 10);
		}
		board[WumpusRow][WumpusCol].setType(Cell.Type.Wumpus);
		setBlood(WumpusRow, WumpusCol);

		// Gets random number of pits.
		int numOfPits = (int) ((Math.random() * 3) + 3);

		for (int i = 0; i < numOfPits; i++) {
			int PitRow = (int) (Math.random() * 10);
			int PitCol = (int) (Math.random() * 10);
			// Makes sure it's empty.
			while (board[PitRow][PitCol].getType() != Cell.Type.Nothing) {
				if (board[PitRow][PitCol].getType() == Cell.Type.Slime
						|| board[PitRow][PitCol].getType() == Cell.Type.Blood)
					break; // It can be a B or S and still place pit.
				else {
					PitRow = (int) (Math.random() * 10);
					PitCol = (int) (Math.random() * 10);
				}

			}
			board[PitRow][PitCol].setType(Cell.Type.Pit);
			setSlime(PitRow, PitCol);
		}
		int HunterRow = (int) (Math.random() * 10);
		int HunterCol = (int) (Math.random() * 10);
		// Place hunter in empty space.
		while (board[HunterRow][HunterCol].getType() != Cell.Type.Nothing) {
			HunterRow = (int) (Math.random() * 10);
			HunterCol = (int) (Math.random() * 10);
		}
		Hunter.setLocation(HunterRow, HunterCol);
		board[HunterRow][HunterCol].changeVisable();

	}

	public Cell[][] getBoard() {
		return board;

	}

	/*
	 * This method if used for JUnit testing. It makes a game where the chars
	 * are given to the method which changes it in the borad variable so we can
	 * play off of the board and have all other methods work.
	 */

	public void updateViewers() {
		this.setChanged();
		notifyObservers();

	}

	public void setBlood(int WumpusRow, int WumpusCol) {

		// For blood rights
		if (board[(WumpusRow)][(WumpusCol + 2) % 10].getType() == Cell.Type.Nothing)
			board[(WumpusRow)][(WumpusCol + 2) % 10].setType(Cell.Type.Blood);

		if (board[(WumpusRow)][(WumpusCol + 1) % 10].getType() == Cell.Type.Nothing)
			board[(WumpusRow)][(WumpusCol + 1) % 10].setType(Cell.Type.Blood);

		// For blood downs
		if (board[(WumpusRow + 2) % 10][WumpusCol].getType() == Cell.Type.Nothing)
			board[(WumpusRow + 2) % 10][WumpusCol].setType(Cell.Type.Blood);

		if (board[(WumpusRow + 1) % 10][WumpusCol].getType() == Cell.Type.Nothing)
			board[(WumpusRow + 1) % 10][WumpusCol].setType(Cell.Type.Blood);

		// For blood ups
		if (WumpusRow - 2 == -2) {

			if (board[(9)][WumpusCol].getType() == Cell.Type.Nothing)
				board[(9)][WumpusCol].setType(Cell.Type.Blood);
			if (board[(8)][WumpusCol].getType() == Cell.Type.Nothing)
				board[(8)][WumpusCol].setType(Cell.Type.Blood);

		} else if (WumpusRow - 2 == -1) {
			if (board[(0)][WumpusCol].getType() == Cell.Type.Nothing)
				board[(0)][WumpusCol].setType(Cell.Type.Blood);
			if (board[(9)][WumpusCol].getType() == Cell.Type.Nothing)
				board[(9)][WumpusCol].setType(Cell.Type.Blood);
		} else {
			if (board[(WumpusRow - 2)][WumpusCol].getType() == Cell.Type.Nothing)
				board[(WumpusRow - 2)][WumpusCol].setType(Cell.Type.Blood);
			if (board[(WumpusRow - 1)][WumpusCol].getType() == Cell.Type.Nothing)
				board[(WumpusRow - 1)][WumpusCol].setType(Cell.Type.Blood);
		}

		// For blood left
		if (WumpusCol - 2 == -2) {
			if (board[(WumpusRow)][9].getType() == Cell.Type.Nothing)
				board[(WumpusRow)][9].setType(Cell.Type.Blood);
			if (board[(WumpusRow)][8].getType() == Cell.Type.Nothing)
				board[(WumpusRow)][8].setType(Cell.Type.Blood);
		} else if (WumpusCol - 2 == -1) {
			if (board[(WumpusRow)][0].getType() == Cell.Type.Nothing)
				board[(WumpusRow)][0].setType(Cell.Type.Blood);
			if (board[(WumpusRow)][9].getType() == Cell.Type.Nothing)
				board[(WumpusRow)][9].setType(Cell.Type.Blood);
		} else {
			if (board[(WumpusRow)][WumpusCol - 2].getType() == Cell.Type.Nothing)
				board[(WumpusRow)][WumpusCol - 2].setType(Cell.Type.Blood);
			if (board[(WumpusRow)][WumpusCol - 1].getType() == Cell.Type.Nothing)
				board[(WumpusRow)][WumpusCol - 1].setType(Cell.Type.Blood);
		}
		// For bottom right corner
		if (WumpusRow + 1 == 10 && WumpusCol + 1 == 10) {
			if (board[(0)][0].getType() == Cell.Type.Nothing)
				board[(0)][0].setType(Cell.Type.Blood);
		} else {
			if (board[(WumpusRow + 1) % 10][(WumpusCol + 1) % 10].getType() == Cell.Type.Nothing)
				board[(WumpusRow + 1) % 10][(WumpusCol + 1) % 10]
						.setType(Cell.Type.Blood);
		}

		// For bottom left corner
		if (WumpusRow + 1 == 10 && WumpusCol - 1 == -1) {
			if (board[(0)][9].getType() == Cell.Type.Nothing)
				board[(0)][9].setType(Cell.Type.Blood);
		} else {
			if (WumpusCol - 1 == -1) {
				board[(WumpusRow + 1) % 10][9].setType(Cell.Type.Blood);
			} else {
				if (board[(WumpusRow + 1) % 10][(WumpusCol - 1)].getType() == Cell.Type.Nothing)
					board[(WumpusRow + 1) % 10][(WumpusCol - 1)]
							.setType(Cell.Type.Blood);

			}

		}

		// For top right corner
		if (WumpusRow - 1 == -1 && WumpusCol + 1 == 10) {
			if (board[(9)][0].getType() == Cell.Type.Nothing)
				board[(9)][0].setType(Cell.Type.Blood);
		} else {
			if (WumpusRow - 1 == -1) {
				if (board[9][(WumpusCol + 1) % 10].getType() == Cell.Type.Nothing)
					board[9][(WumpusCol + 1) % 10].setType(Cell.Type.Blood);
			} else {
				if (board[(WumpusRow - 1)][(WumpusCol + 1) % 10].getType() == Cell.Type.Nothing)
					board[(WumpusRow - 1)][(WumpusCol + 1) % 10]
							.setType(Cell.Type.Blood);
			}
		}

		// For top left corner
		if (WumpusRow - 1 == -1 && WumpusCol - 1 == -1) {
			if (board[(9)][9].getType() == Cell.Type.Nothing)
				board[(9)][9].setType(Cell.Type.Blood);
		} else {
			if (WumpusRow - 1 == -1) {
				if (board[9][(WumpusCol - 1)].getType() == Cell.Type.Nothing)
					board[9][(WumpusCol - 1)].setType(Cell.Type.Blood);
			} else if (WumpusCol - 1 == -1) {
				if (board[(WumpusRow - 1)][(9)].getType() == Cell.Type.Nothing)
					board[(WumpusRow - 1)][(9)].setType(Cell.Type.Blood);
			} else {
				if (board[(WumpusRow - 1)][(WumpusCol - 1)].getType() == Cell.Type.Nothing)
					board[(WumpusRow - 1)][(WumpusCol - 1)]
							.setType(Cell.Type.Blood);
			}

		}
	}

	/*
	 * This method sets the blood for the spaces around Wumpus. It calls
	 * getBloodRule of the location of Wumpus to see where it should place the
	 * blood.
	 */

	/*
	 * This method sets the slime for the spaces around pits. It calls
	 * getSlimeRule of the location of pits to see where it should place the
	 * slime.
	 */

	public void setSlime(int pitRow, int pitCol) {
		// For right slime
		if ((pitCol + 1) / 10 == 1) {
			if (board[(pitRow)][0].getType() == Cell.Type.Nothing) {
				board[(pitRow)][0].setType(Cell.Type.Slime);
			} else if (board[(pitRow)][0].getType() == Cell.Type.Blood)

			{
				board[(pitRow)][0].setType(Cell.Type.Goop);
			}
		} else {
			if (board[(pitRow)][pitCol + 1].getType() == Cell.Type.Nothing) {
				board[(pitRow)][pitCol + 1].setType(Cell.Type.Slime);
			} else if (board[(pitRow)][pitCol + 1].getType() == Cell.Type.Blood) {
				board[(pitRow)][pitCol + 1].setType(Cell.Type.Goop);
			}
		}
		// For down slime
		if ((pitRow + 1) / 10 == 1) {

			if (board[(0)][pitCol].getType() == Cell.Type.Nothing) {
				board[(0)][pitCol].setType(Cell.Type.Slime);
			} else if (board[(0)][pitCol].getType() == Cell.Type.Blood) {
				board[(0)][pitCol].setType(Cell.Type.Goop);
			}

		} else {
			if (board[(pitRow + 1)][pitCol].getType() == Cell.Type.Nothing) {
				board[(pitRow + 1)][pitCol].setType(Cell.Type.Slime);
			} else if (board[(pitRow + 1)][pitCol].getType() == Cell.Type.Blood) {
				board[(pitRow + 1)][pitCol].setType(Cell.Type.Goop);
			}
		}

		// For up slime

		if ((pitRow - 1) < 0) {

			if (board[(9)][pitCol].getType() == Cell.Type.Nothing) {
				board[(9)][pitCol].setType(Cell.Type.Slime);
			} else if (board[(9)][pitCol].getType() == Cell.Type.Blood) {
				board[(9)][pitCol].setType(Cell.Type.Goop);
			}

		} else {

			if (board[(pitRow - 1)][pitCol].getType() == Cell.Type.Nothing) {
				board[(pitRow - 1)][pitCol].setType(Cell.Type.Slime);
			} else if (board[(pitRow - 1)][pitCol].getType() == Cell.Type.Blood) {
				board[(pitRow - 1)][pitCol].setType(Cell.Type.Goop);
			}
		}

		// For down slime

		if ((pitCol - 1) < 0) {

			if (board[(pitRow)][9].getType() == Cell.Type.Nothing) {
				board[(pitRow)][9].setType(Cell.Type.Slime);
			} else if (board[(pitRow)][9].getType() == Cell.Type.Blood) {
				board[(pitRow)][9].setType(Cell.Type.Goop);
			}

		} else {
			if (board[(pitRow)][pitCol - 1].getType() == Cell.Type.Nothing) {
				board[(pitRow)][pitCol - 1].setType(Cell.Type.Slime);
			} else if (board[(pitRow)][pitCol - 1].getType() == Cell.Type.Blood) {
				board[(pitRow)][pitCol - 1].setType(Cell.Type.Goop);
			}
		}

	}

	/*
	 * This method is used when the player shoots an arrow. It checks if the
	 * direction that is shoot has the Wumpus in it. If it does they player
	 * wins, but if not the player loses.
	 */

	public void shoot(String directionAnswer) {	
		int WumpusRow = -1;
		int WumpusCol = -1;
		if (playing) {
			// Hunter location
			int playerRow = Hunter.x;
			int playerCol = Hunter.y;
			// Wumpus location
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if (board[i][j].getType() == Cell.Type.Wumpus) {
						WumpusRow = i;
						WumpusCol = j;
						break;
					}
				}
			}
			
			if (directionAnswer.equals("N") || directionAnswer.equals("S")) {
				// Killed a wumpus in same col
				if (playerCol == WumpusCol) {
					System.out.println("You slayed the Wumpus!!!");
					fixGame(WumpusRow,WumpusCol);
					shootResponse = "You slayed the Wumpus!!!";
					gameOverReason = shootResponse;
					currentUser.setWumpiiSlain(currentUser.getWumpiiSlain()+1);
					playing = false;
					currentUser.setGamesWon(currentUser.getGamesWon()+1);

				} else {
					// Missed the shot:
					System.out.println("You shot yourself!!!");
					shootResponse = "You shot yourself!!!";
					gameOverReason = shootResponse;
					playing = false;
					currentUser.setGamesLost(currentUser.getGamesLost()+1);
					currentUser.setTimesShootSelf(currentUser.getTimesShootSelf()+1);

				}
			}
			if (directionAnswer.equals("E") || directionAnswer.equals("W")) {
				// Killed a Wumpus in same row
				if (playerRow == WumpusRow) {
					System.out.println("You slayed the Wumpus!!!");
					fixGame(WumpusRow,WumpusCol);
					shootResponse = "You slayed the Wumpus!!!";
					gameOverReason = shootResponse;
					currentUser.setWumpiiSlain(currentUser.getWumpiiSlain()+1);
					playing = false;
					currentUser.setGamesWon(currentUser.getGamesWon()+1);
				} else {
					// Missed the shot:
					System.out.println("You shot yourself!!!");
					shootResponse = "You shot yourself!!!";
					gameOverReason = shootResponse;
					playing = false;
					currentUser.setGamesLost(currentUser.getGamesLost()+1);
					currentUser.setTimesShootSelf(currentUser.getTimesShootSelf()+1);
				}

			}

		}
		updateViewers();

	}

	public void fixGame(int wumpusRow1, int wumpusCol1) {
		board[wumpusRow1][wumpusCol1].setType(Cell.Type.DeadWumpus);
		board[wumpusRow1][wumpusCol1].changeVisable();
		updateViewers();
	}

	public boolean isGameOver() {
		return playing;
	}

	// Assuming Valid Input
	/*
	 * This method moves the hunter icon in the direction that player provides.
	 * The direction must be one of the suggested for method to work. This moves
	 * the 'O' to the location and reveals the previous spot. If the user moves
	 * on the Wumpus or pit it says that you lose and stops running.
	 */
	public void move(String directionAnswer) {
		if (playing) {

			int playerRow = Hunter.x;
			int playerCol = Hunter.y;

			// If north.
			if (directionAnswer.equals("N")) {
				if (playerRow == 0) {
					Hunter.setLocation(9, playerCol);
					board[9][playerCol].changeVisable();
					currentUser.setStepsTaken(currentUser.getStepsTaken()+1);
					
					if (board[9][playerCol].getType() == Cell.Type.Wumpus) {
						System.out.println("You got eaten by the Wumpus!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setTimesRunIntoWumpus(currentUser.getTimesRunIntoWumpus()+1);
						gameOverReason = "You got eaten by the Wumpus!!!";

					} else if (board[9][playerCol].getType() == Cell.Type.Pit) {
						System.out.println("You fell into a slime pit!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setDeathByPits(currentUser.getDeathByPits()+1);
						gameOverReason = "You fell into a slime pit!!!";
					}
				} else {
					Hunter.setLocation(playerRow - 1, playerCol);
					board[playerRow - 1][playerCol].changeVisable();
					currentUser.setStepsTaken(currentUser.getStepsTaken()+1);

					if (board[playerRow - 1][playerCol].getType() == Cell.Type.Wumpus) {
						System.out.println("You got eaten by the Wumpus!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setTimesRunIntoWumpus(currentUser.getTimesRunIntoWumpus()+1);
						gameOverReason = "You got eaten by the Wumpus!!!";

					} else if (board[playerRow - 1][playerCol].getType() == Cell.Type.Pit) {
						System.out.println("You fell into a slime pit!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setDeathByPits(currentUser.getDeathByPits()+1);
						gameOverReason = "You fell into a slime pit!!!";

					}
				}

			}
			// If south. 
			if (directionAnswer.equals("S")) {
				if (playerRow == 9) {
					Hunter.setLocation(0, playerCol);
					board[0][playerCol].changeVisable();
					currentUser.setStepsTaken(currentUser.getStepsTaken()+1);

					if (board[0][playerCol].getType() == Cell.Type.Wumpus) {
						System.out.println("You got eaten by the Wumpus!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setTimesRunIntoWumpus(currentUser.getTimesRunIntoWumpus()+1);
						gameOverReason = "You got eaten by the Wumpus!!!";

					} else if (board[0][playerCol].getType() == Cell.Type.Pit) {
						System.out.println("You fell into a slime pit!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setDeathByPits(currentUser.getDeathByPits()+1);
						gameOverReason = "You fell into a slime pit!!!";

					}

				} else {
					Hunter.setLocation(playerRow + 1, playerCol);
					board[playerRow + 1][playerCol].changeVisable();
					currentUser.setStepsTaken(currentUser.getStepsTaken()+1);

					if (board[playerRow + 1][playerCol].getType() == Cell.Type.Wumpus) {
						System.out.println("You got eaten by the Wumpus!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setTimesRunIntoWumpus(currentUser.getTimesRunIntoWumpus()+1);
						gameOverReason = "You got eaten by the Wumpus!!!";

					} else if (board[playerRow + 1][playerCol].getType() == Cell.Type.Pit) {
						System.out.println("You fell into a slime pit!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setDeathByPits(currentUser.getDeathByPits()+1);
						gameOverReason = "You fell into a slime pit!!!";

					}
				}

			}
			// If east.
			if (directionAnswer.equals("E")) {
				if (playerCol == 9) {
					Hunter.setLocation(playerRow, 0);
					board[playerRow][0].changeVisable();
					currentUser.setStepsTaken(currentUser.getStepsTaken()+1);

					if (board[playerRow][0].getType() == Cell.Type.Wumpus) {
						System.out.println("You got eaten by the Wumpus!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setTimesRunIntoWumpus(currentUser.getTimesRunIntoWumpus()+1);
						gameOverReason = "You got eaten by the Wumpus!!!";

					} else if (board[playerRow][0].getType() == Cell.Type.Pit) {
						System.out.println("You fell into a slime pit!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setDeathByPits(currentUser.getDeathByPits()+1);
						gameOverReason = "You fell into a slime pit!!!";

					}
				} else {
					Hunter.setLocation(playerRow, playerCol + 1);
					board[playerRow][playerCol + 1].changeVisable();
					currentUser.setStepsTaken(currentUser.getStepsTaken()+1);

					if (board[playerRow][playerCol + 1].getType() == Cell.Type.Wumpus) {
						System.out.println("You got eaten by the Wumpus!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setTimesRunIntoWumpus(currentUser.getTimesRunIntoWumpus()+1);
						gameOverReason = "You got eaten by the Wumpus!!!";

					} else if (board[playerRow][playerCol + 1].getType() == Cell.Type.Pit) {
						System.out.println("You fell into a slime pit!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setDeathByPits(currentUser.getDeathByPits()+1);
						gameOverReason = "You fell into a slime pit!!!";

					}
				}

			}
			// If west.
			if (directionAnswer.equals("W")) {
				if (playerCol == 0) {
					Hunter.setLocation(playerRow, 9);
					board[playerRow][9].changeVisable();
					currentUser.setStepsTaken(currentUser.getStepsTaken()+1);

					if (board[playerRow][9].getType() == Cell.Type.Wumpus) {
						System.out.println("You got eaten by the Wumpus!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setTimesRunIntoWumpus(currentUser.getTimesRunIntoWumpus()+1);
						gameOverReason = "You got eaten by the Wumpus!!!";

					} else if (board[playerRow][9].getType() == Cell.Type.Pit) {
						System.out.println("You fell into a slime pit!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setDeathByPits(currentUser.getDeathByPits()+1);
						gameOverReason = "You fell into a slime pit!!!";

					}
				} else {
					Hunter.setLocation(playerRow, playerCol - 1);
					board[playerRow][playerCol - 1].changeVisable();
					currentUser.setStepsTaken(currentUser.getStepsTaken()+1);

					if (board[playerRow][playerCol - 1].getType() == Cell.Type.Wumpus) {
						System.out.println("You got eaten by the Wumpus!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setTimesRunIntoWumpus(currentUser.getTimesRunIntoWumpus()+1);
						gameOverReason = "You got eaten by the Wumpus!!!";

					} else if (board[playerRow][playerCol - 1].getType() == Cell.Type.Pit) {
						System.out.println("You fell into a slime pit!!!");
						playing = false;
						currentUser.setGamesLost(currentUser.getGamesLost()+1);
						currentUser.setDeathByPits(currentUser.getDeathByPits()+1);
						gameOverReason = "You fell into a slime pit!!!";

					}
				}

			}
			updateViewers();
		}
	}
	
	/*
	 * This method prints the visableBoard for the user. It is called after
	 * every move that the player makes to show where they are and what they are
	 * standing on so they can make their next move.
	 */
	public String printGame() {
		int playerRow = Hunter.x;
		int playerCol = Hunter.y;

		String thegame = "";
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				thegame += "[";
				if (isGameOver() == false) {
					board[i][j].changeVisable();
				}
				if (board[i][j].isVisable()) {

					switch (board[i][j].getType()) {
					case Blood:
						thegame += "B";
						break;
					case Goop:
						thegame += "G";
						break;
					case Slime:
						thegame += "S";
						break;
					case Wumpus:
						thegame += "W";
						break;
					case Pit:
						thegame += "P";
						break;
					case Nothing:
						thegame += " ";
						break;

					default:
						break;
					}
				} else {

					thegame += "X";

				}

				if (Hunter.x == i && Hunter.y == j) {
					thegame += "O";
				}
				thegame += "] ";
			}
			thegame += "\n";
		}
		thegame += "\n";

		if (hasShoot) {
			thegame += shootResponse;
		}
		// Tells player what they are standing in now.
		else if (board[playerRow][playerCol].getType() == Cell.Type.Nothing) {
			thegame += "You are standing in an empty room.\n";
		}
		// If you move to slime.
		else if (board[playerRow][playerCol].getType() == Cell.Type.Slime) {
			thegame += "You are standing in a room covered in Slime!\n";
		}
		// If you move to blood.
		else if (board[playerRow][playerCol].getType() == Cell.Type.Blood) {
			thegame += "You are standing in a room covered in Blood!\n";
		}
		// If you move to goop.
		else if (board[playerRow][playerCol].getType() == Cell.Type.Goop) {
			thegame += "You are standing in a room covered in Goop!\n";
		} else if (board[playerRow][playerCol].getType() == Cell.Type.Pit) {
			thegame += "You fell into a slime pit!!!\n";
		} else if (board[playerRow][playerCol].getType() == Cell.Type.Wumpus) {
			thegame += "You got eaten by the Wumpus!!!\n";
		}
		return thegame;
	}

	/*
	 * This method if used for JUnit testing. It makes a game where the chars
	 * are given to the method which changes it in the borad variable so we can
	 * play off of the board and have all other methods work.
	 */

	// public void setNotRandomGame(char[][] notVisible, char[][] visible)
	// {
	//
	// for (int i = 0; i < notVisible.length; i++)
	// {
	// for (int j = 0; j < notVisible.length; j++)
	// {
	// board[i][j] = notVisible[i][j];
	// }
	// }
	// for (int i = 0; i < visible.length; i++)
	// {
	// for (int j = 0; j < visible.length; j++)
	// {
	// visableBoard[i][j] = visible[i][j];
	// }
	// }
	//
	// }

	

	public void updateIsPlaying() {
		playing = true;
	}

	public Point getHunter() {
		return Hunter;
	}

}
