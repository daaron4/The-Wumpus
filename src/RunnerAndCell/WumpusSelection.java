package RunnerAndCell;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Torch.TorchGUI;
import ToughTorch.ToughTorchGUI;
import TwoWumpi.TwoWumpiiGUI;
import Users.User;
import Classic.ClassicGUI;

public class WumpusSelection extends JFrame implements ActionListener {
	private JLabel choose;
	private JLabel classicDescription;
	private JLabel twoWumpiiDescription;
	private JLabel torchDescription1;
	private JLabel torchDescription2;
	private JLabel hardTorchDescription;
	
	private JButton classicButton;
	private JButton twoWumpiiButton;
	private JButton torchButton;
	private JButton toughTorchButton;
	private User currentUser;

	public static void main(String[] args) {
		new WumpusSelection(null).setVisible(true);
	}

	public WumpusSelection(User user) {
		currentUser = user;
		layoutThisJFrame();
		registerListeners();
	}

	public void layoutThisJFrame() {
		setTitle("Wumpus Hunter");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setLayout(null);

		choose = new JLabel("Choose a Game:");
		choose.setBounds(190, -50, 125, 125);

		classicDescription = new JLabel(
				"Classic mode creates 3 to 5 slime pits, with only one Wumpus:");
		classicDescription.setBounds(75, -150, 400, 400);

		twoWumpiiDescription = new JLabel(
				"Two Wumpii mode creates 3 to 5 slime pits, but with two deadly Wumpii:");
		twoWumpiiDescription.setBounds(40, -115, 500, 500);
		
		torchDescription1 = new JLabel(
				"Torch Mode creates 3 to 5 slime pits with only one Wumpus, ");
		torchDescription1.setBounds(40, -285, 1000, 1000);
		torchDescription2 = new JLabel(
				"but beware, you can only see the spot you are standing in!!");
		torchDescription2.setBounds(40, -270, 1000, 1000);
		
		hardTorchDescription = new JLabel("Tough Torch Mode, Torch Mode with two Wumpii!!!");
		hardTorchDescription.setBounds(75, 50, 500, 500);
		
		classicButton = new JButton("Classic");
		classicButton.setBounds(170, 75, 125, 30);

		twoWumpiiButton = new JButton("Two Wumpii");
		twoWumpiiButton.setBounds(170, 160, 125, 30);

		torchButton = new JButton("Torch");
		torchButton.setBounds(170, 250, 125, 30);
		
		toughTorchButton = new JButton("Tough Torch");
		toughTorchButton.setBounds(170, 325, 125, 30);

		
		
		
		add(choose);
		add(classicDescription);
		add(classicButton);
		add(twoWumpiiDescription);
		add(twoWumpiiButton);
		add(torchDescription1);
		add(torchDescription2);
		add(torchButton);
		add(hardTorchDescription);
		add(toughTorchButton);

	}

	public void registerListeners() {
		classicButton.addActionListener(this);
		twoWumpiiButton.addActionListener(this);
		torchButton.addActionListener(this);
		toughTorchButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent hasBeenClicked) {
		if (hasBeenClicked.getSource() == classicButton) {
			setVisible(false);
			ClassicGUI game = new ClassicGUI(currentUser);
		}
		if (hasBeenClicked.getSource() == twoWumpiiButton) {
			setVisible(false);
			TwoWumpiiGUI game = new TwoWumpiiGUI(currentUser);
		}
		if (hasBeenClicked.getSource() == torchButton) {
			setVisible(false);
			TorchGUI game = new TorchGUI(currentUser);
		}
		if (hasBeenClicked.getSource() == toughTorchButton) {
			setVisible(false);
			ToughTorchGUI game = new ToughTorchGUI(currentUser);
		}
	}

}
