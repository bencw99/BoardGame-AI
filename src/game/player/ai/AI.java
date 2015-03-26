package game.player.ai;

import game.Game;
import game.board.CheckersBoard;
import game.board.node.Node;
import game.move.Move;
import game.piece.Piece;
import game.piece.Piece.Loyalty;
import game.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
/**
 * A class representing a Human player associated with a game
 * 
 * @author Benjamin Cohen-Wang
 */
public class AI extends Player
{	
	/** The minimax depth of this ai instance **/
	private final int minimaxDepth;
	
	/** The class describing the worths of pieces **/
	private HashMap<Class<? extends Piece>, Double> worthMap;
	
	/** The depth of the minimax search **/
	private static final int DEFAULT_MINIMAX_DEPTH = 12;
	
	/**
	 * Parameterized constructor, initializes name, pieces, and loyalty
	 * 
	 * @param name	the name of this player
	 * @param loyalty	the loyalty of this player
	 */
	public AI(String name, Loyalty loyalty, Game game)
	{
		this(name, loyalty, game, DEFAULT_MINIMAX_DEPTH);
	}
	
	/**
	 * Parameterized constructor, initializes name, pieces, and loyalty
	 * 
	 * @param name	the name of this player
	 * @param loyalty	the loyalty of this player
	 * @param minimaxDepth	the minimaxDepth of this ai
	 */
	public AI(String name, Loyalty loyalty, Game game, int minimaxDepth)
	{
		super(name, loyalty, game);
		this.minimaxDepth = minimaxDepth;
	}

	/**
	 * Returns the move executed this turn
	 * 
	 * @return	the move to be executed this turn
	 * @throws IOException 
	 */
	public Move getThisTurnMove() throws IOException
	{	
		/*
		 * Idea: Use the minimax algorithm evaluation function to teach board game playing and comments on moves made
		 */
		
		double initialTime = System.nanoTime();
		
		ArrayList<Move> possibleMoves = getPossibleMoves();
		
		if(isDefeated())
		{
			return null;
		}
		
		Move[] possibleMovesArray = new Move[possibleMoves.size()];
		
		MinimaxNode[] possibleNextNodes = new MinimaxNode[possibleMoves.size()];
		
		MinimaxNode currentNode = new MinimaxNode(0, new Game(getGame()), null, null, 0, true);
		
		for(int i = 0; i < possibleMoves.size(); i ++)
		{	
			possibleMovesArray[i] = possibleMoves.get(i);
			possibleNextNodes[i] = currentNode.getNextNode(possibleMoves.get(i));
		}
		
		double maxMinimaxVal = Integer.MIN_VALUE;
		
		ArrayList<Integer> maxMovesIndeces = new ArrayList<Integer>();
		
		for(int i = 0; i < possibleNextNodes.length; i ++)
		{
			double currentVal = getMinimaxVal(possibleNextNodes[i], maxMinimaxVal, Integer.MAX_VALUE, functionVal(currentNode));
			
			if(currentVal > maxMinimaxVal)
			{
				maxMovesIndeces.clear();
				maxMovesIndeces.add(i);
				maxMinimaxVal = currentVal;
			}
			else if(currentVal == maxMinimaxVal)
			{
				maxMovesIndeces.add(i);
			}
			
			possibleNextNodes[i].setValue(currentVal);
			
			System.out.println("Move: " + i + ": " + possibleMovesArray[i] + " with value " + currentVal);
		}
	
		System.out.print("Best moves: ");
		
		for(int index : maxMovesIndeces)
		{
			System.out.print(index + " ");
		}
		
		System.out.println();
		
		int random = (int)(maxMovesIndeces.size()*Math.random());
		
		if(getBoard() instanceof CheckersBoard)
		{
			random = 0;
		}
		
		System.out.println("Move chosen: " + maxMovesIndeces.get(random) + " with value " + possibleNextNodes[maxMovesIndeces.get(random)].getValue());
		System.out.println("Time taken: " + (System.nanoTime() - initialTime)/1000000000 + " seconds");
		
		return possibleMovesArray[maxMovesIndeces.get(random)];
	}
	
	/**
	 * Returns the move executed this turn
	 * 
	 * @return	the move to be executed this turn
	 * @throws IOException 
	 */
	public Move getThisTurnMove(long time) throws IOException
	{	
		long initialTime = System.currentTimeMillis();
		
		MinimaxNode currentNode = new MinimaxNode(0, new Game(getGame()), null, null, 0, true);
		
		SearchTree tree = new SearchTree(currentNode, this);
		
		if(isDefeated())
		{
			return null;
		}

		while(System.currentTimeMillis() - initialTime < time)
		{
			System.out.println("Time: " + (System.currentTimeMillis() - initialTime) + " Depth: " + tree.getDepth());
			tree.increaseDepth();
		}

		ArrayList<MinimaxSuperNode> nextNodes = currentNode.getChildren();
		
		ArrayList<Integer> maxMovesIndeces = new ArrayList<Integer>();
		
		double maxMinimaxVal = Integer.MIN_VALUE;
		
		for(int i = 0; i < nextNodes.size(); i ++)
		{
			double currentVal = nextNodes.get(i).getValue();
			
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
		
		return ((MinimaxNode) nextNodes.get(maxMovesIndeces.get(random))).getMove();
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
		
		MinimaxNode currentNode = new MinimaxNode(0, new Game(getGame()), null, null, 0, true);
		
		for(int i = 0; i < possibleMoves.size(); i ++)
		{
			possibleMovesArray[i] = possibleMoves.get(i);
			possibleNextNodes[i] = currentNode.getNextNode(possibleMoves.get(i));
		}
		
		double maxMinimaxVal = getMinimaxVal(possibleNextNodes[0], Integer.MIN_VALUE, Integer.MAX_VALUE);
		
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
//	private int getAppropriateDepth(MinimaxNode node)
//	{
//		return minimaxDepth;
//	}
	
	/**
	 * Returns the minimax val of the given move
	 * 
	 * @param minimaxNode	the move whose minimax val is evaluated
	 * @param alphaVal	the alpha value of this minimax evaluation
	 * @param betaVal	the beta value of this minimax evaluation
	 * @param parentVal	the function value of the paret of this node
	 * @return	the minimax val of the given move
	 * @throws IOException 
	 */
	private double getMinimaxVal(MinimaxNode node, double alphaVal, double betaVal, double parentVal) throws IOException
	{	
		double functionVal = functionVal(parentVal, node.getMove());
		
		if(node.getMinimaxDepth() >= minimaxDepth)
		{
		  	return functionVal;
		}
		
		ArrayList<Move> nextMoves = node.getNextMoves();
		
		if(node.getGame().getPlayers()[node.getGame().getTurn().getVal()].isDefeated())
		{
		  	return functionVal(node);
		}
		
		boolean thisPlayersTurn = getLoyalty().getVal() == node.getGame().getTurn().getVal();
		
		double extreme;
		
//		heuristicSort(nextMoves, node, thisPlayersTurn, functionVal);
		
		if(thisPlayersTurn)
		{
			extreme = Integer.MIN_VALUE;
			
			for(Move nextMove : nextMoves)
			{	
				double maxCand = getMinimaxVal(node.getNextNode(nextMove), alphaVal, betaVal, functionVal);
				
				extreme = Math.max(extreme, maxCand);
				
				alphaVal = Math.max(alphaVal, maxCand);
				
				if(alphaVal >= betaVal)
				{
					break;
				}
			}
		} 
		else
		{
			extreme = Integer.MAX_VALUE;
			
			for(Move nextMove : nextMoves)
			{
				double minCand = getMinimaxVal(node.getNextNode(nextMove), alphaVal, betaVal, functionVal);

				extreme = Math.min(extreme, minCand);
				
				betaVal = Math.min(betaVal, minCand);
				
				if(alphaVal >= betaVal)
				{
					break;
				}
			}
		}
		
		return extreme;
	}
	
	/**
	 * Returns the minimax val of the given move
	 * 
	 * @param minimaxNode	the move whose minimax val is evaluated
	 * @param alphaVal	the alpha value of this minimax evaluation
	 * @param betaVal	the beta value of this minimax evaluation
	 * @return	the minimax val of the given move
	 * @throws IOException 
	 */
	private double getMinimaxVal(MinimaxNode node, double alphaVal, double betaVal) throws IOException
	{	
		if(node.getMinimaxDepth() >= minimaxDepth)
		{
		  	return functionVal(node);
		}
		
		ArrayList<Move> nextMoves = node.getNextMoves();
		
		if(node.getGame().getPlayers()[node.getGame().getTurn().getVal()].isDefeated())
		{
		  	return functionVal(node);
		}
		
		boolean thisPlayersTurn = getLoyalty().getVal() == node.getGame().getTurn().getVal();
		
		double extreme;
		
//		heuristicSort(nextMoves, node, thisPlayersTurn);
		
		if(thisPlayersTurn)
		{
			extreme = Integer.MIN_VALUE;
			
			for(Move nextMove : nextMoves)
			{	
				double maxCand = getMinimaxVal(node.getNextNode(nextMove), alphaVal, betaVal);
				
				extreme = Math.max(extreme, maxCand);
				
				alphaVal = Math.max(alphaVal, maxCand);
				
				if(alphaVal >= betaVal)
				{
					break;
				}
			}
		} 
		else
		{
			extreme = Integer.MAX_VALUE;
			
			for(Move nextMove : nextMoves)
			{
				double minCand = getMinimaxVal(node.getNextNode(nextMove), alphaVal, betaVal);

				extreme = Math.min(extreme, minCand);
				
				betaVal = Math.min(betaVal, minCand);
				
				if(alphaVal >= betaVal)
				{
					break;
				}
			}
		}
		
		return extreme;
	}
	
	/**
	 * Performs a heuristic sort on the array list of moves
	 *
	 * @param moves	the moves to be heuristically sorted
	 * @throws IOException 
	 */
	private ArrayList<Move> heuristicSort(ArrayList<Move> moves, MinimaxNode node, boolean thisPlayersTurn) throws IOException
	{
		ArrayList<MinimaxNode> nextNodes = new ArrayList<MinimaxNode>();
		
		double alphaVal = Integer.MIN_VALUE;
		
		double betaVal = Integer.MAX_VALUE;
		
		double extremeMoveVal = thisPlayersTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		
		for(Move move : moves)
		{
			MinimaxNode nextNode = node.getNextNode(move);
			nextNode.setMinimaxDepth(nextNode.getMinimaxDepth() + 12);
			double candidateVal = getMinimaxVal(nextNode, alphaVal, betaVal);
			
			if(thisPlayersTurn)
			{
				extremeMoveVal = Math.max(extremeMoveVal, candidateVal);
				alphaVal = Math.max(alphaVal, extremeMoveVal);
			}
			else
			{
				extremeMoveVal = Math.min(extremeMoveVal, candidateVal);
				betaVal = Math.max(betaVal, extremeMoveVal);
			}
			
			nextNode.setValue(candidateVal);
			
			nextNodes.add(nextNode);
		}
		
		if(!thisPlayersTurn)
		{
			nextNodes = quickSort(nextNodes, 0, nextNodes.size() - 1);
		}
		else
		{
			nextNodes = reverseQuickSort(nextNodes, 0, nextNodes.size() - 1);
		}
		
		for(int i = 0; i < moves.size(); i ++)
		{
			moves.set(i, nextNodes.get(i).getMove());
		}
		
		return moves;
	}
	
	/**
	 * Performs a heuristic sort on the array list of moves
	 *
	 * @param moves	the moves to be heuristically sorted
	 * @throws IOException 
	 */
	private ArrayList<Move> heuristicSort(ArrayList<Move> moves, MinimaxNode node, boolean thisPlayersTurn, double parentVal) throws IOException
	{
		ArrayList<MinimaxNode> nextNodes = new ArrayList<MinimaxNode>();
		
		for(Move move : moves)
		{
			MinimaxNode nextNode = node.getNextNode(move);
			double candidateVal = functionVal(parentVal, move);
			
			nextNode.setValue(candidateVal);
			
			nextNodes.add(nextNode);
		}
		
		if(!thisPlayersTurn)
		{
			nextNodes = quickSort(nextNodes, 0, nextNodes.size() - 1);
		}
		else
		{
			nextNodes = reverseQuickSort(nextNodes, 0, nextNodes.size() - 1);
		}
		
		for(int i = 0; i < moves.size(); i ++)
		{
			moves.set(i, nextNodes.get(i).getMove());
		}
		
		return moves;
	}
	
	
	/**
	 * Sorts the given array list of minimax nodes
	 * 
	 * @param nodes	the array list to be sorted
	 * @param start	the starting index
	 * @param end	the end index
	 * @return	the sorted array list
	 */
	private ArrayList<MinimaxNode> quickSort(ArrayList<MinimaxNode> nodes, int start, int end)
	{
		if(start <= end)
		{
			return nodes;
		}
		
		MinimaxNode pivotNode = nodes.get(end);
		double pivotVal = pivotNode.getValue();
		
		int delimeterIndex = start;
		
		for(int i = start; i < end; i ++)
		{
			MinimaxNode currentNode = nodes.get(i);
			
			if(currentNode.getValue() < pivotVal)
			{
				nodes.set(i, nodes.get(delimeterIndex));
				nodes.set(delimeterIndex, currentNode);
				
				delimeterIndex ++;
			}
		}
		
		nodes.set(end, nodes.get(delimeterIndex));
		nodes.set(delimeterIndex, pivotNode);
		
		quickSort(nodes, start, delimeterIndex - 1);
		quickSort(nodes, delimeterIndex + 1, end);
		
		return nodes;
	}
	
	/**
	 * Reverse sorts the given array list of minimax nodes
	 * 
	 * @param nodes	the array list to be sorted
	 * @param start	the starting index
	 * @param end	the end index
	 * @return	the sorted array list
	 */
	private ArrayList<MinimaxNode> reverseQuickSort(ArrayList<MinimaxNode> nodes, int start, int end)
	{
		if(start <= end)
		{
			return nodes;
		}
		
		MinimaxNode pivotNode = nodes.get(end);
		double pivotVal = pivotNode.getValue();
		
		int delimeterIndex = start;
		
		for(int i = start; i < end; i ++)
		{
			MinimaxNode currentNode = nodes.get(i);
			
			if(currentNode.getValue() > pivotVal)
			{
				nodes.set(i, nodes.get(delimeterIndex));
				nodes.set(delimeterIndex, currentNode);
				
				delimeterIndex ++;
			}
		}
		
		nodes.set(end, nodes.get(delimeterIndex));
		nodes.set(delimeterIndex, pivotNode);
		
		reverseQuickSort(nodes, start, delimeterIndex - 1);
		reverseQuickSort(nodes, delimeterIndex + 1, end);
		
		return nodes;
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
		
//		HashSet<Node> attackedNodes = new HashSet<Node>();
//		HashSet<Node> enemyAttackedNodes = new HashSet<Node>();
		
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
			return Integer.MAX_VALUE;
		}
		
		if(isDefeated())
		{
			return Integer.MIN_VALUE;
		}
		
		for(Node gridNode : node.getGame().getBoard().getNodes())
		{
			Piece piece = gridNode.getPiece();
			
			if(piece != null)
			{
				if(piece.getLoyalty() == getLoyalty())
				{
					functionVal += piece.getWorth();
					
//					ArrayList<Move> possibleMoves = piece.getPossibleMoves();
//					
//					for(Move possibleMove : possibleMoves)
//					{
//						attackedNodes.add(possibleMove.getNodes().get(possibleMove.getNodes().size() - 1));
//					}
				}
				else
				{
					functionVal -= piece.getWorth();
					
//					ArrayList<Move> possibleMoves = piece.getPossibleMoves();
//					
//					for(Move possibleMove : possibleMoves)
//					{
//						enemyAttackedNodes.add(possibleMove.getNodes().get(possibleMove.getNodes().size() - 1));
//					}
				}
			}
		}
		
//		for(Node attackedNode : attackedNodes)
//		{
//			functionVal += 0.2/(Math.abs(attackedNode.getLoc().getCol() - getBoard().getGrid()[0].length/2) + 1);
//		}
//		
//		for(Node enemyAttackedNode : enemyAttackedNodes)
//		{
//			functionVal -= 0.2/(Math.abs(enemyAttackedNode.getLoc().getCol() - getBoard().getGrid()[0].length/2) + 1);
//		}
		
		return functionVal;
	}
	
	/**
	 * Returns the function value of the given move and parent value
	 * 
	 * @return	the function value of the given move
	 */
	private double functionVal(double parentVal, Move move)
	{
		double functionVal = parentVal;
		
		for(Node jumped : move.getJumped())
		{
			if(jumped.getPiece().getLoyalty() == this.getLoyalty())
			{
				functionVal -= jumped.getPiece().getWorth();
			}
			else
			{
				functionVal += jumped.getPiece().getWorth();
			}
		}
		
		return functionVal;
	}
	
	private void generateWorths()
	{
		Game testGame = new Game(getGame());
		
		for(Player testPlayer : testGame.getPlayers())
		{
			if(testPlayer instanceof AI)
			{
				for(Class<? extends Piece> pieceType : testGame.getBoard().getPieceTypes())
				{
					((AI) testPlayer).worthMap.put(pieceType, Math.random());
				}
			}
		}
	
		while(!testGame.isCompleted())
		{
			try
			{
				testGame.executeTurn();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @return the worthmap of this AI
	 */
	public HashMap<Class<? extends Piece>, Double> getWorthMap()
	{
		return worthMap;
	}
	
	/**
	 * @return the worthmap of this AI
	 */
	public void setWorthMap(HashMap<Class<? extends Piece>, Double> worthMap)
	{
		this.worthMap = worthMap;
	}
	
	/**
	 * A class implementing runnable allowing the threading of minmax evaluation
	 * 
	 * @author Benjamin Cohen-Wang
	 */
	private class MinimaxValueFinder extends Thread
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
				minimaxValue = getMinimaxVal(node, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
