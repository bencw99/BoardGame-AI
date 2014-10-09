package game.board;

import game.piece.Piece;

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
	public Node(Node node)
	{
		this.color = node.color;
		this.loc = node.loc;
	}
	
	/**
	 * Draws this node
	 * 
	 * @param graphics	 the graphics object to be drawn on
	 */
	public void draw(Graphics graphics)
	{
		graphics.setColor(color);
		graphics.fillRect(getLoc().getRow()*Board.NODE_WIDTH, getLoc().getCol()*Board.NODE_HEIGHT, Board.NODE_WIDTH, Board.NODE_HEIGHT);
		
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
}
