/*
* Name : Guannan Ren
* Email : gren3@jhu.edu
* Assignment : 1 part 2
*/

package edu.jhu.cs.gren3.oose.shuffletter.ui;

import java.awt.Color;

import edu.jhu.cs.oose.fall2014.shuffletter.iface.Position;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterTile;

/*
 * interface which the supply grid model
 * and the play grid model are based on
 */
public interface GridModel {

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract int getMinX();
    
    public abstract int getMaxY();
    
    public abstract ShuffletterTile getTileAt(Position position);

    public abstract void addListener(GridListener gridListener);

    public abstract void removeListener(GridListener gridListener);
	
    public abstract Color getDefaultColor();
    
    public abstract Color getFillColor();

    public abstract String toString();

}
