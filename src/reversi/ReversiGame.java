package reversi;

import java.util.Arrays;

/**
 * The {@code ReversiGame} class represents a game of Reversi (also known as Othello).
 * This class manages the game state, including the board, current player, and the game logic required
 * to play Reversi. It provides methods to initialize the game, make moves, print the board, and determine
 * the game outcome.
 */
public class ReversiGame {
	public static int PLAYER_ONE = 1;
	public static int PLAYER_TWO = 2;

	private int[][] board;

	private int curPlayer;

	/**
	 * Initializes the board: all squares are 0 except the four disks in the
	 * middle
	 * Task 1: Implement the constructor
	 *
	 * @return the initialized board
	 */
	public ReversiGame() {
		this.curPlayer = PLAYER_ONE;
		this.board = new int[8][8];
		//p1
		this.board[3][3] = 1;
		this.board[4][4] = 1;
		//p2
		this.board[3][4] = 2;
		this.board[4][3] = 2;

	}
	public ReversiGame(ReversiGame game){
		this.curPlayer = game.curPlayer;
		this.board = new int[8][8];
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				this.board[i][j] = game.board[i][j];
			}
		}

	}

	public int[][] getBoard() {
		return this.board;
	}

	/**
	 * Task 1: Implement this method
	 * @return
	 */
	public int getCurPlayer() {
		return this.curPlayer;
	}

	/**
	 * Task 2: Implement this method
	 *
	 * Prints the board with the row/column indices to the console in the following format
	 *
	 *    0 1 2 3 4 5 6 7
	 *  0 0 0 0 1 0 0 0 0
	 *  1 0 0 0 1 0 0 0 0
	 *  2 0 0 1 2 2 2 0 0
		3 0 0 0 1 2 0 0 0
	 *	4 0 0 0 2 1 0 0 0
	 *	5 0 0 0 0 0 0 0 0
	 *	6 0 0 0 0 0 0 0 0
	 *	7 0 0 0 0 0 0 0 0

	 * *
	 */

	public void printBoard() {
		System.out.println("  0 1 2 3 4 5 6 7");
		for(int i = 0; i<8; i++){
			System.out.print(i+ " ");

			for(int j = 0; j<8; j++){
				System.out.print(this.board[i][j]);
				if(j!=8){
					System.out.print(" ");
				}
			}
			if(i != 8){
				System.out.println();
			}

		}

	}

	/**
	 * * Task 3: Implement this method
	 * @param row
	 * @param col
	 * @return true if the given row and column are on the board, false otherwise
	 */
	public boolean isOnBoard(int row, int col) {
		return row<=7 && row >= 0 && col<=7 && col >= 0;
	}

	/**
	 * Task 3: Implement this method
	 * Returns the opponents player number (1 or 2) for a given player number (1 or 2)
	 * @param player
	 * @return
	 */
	public static int opponentPlayer(int player) {
		if (player == 1){
			return PLAYER_TWO;
		} else{
			return PLAYER_ONE;
		}

	}

// ================================================================================  PART A2  =============================================================================== ======================================== ========================================

	/**
	 * Task 4: Implement this method
	 * @param player player number (1 or 2)
	 * @param row row of the move
	 * @param col column of the move
	 * @param rowInc row increment (-1, 0, or 1)
	 * @param columnInc column increment (-1, 0, or 1)
	 * @return the number of opponent's disks that will be flipped in the given direction
	 */
	public int calcFlipsInDirection(int player, int row, int col, int rowInc, int columnInc) {
		int newRow = row+rowInc;
		int newCol = col+columnInc;
		int resCount = 0;
		if(!isOnBoard(newRow, newCol)){

			return 0;
		}
		if(this.board[newRow][newCol] != opponentPlayer(player) || this.board[row][col] != 0){
			return 0;
		}
		//if next one is not diffrent return 0
		//(0, 1) (0, -1) (1, 0) (1 -1) (-1 0) (-1 1) (1 1) (-1 -1)
		if(rowInc == 0 && columnInc == 1){
			//arrow 1

			while(isOnBoard(newRow, newCol) && this.board[newRow][newCol] == opponentPlayer(player)){
				resCount++;
				newCol++;
			}
			if(isOnBoard(newRow, newCol) && this.board[newRow][newCol] == player){

				return resCount;
			}
		}
		if(rowInc == 0 && columnInc == -1){
			while(isOnBoard(newRow, newCol) && this.board[newRow][newCol] == opponentPlayer(player)){
				resCount++;
				newCol--;
			}
			if(isOnBoard(newRow, newCol) && this.board[newRow][newCol] == player){

				return resCount;
			}
		}
		if(rowInc == 1 && columnInc == 0){
			int i = 0;

			while(isOnBoard(newRow+i, newCol) && this.board[newRow+i][newCol] == opponentPlayer(player)){
				resCount++;
				i++;
			}
			if(isOnBoard(newRow+i, newCol) && this.board[newRow+i][newCol] == player){
				return resCount;
			}
		}
		if(rowInc == 1 && columnInc == -1){
			while(isOnBoard(newRow, newCol) && this.board[newRow][newCol] == opponentPlayer(player)){
				resCount++;
				newRow++;
				newCol--;
			}
			if(isOnBoard(newRow, newCol) && this.board[newRow][newCol] == player){

				return resCount;
			}
		}
		if(rowInc == -1 && columnInc == 0){
			while(isOnBoard(newRow, newCol) && this.board[newRow][newCol] == opponentPlayer(player)){
				resCount++;
				newRow--;
			}

			if(isOnBoard(newRow, newCol) && this.board[newRow][newCol] == player){
				return resCount;
			}
		}
		if(rowInc == -1 && columnInc == 1){
			while(isOnBoard(newRow, newCol) && this.board[newRow][newCol] == opponentPlayer(player)){
				resCount++;
				newRow--;
				newCol++;
			}
			if(isOnBoard(newRow, newCol) && this.board[newRow][newCol] == player){

				return resCount;
			}
		}
		if(rowInc == 1 && columnInc == 1){

			while(isOnBoard(newRow, newCol) && this.board[newRow][newCol] == opponentPlayer(player)){
				resCount++;
				newRow++;
				newCol++;
			}
			if(isOnBoard(newRow, newCol) && this.board[newRow][newCol] == player){

				return resCount;
			}
		}
		if(rowInc == -1 && columnInc == -1){
			while(isOnBoard(newRow, newCol) && this.board[newRow][newCol] == opponentPlayer(player)){
				resCount++;
				newRow--;
				newCol--;
			}
			if(isOnBoard(newRow, newCol) && this.board[newRow][newCol] == player){

				return resCount;
			}
		}

		return 0;

	}

	/**
	 * Task 4: Implement this method
	 * @param row row of the move
	 * @param col column of the move
	 * @param rowInc row increment (-1, 0, or 1)
	 * @param columnInc
	 * @return the number of disks that were flipped in the given direction
	 */
	public int updateMoveDisksInSingleDirection(int row, int col, int rowInc, int columnInc) {
		int countFlips = calcFlipsInDirection(this.curPlayer, row,  col, rowInc, columnInc);
		if(countFlips == 0){
			return 0;
		}
		for(int i = 1; i<=countFlips; i++){
			if(rowInc == 0 && columnInc == 1){
				this.board[row][col+i] = this.curPlayer;
			}
			if(rowInc == 0 && columnInc == -1){
				this.board[row][col-i] = this.curPlayer;
			}
			if(rowInc == 1 && columnInc == 0){
				this.board[row+i][col] = this.curPlayer;
			}
			if(rowInc == 1 && columnInc == -1){
				this.board[row+i][col-i] = this.curPlayer;
			}
			if(rowInc == -1 && columnInc == 0){
				this.board[row-i][col] = this.curPlayer;
			}
			if(rowInc == -1 && columnInc == 1){
				this.board[row-i][col+i] = this.curPlayer;
			}
			if(rowInc == 1 && columnInc == 1){
				this.board[row+i][col+i] = this.curPlayer;

			}
			if(rowInc == -1 && columnInc == -1){
				this.board[row-i][col-i] = this.curPlayer;
			}
		}
		return countFlips;

	}


	/**
	 * Task 5: Implement this method
	 * @param row row of the move
	 * @param col column of the move
	 * @return true if the move was played, false if it failed to be played
	 */
	public boolean placeDisk(int row, int col) {
		// Keep this print. It will help you debug your code
		boolean b = false;
		if(updateMoveDisksInSingleDirection(row, col, 0, 1) != 0){
			b = true;
		}
		if(updateMoveDisksInSingleDirection(row, col, 0, -1) != 0){
			b = true;
		}
		if(updateMoveDisksInSingleDirection(row, col, 1, 0) != 0){
			b = true;
		}
		if(updateMoveDisksInSingleDirection(row, col, 1, -1) != 0){
			b = true;
		}
		if(updateMoveDisksInSingleDirection(row, col, -1, 0) != 0){
			b = true;
		}
		if(updateMoveDisksInSingleDirection(row, col, -1, 1) != 0){
			b = true;
		}
		if(updateMoveDisksInSingleDirection(row, col, 1, 1) != 0){
			b = true;
		}
		if(updateMoveDisksInSingleDirection(row, col, -1, -1) != 0){
			b = true;
		}
		if(!b){
			return false;
		}
		this.board[row][col] = this.curPlayer;
		switchToNextPlayablePlayer();

		//System.out.println("Place disk: " + this.curPlayer + " at row: " + row + " column: " + col);

		return true;
	}

//  ================================================================================  END OF PART A  =============================================================================== ======================================== ========================================

//  ================================================================================  START PART 2A  =============================================================================== ======================================== ========================================

	/**
	 * Task 7: Implement this method
	 * @param player - the player that is making the move
	 * @param row - the row of the move
	 * @param col - the column of the move
	 * @return the number of flips as a result of the move
	 */
	private int calcMoveFlips(int player, int row, int col) {
		int count = 0;
		for(int i = -1; i<=1; i++){
			for(int j = -1; j<=1; j++){
				count += calcFlipsInDirection(player, row, col, i, j);
			}
		}
		return count;
	}


	/**
	 * Task 8: Implement this method
	 *
	 * @return an array of all possible moves for the current player (the array
	 *         doesn't contain nulls). If there are no possible moves, return an
	 *         empty array. For each MoveScore the score will the number of flips
	 */
	public MoveScore[] getPossibleMoves(int player) {
		MoveScore[] arr = new MoveScore[64];
		int pos = 0;
		int factor = 0;
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(calcMoveFlips(player, i, j) != 0){
					arr[pos] = new MoveScore(i, j, calcMoveFlips(player, i, j)+factor);
					pos++;
				}
			}
		}
		MoveScore[] resArr = new MoveScore[pos];
		for(int i = 0; i<pos; i++){
			resArr[i] = arr[i];
		}
		return resArr;
	}

	/**
	 * @return an array of all possible moves for the current player
	 */
	public MoveScore[] getPossibleMoves() {
		return this.getPossibleMoves(this.curPlayer);
	}

	/**
	 * Task 9: Implement this method
	 *
	 * A game is over if none of the players have a move to play (i.e. no empty
	 * squares left on the board or none of the players have a valid move)
	 * @return true if the game is over, false otherwise
	 */
	public boolean isGameOver() {
		return getPossibleMoves(1).length == 0 && getPossibleMoves(2).length== 0;
	}

	/**
	 * Task 10: Implement this method
	 * Switches to the opponent player. If there's no move for the opponent player to play, the current player remains the same
	 * @return the current player after the switch
	 */
	public int switchToNextPlayablePlayer() {
		if(getPossibleMoves(opponentPlayer(this.curPlayer)).length != 0){
			this.curPlayer = opponentPlayer(this.curPlayer);

		}
		return this.curPlayer;
	}


	/**
	 * Task 11: Implement this method
	 * A player wins if the game is over and the player has more pieces on the board than the opponent
	 * @return -1 if the game is not over, 0 for tie, 1 if player 1 wins, 2 if player 2 wins
	 */
	public int getWinner() {
		if(!isGameOver()){
			return -1;
		}
		int countP1 = 0;
		int countP2 = 0;
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(this.board[i][j] == 1){
					countP1++;
				}
				if(this.board[i][j] == 2){
					countP2++;
				}
			}
		}
		if(countP2 > countP1){
			return 2;
		}
		if(countP1 > countP2){
			return 1;
		}
		return 0;
	}
}
