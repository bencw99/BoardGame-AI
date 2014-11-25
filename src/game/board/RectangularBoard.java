package game.board;

import game.Game;

/**
 * A class representing a rectangular board
 * 
 * @author Benjamin Cohen-Wang
 */
public abstract class RectangularBoard extends Board
{
	/** The rectangular grid making up this rectangular board **/
	private Node[][] grid;
	
	/**
	 * Parameterized constructor, initializes board to given length, width, and games
	 * @param length the length of this rectangular board
	 * @param width the width of this rectangular board
	 * @param game the game of this rectangular board
	 */
	public RectangularBoard(int length, int width, Game game)
	{
		super(game);
		grid = new Node[length][width];
		initializeNodes();
		loadBoard();
	}
	
	
}
