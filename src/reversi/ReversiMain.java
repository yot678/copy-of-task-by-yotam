package reversi;
import java.util.*;
/**
 * The main class for the Reversi game.
 * This class contains the main method, which serves as the entry point for the game.
 * It initializes the game board, processes user/bot input for moves, prints the board and declares the winner in the end.
 **/
public class ReversiMain {
	public static void main(String[] args) {
		int count1 = 0;
		int count2 = 0;
		for(int i = 1; i<=18; i++){
			System.out.println(i);
			ReversiGame m = new ReversiGame();
			PrometheusBot bot = new PrometheusBot(m);
			while(!m.isGameOver()) {
				if(m.getCurPlayer() == 1){
					m.placeDisk(bot.getRandomMove().getRow(), bot.getRandomMove().getColumn());
				} else if(!m.isGameOver()) {
					MoveScore m1 = bot.getNextMove();
					m.placeDisk(m1.getRow(), m1.getColumn());
				}

			}
			System.out.println(i + ": game won by: " + m.getWinner());
			if(m.getWinner() == 1){
				count1++;
			} else if(m.getWinner() == 2){
				count2++;
			}
		}
		System.out.println("one wins: " + count1);
		System.out.println("two wins: "+count2);

	}
}