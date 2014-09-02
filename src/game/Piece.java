package game;

/**
 * A class representing a checkers piece
 * 
 * @author Benjamin Cohen-Wang
 */
public class Piece 
{
	/** The enum describing the state of this piece **/
	public static enum State {SOLDIER, KING};
	
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
}
