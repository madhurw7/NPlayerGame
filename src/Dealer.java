import java.util.ArrayList;

public class Dealer implements Runnable {

	private GameData gD;
	private int numberAnnounced  = 0;
	private int countNums = 0; 
	private Player[] players;
	private ArrayList<Integer> generatedNumbers;
	private final int MAXNO;
	
	public Dealer(GameData gameData, Player[] players) {
		this.gD = gameData;
		this.players = players;
		MAXNO = GameSettings.getMAXNO();
		Builder builder = new TicketBuilder(MAXNO);
		this.generatedNumbers = builder.getTicket();
	}
	
	
	@Override
	public void run() {
		synchronized(gD.lock) {
			while(anyPlayerSuccess(gD.playerSuccessFlag) && countNums < 10) {
				
				gD.noAnnouncedFlag = false;
				
				for(int i = 0; i < gD.numPlayers; i++) {
					gD.playerChanceFlag[i] = false;
				}
				
				this.setAnnouncedNumber(generatedNumbers.get(countNums));//Generate a random number here instead and sleep this thread for some time
				System.out.println("Dealer has announced Number " + this.numberAnnounced);
				this.countNums++;
				try {
					Thread.sleep(1000); //Assignment specifies 1 min, but for practical purposes
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
				}
				gD.numberAnnounced = this.numberAnnounced;
				this.setAnnouncedNumber(0);
				
				gD.noAnnouncedFlag = true;
				
				gD.lock.notifyAll();
				
				while(anyPlayerChance(gD.playerChanceFlag)) {
					try {
						gD.lock.wait();
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}	
			}
			printFinalGameState();
			
			
			gD.gameCompleteFlag = true;
			
			gD.lock.notifyAll();
			
			System.exit(0);
		}

	}
	
	private boolean anyPlayerSuccess(boolean[] x) {
		boolean anyPS = !x[0];
		for(int i = 1; i < x.length; i++) {
			anyPS = anyPS && !x[i];
		}
		return anyPS;
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
		
		boolean someWon = false;
		for(int i = 0; i < gD.playerSuccessFlag.length; i++) {
			if(gD.playerSuccessFlag[i]) {
				System.out.println("PLAYER-"+(i+1)+" HAS WON!");
				someWon = true;
				break;
			}
		}
		
		if(!someWon) System.out.println("NONE OF THE PLAYERS WON.");
		
		
		for(int i = 0; i < players.length; i++) {
			players[i].printFinalState();
		}
		
		System.out.println("Total Numbers Generated: " + this.countNums);
	}

}
