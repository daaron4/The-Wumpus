package TwoWumpi;
/*
 * First and Last name: Kal Cramer and David Aaron
 * Assignment name: Hunt the Wumpus: Iteration 2
 * Date due:9/19/14
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;
import RunnerAndCell.Login;
import Users.User;

public class TwoWumpiiGUI extends JFrame {

	private JTabbedPane severalPanels;
	private TwoWumpiiMap theModel;
	private BasicArrowButton moveUp, moveDown, moveLeft, moveRight, shootUp,
			shootDown, shootLeft, shootRight; // arrow
	// buttons
	private JLabel move;
	private JLabel shoot;
	private JPanel StringView; // The views could also be
	private JPanel GUIView; // Observer
	private boolean isShootMode;
	private User currentUser;
	private JButton stats;
	private JButton playAgain;


	public static void main(String[] args) {
		new TwoWumpiiGUI(null).setVisible(true);

	}

	public TwoWumpiiGUI(User user) {
		currentUser = user;
		layoutThisJFrame();
		registerListeners();
		setUpModelAndObservers();

	}

	public void layoutThisJFrame() {
		setTitle("Wumpus Hunter Game");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		StringView = new TwoWumpiiTextView();
		GUIView = new TwoWumpiiDigitalView();

		severalPanels = new JTabbedPane();
		severalPanels.add(StringView, "Text View");
		severalPanels.add(GUIView, "GUI View");

		this.setLocationRelativeTo(null);
		this.setLayout(null);
		severalPanels.setBounds(200, 0, 500, 530);

		move = new JLabel("Move");
		shoot = new JLabel("Shoot");

		shoot.setLayout(null);
		move.setLayout(null);

		move.setBounds(75, 75, 50, 50);
		shoot.setBounds(75, 275, 50, 50);

		/* Create the arrow buttons */
		moveUp = new BasicArrowButton(SwingConstants.NORTH);
		moveDown = new BasicArrowButton(SwingConstants.SOUTH);
		moveLeft = new BasicArrowButton(SwingConstants.WEST);
		moveRight = new BasicArrowButton(SwingConstants.EAST);

		/* Ignore this part, swing is really stupid sometimes */
		moveUp.setFocusable(false);
		moveDown.setFocusable(false);
		moveLeft.setFocusable(false);
		moveRight.setFocusable(false);

		// Busy Work:
		moveUp.setBounds(70, 120, 50, 50);
		moveDown.setBounds(70, 170, 50, 50);
		moveLeft.setBounds(20, 170, 50, 50);
		moveRight.setBounds(120, 170, 50, 50);

		shootUp = new BasicArrowButton(SwingConstants.NORTH);
		shootDown = new BasicArrowButton(SwingConstants.SOUTH);
		shootLeft = new BasicArrowButton(SwingConstants.WEST);
		shootRight = new BasicArrowButton(SwingConstants.EAST);

		/* Ignore this part, swing is really stupid sometimes */
		shootUp.setFocusable(false);
		shootDown.setFocusable(false);
		shootLeft.setFocusable(false);
		shootRight.setFocusable(false);

		shootUp.setBounds(70, 320, 50, 50);
		shootDown.setBounds(70, 370, 50, 50);
		shootLeft.setBounds(20, 370, 50, 50);
		shootRight.setBounds(120, 370, 50, 50);
		
		stats = new JButton("User Stats");
		stats.setBounds(50, 30, 100, 25);
		
		playAgain = new JButton("Play Again");
		playAgain.setBounds(50, 62, 100, 25);
		
		add(move);
		add(shoot);

		add(moveUp);
		add(moveLeft);
		add(moveDown);
		add(moveRight);

		add(shootUp);
		add(shootLeft);
		add(shootDown);
		add(shootRight);
		add(stats);
		add(playAgain);

		add(severalPanels);
		this.setVisible(true);

	}

	public void setUpModelAndObservers() {
		theModel = new TwoWumpiiMap(currentUser);
		theModel.addObserver((Observer) StringView);
		theModel.addObserver((Observer) GUIView);
		theModel.updateViewers();
	}

	public void registerListeners() {
		moveUp.addActionListener(new ButtonMoveListener());
		moveDown.addActionListener(new ButtonMoveListener());
		moveRight.addActionListener(new ButtonMoveListener());
		moveLeft.addActionListener(new ButtonMoveListener());

		shootUp.addActionListener(new ButtonMoveListener());
		shootDown.addActionListener(new ButtonMoveListener());
		shootRight.addActionListener(new ButtonMoveListener());
		shootLeft.addActionListener(new ButtonMoveListener());
		
		stats.addActionListener(new ButtonMoveListener());
		playAgain.addActionListener(new ButtonMoveListener());

		this.addWindowListener(new SaveDataListener());

	}
	public void saveData() {
		try {
			FileOutputStream outStream = new FileOutputStream(new File("accounts.dat"));
			ObjectOutputStream outObject = new ObjectOutputStream(outStream);
			outObject.writeObject(Login.getUsers());
			outObject.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	private class SaveDataListener implements WindowListener {
		// TODO 4 implement SaveDataListener
		@Override
		public void windowClosing(WindowEvent arg0) {
				saveData();
		}

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}

	public class ButtonMoveListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == stats) {
				JOptionPane.showMessageDialog(
						null,
						 currentUser.getUserName()
								+ ", here are your stats:" + "\n"
								+ "Games won: " + currentUser.getGamesWon()
								+ "\n" + "Games lost: "
								+ currentUser.getGamesLost() + "\n"
								+ "Wumpii slain "
								+ currentUser.getWumpiiSlain() + "\n"
								+ "Wupii kills "
								+ currentUser.getTimesRunIntoWumpus() + "\n"
								+ "Times killed self "
								+ currentUser.getTimesShootSelf() + "\n"
								+ "Deaths by slime "
								+ currentUser.getDeathByPits() + "\n"
								+ "Steps Taken: " + currentUser.getStepsTaken());
			}
			if (arg0.getSource() == playAgain) {
				if (theModel.isGameOver()==true)
					JOptionPane.showMessageDialog(null, " Your current game is not over yet!");
				if (theModel.isGameOver()==false)
					theModel.playNextGame();
			}
			if (arg0.getSource() == moveUp) {
				theModel.move("N");
			} else if (arg0.getSource() == moveDown) {
				theModel.move("S");
			} else if (arg0.getSource() == moveLeft) {
				theModel.move("W");
			} else if (arg0.getSource() == moveRight) {
				theModel.move("E");
			}

			if (arg0.getSource() == shootUp) {
				theModel.shoot("N");
			} else if (arg0.getSource() == shootDown) {
				theModel.shoot("S");
			} else if (arg0.getSource() == shootLeft) {
				theModel.shoot("W");
			} else if (arg0.getSource() == shootRight) {
				theModel.shoot("E");
			}

		}
	}

}
