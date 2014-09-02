package game;

/**
 * A class representing a checkers piece
 * 
 * @author Benjamin Cohen-Wang
 */
public class Piece 
{
	/** The enum describing the state of this piece **/
	private static enum State {SOLDIER, KING};
	
	/** The board this piece belongs to **/
	private Board board;
	
	/** The node on the board that this piece belongs to **/
	private Node node;
	
	/** The state of this piece **/
	private State state;
	
}
