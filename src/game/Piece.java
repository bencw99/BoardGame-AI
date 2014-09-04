package game;

import java.util.ArrayList;

/**
 * A class representing a checkers piece
 * 
 * @author Benjamin Cohen-Wang
 */
public class Piece 
{
	/** The enum describing the state of this piece **/
	public static enum State {SOLDIER, KING};
	
	/** The enum describing the loyalty of this piece **/
	public static enum Loyalty {P1, P2};
	
	/** The board this piece belongs to **/
	private Board board;
	
	/** The node on the board that this piece belongs to **/
	private Node node;
	
	/** The state of this piece **/
	private State state;
	
	/**
	 * Default constructor
	 */
	public Piece()
	{
		state = State.SOLDIER;
	}
	
	/**
	 * Parameterized constructor, initializes board and node to given variables
	 * 
	 * @param board	the board of this instance
	 * @param node	the node of this instance on the board
	 */
	public Piece(Board board, Node node)
	{
		this();
		this.board = board;
		this.node = node;
	}
	
	/**
	 * Returns the possible nodes this piece can go to
	 * 
	 * @return	the array list of possible nodes this piece can go to
	 */
	public ArrayList<Node> getPossibleMoves()
	{
		return null;
	}
	
	/**
	 * Returns the possible nodes this piece can jump to
	 * 
	 * @return	the array list of possible nodes this piece can jump to
	 */
	public ArrayList<Node> getJumpMoves()
	{
		return null;
	}
	
	/**
	 * Adds this instance to the given board at the given node
	 * 
	 * @param board	the board this instance is added to
	 * @param node	the node this instance is adde to
	 */
	public void add(Board board, Node node)
	{
		this.board = board;
		this.node = node;
	}
}
