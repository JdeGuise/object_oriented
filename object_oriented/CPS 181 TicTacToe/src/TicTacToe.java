import java.util.Scanner;

public class TicTacToe {

	static Scanner input;
	static int A = 0, B = 0;
	static char p1 = 'X';
	char ai = 'O';
	static char[][] board = new char[3][3];
	public static void main(String[] args) {
		input = new Scanner(System.in);
		boolean hasWon = false;
		boolean cpuWin = false;
		//declare array board as type char

		//setting up spacing of grid
		for(int a = 0; a < 3; a++){
			//second dimension for loop
			for(int b = 0; b < 3; b++){
				//set board[a][b] value as empty space
				board[a][b] = ' ';
			}
		}
		//game loop
		while(isFull() == false && hasWon != true && cpuWin != true){
			displayBoard(board);
			Player(board);
			hasWon = checkWin(board, 'X');
			
			if(hasWon == false){
				AI(board);
				cpuWin = checkWin(board, 'O');
			}else
			{}
		}
		if(hasWon == true){
			System.out.println("You won!");
		}
		else if(cpuWin == true){
			System.out.println("You lost!");
		}
		
		
		
		}
	public static boolean isFull(){
		for(int i=0;i<board.length;i++){
			for(int j=0;j<board[i].length;j++){
				if(board[i][j] == ' '){
					return false;
					
				}
			}
		}
		return true;
	}
	//Method for displaying the board
	public static void displayBoard(char[][] board){
		System.out.println("  1   2   3");
		System.out.println("A " + board[0][0] + " | " + board[0][1] + " | " + board[0][2]);
		System.out.println(" -----------");
		System.out.println("B " + board[1][0] + " | " + board[1][1] + " | " + board[1][2]);
		System.out.println(" -----------");
		System.out.println("C " + board[2][0] + " | " + board[2][1] + " | " + board[2][2]);
	}
	
	public static void Player(char[][] board){
		//declare/initialize argument for while loop
				boolean getGuess = false;
				while(!getGuess){
					System.out.println("Enter a row (a, b, c)");
					String guess = input.next();		
					//x coordinate logic
					if(guess.equals("a")){
						A = 0;
					}
					else if(guess.equals("b")){
						A = 1;
					}
					else if(guess.equals("c")){
						A = 2;
					}
					else{
						getGuess = false;
						continue;
					}
					//prompt user for column
					System.out.println("Enter a column (1, 2, 3)");
					int guess2 = input.nextInt();
					//since columns are numbers, the index is just the column# - 1
					B = guess2 - 1;
					//if space guessed is empty, set an X in the spot, boolean sentinel value, and updated board is displayed
					if(board[A][B] == ' '){
						board[A][B] = p1;
						displayBoard(board);
						getGuess = true;
					}
					//if space guessed is occupied, lets user know
					else if(board[A][B] == ('O')){
						System.out.println("Space occupied. Try again!");
					}
				}
			}
	
	public static void AI(char[][] board){
		int A = 0, B = 0;
		char ai = 'O';
		do{
		A = (int)(Math.random()*3);
		B = (int)(Math.random()*3);
		}
		while(board[A][B] != ' ');
		board[A][B] = ai;

		displayBoard(board);
	}
	
	public static boolean checkWin(char[][] board, char winner){
		if((board[0][0] == (winner) && board[0][1] == (winner) && board[0][2] == (winner)) ||
		   (board[1][0] == (winner) && board[1][1] == (winner) && board[1][2] == (winner)) ||
		   (board[2][0] == (winner) && board[2][1] == (winner) && board[2][2] == (winner)) ||
		   (board[0][0] == (winner) && board[1][0] == (winner) && board[2][0] == (winner)) ||
		   (board[0][1] == (winner) && board[1][1] == (winner) && board[2][1] == (winner)) ||
		   (board[0][2] == (winner) && board[1][2] == (winner) && board[2][2] == (winner)) ||
		   (board[0][0] == (winner) && board[1][1] == (winner) && board[2][2] == (winner)) ||
		   (board[0][2] == (winner) && board[1][1] == (winner) && board[2][0] == (winner))){

		return true;
		
		} 
		return false;
	}

}
