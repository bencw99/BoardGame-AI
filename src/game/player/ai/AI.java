package game.player.ai;

import game.Game;
import game.Move;
import game.piece.Piece;
import game.piece.Piece.Loyalty;
import game.piece.Soldier;
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
	private static final int DEFAULT_MINIMAX_DEPTH = 9;
	
	/** The average number of moves at each point **/
	private static final int AVERAGE_MOVE_NUM = 7;
	
	/** The minimax depth of this ai instance **/
	private final int minimaxDepth;
	
	/** The minimax depth searched in the current turn **/
	private int currentMinimaxDepth;
	
	/**
	 * Parameterized constructor, initializes name, pieces, and loyalty
	 * 
	 * @param name	the name of this player
	 * @param loyalty	the loyalty of this player
	 * @param pieces	the pieces of this player
	 */
	public AI(String name, Loyalty loyalty, ArrayList<Piece> pieces)
	{
		this(name, loyalty, pieces, DEFAULT_MINIMAX_DEPTH);
		
		kingWorth = 5;
	}
	
	/**
	 * Parameterized constructor, initializes name, pieces, and loyalty
	 * 
	 * @param name	the name of this player
	 * @param loyalty	the loyalty of this player
	 * @param pieces	the pieces of this player
	 * @param minimaxDepth	the minimaxDepth of this ai
	 */
	public AI(String name, Loyalty loyalty, ArrayList<Piece> pieces, int minimaxDepth)
	{
		super(name, loyalty, pieces);
		this.minimaxDepth = minimaxDepth;
		
		kingWorth = 5;
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
		
		if(isDefeated())
		{
			return null;
		}
		
		Move[] possibleMovesArray = new Move[possibleMoves.size()];
		
		MinimaxNode[] possibleNextNodes = new MinimaxNode[possibleMoves.size()];
		
		MinimaxNode currentNode = new MinimaxNode(0, new Game(getPieces().get(0).getNode().getBoard().getGame()));
		
		currentMinimaxDepth = getAppropriateDepth(currentNode);
		
		System.out.println(currentMinimaxDepth);
		
		for(int i = 0; i < possibleMoves.size(); i ++)
		{
			possibleMovesArray[i] = possibleMoves.get(i);
			possibleNextNodes[i] = currentNode.getNextNode(possibleMoves.get(i));
		}
		
		double maxMinimaxVal = getMinimaxVal(possibleNextNodes[0], Integer.MIN_VALUE);
		
		ArrayList<Integer> maxMovesIndeces = new ArrayList<Integer>();
		maxMovesIndeces.add(0);
		
		for(int i = 1; i < possibleNextNodes.length; i ++)
		{
			double currentVal = getMinimaxVal(possibleNextNodes[i], maxMinimaxVal);
			
			if(currentVal > maxMinimaxVal)
			{
				maxMovesIndeces.clear();
				maxMovesIndeces.add(i);
				maxMinimaxVal = currentVal;
			}
			
			if(currentVal == maxMinimaxVal)
			{
				maxMovesIndeces.add(i);
			}
		}
		
		int random = (int)(maxMovesIndeces.size()*Math.random());
		
		return possibleMovesArray[maxMovesIndeces.get(random)];
	}
	
	/**
	 * Returns the move executed this turn
	 * 
	 * @return	the move to be executed this turn
	 * @throws IOException 
	 */
	public Move getThisTurnMoveThreaded() throws IOException
	{	
		ArrayList<Move> possibleMoves = getPossibleMoves();
		
		if(isDefeated())
		{
			return null;
		}
		
		Move[] possibleMovesArray = new Move[possibleMoves.size()];
		
		MinimaxNode[] possibleNextNodes = new MinimaxNode[possibleMoves.size()];
		
		MinimaxNode currentNode = new MinimaxNode(0, new Game(getPieces().get(0).getNode().getBoard().getGame()));
		
		currentMinimaxDepth = getAppropriateDepth(currentNode);
		
		for(int i = 0; i < possibleMoves.size(); i ++)
		{
			possibleMovesArray[i] = possibleMoves.get(i);
			possibleNextNodes[i] = currentNode.getNextNode(possibleMoves.get(i));
		}
		
		double maxMinimaxVal = getMinimaxVal(possibleNextNodes[0], Integer.MIN_VALUE);
		
		ArrayList<Integer> maxMovesIndeces = new ArrayList<Integer>();
		maxMovesIndeces.add(0);
		
		MinimaxValueFinder[] minimaxThreads = new MinimaxValueFinder[possibleNextNodes.length];
		
		for(int i = 0; i < minimaxThreads.length; i ++)
		{
			minimaxThreads[i] = new MinimaxValueFinder(possibleNextNodes[i]);
			minimaxThreads[i].start();
		}
		
		boolean threadsHaveFinished = false;
		
		while(!threadsHaveFinished)
		{
			threadsHaveFinished = true;
			
			for(int i = 0; i < minimaxThreads.length; i ++)
			{
				if(minimaxThreads[i].isAlive())
				{
					threadsHaveFinished = false;
				}
			}
		}

		double[] minimaxValues = new double[minimaxThreads.length];
		
		for(int i = 0; i < minimaxThreads.length; i ++)
		{
			minimaxValues[i] = minimaxThreads[i].getMinimaxValue();
		}
		
		for(int i = 0; i < minimaxValues.length; i ++)
		{	
			double currentVal = minimaxValues[i];
			
			if(currentVal > maxMinimaxVal)
			{
				maxMovesIndeces.clear();
				maxMovesIndeces.add(i);
				maxMinimaxVal = currentVal;
			}
			
			if(currentVal == maxMinimaxVal)
			{
				maxMovesIndeces.add(i);
			}
		}
		
		int random = (int)(maxMovesIndeces.size()*Math.random());
		
		return possibleMovesArray[maxMovesIndeces.get(random)];
	}
	
	/**
	 * Computes an appropriate depth for the minimax search executed this turn
	 * 
	 * @param node	the node at which the search begins
	 * @return	an appropriate depth from this node
	 */
	private int getAppropriateDepth(MinimaxNode node)
	{
		int numberOfMoves = node.getNextMoves().size();
		
		return (int)(Math.round(minimaxDepth*Math.log(numberOfMoves)/Math.log(AVERAGE_MOVE_NUM)));
	}
	
	/**
	 * Returns the minimax val of the given move
	 * 
	 * @param minimaxNode	the move whose minimax val is evaluated
	 * @return	the minimax val of the given move
	 * @throws IOException 
	 */
	private double getMinimaxVal(MinimaxNode node, double alphaVal) throws IOException
	{
		if(node.getMinimaxDepth() >= currentMinimaxDepth)
		{
		  	return functionVal(node);
		}
		
		ArrayList<Move> nextMoves = node.getNextMoves();
		
		if(node.getGame().getPlayers()[node.getGame().getTurn().getVal()].isDefeated())
		{
		  	return functionVal(node);
		}
		 
		double extreme;
		
		if(getLoyalty().getVal() == node.getGame().getTurn().getVal())
		{
			extreme = getMinimaxVal(node.getNextNode(nextMoves.get(0)), Integer.MIN_VALUE);
			
			for(Move nextMove : nextMoves)
			{	
				double maxCand = getMinimaxVal(node.getNextNode(nextMove), extreme);
				
				if(maxCand > extreme)
		  		{
		  			extreme = maxCand;
		  		}
				
				if(alphaVal <= maxCand)
				{
					break;
				}
			}
		} 
		else
		{
			extreme = getMinimaxVal(node.getNextNode(nextMoves.get(0)), Integer.MAX_VALUE);
			
			for(Move nextMove : nextMoves)
			{
				double minCand = getMinimaxVal(node.getNextNode(nextMove), extreme);

				if(minCand < extreme)
				{
					extreme = minCand;
				}
				
				if(alphaVal >= minCand)
				{
					break;
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
	private double getMinimaxVal(MinimaxNode node, String sameMethodSignaturePreventing) throws IOException
	{
		Stack<MinimaxNode> stack = new Stack<MinimaxNode>();
		
		stack.push(node);
		
		MinimaxNode currentNode = stack.peek();
		
		ArrayList<Move> nextMoves = currentNode.getNextMoves();
		
		double extreme = getMinimaxVal(currentNode.getNextNode(nextMoves.get(0)), 0);
		
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
	private double functionVal(MinimaxNode node)
	{
		Player[] players = node.getGame().getPlayers();
		double functionVal = 0;
		
		boolean hasWon = true;
		
		for(Player enemy : players)
		{
			if(enemy.getLoyalty() != this.getLoyalty())
			{
				if(!enemy.isDefeated())
				{
					hasWon = false;
				}
			}
		}
		
		if(hasWon)
		{
			return Double.MAX_VALUE;
		}
		
		if(isDefeated())
		{
			return Double.MIN_VALUE;
		}
		
		for(Player player : players)
		{
			if(player.getLoyalty() == getLoyalty())
			{
				for(Piece piece : player.getPieces())
				{
					if(piece instanceof Soldier)
						functionVal += piece.getWorth();
					else
						functionVal += kingWorth;
				}
			}
			else
			{
				for(Piece piece : player.getPieces())
				{
					if(piece instanceof Soldier)
						functionVal -= piece.getWorth();
					else
						functionVal -= kingWorth;
				}
			}
		}
		
		return functionVal;
	}
	
	/**
	 * A class implementing runnable allowing the threading of minmax evaluation
	 * 
	 * @author Benjamin Cohen-Wang
	 */
	public class MinimaxValueFinder extends Thread
	{
		/** The minimax node of this minimax value finder **/
		private MinimaxNode node;
		
		/** The minimax value of this minimax value finder **/
		private double minimaxValue;
		
		/**
		 * Parameterized constructor, initializes MinimaxValue instance to given node
		 * 
		 * @param node	the node to be set to
		 */
		public MinimaxValueFinder(MinimaxNode node)
		{
			this.node = node;
		}

		/**
		 * Evaluates the minimax val of the node of this instance
		 */
		public void run()
		{
			try
			{
				minimaxValue = getMinimaxVal(node, Integer.MIN_VALUE);
			} 
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
		}
		
		/**
		 * Returns the minimax value of this instance
		 * 
		 * @return	the minimax value of this instance
		 */
		public double getMinimaxValue()
		{
			return minimaxValue;
		}
	}
}
