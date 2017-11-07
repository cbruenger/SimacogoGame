// A class that represents a column of the board
public class Column {
	
	// Private class variable which represent the initial details of the column
	// These are updated as pieces are added to the columns 
	private String[] pieces = {" ", " ", " ", " ", " ", " ", " ", " ", " "};
	private final int columnSize = pieces.length;
	private int height = 0;
	private boolean columnIsFull = false;
	
	// This function adds a new piece to the column and updates class variables
	public void addPiece(String pieceIn){
		if (height <= (columnSize - 1)){
			pieces[height] = pieceIn;
			height++;
			if (height == columnSize) columnIsFull = true;
		}
	}
	
	// Returns the array of pieces in this column
	public String[] getPieces(){
		return pieces;
	}
	
	// Returns the length of the column
	public int getColumnSize(){
		return columnSize;
	}
	
	// Returns the current height of the pieces in this column
	public int getHeight(){
		return height;
	}
	
	// Returns true if column is full, false otherwise
	public boolean fullColumnCheck(){
		return columnIsFull;
	}
	
	// Takes an index and returns the string stored at that index
	public String getValue(int index){
		return pieces[index];
	}
}