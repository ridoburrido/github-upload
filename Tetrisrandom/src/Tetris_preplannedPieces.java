import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


// making a note here to push safsgfrsdfsdf hnsagksdfghjksdsfghj
/**
 * 
 * Note:  
 * This file was downloaded from https://gist.github.com/DataWraith/5236083
 * just for educational purposes and nothing else!
 * 
 * End of game rule: 
 * The code also was modified so that the game ends faster! 
 * After 25 pieces game will be ended.
 *
 *  This version will select 4 piece our of 7 piece and let the user select their order
 */
public class Tetris_preplannedPieces extends JPanel {

	
	
	private static final long serialVersionUID = -8715353373678321308L;

	private final Point[][][] Tetraminos = {
			// I-Piece
			{
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) }
			},

			// J-Piece
			{
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) }
			},

			// L-Piece
			{
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) }
			},

			// O-Piece
			{
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
			},

			// S-Piece
			{
				{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
				{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
			},

			// T-Piece
			{
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
				{ new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2) }
			},

			// Z-Piece
			{
				{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
				{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }
			}
	};

	private final Color[] tetraminoColors = {
			Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.pink, Color.red
	};

	static JTextField textfield1, textfield2, textfield3;

	private Point pieceOrigin;
	private int currentPiece = -1;
	private int rotation;
	private ArrayList<Integer> nextPieces = new ArrayList<Integer>();

	private long score;
	private Color[][] well;
	private int numberOfPieces;
	private static int END_GAME = 25;
	private int previousPiece = -1;
	private int selectedPiece = -1;


	// Creates a border around the well and initializes the dropping piece
	private void init() {
		well = new Color[12][24];
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 23; j++) {
				if (i == 0 || i == 11 || j == 22) {
					well[i][j] = Color.GRAY;
				} else {
					well[i][j] = Color.BLACK;
				}
			}
		}
		newPiece();
	}

	// Put a new, 3 random piece into the dropping position
	public void newPiece() {
		pieceOrigin = new Point(5, 2);
		rotation = 0;
		if (nextPieces.isEmpty()) {
			Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6);
			Collections.shuffle(nextPieces);
			if(previousPiece != -1) {
				nextPieces.remove(nextPieces.indexOf(previousPiece));
			}
			for(int i = 0; nextPieces.size() > 4; i++) {
				int rand = (int) (Math.random() * nextPieces.size());
				nextPieces.remove(rand);
			}
		}


		JFrame p = new JFrame("Select The Next Piece"); 
		//submit button
		JButton b=new JButton("Submit");    
		b.setBounds(100,100,140, 40);    
		//enter name label
		JLabel label = new JLabel();		
		label.setText("Select the next valid piece:");
		label.setBounds(10, 10, 100, 100);
		//empty label which will show event after button clicked
		JLabel label1 = new JLabel();
		label1.setBounds(10, 110, 200, 100);
		label1.setText("List of valid pieces : " + nextPieces.toString());
		//textfield to enter the piece
		JTextField textfield= new JTextField();
		textfield.setBounds(110, 50, 130, 30);
		//add to frame
		p.add(label1);
		p.add(textfield);
		p.add(label);
		p.add(b);    
		p.setSize(300,300);    
		p.setLayout(null);    
		p.setVisible(true);    
		p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   

		//action listener
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (textfield.getText() == null)
					label1.setText("Select a valid number!");
				if(textfield.getText()!= null && 
						!textfield.getText().trim().isEmpty()) {
					selectedPiece = Integer.parseInt(textfield.getText().trim());

					if(nextPieces.contains(selectedPiece) && selectedPiece != previousPiece ) {
						currentPiece = selectedPiece;
						nextPieces.remove(nextPieces.indexOf(selectedPiece));
						previousPiece = currentPiece;
						numberOfPieces++; 
						p.dispose();

					}
				}

			}         
		});

	}

	// Collision test for the dropping piece
	private boolean collidesAt(int x, int y, int rotation) {
		for (Point p : Tetraminos[currentPiece][rotation]) {
			if (well[p.x + x][p.y + y] != Color.BLACK) {
				return true;
			}
		}
		return false;
	}

	// Rotate the piece clockwise or counterclockwise
	public void rotate(int i) {
		int newRotation = (rotation + i) % 4;
		if (newRotation < 0) {
			newRotation = 3;
		}
		if (!collidesAt(pieceOrigin.x, pieceOrigin.y, newRotation)) {
			rotation = newRotation;
		}
		repaint();
	}

	// Move the piece left or right
	public void move(int i) {
		if (!collidesAt(pieceOrigin.x + i, pieceOrigin.y, rotation)) {
			pieceOrigin.x += i;	
		}
		repaint();
	}

	// Drops the piece one line or fixes it to the well if it can't drop
	public void dropDown() {
		if(currentPiece!=-1) {
			if (!collidesAt(pieceOrigin.x, pieceOrigin.y + 1, rotation)) {
				pieceOrigin.y += 1;
			} else {
				fixToWell();
			}

			repaint();
		}
	}

	// Make the dropping piece part of the well, so it is available for
	// collision detection.
	public void fixToWell() {
		for (Point p : Tetraminos[currentPiece][rotation]) {
			well[pieceOrigin.x + p.x][pieceOrigin.y + p.y] = tetraminoColors[currentPiece];
		}
		clearRows();
		if(numberOfPieces <= END_GAME) {
			currentPiece = -1; //set it back -1 so we ask user to enter a number
			newPiece();

		}
	}

	public void deleteRow(int row) {
		for (int j = row-1; j > 0; j--) {
			for (int i = 1; i < 11; i++) {
				well[i][j+1] = well[i][j];
			}
		}
	}

	// Clear completed rows from the field and award score according to
	// the number of simultaneously cleared rows.
	public void clearRows() {
		boolean gap;
		int numClears = 0;

		for (int j = 21; j > 0; j--) {
			gap = false;
			for (int i = 1; i < 11; i++) {
				if (well[i][j] == Color.BLACK) {
					gap = true;
					break;
				}
			}
			if (!gap) {
				deleteRow(j);
				j += 1;
				numClears += 1;
			}
		}

		switch (numClears) {
		case 1:
			score += 100;
			break;
		case 2:
			score += 300;
			break;
		case 3:
			score += 500;
			break;
		case 4:
			score += 800;
			break;
		}
	}

	// Draw the falling piece
	private void drawPiece(Graphics g) {
		if(currentPiece!=-1) {
			g.setColor(tetraminoColors[currentPiece]);
			for (Point p : Tetraminos[currentPiece][rotation]) {
				g.fillRect((p.x + pieceOrigin.x) * 26, 
						(p.y + pieceOrigin.y) * 26, 
						25, 25);
			}
		}
	}

	@Override 
	public void paintComponent(Graphics g)
	{
		// Paint the well
		g.fillRect(0, 0, 26*12, 26*23);
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 23; j++) {
				g.setColor(well[i][j]);
				g.fillRect(26*i, 26*j, 25, 25);
			}
		}

		// Display the score
		g.setColor(Color.WHITE);
		g.drawString("Score = " + score, 19*12-200, 25);

		g.setColor(Color.RED);
		g.drawString("Number Of Pieces = " + numberOfPieces, 19*12-200, 10); 


		// Draw the currently falling piece
		drawPiece(g);
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("Tetris");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setSize(12*26+10, 26*23+25);

		final Tetris_preplannedPieces game = new Tetris_preplannedPieces();
		f.add(game);
		game.init();


		// Keyboard controls
		f.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					game.rotate(-1);
					break;
				case KeyEvent.VK_DOWN:
					game.rotate(+1);
					break;
				case KeyEvent.VK_LEFT:
					game.move(-1);
					break;
				case KeyEvent.VK_RIGHT:
					game.move(+1);
					break;
				case KeyEvent.VK_SPACE:
					game.dropDown();
					//game.score += 1;
					break;
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		});

		// Make the falling piece drop every second
		new Thread() {
			@Override public void run() {		
				while (true) {
					try {
						Thread.sleep(400);
						game.dropDown();
					} catch ( InterruptedException e ) {}
				}

			}
		}.start();
	}
}
