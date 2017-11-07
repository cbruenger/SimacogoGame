import java.util.Scanner;

// Main class for playing Simacogo 
public class SimacogoMain {
	
	public static void main(String[] args){
		
		Scanner user_input = new Scanner(System.in);
		
		// Create the initial board 
		Column[] board = new Column[9];
		for (int i = 0; i < 9; i++){
			board[i] = new Column();
		}
		
		// Create initial state
		BoardState state = new BoardState(board, true, 0, null, 0, 0);
		
		System.out.println("   SSSSS     II   MMMM       MMMM       AAA        CCCCCCCC     OOOOOOOO     GGGGGGGG     OOOOOOOO");
		System.out.println(" SS     SS   II   MM MM     MM MM      AA AA      CC      CC   OO      OO   GG      GG   OO      OO");
		System.out.println(" SS     SS   II   MM  MM   MM  MM     AA   AA     CC      CC   OO      OO   GG      GG   OO      OO");
		System.out.println("  SS         II   MM   MMMM    MM    AA     AA    CC           OO      OO   GG           OO      OO");
		System.out.println("    SS       II   MM    MM     MM   AA       AA   CC           OO      OO   GG           OO      OO");
		System.out.println("     SS      II   MM           MM   AAAAAAAAAAA   CC           OO      OO   GG    GGGG   OO      OO");
		System.out.println("       SS    II   MM           MM   AA       AA   CC           OO      OO   GG    GGGG   OO      OO");
		System.out.println(" SS     SS   II   MM           MM   AA       AA   CC      CC   OO      OO   GG      GG   OO      OO");
		System.out.println(" SS     SS   II   MM           MM   AA       AA   CC      CC   OO      OO   GG      GG   OO      OO");
		System.out.println("   SSSSS     II   MM           MM   AA       AA    CCCCCCCC     OOOOOOOO     GGGGGGGG     OOOOOOOO");
		System.out.println();
		System.out.println();
		
		// Get the ply choice from the user
		int plyChoice;
		System.out.println("Enter the number of plys for the AI to search:");
		plyChoice = user_input.nextInt();
		
		boolean notTerminalState = true;
		
		// Loop until the terminal state is reached
		while (notTerminalState){
			// If it is the user's turn, print the board, scores, take their next move, verify that the
			// move is valid, re-define the state considering the users choice, and then check if 
			// the new state is the terminal state and alert result of the game
			if (state.getIsUserTurn()){
				state.printBoardState();
				System.out.println();
				System.out.println();
				System.out.println("Your score is: " + state.getUserScore());
				System.out.println("AI's score is: " + state.getAIScore());
				System.out.println();
				int userMove;
				System.out.println("Enter the column number of your move:");
				userMove = user_input.nextInt();
				boolean validColumn = false;
				if (userMove < 1 || userMove > 9){
					while (!validColumn){
						System.out.println("That is not a valid column. Make another choice:");
						userMove = user_input.nextInt();
						if (userMove >= 1 && userMove <= 9){
							validColumn = !validColumn;
						}
					}
				}
				boolean validMove = !state.getCurrentBoard()[userMove - 1].fullColumnCheck();
				if (validMove == false){
					while (!validMove){
						System.out.println("That column is already full. Make another choice:");
						userMove = user_input.nextInt();
						if (!state.getCurrentBoard()[userMove - 1].fullColumnCheck()){
							validMove = true;
						}
					}
				}
				state = new BoardState(state.getCurrentBoard(), false, userMove, "O", state.getAIScore(), state.getUserScore());
				
				// If the board is full, print the board and alert if user won, lost, or tied
				if (state.getIsTerminalState()){
					notTerminalState = false;
					state.printBoardState();
					System.out.println();
					System.out.println();
					System.out.println("Your final score is: " + state.getUserScore());
					System.out.println("AI's final score is: " + state.getAIScore());
					System.out.println();
					if (state.getUserScore() > state.getAIScore()){
						System.out.println("YOU WON!");
					}else if (state.getUserScore() == state.getAIScore()){
						System.out.println("TIE!");
					}else{
						System.out.println("YOU LOST!");
					}
				}
				
			// If it is the AI's turn, print the state and scores, call the minimax search, and 
			// re-define the state according to the result of the minimax search
			}else if (!state.getIsUserTurn()){
				state.printBoardState();
				System.out.println();
				System.out.println();
				System.out.println("Your score is: " + state.getUserScore());
				System.out.println("AI's score is: " + state.getAIScore());
				System.out.println();
				System.out.println("The AI may take a moment to choose its move....");
				MiniMax aiSearch = new MiniMax(state, plyChoice);
				int aiMove = aiSearch.getAIColumnChoice();
				state = new BoardState(state.getCurrentBoard(), true, aiMove, "X", state.getAIScore(), state.getUserScore());
			}
		}
		user_input.close();
	}
}