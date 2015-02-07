/*
* Name : Guannan Ren
* Email : gren3@jhu.edu
* Assignment : 1 part 2
*/

package edu.jhu.cs.gren3.oose.shuffletter.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.Scrollable;

import edu.jhu.cs.oose.fall2014.shuffletter.iface.Position;
import edu.jhu.cs.oose.fall2014.shuffletter.iface.ShuffletterTile;

/*
 * GridComponent acts as the common link between the playboard and
 * the supplyboard. Both are rendered using this class
 */
public class GridComponent extends JComponent implements Scrollable{

	private GridModel gridModel;
	private final int tileWidth = 55;
	private final int tileHeight = 55;
	private final Font letterFont = new Font("Serif", 1, 20);
	private int resizedTileWidth = 55;
	private int resizedTileHeight = 55;
	private Set<ChoiceListener> choiceListeners;
	private SelectedTileModel mySelectedTileModel;
	
	/*
	 * Initialization of a grid component
	 */
	public GridComponent(GridModel gridModel) {
        this.setBackground(Color.BLACK);
        this.gridModel = gridModel;
		this.mySelectedTileModel = new SelectedTileModel();
		this.choiceListeners = new HashSet<ChoiceListener>();
		
		// used to obtain current mouse point location
		// translates mouse position to tile grid system position
        this.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent mouseEvent) {
        		double mouseX = mouseEvent.getPoint().getX();
        		double mouseY = mouseEvent.getPoint().getY();
        		
        		int minX = gridModel.getMinX();
        		int maxY = gridModel.getMaxY();
        		int tileRow = (int) (mouseY/GridComponent.this.resizedTileHeight);
        		int tileCol = (int) (mouseX/GridComponent.this.resizedTileWidth);
        		tileRow = maxY - tileRow;
        		tileCol += minX;
        		
        		for (ChoiceListener listener : GridComponent.this.choiceListeners) {
        			listener.positionChosen(new Position(tileCol, tileRow));
        		}
        		
        		GridComponent.this.repaint();
            }
        });
        
        // let own grid model control when to re-render graphics
        this.gridModel.addListener(new GridListener() {
        	public void tileChanged() {
        		GridComponent.this.revalidate();  // updates scroll pane/bars dynamically
        		GridComponent.this.repaint();
        	}
        });
        
        // let an highlight event control when to re-render graphics
        this.mySelectedTileModel.addListener(new SelectedTileModelListener() {
            public void selectedTileChanged(Position newPosition)
            {
                GridComponent.this.repaint();
            }
        });        
	}
	
	public SelectedTileModel getSelectedTileModel() {
		return this.mySelectedTileModel;
	}
	
	public void addChoiceListener(ChoiceListener listener) {
		this.choiceListeners.add(listener);
	}
	
	public void removeChoiceListener(ChoiceListener listener) {
		this.choiceListeners.remove(listener);
	}
	
	public GridModel getGridModel() {
		return this.gridModel;
	}

	public Dimension getPreferredSize(){
		int gridHeight = this.gridModel.getHeight() * this.tileHeight;
		int gridWidth = this.gridModel.getWidth() * this.tileWidth;
		return new Dimension(gridWidth, gridHeight);
	}

	private void updateResizedTileSize() {
		int boardWidth = this.getWidth();
		int boardHeight = this.getHeight();
		this.resizedTileWidth = boardWidth/this.gridModel.getWidth();  //get resized tile width
		this.resizedTileHeight = boardHeight/this.gridModel.getHeight();  //get resized tile height
	}
	
	/*
	 * @param Graphics2D used to paint component
	 * controls the coloring of play board/supply tiles
	 * updates for highlighted tiles
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		updateResizedTileSize();
		int minX = this.gridModel.getMinX();
		int maxY = this.gridModel.getMaxY();
		int maxX = minX + this.gridModel.getWidth();
		int minY = maxY - this.gridModel.getHeight();
		
		for (int x = minX; x < maxX; x++) {
			for (int y = maxY; y > minY; y--) {
				int xOffset = x-minX;
				int yOffset = Math.abs(y-maxY);
				ShuffletterTile tile = this.gridModel.getTileAt(new Position(x, y));
				Rectangle rect = new Rectangle(new Point(xOffset*this.resizedTileWidth, yOffset*this.resizedTileHeight), 
						new Dimension(this.resizedTileWidth, this.resizedTileHeight));
				
				double letterWidthScale = 0.4;
				double letterHeightScale = 0.65;
				if (tile != null) {
					g2.setColor(this.gridModel.getFillColor());
					g2.fill(rect);
					g2.setColor(Color.BLACK);
					g2.setFont(letterFont);
					if (!tile.isWild()) {
						g2.drawString(Character.toString(tile.getLetter()), 
								(int) (xOffset*this.resizedTileWidth+letterWidthScale*this.resizedTileWidth), 
								(int) (yOffset*this.resizedTileHeight+letterHeightScale*this.resizedTileHeight));	
					}
				} else {
					g2.setColor(this.gridModel.getDefaultColor());
					g2.fill(rect);
				}

				int highlightBoundarySize = 3;
				int highlightBoundaryOffset = 2;
				int highlightStrokeSize = 5;
				if (this.getSelectedTileModel().getSelectedPosition() != null && 
						tile == this.gridModel.getTileAt(this.getSelectedTileModel().getSelectedPosition())) {
					g2.setColor(Color.ORANGE);
					g2.setStroke(new BasicStroke(highlightStrokeSize));
					Rectangle smallRect = new Rectangle(new Point(xOffset*this.resizedTileWidth+highlightBoundaryOffset, yOffset*this.resizedTileHeight+highlightBoundaryOffset), 
							new Dimension(this.resizedTileWidth-highlightBoundarySize, this.resizedTileHeight-highlightBoundarySize));
					g2.draw(smallRect);
				} else {
					g2.setColor(Color.BLACK);
					g2.setStroke(new BasicStroke());  // default stroke size
					g2.draw(rect);
				}
			}
		}
	}
	
    public Dimension getPreferredScrollableViewportSize() {
    	return this.getPreferredSize();
    }
    
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
    
    public int getScrollableUnitIncrement(Rectangle rectangle, int i, int j) {
    	return this.tileHeight;  // used for faster scrolling increments
    }
    
    public int getScrollableBlockIncrement(Rectangle rectangle, int i, int j) {
    	return this.tileHeight;   // used for faster scrolling increments
    }
    
}
