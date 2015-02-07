/*
* Name : Guannan Ren
* Email : gren3@jhu.edu
* Assignment : 1 part 2
*/

package edu.jhu.cs.gren3.oose.shuffletter.ui;

import java.io.IOException;

import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterModel;

/*
 * Class contains main to run the game!
 * Uses standard shuffletter model provided and
 * MyShuffletterFrame for gui
 */
public class MyShuffletterMain {
	
	public static void main(String[] args) throws IOException {
		ShuffletterModel model = new edu.jhu.cs.oose.fall2014.shuffletter.model.StandardShuffletterModel();
		MyShuffletterFrame gui = new MyShuffletterFrame(model);
	    gui.setLocationRelativeTo(null);
	    gui.setVisible(true);
	}
	
}
