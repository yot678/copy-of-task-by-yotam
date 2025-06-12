package reversi;

import java.util.Random;

/**
* This is a template for a bot.
* Give your bot a unique name and rename this class and the file accordingly.
* 
* PLEASE name your bot with a name that ends with "Bot" (e.g., FlipMasterBot or ReversiConquerorBot).
*/
public class PrometheusBot implements ReversiBot {

    private final ReversiGame game;

    public PrometheusBot(ReversiGame game) {
		this.game = game;
	}
	public static int opponentPlayer(int player) {
		if (player == 1){
			return 1;
		} else{
			return 2;
		}

	}

	public boolean checkFlipInSpot(MoveScore m, ReversiGame g, int row, int col){
		int[][] board = g.getBoard();
		if(board[row][col] != 0){
			return false;
		}
		ReversiGame nG = new ReversiGame(g);
		nG.placeDisk(m.getRow(), m.getColumn());
		if(nG.getBoard()[row][col] != 0){
			return true;
		}
		return false;
	}
	public boolean checkEdges(MoveScore m, ReversiGame g){
		for (int col = 1; col < 7; col++) {
			if(checkFlipInSpot(m, g, 0, col)){
				return true;
			}
		}
		for (int col = 1; col < 7; col++) {
			if(checkFlipInSpot(m, g, 7, col)){
				return true;
			}
		}
		for (int row = 1; row < 7; row++) {
			if(checkFlipInSpot(m, g, row, 0)){
				return true;
			}
		}
		for (int row = 1; row < 7; row++) {
			if(checkFlipInSpot(m, g, row, 7)){
				return true;
			}
		}
		return false;

	}
	public MoveScore getNextMove() {
		int maxScore = -111111;
		MoveScore maxMove = game.getPossibleMoves()[0];
		int[] scores = new int[game.getPossibleMoves().length];
		MoveScore[] moves = new MoveScore[game.getPossibleMoves().length];
		int pos = 0;
		for(MoveScore m1 : game.getPossibleMoves()){
			int score = 0;
			//get game move.x, move.y == 0 do move check if x y == player
			ReversiGame g1 = new ReversiGame(game);
			if(checkFlipInSpot(m1, g1, 0, 0) || checkFlipInSpot(m1, g1, 0, 7)
			||checkFlipInSpot(m1, g1, 7, 0) || checkFlipInSpot(m1, g1, 7, 7)){
				score +=25;
			}
			if(checkEdges(m1, g1)){
				score += 4;
			}
			if(checkFlipInSpot(m1, g1, 1, 1) || checkFlipInSpot(m1, g1, 1, 6)
					||checkFlipInSpot(m1, g1, 6, 1) || checkFlipInSpot(m1, g1, 6, 6)){
					score -=10;
			}

			g1.placeDisk(m1.getRow(), m1.getColumn());
			if(g1.getWinner() == game.getCurPlayer()){
				score += 100000000;
				return m1;
			}
			score -= g1.getPossibleMoves(opponentPlayer(game.getCurPlayer())).length*4;
			for(MoveScore m2 : g1.getPossibleMoves(opponentPlayer(game.getCurPlayer()))){
				ReversiGame g2 = new ReversiGame(game);
				if(checkFlipInSpot(m2, g2, 0, 0) || checkFlipInSpot(m2, g2, 0, 7)
						||checkFlipInSpot(m2, g2, 7, 0) || checkFlipInSpot(m2, g2, 7, 7)){
					score -=25;
				}
				if(checkEdges(m2, g2)){
					score -= 4;
				}
				if(checkFlipInSpot(m2, g2, 1, 1) || checkFlipInSpot(m2, g2, 1, 6)
						||checkFlipInSpot(m2, g2, 6, 1) || checkFlipInSpot(m2, g2, 6, 6)){
					score +=10;
				}
				if(g2.getWinner() == g2.getCurPlayer()){
					score -= 100000000;
					break;
				}
				g2.placeDisk(m2.getRow(), m2.getColumn());

				score += g2.getPossibleMoves(opponentPlayer(g1.getCurPlayer())).length*4;
				for(MoveScore m3 : g2.getPossibleMoves(opponentPlayer(g1.getCurPlayer()))) {
					//get game move.x, move.y == 0 do move check if x y == player
					ReversiGame g3 = new ReversiGame(game);
					if (checkFlipInSpot(m3, g3, 0, 0) || checkFlipInSpot(m3, g3, 0, 7)
							|| checkFlipInSpot(m3, g3, 7, 0) || checkFlipInSpot(m3, g3, 7, 7)) {
						score += 25;
					}
					if(checkEdges(m3, g3)){
						score += 4;
					}
					if(checkFlipInSpot(m3, g3, 1, 1) || checkFlipInSpot(m3, g3, 1, 6)
							||checkFlipInSpot(m3, g3, 6, 1) || checkFlipInSpot(m3, g3, 6, 6)){
						score -=10;
					}
					g3.placeDisk(m3.getRow(), m3.getColumn());
					if (g3.getWinner() == game.getCurPlayer()) {
						score += 100000000;
					}
					score -= g3.getPossibleMoves(opponentPlayer(g2.getCurPlayer())).length * 4;
					for(MoveScore m4 : g3.getPossibleMoves(opponentPlayer(g2.getCurPlayer()))) {
						ReversiGame g4 = new ReversiGame(game);
						if (checkFlipInSpot(m4, g4, 0, 0) || checkFlipInSpot(m4, g4, 0, 7)
								|| checkFlipInSpot(m4, g4, 7, 0) || checkFlipInSpot(m4, g4, 7, 7)) {
							score -= 25;
						}
						if (checkEdges(m4, g4)) {
							score += 4;
						}
						if (checkFlipInSpot(m4, g4, 1, 1) || checkFlipInSpot(m4, g4, 1, 6)
								|| checkFlipInSpot(m4, g4, 6, 1) || checkFlipInSpot(m4, g4, 6, 6)) {
							score += 10;
						}
						if (g4.getWinner() == g4.getCurPlayer()) {
							score -= 100000000;
							break;
						}
						g4.placeDisk(m4.getRow(), m4.getColumn());
						score += g4.getPossibleMoves(opponentPlayer(g3.getCurPlayer())).length * 4;
					}
				}
			}
			moves[pos] = m1;
			scores[pos] = score;
			pos++;
		}
		int max = Integer.MIN_VALUE;

		for(int i = 0; i<scores.length; i++){
			if(scores[i] > max){
				max = scores[i];
				maxMove = moves[i];
			}
		}
//		int maxFlips = -1111;
//		for(int i = 0; i<scores.length; i++){
//			if(scores[i] == max){
//				if(maxMove.getScore() > maxFlips){
//					maxMove = moves[i];
//					maxFlips = maxMove.getScore();
//				}
//
//			}
//		}
		return maxMove;
	}

	public MoveScore getRandomMove(){
		MoveScore[] possibleMoves = this.game.getPossibleMoves();
		if (possibleMoves.length == 0) {
			return null;
		}
		Random rand = new Random();
		return possibleMoves[rand.nextInt(possibleMoves.length)];		
	}
	public MoveScore getNextGreedyMove(){
		MoveScore[] moves = game.getPossibleMoves(game.getCurPlayer());
		if(moves.length == 0){
			return null;
		}
		MoveScore bestMove = moves[0];
		for (MoveScore move : moves){
			if(move.getScore() > bestMove.getScore()){
				bestMove = move;
			}
		}
		return bestMove;
	}
	public MoveScore getGreedyTwoStepsMove(){
			int maxDiff = Integer.MIN_VALUE;
			ReversiGame newGame;
			MoveScore bestMove = game.getPossibleMoves(game.getCurPlayer())[0];
			int curP = game.getCurPlayer();
			int m1Score = 0;
			int m2Score = 0;
			for (MoveScore m1 : game.getPossibleMoves(curP)) {
				newGame = new ReversiGame(game);
				m1Score = m1.getScore();
				newGame.placeDisk(m1.getRow(), m1.getColumn());
				for (MoveScore m2 : newGame.getPossibleMoves(opponentPlayer(game.getCurPlayer()))) {
					m2Score = m2.getScore();
					if ((m1Score - m2Score) > maxDiff) {
						maxDiff = m1Score - m2Score;
						bestMove = m1;
					}
				}

			}
			return bestMove;
	}
	public boolean checkIfTileIsFlipedByMove(MoveScore m, int player, ReversiGame game, int row, int col){
		if(game.getBoard()[row][col] != 0){
			return false;

		}
		ReversiGame nGame = new ReversiGame(game);
		nGame.placeDisk(m.getRow(), m.getColumn());
		if(nGame.getBoard()[row][col] == player){
			return true;
		}
		return false;
	}
	public MoveScore getGreedyTwoStepsMoveCorners() {
		int maxDiff = Integer.MIN_VALUE;
		ReversiGame newGame;
		MoveScore bestMove = getGreedyTwoStepsMove();
		int curP = game.getCurPlayer();
		for (MoveScore m1 : game.getPossibleMoves(curP)) {
			newGame = new ReversiGame(game);
			newGame.placeDisk(m1.getRow(), m1.getColumn());
			if (checkIfTileIsFlipedByMove(m1, game.getCurPlayer(), game, 0, 0)
					|| checkIfTileIsFlipedByMove(m1, game.getCurPlayer(), game, 0, 7)
					|| checkIfTileIsFlipedByMove(m1, game.getCurPlayer(), game, 7, 0)
					|| checkIfTileIsFlipedByMove(m1, game.getCurPlayer(), game, 7, 7)) {
				bestMove = m1;
			}
		}
		return bestMove;
	}
}