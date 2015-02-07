/*
* Name : Guannan Ren
* Email : gren3@jhu.edu
* Assignment : 1 part 2
*/

package edu.jhu.cs.gren3.oose.shuffletter.ui;

import edu.jhu.cs.oose.fall2014.shuffletter.iface.Position;

// used to communicate from grid component to frame to ShuffletterModel
// that a new play or move move happened
public interface ChoiceListener {

    public abstract void positionChosen(Position pos);

}
