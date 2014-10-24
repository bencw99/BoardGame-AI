package game.board;

import game.piece.King;
import game.piece.Piece;
import game.piece.Soldier;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A class representing a checkers board node
 * 
 * @author Benjamin Cohen-Wang
 */
public class Node 
{
	/** The board containing this node **/
	private Board board;
	
	/** The location of this node **/
	private Location loc;
	
	/** The piece currently contained in this node **/
	private Piece piece;
	
	/** The color of this node **/
	private Color color;
	
	/** The boolean determining whether or not this node is highlighted **/
	private boolean isHighlighted;
	
	private static final Color DEFAULT_HIGHLIGHT_COLOR = new Color(255, 255, 255);
	
	/**
	 * Parameterized constructor, initializes Node location
	 */
	public Node(Location loc, Board board)
	{
		this.loc = loc;
		this.board = board;
	}
	
	/**
	 * Parameterized constructor, initializes color to given color, and location to given values
	 */
	public Node(Location loc, Board board, Color color)
	{
		this(loc, board);
		this.color = color;
	}
	
	/**
	 * Parameterized constructor, initializes node to given node copy (has no board)
	 */
	public Node(Node node, Board board)
	{
		this.color = node.color;
		this.loc = new Location(node.loc);
		this.board = board;
		
		if(node.getPiece() == null)
		{
			this.piece = null;
		}
		else
		{
			if(node.getPiece() instanceof Soldier)
			{
				this.piece = new Soldier(node.getPiece(), this);
			}
			if(node.getPiece() instanceof King)
			{
				this.piece = new King(node.getPiece(), this);
			}
			this.board.getGame().getPlayers()[this.piece.getLoyalty().getVal()].add(this.piece);
		}
	}
	
	/**
	 * Draws this node
	 * 
	 * @param graphics	 the graphics object to be drawn on
	 */
	public void draw(Graphics graphics)
	{
		graphics.setColor(color);
		graphics.fillRect(getLoc().getCol()*Board.NODE_HEIGHT, getLoc().getRow()*Board.NODE_WIDTH, Board.NODE_HEIGHT, Board.NODE_WIDTH);
		
		if(isHighlighted)
		{
			graphics.setColor(DEFAULT_HIGHLIGHT_COLOR);
			graphics.drawRect(getLoc().getCol()*Board.NODE_HEIGHT, getLoc().getRow()*Board.NODE_WIDTH, Board.NODE_HEIGHT - 1, Board.NODE_WIDTH - 1);
		}
		
		if(piece != null)
		{
			piece.draw(graphics);
		}
	}
	
	public String toString()
	{
		return ("(" + loc.getRow() + ", " + loc.getCol() + ")");
	}
	
	/**
	 * Adds the given piece to this node
	 * 
	 * @param piece	the piece added to this node
	 */ 
	public void add(Piece piece)
	{
		this.piece = piece;
	}
	
	/**
	 * Returns the piece contained in this node
	 * 
	 * @return the piece contained in this node
	 */ 
	public Piece getPiece()
	{
		return piece;
	}
	
	/**
	 * Returns the location of this node
	 * 
	 * @return the location of this node
	 */ 
	public Location getLoc()
	{
		return loc;
	}
	
	/**
	 * Returns the board of this node
	 * 
	 * @return the board of this node
	 */ 
	public Board getBoard()
	{
		return board;
	}
	
	
	/**
	 * Returns the color of this node
	 * 
	 * @return the color of this node
	 */ 
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * Sets the highlight of this node
	 * 
	 * @param isHighlighted	the highlight state to be state
	 */
	public void setHighlighted(boolean isHighlighted)
	{
		this.isHighlighted = isHighlighted;
	}
}
