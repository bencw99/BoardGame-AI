package game;

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
	
	/** The row this node is part of **/
	private int row;
	
	/** The column this node is part of **/
	private int col;
	
	/** The piece currently contained in this node **/
	private Piece piece;
	
	/** The color of this node **/
	private Color color;
	
	/**
	 * Paremeterized constructor, initializes Node location
	 */
	public Node(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Parameterized constructor, initializes color to given color, and location to given values
	 */
	public Node(int row, int col, Color color)
	{
		this(row, col);
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
	 * Returns the row of this node
	 * 
	 * @return the row of this node
	 */ 
	public int getRow()
	{
		return row;
	}
	
		/**
	 * Returns the col of this node
	 * 
	 * @return the col of this node
	 */ 
	public int getCol()
	{
		return col;
	}
}
