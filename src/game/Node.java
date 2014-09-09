package game;

import game.piece.Piece;

import java.awt.Color;

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
	public Node(Location loc)
	{
		this.loc = loc;
	}
	
	/**
	 * Parameterized constructor, initializes color to given color, and location to given values
	 */
	public Node(Location loc, Color color)
	{
		this(loc);
		this.color = color;
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
