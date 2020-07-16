//import java.io.IOException;

public class GameApp {

	public static void main(String[] args) {
		final GameData game = GameData.getInstance();
		
		
//		NumPlayers.setNumPlayers(7);
		
		int numPlayers = NumPlayers.numPlayers;
		final Player[] players = new Player[numPlayers];
		final Dealer dealer = new Dealer(game, players);
		
		for(int i = 0; i < numPlayers; i++) {
			players[i] = new Player(game, i);
		}
		
		Thread dealerThread = new Thread(dealer);
		Thread[] playerThreads = new Thread[numPlayers];
		
		
		for(int i = 0; i < numPlayers; i++) {
			playerThreads[i] = new Thread(players[i]);
		}
		dealerThread.start();
		for(int i = 0; i < numPlayers; i++) {
			playerThreads[i].start();
		}
		
	}

}
