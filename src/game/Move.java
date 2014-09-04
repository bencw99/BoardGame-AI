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
}
