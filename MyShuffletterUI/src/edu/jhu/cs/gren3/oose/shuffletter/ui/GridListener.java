/*
* Name : Guannan Ren
* Email : gren3@jhu.edu
* Assignment : 1 part 2
*/

package edu.jhu.cs.gren3.oose.shuffletter.ui;

/* used to communicate between grid model and grid component
 * that a tile position has changed and grid needs repainting
 */
public interface GridListener {

	public abstract void tileChanged();
	
}
