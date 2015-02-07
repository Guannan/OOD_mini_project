/*
* Name : Guannan Ren
* Email : gren3@jhu.edu
* Assignment : 1 part 2
*/

package edu.jhu.cs.gren3.oose.shuffletter.ui;

import java.util.HashSet;
import java.util.Set;

import edu.jhu.cs.oose.fall2014.shuffletter.iface.Position;

/*
 * Model used to keep track for the grid component 
 * in order to distinguish between a play, and a move
 */
public class SelectedTileModel {

	private Set<SelectedTileModelListener> listeners;
	private Position selectedPosition;
	
	public SelectedTileModel() {
        selectedPosition = null;
        listeners = new HashSet<SelectedTileModelListener>();
    }

    public void addListener(SelectedTileModelListener listener) {
        listeners.add(listener);
    }

    public void removeListener(SelectedTileModelListener listener) {
        listeners.remove(listener);
    }

    public Position getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(Position newPosition) {
        selectedPosition = newPosition;
        
        for (SelectedTileModelListener listener : listeners) {
        	listener.selectedTileChanged(newPosition);
        }
        
    }
    
}
