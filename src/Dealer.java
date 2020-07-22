import java.util.Random;

public class Dealer implements Runnable {

	private GameData gD;
	private int numberAnnounced  = 0;
	private int countNums = 0; 
	private Player[] players;
	
	public Dealer(GameData gameData, Player[] players) {
		this.gD = gameData;
		this.players = players;
	}
	
	
	@Override
	public void run() {
		synchronized(gD.lock) {
			while(!gD.getSomePlayerWon() && countNums < 10) {
				
				gD.setNoAnnouncedFlag(false);
				
				for(int i = 0; i < players.length; i++) {
					gD.setPlayerChanceFlag(false, i);
				}
				
				this.setAnnouncedNumber(randInt(0, 50));
				System.out.println("Dealer has announced Number " + this.numberAnnounced);
				this.countNums++;
				

				gD.setNumAnnounced(this.numberAnnounced);
				this.setAnnouncedNumber(0);
				
				gD.setNoAnnouncedFlag(true);
				
				gD.lock.notifyAll();
				
				try {
					Thread.sleep(1000); //Assignment specifies 1 min, but for practical purposes
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
				}
				
				while(anyPlayerChance(gD.getPlayerChanceFlagArray())) {
					try {
						gD.lock.wait();
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}	
			}
			gD.setGameCompleteFlag(true);
			printFinalGameState();
			
			
			
			
			gD.lock.notifyAll();
			
		}

	}
	
	
	private boolean anyPlayerChance(boolean[] x) {
		boolean anyPC = !x[0];
		for(int i = 1; i < x.length; i++) {
			anyPC = anyPC || !x[i];
		}
		return anyPC;
	}
	
	public void setAnnouncedNumber(int i) {
		this.numberAnnounced = i;
	}
	
	
	private void printFinalGameState() {
		System.out.println();
		System.out.println("----FINAL GAME STATE----");
		System.out.println();
		
		
		
		if(gD.getSomePlayerWon()) System.out.println("PLAYER-"+(gD.getWinnedId()+1)+" HAS WON!");
		else System.out.println("NONE OF THE PLAYERS WON.");
		
		
		for(int i = 0; i < players.length; i++) {
			players[i].printFinalState();
		}
		
		System.out.println("Total Numbers Generated: " + this.countNums);
	}
	
	private static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

}
