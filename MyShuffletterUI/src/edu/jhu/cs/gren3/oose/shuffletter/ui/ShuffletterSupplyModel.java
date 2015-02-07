/*
* Name : Guannan Ren
* Email : gren3@jhu.edu
* Assignment : 1 part 2
*/

package edu.jhu.cs.gren3.oose.shuffletter.ui;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.jhu.cs.oose.fall2014.shuffletter.iface.NoOpShuffletterModelListener;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.Position;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterModel;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterTile;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterTilePlayedEvent;

/*
 * Model for handling supply board parameters,
 * communicates with ShuffletterModel
 */
public class ShuffletterSupplyModel extends AbstractGridModel{

	private ShuffletterModel model;
	private List<ShuffletterTile> supplyTiles;
	private Map<Position, ShuffletterTile> supplyGrid;
	private final int WIDTH = 2;
	private final int HEIGHT = 16;
	private final Color blankColor = Color.LIGHT_GRAY;
	private final Color fillColor = Color.YELLOW;
	
	/*
	 * Initialization of starting supply tiles
	 */
	public ShuffletterSupplyModel(ShuffletterModel model) {
        this.model = model;
        this.supplyGrid = new HashMap<Position, ShuffletterTile>();
        this.supplyTiles = this.model.getSupplyContents();

        this.updateSupplyGrid();
        
        this.model.addListener(new NoOpShuffletterModelListener() {
        	
        	// if not game over, add new supply tile to display, repaint
            public void roundEnded() {
            	if (!model.isGameOver()) {
                	ShuffletterSupplyModel.this.supplyTiles = model.getSupplyContents();
                	ShuffletterSupplyModel.this.updateSupplyGrid();
                	ShuffletterSupplyModel.this.updateListener();	// repaints supply tray
            	}
            }

            // after a successful tile placed onto the play board, remove the tile from supply
            public void tilePlayed(ShuffletterTilePlayedEvent event) {
            	ShuffletterTile tile = event.getTile();
            	
            	ShuffletterSupplyModel.this.supplyTiles = model.getSupplyContents();
            	ShuffletterSupplyModel.this.updateSupplyGrid();
            	ShuffletterSupplyModel.this.updateListener();  // repaints the supply tray
            }
        });
	}
	
	/*
	 * Initialize supply board positions for the
	 * Shuffletter Tiles
	 */
	private void updateSupplyGrid() {
		int row = 0;
		int col = 0;
        this.supplyGrid = new HashMap<Position, ShuffletterTile>();
        
		for (ShuffletterTile tile : supplyTiles) {
        	supplyGrid.put(new Position(col, row), tile);
        	if (row == -getHeight() + 1) {
        		col += 1;
        		row = 0;
        	} else {
        		row -= 1;
        	}
        }
	}
	
	public Color getDefaultColor() {
		return this.blankColor;
	}
	
	public Color getFillColor() {
		return this.fillColor;
	}
	
	public int getMinX() {
		return 0;
	}
	
	public int getMaxY() {
		return 0;
	}
	
	public int getWidth() {
		return this.WIDTH;
	}
	
	public int getHeight() {
		return this.HEIGHT;
	}
	
	/*
	 * @param Position of tile
	 * @return ShuffletterTile at the specific location
	 */
	public ShuffletterTile getTileAt (Position position) {
		return supplyGrid.get(position);
	}
	
	public String toString() {
		return "Supply Board Model";
	}
	
}
