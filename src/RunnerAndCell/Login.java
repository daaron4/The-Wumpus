package RunnerAndCell;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Users.User;

public class Login extends JFrame implements Serializable {

	private static HashMap<String, User> users; // store each user with a key
	private User currentUser;

	private JPanel loginPanel;
	private JTextField userName;
	private JPasswordField passField;
	private JTextField userField;
	private JButton loginButton;
	private JButton addAccountButton;
	private JButton guest;

	public static void main(String[] args) {
		new Login().setVisible(true);
	}

	public Login() {
		users = new HashMap<String, User>();
		loadData();
		setTitle("Login");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		loginPanel = createLoginPanel();

		this.add(loginPanel);

	}

	

	private JPanel createLoginPanel() {
		passField = new JPasswordField();
		passField.setPreferredSize(new Dimension(200, 25));
		userField = new JTextField();
		userField.setPreferredSize(new Dimension(200, 25));

		JLabel passLabel = new JLabel("Password: ");
		JLabel userLabel = new JLabel("Username: ");

		loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login();

			}
		});

		addAccountButton = new JButton("Create Account");
		addAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addAccount();

			}
		});

		guest = new JButton("Play as a Guest");
		guest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playAsGuest();

			}
		});

		JPanel login = new JPanel();

		JPanel temp;

		temp = new JPanel();
		temp.add(userLabel);
		temp.add(userField);
		login.add(temp);

		temp = new JPanel();
		temp.add(passLabel);
		temp.add(passField);
		login.add(temp);

		temp = new JPanel();
		temp.add(loginButton);
		temp.add(addAccountButton);
		login.add(temp);

		temp = new JPanel();
		temp.add(guest);
		login.add(temp);

		return login;
	}

	public void login() {
		String username = userField.getText();
		String password = new String(passField.getPassword());
		if (username.equals("") || password.equals("")) {
			JOptionPane.showMessageDialog(null, "Invalid username or password");
			userField.setText("");
			passField.setText("");
			return;
		}
		User aUser = users.get(username);
		if (aUser == null || !aUser.matches(passField.getPassword())) {
			JOptionPane.showMessageDialog(null, "Invalid username or password");
			userField.setText("");
			passField.setText("");
			return;
		}

		currentUser = aUser;
		userField.setText("");
		passField.setText("");

		JOptionPane.showMessageDialog(
				null,
				"Welcome " + currentUser.getUserName()
						+ ", here are your stats:" + "\n" + "Games won: "
						+ currentUser.getGamesWon() + "\n" + "Games lost: "
						+ currentUser.getGamesLost() + "\n" + "Wumpii slain "
						+ currentUser.getWumpiiSlain() + "\n" + "Wupii kills "
						+ currentUser.getTimesRunIntoWumpus() + "\n"
						+ "Times killed self "
						+ currentUser.getTimesShootSelf() + "\n"
						+ "Deaths by slime " + currentUser.getDeathByPits() + "\n"  
						+ "Steps Taken: " + currentUser.getStepsTaken());

		setVisible(false);
		WumpusSelection w = new WumpusSelection(currentUser);
		w.setVisible(true);
	}

	public void playAsGuest() {
		currentUser = new User("Guest", "Guest");
		JOptionPane.showMessageDialog(
				null,
				"Welcome " + currentUser.getUserName()
						+ ", here are your stats:" + "\n" + "Games won: "
						+ currentUser.getGamesWon() + "\n" + "Games lost: "
						+ currentUser.getGamesLost() + "\n" + "Wumpii slain "
						+ currentUser.getWumpiiSlain() + "\n" + "Wupii kills "
						+ currentUser.getTimesRunIntoWumpus() + "\n"
						+ "Times killed self "
						+ currentUser.getTimesShootSelf() + "\n"
						+ "Deaths by slime " + currentUser.getDeathByPits() + "\n"
						+ "Steps Taken: " + currentUser.getStepsTaken());


		setVisible(false);
		WumpusSelection w = new WumpusSelection(currentUser);
		w.setVisible(true);
	}

	public void addAccount() {
		String username = userField.getText();
		String password = new String(passField.getPassword());

		if (username.equals("") || password.equals("")) {
			JOptionPane.showMessageDialog(null, "Invalid username or password");
			return;
		}

		if (users.get(username) != null) {
			JOptionPane.showMessageDialog(null,
					"An account exists with that name");
			return;
		}
		users.put(username, new User(username, password));
		login();
	}
	public static HashMap<String, User> getUsers() {
		return users;
	}
	public void loadData() {
		try {
			FileInputStream inStream = new FileInputStream(new File("accounts.dat"));
			ObjectInputStream inObject = new ObjectInputStream(inStream);
			users = (HashMap<String, User>)inObject.readObject();
			
			inObject.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
