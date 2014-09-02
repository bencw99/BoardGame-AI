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
	
	/** The default length of the board grid **/
	public final static int DEFAULT_GRID_LENGTH = 8;
	
	/** The default number of pieces on the checkers grid **/
	public final static int DEFAULT_PIECE_NUM = 12;
	
	/**
	 * Default constructor, initializes grid to size 8 by 8
	 */
	public Board()
	{
		this(DEFAULT_GRID_LENGTH, DEFAULT_PIECE_NUM);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a square with given side length
	 * 
	 * @param length	the side length of the grid
	 * @param pieceNum	the number of pieces of each player
	 */
	public Board(int length, int pieceNum)
	{
		this(length, length, pieceNum);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a rectangle with given length and width
	 * 
	 * @param length	the side length of the grid
	 * @param pieceNum	the number of pieces of each player
	 */
	public Board(int length, int width, int pieceNum)
	{
		grid = new Node[length][width];
	}
}
