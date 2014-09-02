package game;

import java.util.ArrayList;

/**
 * A class representing a checkers board
 * 
 * @author Benjamin Cohen-Wang
 */
public class Board 
{
	/** The array of nodes composing the board grid **/
	private Node[][] grid;
	
	/** The array list containing the pieces on the board **/
	ArrayList<Piece> pieces;
	
	/**
	 * Default constructor, initializes grid to size 8 by 8
	 */
	public Board()
	{
		this(8);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a square with given side length
	 * 
	 * @param length	the side length of the grid
	 */
	public Board(int length)
	{
		this(length, length);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a rectangle with given length and width
	 * 
	 * @param length	the side length of the grid
	 */
	public Board(int length, int width)
	{
		grid = new Node[length][width];
	}
}
