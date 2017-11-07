import java.util.ArrayList;

// A class which represents the current state of the board
// Also generates successor states
public class BoardState {
	
	// Private class variables which represent the details of the current state
	private Column[] currentBoard;
	private int numberOfColumns;
	private int columnChanged;
	private String newestPiece;
	private int userScore;
	private int aiScore;
	private int aiUtility;
	private boolean isTerminalState = false;
	private boolean isUserTurn = false;
	
	// Constructor receives a board, a boolean representing who's turn it currently is, a column which
	// will receive the piece chosen in the previous state, the piece to be added to the column, and the scores
	// of the user and AI. Column should be 0 if constructing the initial state, String should be null.
	// Constructor updates the current board by adding the move chosen in the previous state, assigns some class
	// variables, and calls functions to assign others.
	public BoardState(Column[] boardIn, boolean turnIn, int column, String pieceIn, int aiScoreIn, int userScoreIn){
		currentBoard = boardIn;
		newestPiece = pieceIn;
		isUserTurn = turnIn;
		columnChanged = column;
		numberOfColumns = boardIn.length;
		aiScore = aiScoreIn;
		userScore = userScoreIn;
		addPiece(pieceIn);
		assignIsTerminalState();
		assignScores();
		assignAIUtility();
	}
	
	// Returns an array of Columns which represents the current board
	public Column[] getCurrentBoard(){
		return currentBoard;
	}
	
	// Returns the number of columns in the current board
	public int getNumberOfColumns(){
		return numberOfColumns;
	}
	
	// Called by constructor to update the board by adding the piece to to column chosen in the previous state
	public void addPiece(String pieceIn){
		if (columnChanged == 0) return;
		currentBoard[getColumnChanged() - 1].addPiece(pieceIn); 
	}
	
	// Returns a string of the newestPiece
	public String getNewestPiece(){
		return newestPiece;
	}
	
	// Called by constructor to check where the current state is the terminal state
	public void assignIsTerminalState(){
		for (int i = 0; i < numberOfColumns; i++){
			if (currentBoard[i].getPieces()[8] == " "){
				isTerminalState = false;
				return;
			}
		}
		isTerminalState = true;
	}
	
	// Returns the boolean class variable representing terminal state. True if terminal, false otherwise.
	public boolean getIsTerminalState(){
		return isTerminalState;
	}
	
	// Returns the boolean class variable representing who's turn it currently is. True if user, false if AI.
	public boolean getIsUserTurn(){
		return isUserTurn;
	}
	
	// Returns the column selected in previous state to receive a piece
	public int getColumnChanged(){
		return columnChanged;
	}
	
	// Called by the constructor to update the scores of the current state considering the move made in the previous state
	public void assignScores(){
		// Checks if column changed is 0 which is only the case for the initial state before any moves
		if (columnChanged == 0){
			return;
		}
		// Checks if column added to was first column
		if (columnChanged == 1){
			// Checks if there are multiple pieces in this column and if the piece added matches the previous
			if (currentBoard[0].getHeight() > 1){
				if (currentBoard[0].getPieces()[currentBoard[0].getHeight() - 2] == newestPiece){
					if (newestPiece == "O") userScore += 2;
					if (newestPiece == "X") aiScore += 2;
				}
			}
			// Checks if the new piece has a match up and to the right
			if (currentBoard[0].getHeight() <= 8){
				if (currentBoard[1].getPieces()[currentBoard[0].getHeight()] == newestPiece){
					if (!isUserTurn) userScore++;
					else aiScore++;
				}
			}
			// Checks if the new piece has a match to the right
			if (currentBoard[1].getPieces()[currentBoard[0].getHeight() - 1] == newestPiece){
				if (!isUserTurn) userScore += 2;
				else aiScore += 2;
			}
			// Checks if the new piece has a match down and to the right
			if (currentBoard[0].getHeight() >= 2){ 
				if (currentBoard[1].getPieces()[currentBoard[0].getHeight() - 2] == newestPiece){
					if (!isUserTurn) userScore++;
					else aiScore++;
				}
			}
		// Checks if the column added to was the last column
		}else if (columnChanged == 9){
			// Checks if there are multiple pieces in this column and if the piece added matches the previous
			if (currentBoard[8].getHeight() > 1){
				if (currentBoard[8].getPieces()[currentBoard[8].getHeight() - 2] == newestPiece){
					if (newestPiece == "O") userScore += 2;
					if (newestPiece == "X") aiScore += 2;
				}
			}
			// Checks if the new piece has a match up and to the left
			if (currentBoard[8].getHeight() <= 8){
				if (currentBoard[7].getPieces()[currentBoard[8].getHeight()] == newestPiece){
					if (!isUserTurn) userScore++;
					else aiScore++;
				}
			}
			// Checks if the new piece has a match to the left
			if (currentBoard[7].getPieces()[currentBoard[8].getHeight() - 1] == newestPiece){
				if (!isUserTurn) userScore += 2;
				else aiScore += 2;
			}
			// Checks if the new piece has a match down and to the left
			if (currentBoard[8].getHeight() >= 2){
				if (currentBoard[7].getPieces()[currentBoard[8].getHeight() - 2] == newestPiece){
					if (!isUserTurn) userScore++;
					else aiScore++;
				}
			}
		// Checks if columns 2 through 8 were added to
		}else{
			// Checks if there are multiple pieces in this column and if the piece added matches the previous
			if (currentBoard[columnChanged - 1].getHeight() > 1){
				if (currentBoard[columnChanged - 1].getPieces()[currentBoard[columnChanged - 1].getHeight() - 2] == newestPiece){
					if (newestPiece == "O") userScore += 2;
					if (newestPiece == "X") aiScore += 2;
				}
			}
			// Checks neighboring columns diagonally up
			if (currentBoard[columnChanged -1].getHeight() <= 8){
				// Checks if new piece has a match up and to the left
				if (currentBoard[columnChanged - 2].getPieces()[currentBoard[columnChanged - 1].getHeight()] == newestPiece){
					if (!isUserTurn) userScore++;
					else aiScore++;
				}
				// Checks if new piece has a match up and to the right
				if (currentBoard[columnChanged].getPieces()[currentBoard[columnChanged - 1].getHeight()] == newestPiece){
					if (!isUserTurn) userScore++;
					else aiScore++;
				}
			}
			// Checks if new piece has a match to the left
			if (currentBoard[columnChanged - 2].getPieces()[currentBoard[columnChanged - 1].getHeight() -1] == newestPiece){
				if (!isUserTurn) userScore += 2;
				else aiScore += 2;
			}
			// Checks if new piece has a match to the right
			if (currentBoard[columnChanged].getPieces()[currentBoard[columnChanged - 1].getHeight() - 1] == newestPiece){
				if (!isUserTurn) userScore += 2;
				else aiScore += 2;
			}
			// Checks neighboring columns diagonally down
			if (currentBoard[columnChanged - 1].getHeight() >= 2){
				// Checks if newest piece has a match down and to the left
				if (currentBoard[columnChanged - 2].getPieces()[currentBoard[columnChanged - 1].getHeight() -2] == newestPiece){
					if (!isUserTurn) userScore++;
					else aiScore++;
				}
				// Checks if newest piece has a match down and to the right
				if (currentBoard[columnChanged].getPieces()[currentBoard[columnChanged - 1].getHeight() -2] == newestPiece){
					if (!isUserTurn) userScore++;
					else aiScore++;
				}
			}
		}
	}
	
	// Called by the constructor after scores have been updated to calculate the AI's utility
	public void assignAIUtility(){
		aiUtility = aiScore - userScore;
	}
	
	// Returns the AI's utility
	public int getAIUtility(){
		return aiUtility;
	}
	
	// Returns the user's score
	public int getUserScore(){
		return userScore;
	}
	
	// Returns the AI's score
	public int getAIScore(){
		return aiScore;
	}
	
	// Returns a copy of the current board configuration
	public Column[] getCurrentBoardCopy(){
		Column[] boardCopy = new Column[numberOfColumns];
		for (int i = 0; i < numberOfColumns; i++){
			boardCopy[i] = new Column();
			for (int j = 0; j < currentBoard[i].getHeight(); j++){
				boardCopy[i].addPiece(currentBoard[i].getPieces()[j]);
			}
		}
		return boardCopy;
	}
	
	// Returns an ArrayList containing successor states by iterating through each column and, if the column is
	// not full, creating a copy of the board and generating a successor state with a piece added to it
	public ArrayList<BoardState> generateSuccessors(){
		ArrayList<BoardState> successors = new ArrayList<BoardState>();
		for (int i = 0; i < numberOfColumns; i++){
			if (currentBoard[i].getPieces()[8] == " "){
				Column[] boardCopy = getCurrentBoardCopy();
				if (isUserTurn) successors.add(new BoardState(boardCopy, false, i + 1, "O", aiScore, userScore)); 
				else successors.add(new BoardState(boardCopy, true, i + 1, "X", aiScore, userScore)); 
			}
		}
		return successors;
	}
	
	// Prints the current board state
	public void printBoardState(){
		System.out.println(" _____________________________________________________");
		System.out.println("|     |     |     |     |     |     |     |     |     |");
		System.out.println("|  "+currentBoard[0].getValue(8)+"  |  "+currentBoard[1].getValue(8)+"  |  "+currentBoard[2].getValue(8)+
						 "  |  "+currentBoard[3].getValue(8)+"  |  "+currentBoard[4].getValue(8)+"  |  "+currentBoard[5].getValue(8)+
						 "  |  "+currentBoard[6].getValue(8)+"  |  "+currentBoard[7].getValue(8)+"  |  "+currentBoard[8].getValue(8)+"  |");
		System.out.println("|_____|_____|_____|_____|_____|_____|_____|_____|_____|");
		System.out.println("|     |     |     |     |     |     |     |     |     |");
		System.out.println("|  "+currentBoard[0].getValue(7)+"  |  "+currentBoard[1].getValue(7)+"  |  "+currentBoard[2].getValue(7)+
				 		 "  |  "+currentBoard[3].getValue(7)+"  |  "+currentBoard[4].getValue(7)+"  |  "+currentBoard[5].getValue(7)+
				 		 "  |  "+currentBoard[6].getValue(7)+"  |  "+currentBoard[7].getValue(7)+"  |  "+currentBoard[8].getValue(7)+"  |");
		System.out.println("|_____|_____|_____|_____|_____|_____|_____|_____|_____|");
		System.out.println("|     |     |     |     |     |     |     |     |     |");
		System.out.println("|  "+currentBoard[0].getValue(6)+"  |  "+currentBoard[1].getValue(6)+"  |  "+currentBoard[2].getValue(6)+
				 		 "  |  "+currentBoard[3].getValue(6)+"  |  "+currentBoard[4].getValue(6)+"  |  "+currentBoard[5].getValue(6)+
				 		 "  |  "+currentBoard[6].getValue(6)+"  |  "+currentBoard[7].getValue(6)+"  |  "+currentBoard[8].getValue(6)+"  |");
		System.out.println("|_____|_____|_____|_____|_____|_____|_____|_____|_____|");
		System.out.println("|     |     |     |     |     |     |     |     |     |");
		System.out.println("|  "+currentBoard[0].getValue(5)+"  |  "+currentBoard[1].getValue(5)+"  |  "+currentBoard[2].getValue(5)+
				 		 "  |  "+currentBoard[3].getValue(5)+"  |  "+currentBoard[4].getValue(5)+"  |  "+currentBoard[5].getValue(5)+
				 		 "  |  "+currentBoard[6].getValue(5)+"  |  "+currentBoard[7].getValue(5)+"  |  "+currentBoard[8].getValue(5)+"  |");
		System.out.println("|_____|_____|_____|_____|_____|_____|_____|_____|_____|");
		System.out.println("|     |     |     |     |     |     |     |     |     |");
		System.out.println("|  "+currentBoard[0].getValue(4)+"  |  "+currentBoard[1].getValue(4)+"  |  "+currentBoard[2].getValue(4)+
				 		 "  |  "+currentBoard[3].getValue(4)+"  |  "+currentBoard[4].getValue(4)+"  |  "+currentBoard[5].getValue(4)+
				 		 "  |  "+currentBoard[6].getValue(4)+"  |  "+currentBoard[7].getValue(4)+"  |  "+currentBoard[8].getValue(4)+"  |");
		System.out.println("|_____|_____|_____|_____|_____|_____|_____|_____|_____|");
		System.out.println("|     |     |     |     |     |     |     |     |     |");
		System.out.println("|  "+currentBoard[0].getValue(3)+"  |  "+currentBoard[1].getValue(3)+"  |  "+currentBoard[2].getValue(3)+
				 		 "  |  "+currentBoard[3].getValue(3)+"  |  "+currentBoard[4].getValue(3)+"  |  "+currentBoard[5].getValue(3)+
				 		 "  |  "+currentBoard[6].getValue(3)+"  |  "+currentBoard[7].getValue(3)+"  |  "+currentBoard[8].getValue(3)+"  |");
		System.out.println("|_____|_____|_____|_____|_____|_____|_____|_____|_____|");
		System.out.println("|     |     |     |     |     |     |     |     |     |");
		System.out.println("|  "+currentBoard[0].getValue(2)+"  |  "+currentBoard[1].getValue(2)+"  |  "+currentBoard[2].getValue(2)+
				 		 "  |  "+currentBoard[3].getValue(2)+"  |  "+currentBoard[4].getValue(2)+"  |  "+currentBoard[5].getValue(2)+
				 		 "  |  "+currentBoard[6].getValue(2)+"  |  "+currentBoard[7].getValue(2)+"  |  "+currentBoard[8].getValue(2)+"  |");
		System.out.println("|_____|_____|_____|_____|_____|_____|_____|_____|_____|");
		System.out.println("|     |     |     |     |     |     |     |     |     |");
		System.out.println("|  "+currentBoard[0].getValue(1)+"  |  "+currentBoard[1].getValue(1)+"  |  "+currentBoard[2].getValue(1)+
				 		 "  |  "+currentBoard[3].getValue(1)+"  |  "+currentBoard[4].getValue(1)+"  |  "+currentBoard[5].getValue(1)+
				 		 "  |  "+currentBoard[6].getValue(1)+"  |  "+currentBoard[7].getValue(1)+"  |  "+currentBoard[8].getValue(1)+"  |");
		System.out.println("|_____|_____|_____|_____|_____|_____|_____|_____|_____|");
		System.out.println("|     |     |     |     |     |     |     |     |     |");
		System.out.println("|  "+currentBoard[0].getValue(0)+"  |  "+currentBoard[1].getValue(0)+"  |  "+currentBoard[2].getValue(0)+
				 		 "  |  "+currentBoard[3].getValue(0)+"  |  "+currentBoard[4].getValue(0)+"  |  "+currentBoard[5].getValue(0)+
				 		 "  |  "+currentBoard[6].getValue(0)+"  |  "+currentBoard[7].getValue(0)+"  |  "+currentBoard[8].getValue(0)+"  |");
		System.out.println("|_____|_____|_____|_____|_____|_____|_____|_____|_____|");
		System.out.println("   1     2     3     4     5     6     7     8     9");
	}
}