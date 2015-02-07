/*
* Name : Guannan Ren
* Email : gren3@jhu.edu
* Assignment : 1 part 2
*/

package edu.jhu.cs.gren3.oose.shuffletter.ui;

import edu.jhu.cs.oose.fall2014.shuffletter.iface.Position;

/*
 * Listener used to alert when a selected tile changes
 */
public interface SelectedTileModelListener {

	public abstract void selectedTileChanged(Position newPosition);

}
