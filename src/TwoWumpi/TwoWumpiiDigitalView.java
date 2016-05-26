package TwoWumpi;
/*
 * First and Last name: Kal Cramer and David Aaron
 * Assignment name: Hunt the Wumpus: Iteration 2
 * Date due:9/19/14
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import RunnerAndCell.Cell;

public class TwoWumpiiDigitalView extends JPanel implements Observer {
	private BufferedImage image;
	private Point pos;
	private BufferedImage hunterImage, groundImage, bloodImage, goopImage,
			slimeImage, slimePitImage, wumpusImage, blackImage, deadWumpusImage;
	private Cell[][] board;
	private TwoWumpiiMap theModel;
	private JLabel message;

	public TwoWumpiiDigitalView() {
		setLayout(null);
		message = new JLabel("");
		message.setFont(new Font("Algerian", Font.BOLD, 32));
		message.setBounds(0, 0, 500, 500);
		message.setForeground(Color.GREEN);
		
		add(message);
		pos = new Point(0, 0);
		try {
			hunterImage = ImageIO.read(new File("TheHunter.png"));
			groundImage = ImageIO.read(new File("Ground.png"));
			bloodImage = ImageIO.read(new File("Blood.png"));
			goopImage = ImageIO.read(new File("Goop.png"));
			slimeImage = ImageIO.read(new File("Slime.png"));
			slimePitImage = ImageIO.read(new File("SlimePit.png"));
			wumpusImage = ImageIO.read(new File("Wumpus.png"));
			blackImage = ImageIO.read(new File("blankspace.png"));
			deadWumpusImage = ImageIO.read(new File("DeadWumpus.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void update(Observable arg0, Object arg1) {
		theModel = (TwoWumpiiMap) arg0;
		board = theModel.getBoard();
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				pos.x = j;
				pos.y = i;
				if (board[i][j].isVisable()) {
					image = groundImage;
					g.drawImage(image, pos.x * 50, pos.y * 50, 50, 50, null);

					if (board[i][j].getType() == Cell.Type.Blood) {
						image = bloodImage;
						g.drawImage(image, pos.x * 50, pos.y * 50, 50, 50, null);
					}

					if (board[i][j].getType() == Cell.Type.Goop) {
						image = goopImage;
						g.drawImage(image, pos.x * 50, pos.y * 50, 50, 50, null);
					}

					if (board[i][j].getType() == Cell.Type.Slime) {
						image = slimeImage;
						g.drawImage(image, pos.x * 50, pos.y * 50, 50, 50, null);
					}

					if (board[i][j].getType() == Cell.Type.Pit) {
						image = slimePitImage;
						g.drawImage(image, pos.x * 50, pos.y * 50, 50, 50, null);
					}
					if (board[i][j].getType() == Cell.Type.Wumpus) {
						image = wumpusImage;
						g.drawImage(image, pos.x * 50, pos.y * 50, 50, 50, null);
					}
					if (board[i][j].getType() == Cell.Type.DeadWumpus) {
						image = deadWumpusImage;
						g.drawImage(image, pos.x * 50, pos.y * 50, 50, 50, null);
					}

				} else {
					image = blackImage;
					g.drawImage(image, pos.x * 50, pos.y * 50, 50, 50, null);
				}
				pos.x = theModel.getHunter().y;
				pos.y = theModel.getHunter().x;
				Point hunter = new Point(theModel.getHunter().x,
						theModel.getHunter().y);
				image = hunterImage;
				g.drawImage(image, pos.x * 50, pos.y * 50, 50, 50, null);
				message.setText(theModel.gameOverReason);
				
				
			}

		}


	}
}
