/*Eric Pickup
Problem 3. (25 points)
Tic-Tac-Toe: Write a Java program that plays tic-tac-toe. The tic-tac-toe game is played on a 3X3
grid. The game is played by two players, who take turns. The first player marks a cell with a circle, the
second one another cell with a cross. The player who has formed a horizontal, vertical, or diagonal sequence
of three marks wins. Your program should draw the game board, ask the user for the coordinates of the next
mark, redraw the board, change the players after every successful move, and announce the winner.

Note: Do not create a graphical user interface for this program. You can just draw the board using
some characters on the terminal.
*/

import java.util.Scanner;

public class tictactoe {

	public static void main(String args[]) {
		
		char[][] board = new char[3][3];
		
		for (int i = 0; i < 3; i++) {	//Initialize all slots as spaces
			for (int j = 0; j < 3; j++) {
				board[i][j] = ' ';
			}
		}
		
		Scanner input = new Scanner(System.in);
		
		drawBoard(board);	//Draw empty board initially
		int y, x, count = 0;
		
		while(checkForWinner(board) == 0 && isFull(board) == 0) {	//Continue to next turn as long as the board isn't full and we don't have a winner
			
			System.out.printf("\nPlayer %d, enter your move (y then x separated by space): ", (count % 2) + 1);
			
			y = input.nextInt();
			x = input.nextInt();
			
			while (isValidMove(y,x,board) == 0) {	//If the move is invalid, print that, and ask for turn again
				System.out.printf("INVALID MOVE! Make sure your coordinates are in bounds (0-2) and the position isn't already taken!\n");
				System.out.printf("\nPlayer %d, enter your move: ", (count % 2) + 1);
				y = input.nextInt();
				x = input.nextInt();
			}
			
			if (count % 2 == 0) {	//Player 1 (x)
				board[y][x] = 'x';
			} else {	//Player 2 (o)
				board[y][x] = 'o';
			}
			drawBoard(board);
			count++;
		}
		
		if (checkForWinner(board) == 0) {
			System.out.println("NO WINNER");
		} else if (checkForWinner(board) == 1) {
			System.out.println("PLAYER 1 WINS!");
		} else {
			System.out.println("PLAYER 2 WINS!");
		}
	
		System.out.printf("\n\n");
		
	}
	
	/**
	 * Prints the board using characters
	 * @param board - array containing current board markings
	 */
	public static void drawBoard(char[][] board) {
		
		int count = 0;
		System.out.printf("\n");
		for (int i = 0; i < 12; i++) {	//First row
			if (i == 1 || i == 5 || i == 9) {	//Printing x's, o's or space
				System.out.printf("%c", board[0][count]);
				count++;
			} else if (i == 3 || i == 7) {
				System.out.printf("|");
			} else {
				System.out.printf(" ");
			}
		}
		System.out.printf("\n-----------\n");
		count = 0;
		for (int i = 0; i < 12; i++) {	//First row
			if (i == 1 || i == 5 || i == 9) {	//Printing x's, o's or space
				System.out.printf("%c", board[1][count]);
				count++;
			} else if (i == 3 || i == 7) {
				System.out.printf("|");
			} else {
				System.out.printf(" ");
			}
		}
		System.out.printf("\n-----------\n");
		count = 0;
		for (int i = 0; i < 12; i++) {	//First row
			if (i == 1 || i == 5 || i == 9) {	//Printing x's, o's or space
				System.out.printf("%c", board[2][count]);
				count++;
			} else if (i == 3 || i == 7) {
				System.out.printf("|");
			} else {
				System.out.printf(" ");
			}
		}
		System.out.printf("\n\n");
		
	}
	
	/**
	 * Checks if there is a winner (match of all 3 vertically, horizontally or diagonally)
	 * @param board - board array containing current markings
	 * @return 0 if there is none, 1 if player 1 (x) wins, 2 if player 2 (o) wins
	 */
	public static int checkForWinner(char[][] board) {
		
		for (int i = 0; i < 3; i++) {	
			if (board[i][0] != ' ' && board[i][1] == board[i][0] && board[i][2] == board[i][0]) {//Checking for horizontal winners
				if (board[i][0] == 'x') {
					return 1;	//Player 1 wins
				} else {
					return 2;	//Player 2 wins
				}
			}
			if (board[0][i] != ' ' && board[1][i] == board[i][0] && board[2][i] == board[0][i]) {	//Vertical winners
				if (board[i][0] == 'x') {
					return 1;	//Player 1 wins
				} else {
					return 2;	//Player 2 wins
				}
			}
		}
		if ((board[0][0] != ' ' && board[1][1] == board[0][0] && board[2][2] == board[0][0]) || (board[2][0] != ' ' && board[1][1] == board[2][0] && board[0][2] == board[2][0])) {	//Diagonal win (Both ways)
			if (board[0][0] == 'x') {
				return 1;
			} else {
				return 2;
			}
		}
		return 0;
	}
	
	/**
	 * Checks if the move is valid (in bounds, position not already taken)
	 * @param y - y-coordinate of attempted move
	 * @param x - x-coordinate of attempted move
	 * @param board - board array containing all current moves
	 * @return 0 if invalid, 1 if valid
	 */
	public static int isValidMove(int y, int x, char[][] board) {
		
		if (y < 0 || x < 0 || y > 2 || x > 2) {	//Out of bounds check
			return 0;
		}
		
		if (board[y][x] != ' ') {	//Position already been used
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * Check if the board is full - every position in array should contain an x or o (game ends)
	 * @param board - board array containing all current moves
	 * @return 0 if not full, 1 if full
	 */
	public static int isFull(char[][] board) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == ' ') {
					return 0;
				}
			}
		}
		return 1;
	}
	
}
