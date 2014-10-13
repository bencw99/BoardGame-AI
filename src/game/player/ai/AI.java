package game.player.ai;

import game.Game;
import game.Move;
import game.piece.Piece;
import game.piece.Piece.Loyalty;
import game.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * A class representing a Human player associated with a checkers game
 * 
 * @author Benjamin Cohen-Wang
 */
public class AI extends Player
{
	/** The depth of the minimax search **/
	private static final int MINIMAX_DEPTH = 5;
	
	/**
	 * Parameterized constructor, initializes name, pieces, and loyalty
	 * 
	 * @param name	the name of this player
	 * @param loyalty	the loyalty of this player
	 * @param pieces	the pieces of this player
	 */
	public AI(String name, Loyalty loyalty, ArrayList<Piece> pieces)
	{
		super(name, loyalty, pieces);
	}

	/**
	 * Returns the move executed this turn
	 * 
	 * @return	the move to be executed this turn
	 * @throws IOException 
	 */
	public Move getThisTurnMove() throws IOException
	{	
		ArrayList<Move> possibleMoves = getPossibleMoves();
		
		Move[] possibleMovesArray = new Move[possibleMoves.size()];
		
		MinimaxNode[] possibleNextNodes = new MinimaxNode[possibleMoves.size()];
		
		MinimaxNode currentNode = new MinimaxNode(0, new Game(getPieces().get(0).getNode().getBoard().getGame()));
		
		for(int i = 0; i < possibleMoves.size(); i ++)
		{
			possibleMovesArray[i] = possibleMoves.get(i);
			possibleNextNodes[i] = currentNode.getNextNode(possibleMoves.get(i));
		}
		
		double maxMinimaxVal = getMinimaxVal(possibleNextNodes[0]);
		int maxMinimaxIndex = 0;
		
		for(int i = 1; i < possibleNextNodes.length; i ++)
		{
			double currentVal = getMinimaxVal(possibleNextNodes[i]);
			
			if(currentVal > maxMinimaxVal)
			{
				maxMinimaxVal = currentVal;
				maxMinimaxIndex = i;
			}
		}
		
		return possibleMovesArray[maxMinimaxIndex];
	}
	
	/**
	 * Returns the minimax val of the given move
	 * 
	 * @param minimaxNode	the move whose minimax val is evaluated
	 * @return	the minimax val of the given move
	 * @throws IOException 
	 */
	private double getMinimaxVal(MinimaxNode node) throws IOException
	{
		if(node.getMinimaxDepth() >= MINIMAX_DEPTH)
		{
		  	return functionVal(node);
		}
		
		ArrayList<Move> nextMoves = node.getNextMoves();
		
		double extreme = getMinimaxVal(node.getNextNode(nextMoves.get(0)));
		  
		if(getLoyalty().getVal() == node.getGame().getTurn().getVal())
		{
			for(Move nextMove : nextMoves)
			{
				double maxCand = getMinimaxVal(node.getNextNode(nextMove));
				
				if(maxCand > extreme)
		  		{
		  			extreme = maxCand;
		  		}
			}
		} 
		else
		{
			for(Move nextMove : nextMoves)
			{
				double minCand = getMinimaxVal(node.getNextNode(nextMove));
					
				if(minCand < extreme)
				{
					extreme = minCand;
				}
			}
		}
		
		return extreme;
	}
	
	/**
	 * Returns the minimax val of the given node
	 * 
	 * @param node	the minimaxNode whose minimax value is evaluated
	 * @throws IOException 
	 */
	private double getMinimaxVal(MinimaxNode node, int sameMethodSignaturePreventing) throws IOException
	{
		Stack<MinimaxNode> stack = new Stack<MinimaxNode>();
		
		stack.push(node);
		
		MinimaxNode currentNode = stack.peek();
		
		ArrayList<Move> nextMoves = currentNode.getNextMoves();
		
		double extreme = getMinimaxVal(currentNode.getNextNode(nextMoves.get(0)));
		
		while(!stack.isEmpty())
		{
			currentNode = stack.pop();
			
			nextMoves = currentNode.getNextMoves();
			
			if(getLoyalty().getVal() == currentNode.getGame().getTurn().getVal())
			{
				for(Move nextMove : nextMoves)
				{
					//Evaluate minimax of nextMove
					//Set extreme to max
				}
			} 
			else
			{
				for(Move nextMove : nextMoves)
				{
					//Evaluate minimax of nextMove
					//Set extreme to min
				}
			}
		}
		
		return extreme;
	}
	
	/**
	 * Returns the function value of the given move
	 * 
	 * @param move	the move whose function value is evaluated
	 * @return	the function value of the given move
	 */
	private static double functionVal(MinimaxNode node)
	{
		Player[] players = node.getGame().getPlayers();
		double functionVal = 0;
		
		for(Player player : players)
		{
			if(player.getLoyalty().getVal() == node.getGame().getTurn().getVal())
			{
				for(Piece piece : player.getPieces())
				{
					functionVal += piece.getWorth();
				}
			}
			else
			{
				for(Piece piece : player.getPieces())
				{
					functionVal -= piece.getWorth();
				}
			}
		}
		
		return functionVal;
	}
}
