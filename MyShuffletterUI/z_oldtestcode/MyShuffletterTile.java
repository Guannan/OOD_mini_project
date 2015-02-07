/*
* Name : Guannan Ren
* Email : gren3@jhu.edu
* Assignment : 1 part 1
* Worked alone
*/

package edu.jhu.cs.gren3.oose.shuffletter.ui;

import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterTile;

public class MyShuffletterTile implements ShuffletterTile{

	private Character letter;
	
	// constructor for initializing a Wild tile
	public MyShuffletterTile () {
		this.letter = null;
	}
	
	// constructor for initializing all normal tiles
	public MyShuffletterTile (char letter) {
		this.letter = Character.valueOf(letter);
	}
	
	// @return Character the tile holds
	public char getLetter () {
		if (isWild()) {
			return '\u0000';
		} else {
			return this.letter.charValue();		
		}
	}
	
	// @return Boolean if tile contains the wild letter
	public boolean isWild () {
		return this.letter == null;
	}
}
