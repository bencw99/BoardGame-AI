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
import java.util.TreeMap;
/**
 * A class representing a Human player associated with a game
 * 
 * @author Benjamin Cohen-Wang
 */
public class AI extends Player
{	
	/** The minimax depth of this ai instance **/
	private final int minimaxDepth;
	
	/** The transposition table of this ai instance **/
	private ArrayList<HashMap<MinimaxNodeContents, Double>> transpositionTables;
	
	/** The map containing the worths of pieces **/
	private TreeMap<Class<? extends Piece>, Double> worthMap;
	
	/** The depth of the minimax search **/
	private static final int DEFAULT_MINIMAX_DEPTH = 12;
	
	/** The minimax threads to run while other players turns are running **/
	MinimaxValueFinder[] minimaxThreads;
	
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
		this.transpositionTables = new ArrayList<HashMap<MinimaxNodeContents, Double>>();
	}

	/**
	 * Returns the move executed this turn
	 * 
	 * @return	the move to be executed this turn
	 * @throws IOException 
	 */
	@Override
	public Move getThisTurnMove() throws IOException
	{	
		/*
		 * Idea: Use the minimax algorithm evaluation function to teach board game playing and comments on moves made
		 */
		
		transpositionTables.clear();
		
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
			
			System.out.println("Move " + i + ": " + possibleMovesArray[i] + " with value " + currentVal);
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
		
		double maxMinimaxVal = Integer.MIN_VALUE;
		
		ArrayList<Integer> maxMovesIndeces = new ArrayList<Integer>();
		
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
	 * Returns the minimax val of the given move
	 * 
	 * @param minimaxNode	the move whose minimax val is evaluated
	 * @param alphaVal	the alpha value of this minimax evaluation
	 * @param betaVal	the beta value of this minimax evaluation
	 * @param parentVal	the function value of the paret of this node
	 * @return	the minimax val of the given move
	 * @throws IOException 
	 */
	private double getMinimaxVal(MinimaxNode node, double alphaVal, double betaVal, Double parentVal, int specificMinimaxDepth, Player maximizedPlayer) throws IOException
	{	
		/** Dynamic programming transposition table search **/
		
		/** Hash table initialization for this level **/
		if(transpositionTables.size() <= node.getMinimaxDepth())
		{
			transpositionTables.add(new HashMap<MinimaxNodeContents, Double>());
		}
		else
		{
			/** Search for any similar nodes in smaller or equal depth of tree **/
			
			/** Currently out of use, unclear if beneficial
			for(int i = node.getMinimaxDepth(); i >= 0; i --)
			{
				Double transposedVal = transpositionTables.get(i).get(node.getContents());
				
				if(transposedVal != null)
				{
					return transposedVal;
				}
			}
			**/
			
			/** Case of match: value returned **/
			Double transposedVal = transpositionTables.get(node.getMinimaxDepth()).get(node.getContents());
			
			if(transposedVal != null)
			{
				return transposedVal;
			}
		}
		
		/** Heuristic of this node based on parent heuristic **/
		double functionVal;
		
		if(parentVal != null)
		{
			functionVal = functionVal(parentVal, node.getMove(), maximizedPlayer);
		}
		else
		{
			functionVal = functionVal(node, maximizedPlayer);
		}
		
		
		/** Leaf node case testing **/
		if(node.getMinimaxDepth() >= specificMinimaxDepth)
		{
		  	return functionVal;
		}
	
		ArrayList<Move> nextMoves = node.getNextMoves();
		
		if(node.getGame().getPlayers()[node.getGame().getTurn().getVal()].isDefeated())
		{
		  	return functionVal;
		}
		
		boolean thisPlayersTurn = maximizedPlayer.getLoyalty().getVal() == node.getGame().getTurn().getVal();
		
		double extreme;
		
		/** Heuristic sorts children for alpha-beta optimizations **/
//		heuristicSort(nextMoves, node, thisPlayersTurn, functionVal);
		
		/** Minimax evaluations **/
		if(thisPlayersTurn)
		{
			extreme = Integer.MIN_VALUE;
			
			for(Move nextMove : nextMoves)
			{	
				double maxCand = getMinimaxVal(node.getNextNode(nextMove), alphaVal, betaVal, functionVal, specificMinimaxDepth, maximizedPlayer);
				
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
				double minCand = getMinimaxVal(node.getNextNode(nextMove), alphaVal, betaVal, functionVal, specificMinimaxDepth, maximizedPlayer);

				extreme = Math.min(extreme, minCand);
				
				betaVal = Math.min(betaVal, minCand);
				
				if(alphaVal >= betaVal)
				{
					break;
				}
			}
		}
		
		/** Transposition table insertion **/
		transpositionTables.get(node.getMinimaxDepth()).put(node.getContents(), extreme);
		
		return extreme;
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
	private double getMinimaxVal(MinimaxNode node, double alphaVal, double betaVal, Double parentVal, int specificMinimaxDepth) throws IOException
	{	
		return this.getMinimaxVal(node, alphaVal, betaVal, parentVal, specificMinimaxDepth, this);
	}
	
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
	private double getMinimaxVal(MinimaxNode node, double alphaVal, double betaVal, Double parentVal) throws IOException
	{	
		return this.getMinimaxVal(node, alphaVal, betaVal, parentVal, this.minimaxDepth);
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
		return this.getMinimaxVal(node, alphaVal, betaVal, null);
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
	private double getMinimaxVal(MinimaxNode node, double alphaVal, double betaVal, int specificMinimaxDepth) throws IOException
	{	
		return this.getMinimaxVal(node, alphaVal, betaVal, null, specificMinimaxDepth);
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
		return functionVal(node.getGame());
	}
	
	/**
	 * Returns the function value of the given move
	 * 
	 * @param move	the move whose function value is evaluated
	 * @return	the function value of the given move
	 */
	private double functionVal(MinimaxNode node, Player player)
	{
		return functionVal(node.getGame(), player);
	}
	
	/**
	 * Returns the function value of the given move and parent value
	 * 
	 * @return	the function value of the given move
	 */
	private double functionVal(double parentVal, Move move, Player player)
	{
		double functionVal = parentVal;
		
		Player[] players = player.getGame().getPlayers();
		
		boolean hasWon = true;
		
		for(Player enemy : players) 
		{
			if(enemy.getLoyalty() != player.getLoyalty())
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
		
		for(Node jumped : move.getJumped())
		{
			if(jumped.getPiece().getLoyalty() == player.getLoyalty())
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
	
	/**
	 * Returns the function value of the given move and parent value
	 * 
	 * @return	the function value of the given move
	 */
	private double functionVal(double parentVal, Move move)
	{
		return functionVal(parentVal, move, this);
	}
	
	private double functionVal(Game game, Player player)
	{
		Player[] players = game.getPlayers();
		double functionVal = 0;
		
		boolean hasWon = true;
		
		for(Player enemy : players) 
		{
			if(enemy.getLoyalty() != player.getLoyalty())
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
		
		for(Node gridNode : game.getBoard().getNodes())
		{
			Piece piece = gridNode.getPiece();
			
			if(piece != null)
			{
				if(piece.getLoyalty() == player.getLoyalty())
				{
					functionVal += piece.getWorth();
				}
				else
				{
					functionVal -= piece.getWorth();
				}
			}
		}
		
		return functionVal;
	}
	
	private double functionVal(Game game)
	{
		return functionVal(game, this);
	}
	
	/**
	 * Initializes threads to evaluate other player's moves 
	 * 
	 * @throws IOException 
	 * @param player the player to be evaluated
	 */
	public void startPlayerEvaluationThreads(Player player) throws IOException
	{
		transpositionTables.clear();
		
		ArrayList<Move> possibleMoves = player.getPossibleMoves();
		
		MinimaxNode[] possibleNextNodes = new MinimaxNode[possibleMoves.size()];
		
		MinimaxNode currentNode = new MinimaxNode(0, new Game(player.getGame()), null, null, 0, true);
		
		for(int i = 0; i < possibleMoves.size(); i ++)
		{
			possibleNextNodes[i] = currentNode.getNextNode(possibleMoves.get(i));
		}
		
		minimaxThreads = new MinimaxValueFinder[possibleNextNodes.length];
		
		for(int i = 0; i < minimaxThreads.length; i ++)
		{
			/** This piece should be changed to evaluate for player, not for the instance of AI **/
			minimaxThreads[i] = new MinimaxValueFinder(possibleNextNodes[i], functionVal(currentNode, player), player);
			minimaxThreads[i].start();
		}
		
		System.out.println("Evaluation threads initialized");
	}
	
	/**
	 * Evaluates a player based off of minimax results
	 */
	public void finishPlayerEvaluationThreads(Player player)
	{
		MinimaxNodeContents newContents = new MinimaxNodeContents(player.getGame());
		int chosenIndex = 0;
		double chosenValue = 0;
		
		for(int i = 0; i < minimaxThreads.length; i ++)
		{
			if(minimaxThreads[i].getNode().getContents().equals(newContents))
			{
				chosenValue = minimaxThreads[i].getMinimaxValue();
				chosenIndex = i;
			}
			
			System.out.println("Move " + i + ": " + minimaxThreads[i].getMinimaxValue());
		}
		
		System.out.println("Move chosen: " + chosenIndex + " with value " + chosenValue);
		
		double count = 0;
		double greaterCount = 0;
		
		for(MinimaxValueFinder minimaxThread : minimaxThreads)
		{
			count ++;
			
			if(minimaxThread.getMinimaxValue() > chosenValue)
			{
				greaterCount ++;
			}
		}
			
		double evaluatedRank = (count - greaterCount)/count;
		
		System.out.println("Evaluated Rank: " + evaluatedRank);

		for(int i = 0; i < minimaxThreads.length; i ++)
		{
			/** Find alternative way to stop this thread **/
			minimaxThreads[i].stop();
		}
	}
	
	private void generateWorths(int minimaxDepth, int repetitionNum, double increment)
	{
		Game testGame = new Game(getGame());
		
		for(Player testPlayer : testGame.getPlayers())
		{
			testPlayer = new AI("", testPlayer.getLoyalty(), testGame, minimaxDepth);
			
			for(Class<? extends Piece> pieceType : testGame.getBoard().getPieceTypes())
			{
				((AI) testPlayer).worthMap.put(pieceType, 1.0);
			}
		}
		
		for(int i = 0; i <= repetitionNum; i ++)
		{
			for(Class<? extends Piece> pieceType : ((AI) testGame.getPlayers()[0]).worthMap.keySet())
			{
				boolean stable = false;
				
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
				
				double previousVal = functionVal(testGame);
				double currentVal;
				double derivative = 0;
				
				Double pieceWorth = ((AI) testGame.getPlayers()[0]).worthMap.get(pieceType);
				
				pieceWorth += increment;
				
				while(!stable)
				{
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
					
					currentVal = functionVal(testGame);
					
					double thisDeriv = currentVal - previousVal;
					
					if(thisDeriv > 0)
					{	
						if(derivative < 0)
						{
							stable = true;
						}
					}
					else
					{
						increment *= -1;
					}
					
					pieceWorth += increment;
					
					derivative = currentVal - previousVal;
					
					previousVal = currentVal;
					
					Player[] oldPlayers = testGame.getPlayers();
					
					testGame = new Game(getGame());
					
					testGame.setPlayers(oldPlayers);
				}
			}
		}
	}
	
	/**
	 * @return the worth map of this AI
	 */
	public TreeMap<Class<? extends Piece>, Double> getWorthMap()
	{
		return worthMap;
	}
	
	/**
	 * @return the worth map of this AI
	 */
	public void setWorthMap(TreeMap<Class<? extends Piece>, Double> worthMap)
	{
		this.worthMap = worthMap;
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
		
		/** The parent function value of this minimax value finder **/
		private Double parentValue;
		
		/** The player this minimax value finder is working to evaluate **/
		private Player player;
		
		/**
		 * Parameterized constructor, initializes MinimaxValue instance to given node
		 * 
		 * @param node	the node to be set to
		 */
		public MinimaxValueFinder(MinimaxNode node, Player player)
		{
			this(node, null, player);
		}
		
		/**
		 * Parameterized constructor, initializes MinimaxValue instance to given node
		 * 
		 * @param node	the node to be set to
		 */
		public MinimaxValueFinder(MinimaxNode node)
		{
			this(node, null);
		}
		
		/**
		 * Parameterized constructor, initializes MinimaxValue instance to given node
		 * 
		 * @param node	the node to be set to
		 */
		public MinimaxValueFinder(MinimaxNode node, Double parentValue, Player player)
		{
			this.node = node;
			this.parentValue = parentValue;
			this.player = player;
		}

		/**
		 * Evaluates the minimax val of the node of this instance
		 */
		@Override
		public void run()
		{
			if(parentValue == null)
			{
				for(int i = 1; i <= minimaxDepth; i ++)
				{
					try
					{
						minimaxValue = getMinimaxVal(node, Integer.MIN_VALUE, Integer.MAX_VALUE, null, i, player);
					} 
					catch (IOException exception)
					{
						exception.printStackTrace();
					}
				}
			}
			else
			{
				for(int i = 1; i <= minimaxDepth; i ++)
				{
					try
					{
						minimaxValue = getMinimaxVal(node, Integer.MIN_VALUE, Integer.MAX_VALUE, parentValue, i, player);
					} 
					catch (IOException exception)
					{
						exception.printStackTrace();
					}
				}
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
		
		/**
		 * Returns the minimax node of this instance
		 * 
		 * @return	the minimax node of this instance
		 */
		public MinimaxNode getNode()
		{
			return node;
		}
	}
}
