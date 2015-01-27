package game.board;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

import game.Game;
import game.board.node.Node;
import game.move.Move;
import game.piece.Piece;
import game.piece.Piece.Loyalty;
import game.piece.checkersPieces.King;
import game.player.Player.State;

/**
 * A superclass representing a general game board
 * 
 * @author Benjamin Cohen-Wang
 */
public abstract class Board
{
	/** The game this board belong to **/
	private Game game;
	
	/** The arraylist of nodes composing this grid **/
	private ArrayList<Node> nodes;
	 
	/**
	 * Parameterized constructor, initializes game to given game
	 */
	public Board(Game game)
	{
		this.game = game;
		this.nodes = new ArrayList<Node>();
	}
	
	/** 
	 * Draws this board
	 * 
	 * @param graphics
	 */
	public void draw(Graphics graphics)
	{
		for(Node node : nodes)
		{
			node.draw(graphics);
		}
	}
	
	/**
	 * Executes the given move
	 * 
	 * @param move	the move to be executed
	 */
	public abstract void executeMove(Move move);
	
	/**
	 * Initializes the array list of nodes of this game
	 */
	public abstract void initializeNodes();
	
	/**
	 * Loads the initial position of this board
	 * @throws IOException 
	 */
	public abstract void loadBoard() throws IOException;
	
	/**
	 * Gets the possible moves of the given loyalty
	 * 
	 * @param loyalty	the loyalty to be tested
	 */
	public ArrayList<Move> getPossibleMoves(Loyalty loyalty)
	{
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		
		for(Node node : nodes)
		{
			if(node.getPiece() != null && node.getPiece().getLoyalty() == loyalty)
			{
				pieces.add(node.getPiece());
			}
		}
		
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		
		for(Piece piece : pieces)
		{
			for(Move possibleMove : piece.getPossibleMoves())
			{
				possibleMoves.add(possibleMove);
			}
		}

		return possibleMoves;
	}
	
	/**
	 * Returns a clone of this board with the given game
	 * 
	 * @param game	the game to be cloned with
	 * @return	the cloned board
	 * @throws IOException 
	 */
	public abstract Board clone(Game game);
	
	/**
	 * @return the game of this board
	 */
	public Game getGame()
	{
		return game;
	}
	
	/**
	 * Sets this board to have the given game
	 * 
	 * @param game	the game to set this instance to
	 */
	public void setGame(Game game)
	{
		this.game = game;
	}
	
	/**
	 * @return the array list of nodes of this board
	 */
	public ArrayList<Node> getNodes()
	{
		return nodes;
	}
	
	/**
	 * @return the node width of this board
	 */
	public abstract int getNodeWidth();

	/**
	 * @return the node height of this board
	 */
	public abstract int getNodeHeight();
}
