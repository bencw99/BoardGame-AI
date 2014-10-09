package game.player.ai;

import java.io.IOException;
import java.util.ArrayList;

import game.Game.Turn;
import game.Move;
import game.board.Board;
import game.board.Location;
import game.board.Node;
import game.piece.King;
import game.piece.Piece;
import game.piece.Soldier;
import game.player.Player;
/**
 * A class representing a node in the minimax algorithm
 * 
 * @author Benjamin Cohen-Wang
 */
public class MinimaxNode 
{
	/** The minimax depth of this minimax node **/
	private int minimaxDepth;
	
	/** The turn of this minimax node **/
	private Turn turn;
	
	/** The array of nodes composing the board grid **/
	private Node[][] grid;
	
	/** The array of Arraylists containing the pieces of this grid **/
	ArrayList<ArrayList<Piece>> playerPieces;
	
	/**
	 * Parameterized constructor, initializes board and minimax depth to given values
	 * 
	 * @param minimaxDepth	the minimaxDepth to be set to
	 * @param board			the board to be set to
	 * @throws IOException 
	 */
	public MinimaxNode(int minimaxDepth, Board board) throws IOException
	{
		this.minimaxDepth = minimaxDepth;
		this.turn = board.getGame().getTurn();
		
		Node[][] boardGrid = board.getGrid();
		grid = new Node[boardGrid.length][boardGrid[0].length];
		
		for(int i = 0; i < grid.length; i ++)
		{
			for(int j = 0; j < grid[0].length; j ++)
			{
				grid[i][j] = new Node(boardGrid[i][j]);
			}
		}
		
		playerPieces = new ArrayList<ArrayList<Piece>>();
		
		for(Player player : board.getGame().getPlayers())
		{
			ArrayList<Piece> pieces = new ArrayList<Piece>();
			
			for(Piece piece : player.getPieces())
			{
				Piece newPiece = null;
				
				if(piece.getWorth() == Soldier.SOLDIER_WORTH)
				{
					newPiece = new Soldier(player.getLoyalty());
				}
				
				if(piece.getWorth() == King.KING_WORTH)
				{
					newPiece = new King(player.getLoyalty());
				}
				
				Location loc = piece.getNode().getLoc();
				
				pieces.add(newPiece);
				grid[loc.getRow()][loc.getCol()].add(newPiece);
				newPiece.add(grid[loc.getRow()][loc.getCol()]);
			}
			
			playerPieces.add(pieces);
		}
	}
	
	/**
	 * Returns the loyalty associated with 
	 * 
	 * @return	the loyalty of this node
	 */
	public Turn getTurn()
	{
		return turn;
	}
	
	/**
	 * Returns the next node of the given move
	 * 
	 * @return	the minimax node resulting from the given move
	 */
	public MinimaxNode getNextNode(Move move)
	{
		return null;
	}
	
	/**
	 * Returns the possible next moves of this node
	 * 
	 * @return	the array list of edges from this node
	 */
	public ArrayList<Move> getNextMoves()
	{
		return null;
	}
	
	/**
	 * @return the minimaxDepth
	 */
	public int getMinimaxDepth() 
	{
		return minimaxDepth;
	}
}
