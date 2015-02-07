/*
* Name : Guannan Ren
* Email : gren3@jhu.edu
* Assignment : 1 part 2
*/

package edu.jhu.cs.gren3.oose.shuffletter.ui;

import java.awt.Color;

import edu.jhu.cs.oose.fall2014.shuffletter.iface.NoOpShuffletterModelListener;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.Position;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterModel;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterTile;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterTileMovedEvent;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterTilePlayedEvent;

/*
 * Model for handling play board parameters,
 * communicates with ShuffletterModel
 */
public class ShuffletterBoardModel extends AbstractGridModel{
	
	private ShuffletterModel model;
	private final Color blankColor = Color.WHITE;
	private final Color fillColor = Color.YELLOW;
	private final static int START_WIDTH = 16;
	private final static int START_HEIGHT = 16;
	private int width;
	private int height;
	private Position topLeft;
	
	public ShuffletterBoardModel(ShuffletterModel model) {
        this.model = model;
        this.width = START_WIDTH;
        this.height = START_HEIGHT;
        this.topLeft = new Position(0,0);  // row 0 to 15, col 0 to -15 grid to start with
        
        this.model.addListener(new NoOpShuffletterModelListener() {
        	
        	// handles tile moved event by calling repaint on grid component
			public void tileMoved(ShuffletterTileMovedEvent event) {
            	Position source = event.getSource();
            	Position target = event.getTarget();
            	ShuffletterTile tile = event.getTile();
            	
            	ShuffletterBoardModel.this.updateBoundaryTiles(target);
            	ShuffletterBoardModel.this.updateListener();  // repaints board
            }
            
            // handles successful play event, repaint the board with new tile placed
            public void tilePlayed(ShuffletterTilePlayedEvent event) {
            	Position tilePosition = event.getTarget();
            	ShuffletterTile tile = event.getTile();
            	
            	ShuffletterBoardModel.this.updateBoundaryTiles(tilePosition);
            	ShuffletterBoardModel.this.updateListener();  // repaints board
            }
        });
	}
    
	/*
	 * @param Position of newly placed tile
	 * checks and update current grid size if tile placed
	 * near boundary
	 */
	private void updateBoundaryTiles(Position pos) {
    	int maxY = this.topLeft.getY();
		int minX = this.topLeft.getX();
		int minY = this.topLeft.getY() - getHeight() + 1;
		int maxX = this.topLeft.getX() + getWidth() - 1;
        
		if (pos.getX() == minX) {  //expand up by 1 row
			this.width += 1;
			this.topLeft = new Position(--minX, maxY);
		} else if (pos.getX() == maxX) {			//expand down by 1 row
			this.width += 1;
		}
		
		if (pos.getY() == maxY) {			// expand left by 1 col
			this.height += 1;
			this.topLeft = new Position(minX, ++maxY);
		} else if (pos.getY() == minY) {			// expand right by 1 col
			this.height += 1;
		}
	}
	
	public Color getDefaultColor() {
		return this.blankColor;
	}
	
	public Color getFillColor() {
		return this.fillColor;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public Position getTopLeft() {
		return this.topLeft;
	}
	
	public int getMinX() {
		return this.topLeft.getX();
	}
	
	public int getMaxY() {
		return this.topLeft.getY();
	}
	
	/*
	 * @param Position of tile
	 * @return ShuffletterTile at the specific location
	 */
	public ShuffletterTile getTileAt (Position position) {
		return this.model.getTile(position);
	}
	
	public String toString() {
		return "Play Board Model";
	}
	
}
