import java.util.Scanner;

//import java.io.IOException;

public class GameApp {

	public static void main(String[] args) {
		
		System.out.println("Enter the number of Players(n)...");
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(System.in);
			int x = Integer.parseInt(scanner.nextLine());
			if(x <= 1) throw new Exception();
			GameSettings.setNumPlayers(x);
		}catch(NumberFormatException e) {
			System.out.println("Invalid Input, defaulting to n = 5.");
		}catch(Exception e){
			System.out.println("Invalid Input, defaulting to n = 5.");
		}finally {
			if(scanner != null) scanner.close();
		}
		
		
		
		final GameData game = GameData.getInstance();
		
		int numPlayers = GameSettings.getNumPlayers();
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
