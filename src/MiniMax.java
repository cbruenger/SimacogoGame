// A class to implement the minimax search used by the AI
public class MiniMax {
	
	// A class variable to represent the column choice
	private int aiColumnChoice;
	
	
	// Constructor takes a BoardState, a chosen play, and calls the search method
	public MiniMax(BoardState boardIn, int plyIn){
		aiMove(boardIn, plyIn);
	}
	
	// Called by constructor and checks the successors for best choice based off 
	// the call to  the recursive function minValue
	public void aiMove(BoardState boardIn, int plyIn){
		BoardState bestMove = null;
		int bestValue = Integer.MIN_VALUE;
		for (BoardState successor : boardIn.generateSuccessors()){
			int utilityValue = minValue(successor, plyIn);
			if (utilityValue > bestValue){
				bestValue = utilityValue;
				bestMove = successor;
			}
		}
		aiColumnChoice = bestMove.getColumnChanged();
	}
	
	// Recursive function which evaluates the leaves and returns the lowest utility
	public static int minValue (BoardState stateIn, int plyIn){
		if (stateIn.getIsTerminalState() || plyIn == 0) return stateIn.getAIUtility();
		int returnValue = Integer.MAX_VALUE;
		for (BoardState successor : stateIn.generateSuccessors()){
			returnValue = Math.min(returnValue, maxValue(successor, plyIn - 1));
		}
		return returnValue;
	}
	
	// Recursive function which evaluates the leaves and returns the highest utility
	public static int maxValue (BoardState stateIn, int plyIn){
		if (stateIn.getIsTerminalState() || plyIn == 0) return stateIn.getAIUtility();
		int returnValue = Integer.MIN_VALUE;
		for (BoardState successor : stateIn.generateSuccessors()){
			returnValue = Math.max(returnValue, minValue(successor, plyIn - 1));
		}
		return returnValue;
	}
	
	// Returns the column choice produced by the minimax search
	public int getAIColumnChoice(){
		return aiColumnChoice;
	}
	
}