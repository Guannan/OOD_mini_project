/*
* Name : Guannan Ren
* Email : gren3@jhu.edu
* Assignment : 1 part 2
*/

package edu.jhu.cs.gren3.oose.shuffletter.ui;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterTile;

/*
 * Abstract class of GridModel that implements
 * add/update/remove listener methods for children classes
 */
public abstract class AbstractGridModel implements GridModel{

	private Set<GridListener> listeners;

	public AbstractGridModel() {
        this.listeners = new HashSet<GridListener>();
    }

    public void addListener(GridListener gridListener) {
        this.listeners.add(gridListener);
    }

    public void removeListener(GridListener gridListener) {
        this.listeners.remove(gridListener);
    }
    
    public ShuffletterTile getTileAt(Point position) {
        return getTileAt(position);
    }
    
    public void updateListener() {
    	for (GridListener listener : listeners) {
    		listener.tileChanged();
    	}
    }
}
