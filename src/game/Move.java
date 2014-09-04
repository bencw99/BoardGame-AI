package game;

/**
 * A class representing a move of a piece in a checkers game 
 * 
 * @author Benjamin Cohen-Wang
 */
public class Move
{
	/** The node this move starts at **/
	private Node start;
	
	/** The node this move ends at **/
	private Node end;
	
	/** The array list of nodes between the starting and ending nodes **/
	private ArrayList<Node> interNodes;
	
	/** The array list of pieces jumped in this move **/
	private ArrayList<Piece> jumped;
	
	/** The board this move is executed on **/
	private Board board;
	
	/**
	 * Parameterized Constructor, initializes start and end nodes, board, and interNodes
	 */
	public Move(Node start, Node end, ArrayList<Node> interNodes, Board board)
	{
		this.start = start;
		this.end = end;
		this.interNodes = interNodes;
		this.board = board;
	}
}
