/*
* Name : Guannan Ren
* Email : gren3@jhu.edu
* Assignment : 1 part 1
* Worked alone
*/

package edu.jhu.cs.gren3.oose.shuffletter.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import edu.jhu.cs.oose.fall2014.shuffletter.iface.Position;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterIllegalMoveEvent;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterModel;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterModelListener;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterTile;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterTileMovedEvent;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterTilePlayedEvent;

public class MyShuffletterModel implements ShuffletterModel {
	
	private Map<Position, ShuffletterTile> gameGrid;
	private String player;
	private Set<String> words;
	private List<ShuffletterTile> bagTiles;
	private List<ShuffletterTile> supplyTiles;
	private Set<ShuffletterModelListener> listeners ;
	private boolean gameOver = false;

	/*
	 * Initialize class variables
	 * Sets up game model
	 * Initialize character tiles
	 * Read in list of legal english words
	 */
	public MyShuffletterModel(){
		String playerName = "Guannan Ren";
		
		this.gameGrid = new HashMap<Position, ShuffletterTile>();
		this.player = playerName;
		this.words = new HashSet<String>();
		this.bagTiles = new ArrayList<ShuffletterTile>();
		this.supplyTiles = new ArrayList<ShuffletterTile>();		
		this.listeners = new HashSet<ShuffletterModelListener>();
		
		File file = new File("files" + File.separator + "wordlist.txt");
		FileInputStream fis = null;
		BufferedReader reader = null;
		
		try {
			fis = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(fis));	        

			String line = reader.readLine();
	        while(line != null){
	            words.add(line.toLowerCase());
	            line = reader.readLine();
	        }
	    } catch (IOException ex) {
	    	System.err.println("Input file word list error!");
	    } finally {
	    	try {
	    		reader.close();
	            fis.close();
	    	} catch (Exception e) {
	    		System.err.println("File close error!");
	    	}
	    }
		
//		initializeTiles();
		initializeTilesTest();
		initializeSupply();
		initializeBoardTest();
	}
	
	public void initializeBoardTest() {
		play(new MyShuffletterTile('R'), new Position(4, -2));
		play(new MyShuffletterTile('Z'), new Position(16, -1));
		play(new MyShuffletterTile('S'), new Position(14, -2));
		play(new MyShuffletterTile('L'), new Position(15, -2));
		play(new MyShuffletterTile('O'), new Position(16, -2));
		play(new MyShuffletterTile('T'), new Position(17, -2));
		play(new MyShuffletterTile('Q'), new Position(3, -3));
		play(new MyShuffletterTile(), new Position(4, -3));
		play(new MyShuffletterTile('I'), new Position(5, -3));
		play(new MyShuffletterTile('P'), new Position(6, -3));
		play(new MyShuffletterTile('U'), new Position(14, -3));
		play(new MyShuffletterTile('O'), new Position(16, -3));
		play(new MyShuffletterTile('B'), new Position(4, -4));
		play(new MyShuffletterTile('U'), new Position(6, -4));
		play(new MyShuffletterTile('A'), new Position(8, -4));
		play(new MyShuffletterTile('I'), new Position(9, -4));
		play(new MyShuffletterTile('M'), new Position(10, -4));
		play(new MyShuffletterTile('L'), new Position(12, -4));
		play(new MyShuffletterTile('E'), new Position(13, -4));
		play(new MyShuffletterTile('N'), new Position(14, -4));
		play(new MyShuffletterTile('D'), new Position(15, -4));
		play(new MyShuffletterTile('P'), new Position(6, -5));
		play(new MyShuffletterTile('A'), new Position(7, -5));
		play(new MyShuffletterTile('T'), new Position(8, -5));
		play(new MyShuffletterTile('O'), new Position(10, -5));
		play(new MyShuffletterTile('E'), new Position(12, -5));
		play(new MyShuffletterTile('X'), new Position(7, -6));
		play(new MyShuffletterTile('W'), new Position(10, -6));
		play(new MyShuffletterTile('O'), new Position(11, -6));
		play(new MyShuffletterTile('A'), new Position(12, -6));
		play(new MyShuffletterTile('D'), new Position(13, -6));
		play(new MyShuffletterTile('H'), new Position(6, -7));
		play(new MyShuffletterTile('E'), new Position(7, -7));
		play(new MyShuffletterTile('N'), new Position(12, -7));
		play(new MyShuffletterTile('O'), new Position(13, -7));
		play(new MyShuffletterTile('T'), new Position(14, -7));
		play(new MyShuffletterTile('Y'), new Position(4, -7));
		play(new MyShuffletterTile('N'), new Position(3, -8));
		play(new MyShuffletterTile('O'), new Position(4, -8));
		play(new MyShuffletterTile('A'), new Position(6, -8));
		play(new MyShuffletterTile('L'), new Position(13, -8));
		play(new MyShuffletterTile('R'), new Position(4, -9));
		play(new MyShuffletterTile('I'), new Position(5, -9));
		play(new MyShuffletterTile('D'), new Position(6, -9));
		play(new MyShuffletterTile('V'), new Position(12, -9));
		play(new MyShuffletterTile('E'), new Position(13, -9));
		play(new MyShuffletterTile('T'), new Position(14, -9));
		play(new MyShuffletterTile('P'), new Position(1, -10));
		play(new MyShuffletterTile('A'), new Position(2, -10));
		play(new MyShuffletterTile('C'), new Position(3, -10));
		play(new MyShuffletterTile('E'), new Position(4, -10));
		play(new MyShuffletterTile('A'), new Position(14, -10));
		play(new MyShuffletterTile('H'), new Position(16, -10));
		play(new MyShuffletterTile('E'), new Position(1, -11));
		play(new MyShuffletterTile('S'), new Position(2, -11));
		play(new MyShuffletterTile('O'), new Position(14, -11));
		play(new MyShuffletterTile('V'), new Position(15, -11));
		play(new MyShuffletterTile('E'), new Position(16, -11));
		play(new MyShuffletterTile('R'), new Position(17, -11));
		play(new MyShuffletterTile('H'), new Position(1, -12));
		play(new MyShuffletterTile('X'), new Position(16, -12));
		play(new MyShuffletterTile('O'), new Position(1, -13));
		play(new MyShuffletterTile('A'), new Position(2, -13));
		play(new MyShuffletterTile('K'), new Position(3, -13));
//		play(new MyShuffletterTile('E'), new Position(1, 14));
		play(new MyShuffletterTile('D'), new Position(2, -14));
		play(new MyShuffletterTile('P'), new Position(4, -14));
		play(new MyShuffletterTile('T'), new Position(6, -14));
		play(new MyShuffletterTile('A'), new Position(0, -15));
		play(new MyShuffletterTile('N'), new Position(1, -15));
		play(new MyShuffletterTile('D'), new Position(2, -15));
		play(new MyShuffletterTile('R'), new Position(3, -15));
		play(new MyShuffletterTile('E'), new Position(4, -15));
		play(new MyShuffletterTile('I'), new Position(6, -15));
		play(new MyShuffletterTile('F'), new Position(7, -15));
		play(new MyShuffletterTile('R'), new Position(4, -16));
		play(new MyShuffletterTile('Y'), new Position(5, -16));
		play(new MyShuffletterTile('E'), new Position(6, -16));
	}
	
	private void initializeTilesTest () {
		bagTiles.add (new MyShuffletterTile('E'));
		bagTiles.add (new MyShuffletterTile());
	}
	
	/**
	 * Adds a ShuffletterModelListener to a list of listeners
	 */
	public void addListener(ShuffletterModelListener listener){
		this.listeners.add(listener);
	}
	
	/**
	 * Checks if all conditions for ending a round has been met
	 * Checks if supply is empty, checks if all board tiles are connected
	 * checks if board words are legal
	 */
	public void endRound () {
		//check if supply is empty
		if (getSupplyContents().size() != 0) {
			ShuffletterIllegalMoveEvent e = new ShuffletterIllegalMoveEvent ("Supply is not empty yet.");
			for (ShuffletterModelListener l : this.listeners) {
				l.illegalMoveMade(e);
			}
			return;
		}
		
		if (!checkConnectivity()) {
			ShuffletterIllegalMoveEvent e = new ShuffletterIllegalMoveEvent ("Tiles are not connected.");
			for (ShuffletterModelListener l : this.listeners) {
				l.illegalMoveMade(e);
			}
			return;
		}
		
		List<String> gridWords = traceWords(getTilePositions());
		Set<String> legalWords = getLegalWords();
		Set<Character> validChar = new HashSet<Character>();   // stores potential wild letter substitutions
		
		for (String s : gridWords) {
			boolean match = false;
			if (s.contains("?")) {
				if (validChar.isEmpty()) {
					validChar = generateValidWildChar(s);
				} else {
					validChar.retainAll(generateValidWildChar(s));	
				}
				if (!validChar.isEmpty()) {
					match = true;
					continue;
				}
			} else if (legalWords.contains(s.toLowerCase())) {
				match = true;
			}
			if (match == false) {
				ShuffletterIllegalMoveEvent e = new ShuffletterIllegalMoveEvent ("Illegal Word : " + s);
				for (ShuffletterModelListener l : this.listeners) {
					l.illegalMoveMade(e);
				}
				return;
			}
		}
		
		if (getBagCount() == 0 && getSupplyContents().size() == 0) {
			this.gameOver = true;
			for (ShuffletterModelListener l : this.listeners) {
				l.gameEnded();
			}
		} else {
			Random randGen = new Random();
			int ind = randGen.nextInt(getBagCount());
			supplyTiles.add(bagTiles.remove(ind));
			for (ShuffletterModelListener l : this.listeners) {
				l.roundEnded();
			}
		}
	}
	
	/*
	 * Function generates the set of valid characters for the wild tile
	 * so that the strings are in the list of legal words
	 */
	private Set<Character> generateValidWildChar(String wildStr) {
		Set<String> legalWords = getLegalWords();
		Set<Character> validChar = new HashSet<Character>();
		char curChar = 'a';
		
		String permutation = "";
		while (curChar != 'z') {
			permutation = wildStr.replaceAll("\\?", Character.valueOf(curChar).toString());
			if (legalWords.contains(permutation.toLowerCase())) {	
				validChar.add(Character.valueOf(curChar));
			}
			
			curChar += 1;
		}
			
		return validChar;
	}
	
	/*
	 * @return Boolean value if all tiles on board are connected
	 * Checks if every tile is being connected to another tile
	 * Implementing a union-find algorithm to check connectivity
	 */
	private boolean checkConnectivity() {
	
		Collection<Position> pos = getTilePositions();
		List<Set<Position>> sets = new ArrayList<Set<Position>>();
		
		for (Position p : pos) {
			Set<Position> mySet = new HashSet<Position>();
			mySet.add(p);
			sets.add(mySet);
		}
		
		for (Position p : pos) {
			int row = p.getY();
			int col = p.getX();
			Position left = new Position(col-1, row);
			Position right = new Position(col+1, row);
			Position up = new Position(col, row+1);
			Position down = new Position(col, row-1);
			Set<Position> leftSet = new HashSet<Position>();
			Set<Position> rightSet = new HashSet<Position>();
			Set<Position> upSet = new HashSet<Position>();
			Set<Position> downSet = new HashSet<Position>();
			Set<Position> pSet = new HashSet<Position>();
			
			for (Set<Position> set : sets) {
				if (set.contains(left) && !set.contains(p)) {
					leftSet = set;
				}
				if (set.contains(right) && !set.contains(p)) {
					rightSet = set;
				}
				if (set.contains(up) && !set.contains(p)) {
					upSet = set;
				}
				if (set.contains(down) && !set.contains(p)) {
					downSet = set;
				}
				if (set.contains(p)) {
					pSet = set;
				}
			}
			pSet.addAll(leftSet);
			pSet.addAll(rightSet);
			pSet.addAll(upSet);
			pSet.addAll(downSet);
			sets.remove(leftSet);
			sets.remove(rightSet);
			sets.remove(upSet);
			sets.remove(downSet);
		}
		
		return sets.size() == 1;   // union-find should return 1 set if all tiles are connected
	}

	/*
	 * @return List of words appearing on the game board
	 * checks all rows and columns containing a tile to
	 * get horizontal and vertical words
	 */
	private List<String> traceWords(Collection<Position> pos) {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		
		for (Position p : pos) {
			if (p.getX() < minX) {
				minX = p.getX();
			}
			if (p.getX() > maxX) {
				maxX = p.getX();
			}
			if (p.getY() < minY) {
				minY = p.getY();
			}
			if (p.getY() > maxY) {
				maxY = p.getY();
			}
		}
		
		List<String> boardWords = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		// loop through all rows, get all horizontal words
		// assuming all words must be at least 2 letters length
		for (int y = maxY; y >= minY; y--) {
			sb = new StringBuilder();
			for (int x = minX; x <= maxX; x++) {
				Position curPosition = new Position(x,y);
				if (this.gameGrid.containsKey(curPosition)) {
					if (this.gameGrid.get(curPosition).isWild()) {
						sb.append("?");
					} else {
						sb.append(this.gameGrid.get(curPosition).getLetter());	
					}
				} else {
					if (sb.length() > 1) {
						boardWords.add(sb.toString());
					}
					sb = new StringBuilder();
				}
			}
			if (sb.length() > 1) {
				boardWords.add(sb.toString());
			}
		}
		
		// loop through all columns, get all vertical words
		// assuming all words must be at least 2 letters length
		for (int x = minX; x <= maxX; x++) {
			sb = new StringBuilder();
			for (int y = maxY; y >= minY; y--) {
				Position curPosition = new Position(x, y);
				if (this.gameGrid.containsKey(curPosition)) {
					if (this.gameGrid.get(curPosition).isWild()) {
						sb.append("?");
					} else {
						sb.append(this.gameGrid.get(curPosition).getLetter());
					}
				} else {
					if (sb.length() > 1) {
						boardWords.add(sb.toString());
					}
					sb = new StringBuilder();
				}
			}
			if (sb.length() > 1) {
				boardWords.add(sb.toString());
			}
		}
		
		return boardWords;
	}

	/**
	 * @return String containing player's name
	 */
	public String getAuthorName () {
		return this.player;
	}
	
	/**
	 * @return integer number of tiles left in bag
	 */
	public int getBagCount () {
		return this.bagTiles.size();
	}
	
	/**
	 * @return Set of Strings, a list of all legal words
	 */
	public Set<String> getLegalWords () {
		return this.words;
	}
	
	/**
	 * @return List of ShuffletterTiles that remains in supply
	 */
	public List<ShuffletterTile> getSupplyContents () {
		return this.supplyTiles;
	}
	
	/**
	 * @return ShuffletterTile given its board position
	 */
	public ShuffletterTile getTile (Position p) {
		return this.gameGrid.get(p);
	}
	
	/**
	 * @return Collection of on-board tile positions
	 */
	public Collection<Position> getTilePositions () {
		return gameGrid.keySet();
	}
	
	/**
	 * @return Boolean value indicating if game is over
	 */
	public boolean isGameOver () {
		return this.gameOver;
	}
	
	/**
	 * Swaps the tiles if target location contains a tile
	 */
	public void move (Position source, Position target) {
		if (this.gameOver) {
			return;
		}
		if (getTile(source) == null){
			throw new IllegalArgumentException ("Source tile does not exist.");			
		} else {
			ShuffletterTile srcTile = getTile(source);
			ShuffletterTile tempTile;
			if (srcTile.isWild()) {
				tempTile = new MyShuffletterTile ();
			} else {
				char srcLetter = srcTile.getLetter();
				tempTile = new MyShuffletterTile (srcLetter);	
			}
			if (getTile(target) == null) {
				this.gameGrid.put(target, tempTile);
				this.gameGrid.remove(source);
			} else {
				this.gameGrid.put(source, getTile(target));
				this.gameGrid.put(target, tempTile);
			}
			// update listeners with tile moved event
			ShuffletterTileMovedEvent e = new ShuffletterTileMovedEvent(source, target, srcTile);			
			for (ShuffletterModelListener l : this.listeners) {
				l.tileMoved(e);
			}
		}
	}
	
	/*
	 * Checks for target location already contains a tile
	 */
	public void play (ShuffletterTile tile, Position target) {
//		if (!supplyTiles.contains(tile)) {
//			throw new IllegalArgumentException ("Play tile not in supply!");
//		} else 
		if (getTile(target) != null) {
			ShuffletterIllegalMoveEvent e = new ShuffletterIllegalMoveEvent ("There already is a tile at the target location.");
			for (ShuffletterModelListener l : this.listeners) {
				l.illegalMoveMade(e);
			}
		} else {
			this.gameGrid.put(target, tile);
			this.supplyTiles.remove(tile);
			// update listeners with tile played event
			ShuffletterTilePlayedEvent e = new ShuffletterTilePlayedEvent(target, tile);
			for (ShuffletterModelListener l : this.listeners) {
				l.tilePlayed(e);
			}
		}
	}
	
	public void removeListener (ShuffletterModelListener listener) {
		this.listeners.remove (listener);
	}
	
	private void initializeSupply () {
		int supplySize = 1;
		Random randGen = new Random();
		
		for (int i = 0; i < supplySize; i++) {
			int ind = randGen.nextInt(getBagCount());
			supplyTiles.add(bagTiles.remove(ind));
		}
	}
	
	private void initializeTiles () {
		// 2 of each of these letters
		for (int i = 0; i < 2; i++) {
			bagTiles.add (new MyShuffletterTile('J'));
			bagTiles.add (new MyShuffletterTile ('X'));
			bagTiles.add (new MyShuffletterTile ('K'));
			bagTiles.add (new MyShuffletterTile ('Z'));
			bagTiles.add (new MyShuffletterTile ('Q'));
		}

		// 3 of each of these letters
		for (int j = 0; j < 3; j++) {
			bagTiles.add (new MyShuffletterTile ('C'));
			bagTiles.add (new MyShuffletterTile ('M'));
			bagTiles.add (new MyShuffletterTile ('W'));
			bagTiles.add (new MyShuffletterTile ('F'));
			bagTiles.add (new MyShuffletterTile ('P'));
			bagTiles.add (new MyShuffletterTile ('Y'));
			bagTiles.add (new MyShuffletterTile ('B'));
			bagTiles.add (new MyShuffletterTile ('H'));
			bagTiles.add (new MyShuffletterTile ('V'));			
			bagTiles.add (new MyShuffletterTile ('D'));	 // 6 of these					
			bagTiles.add (new MyShuffletterTile ('D'));  // 6 of these	
			bagTiles.add (new MyShuffletterTile ('S'));	 // 6 of these					
			bagTiles.add (new MyShuffletterTile ('S'));	 // 6 of these								
			bagTiles.add (new MyShuffletterTile ('U'));	 // 6 of these					
			bagTiles.add (new MyShuffletterTile ('U'));	 // 6 of these								
		}

		// 4 of these
		for (int k = 0; k < 4; k++) {
			bagTiles.add (new MyShuffletterTile ('G'));
			bagTiles.add (new MyShuffletterTile ('L'));						
			bagTiles.add (new MyShuffletterTile ('N'));	 // 8 of these
			bagTiles.add (new MyShuffletterTile ('N'));	 // 8 of these					
		}
		bagTiles.add (new MyShuffletterTile ('L'));	// 5 of these		
		
		for (int l = 0; l < 9; l++) {
			bagTiles.add (new MyShuffletterTile ('T'));			
			bagTiles.add (new MyShuffletterTile ('R'));
			bagTiles.add (new MyShuffletterTile ('E'));  // 18 of these
			bagTiles.add (new MyShuffletterTile ('E'));  // 18 of these
			bagTiles.add (new MyShuffletterTile ('O'));					
		}
		bagTiles.add (new MyShuffletterTile ('O'));	// 11 of these				
		bagTiles.add (new MyShuffletterTile ('O'));	// 11 of these				
		
		// 12 of these
		for (int m = 0; m < 12; m++) {
			bagTiles.add (new MyShuffletterTile ('I'));					
			bagTiles.add (new MyShuffletterTile ('A'));					
		}
		bagTiles.add (new MyShuffletterTile ('A'));	 // 13 of these	
		
		bagTiles.add(new MyShuffletterTile ());  // a single wild tile
	}
	
}
