package game.board;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import game.Game;
import game.board.node.Location;
import game.board.node.Node;
import game.move.Move;
import game.piece.Piece;
import game.piece.Piece.Loyalty;
import game.piece.chessPieces.King;

/**
 * A class representing a chess board
 * 
 * @author Benjamin Cohen-Wang
 */
public class ChessBoard	extends RectangularBoard
{
	/** The default length of the board grid **/
	public static final int CHESS_GRID_LENGTH = 8;
	
	/** The default width of nodes of this board **/
	public static final int NODE_WIDTH =  30;
	
	/** The default height of nodes of this board **/
	public static final int NODE_HEIGHT =  30;
	
	/**
	 * Parameterized constructor, initializes grid to size 8 by 8, and game to given game
	 * 
	 * @param game	the game of this board
	 */
	public ChessBoard(Game game)
	{
		this(CHESS_GRID_LENGTH, game);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a square with given side length
	 * 
	 * @param length	the side length of the grid
	 * @param game	the game of this board
	 */
	public ChessBoard(int length, Game game)
	{
		this(length, length, game);
	}
	
	/**
	 * Parameterized constructor, initializes board to copy of given Board but with no game
	 * 
	 * @param board	the board whose copy is made
	 */
	public ChessBoard(ChessBoard board, Game game)
	{
		super(board, game);
	}
	
	/**
	 * Parameterized constructor, initializes grid to be a rectangle with given length and width
	 * 
	 * @param length	the side length of the grid
	 * @param game	the game of this board
	 */
	public ChessBoard(int length, int width, Game game)
	{
		super(length, width, game);
	}

	/**
	 * Initializes the array list of nodes of this game
	 */
	public void initializeNodes()
	{
		for(int i = 0; i < getGrid().length; i ++)
		{
			for(int j = 0; j < getGrid()[0].length; j ++)
			{
				Color color;
				
				if((i + j) % 2 == 0)
				{
					color = new Color(222, 185, 119);
				}
				else
				{
					color = new Color(255, 214, 140);
				}
				
				getGrid()[i][j] = new Node(new Location(i, j), this, color); 
			}
		}
	}

	/**
	 * Loads the board grid
	 */
	public void loadBoard()
	{
		int[] piecesLeft = new int[getGame().getPlayers().length];
		
		for(int i = 0; i < piecesLeft.length; i ++)
		{
			piecesLeft[i] = getGame().getPlayers()[i].getPieces().size();
		}

		for(int i = 0; i < getGrid().length; i ++)
		{
			for(int j = 0; j < getGrid()[0].length; j ++)
			{	
				if(piecesLeft[0] > 0)
				{
					put(getGame().getPlayers()[0].getPieces().get(piecesLeft[0] - 1), getGrid()[i][j].getLoc());
					getGame().getPlayers()[0].getPieces().get(piecesLeft[0] - 1).add(getGrid()[i][j]);
				}
						
				piecesLeft[0] --;
			}
		}
		
		for(int i = getGrid().length - 1; i >= 0; i --)
		{
			for(int j = 0; j < getGrid()[0].length; j ++)
			{	
				if(piecesLeft[1] > 0)
				{
					put(getGame().getPlayers()[1].getPieces().get(piecesLeft[1] - 1), getGrid()[i][j].getLoc());
					getGame().getPlayers()[1].getPieces().get(piecesLeft[1] - 1).add(getGrid()[i][j]);
				}
						
				piecesLeft[1] --;
			}
		}
	}
	
	/**
	 * Executes the given move
	 * 
	 * @param move	the move to be executed
	 * @throws IOException 
	 */
	public void executeMove(Move move)
	{	
		for(Node jumped : move.getJumped())
		{
			remove(jumped.getLoc());
		}
		
		move(move.getNodes().get(0).getLoc(), move.getNodes().get(move.getNodes().size() - 1).getLoc());
	}

	/**
	 * Gets the possible moves of the given loyalty
	 * 
	 * @param loyalty	the loyalty to be tested
	 */
	public ArrayList<Move> getPossibleMoves(Loyalty loyalty)
	{
		ArrayList<Move> possibleMoves = super.getPossibleMoves(loyalty);
		ArrayList<Move> realPossibleMoves = new ArrayList<Move>();
		
		for(Move possibleMove : possibleMoves)
		{
			boolean possible = true;
			
			Game nextGame = new Game(getGame());
			
			Board nextBoard = nextGame.getBoard();
			
			nextBoard.executeMove(possibleMove);
			
			for(Node node : nextBoard.getNodes())
			{
				Piece checkCandidate = null;
				
				if(node.getPiece() != null && node.getPiece().getLoyalty() != loyalty)
				{
					checkCandidate = node.getPiece();
				}
				
				if(checkCandidate == null)
				{
					continue;
				}
				else
				{
					for(Move move : checkCandidate.getPossibleMoves())
					{
						for(Node jumped : move.getJumped())
						{
							if(jumped.getPiece() instanceof King)
							{
								possible = false;
							}
						}
					}
				}
			}
			
			if(possible)
			{
				realPossibleMoves.add(possibleMove);
			}
		}
		
		return realPossibleMoves;
	}
	
	/**
	 * @return the node width of this board
	 */
	public int getNodeWidth()
	{
		return NODE_WIDTH;
	}

	/**
	 * @return the node height of this board
	 */
	public int getNodeHeight()
	{
		return NODE_HEIGHT;
	}
}
