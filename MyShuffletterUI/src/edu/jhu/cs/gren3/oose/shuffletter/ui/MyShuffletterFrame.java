/*
* Name : Guannan Ren
* Email : gren3@jhu.edu
* Assignment : 1 part 2
*/

package edu.jhu.cs.gren3.oose.shuffletter.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.jhu.cs.oose.fall2014.shuffletter.iface.NoOpShuffletterModelListener;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.Position;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterIllegalMoveEvent;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterModel;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterTileMovedEvent;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterTilePlayedEvent;

public class MyShuffletterFrame extends JFrame{

	private ShuffletterModel model;
	private GridComponent board;
	private GridComponent supply;
	private ShuffletterMessagePane msgPane;
	private JScrollPane scrollBoard;
	private JPanel bottomPanel;
	
	/*
	 * Renders the playboard, supplyboard, and message pane/end round button
	 * @param model, the StandardShuffletterModel model
	 */
	public MyShuffletterFrame(final ShuffletterModel model) {
		super("The Shuffletter Game");
		this.model = model;
		this.board = new GridComponent(new ShuffletterBoardModel(model));
		this.supply = new GridComponent(new ShuffletterSupplyModel(model));
		this.msgPane = new ShuffletterMessagePane(model);
		this.scrollBoard = new JScrollPane(this.board);
		this.bottomPanel = new JPanel();
	
		this.addMenuBar();
		JButton endRoundButton = new JButton("End Round");
		this.bottomPanel.setLayout(new BorderLayout());
		this.bottomPanel.add(BorderLayout.CENTER, msgPane);
		this.bottomPanel.add(BorderLayout.EAST, endRoundButton);
		displaySetup();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.repaint();
	    this.setVisible(true);
		
		this.pack();
		
	    endRoundButton.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		model.endRound();
	    	}
	    });
	    
	    addMyListeners();
	}

	/*
	 * Adds listeners to game model, board, and
	 * supply components
	 */
	private void addMyListeners() {
	    
		this.model.addListener(new NoOpShuffletterModelListener() {
	    	public void gameEnded() {
	    		msgPane.newMessage("Game Over! New Game?");
	    	}
	    	
	    	public void illegalMoveMade(ShuffletterIllegalMoveEvent event) {
	    		msgPane.newErrorMessage(event.getMessage());
	    	}
	    	
	    	public void roundEnded() {
	    		displayBagContent();
	    	}
	    	
	    	public void tileMoved(ShuffletterTileMovedEvent event) {
	    		displayBagContent();
	    	}
	    	
	    	public void tilePlayed(ShuffletterTilePlayedEvent event) {
	    		displayBagContent();
	    	}
            
	    	private void displayBagContent() {
	    		StringBuilder message = new StringBuilder();
	    		message.append(model.getBagCount()).append(" tiles left in the bag currently.");
	    		MyShuffletterFrame.this.msgPane.newMessage(message.toString());
	    	}
	    });
	    
	    this.board.addChoiceListener(new ChoiceListener() {
	    	public void positionChosen(Position pos) {
	    		if (supply.getSelectedTileModel().getSelectedPosition() != null) {
	    			Position supplyTilePos = supply.getSelectedTileModel().getSelectedPosition();
	    			model.play(supply.getGridModel().getTileAt(supplyTilePos), pos);
	    			supply.getSelectedTileModel().setSelectedPosition(null);
	    			board.getSelectedTileModel().setSelectedPosition(null);
	    		} else {
	    			Position oldPos = board.getSelectedTileModel().getSelectedPosition();
		    		if (oldPos == null && board.getGridModel().getTileAt(pos) != null) {
		    			board.getSelectedTileModel().setSelectedPosition(pos);
		    		} else if (oldPos != null && oldPos.equals(pos)) {
		    			board.getSelectedTileModel().setSelectedPosition(null);
		    		} else if (oldPos != null){
		    			model.move(oldPos, pos);
		    			board.getSelectedTileModel().setSelectedPosition(null);
		    		}
	    		}
	    	}
	    });
	    
	    this.supply.addChoiceListener(new ChoiceListener() {
	    	public void positionChosen(Position pos) {
	    		if (supply.getGridModel().getTileAt(pos) != null) {
	    			if (supply.getSelectedTileModel().getSelectedPosition() != null) {
		    			if (supply.getSelectedTileModel().getSelectedPosition().equals(pos)) {
		    				supply.getSelectedTileModel().setSelectedPosition(null);  // deselect
		    			} else {
		    				supply.getSelectedTileModel().setSelectedPosition(pos);
		    			}
	    			} else {
	    				supply.getSelectedTileModel().setSelectedPosition(pos);
	    			}
	    		}
	    	}
	    });
		
	}
	
	/*
	 * Connects the board and supply components
	 * to the main frame, setup preferences for components
	 */
	 private void displaySetup() {
		 this.getContentPane().setLayout(new BorderLayout());
		 this.getContentPane().add(BorderLayout.SOUTH, this.bottomPanel);
		 this.getContentPane().add(BorderLayout.CENTER, this.scrollBoard);
		 this.getContentPane().add(BorderLayout.EAST, this.supply);
		 this.board.setPreferredSize(board.getPreferredSize());
		 this.supply.setPreferredSize(supply.getPreferredSize());
		 this.board.setBorder(BorderFactory.createEtchedBorder());
		 this.supply.setBorder(BorderFactory.createEtchedBorder());
	 }
	 
	 /*
	  * Starts a new game by loading new instances of
	  * model, board, and supply
	  */
	 private void resetGame() {
		try {
			this.remove(this.scrollBoard);
			this.remove(this.supply);
			this.remove(this.bottomPanel);
			final ShuffletterModel model = new edu.jhu.cs.oose.fall2014.shuffletter.model.StandardShuffletterModel();
			this.model = model;
			this.board = new GridComponent(new ShuffletterBoardModel(model));
			this.supply = new GridComponent(new ShuffletterSupplyModel(model));
			this.scrollBoard = new JScrollPane(this.board);
			displaySetup();
			addMyListeners();
			this.revalidate();
			this.repaint();
		} catch (IOException e) {
    		System.err.println("Model Initialization Error!");
    		System.exit(0);
		}
	 }
	
	 /*
	  * Top menu bar for the game, has options
	  * File/exit and Help/about
	  */
	 private void addMenuBar() {
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		file.setMnemonic('F');
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem newGame = new JMenuItem("New Game");
		JMenu help = new JMenu("Help");
		help.setMnemonic('H');
		JMenuItem about = new JMenuItem("About");

		menubar.add(file);
		file.add(exit);
		file.add(newGame);
		menubar.add(help);
		help.add(about);
		
		this.setJMenuBar(menubar);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MyShuffletterFrame.this.resetGame();
			}
		});
		
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionevent) {
                JOptionPane.showMessageDialog(MyShuffletterFrame.this, 
                		(new StringBuilder()).append("<html><center><br/>Welcome !").
                		append("<br/>to the Shuffletter Game, game model by <br/>").
                		append(model.getAuthorName()).toString(), "About", 1);
            }
        });
	 }

	 /*
	  * Nested java class for message pane display
	  */
	 private class ShuffletterMessagePane extends JPanel {
		
		 private JLabel message;
		
		 public ShuffletterMessagePane(ShuffletterModel model) {
			
			 this.setLayout(new GridBagLayout());
			 this.setBorder(BorderFactory.createLineBorder(Color.black));
						
			 message = new JLabel();
			
			 this.setLayout(new BorderLayout());
			 this.add(BorderLayout.WEST, message);
		 }
		
		 public void newMessage(String msg) {
			 message.setForeground(Color.BLACK);
			 message.setText(msg);
		 }
	
		 public void newErrorMessage(String msg) {
			 message.setForeground(Color.RED);
			 message.setText(msg);
		 }
	 }

}
