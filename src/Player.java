import java.util.ArrayList;
import java.util.HashSet;

public class Player implements Runnable {

	private GameData gD;
	private int id;
	private final static int MAXNO = 10;
	private int totalNumbersFound;
	public final static int WIN_LIM = 3;
	private HashSet<Integer> removedNums = new HashSet<Integer>(); 
	
	private ArrayList<Integer> ticket; //Make Arra
	
	public Player(GameData gameData, int id) {
		this.id = id;
		this.gD = gameData;
		this.totalNumbersFound = 0;
		Builder builder = new TicketBuilder(MAXNO);
		this.ticket = builder.getTicket();
		printPlayerTicket();
//		this.ticket = Use TicketBuilder; 
	}
	
	
	@Override
	public void run() {
		synchronized(gD.lock) {
			while(!gD.gameCompleteFlag) {
				while(!gD.noAnnouncedFlag || gD.playerChanceFlag[id]) {
					try {
						gD.lock.wait();
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if(!gD.gameCompleteFlag && !gD.playerWonMiddle) {
					
					for(int i = 0; i < MAXNO; i++) {
						if(!removedNums.contains(i) && ticket.get(i) == gD.numberAnnounced) {
							this.totalNumbersFound++;
							removedNums.add(i);
							break;
						}
					}
					
					if(this.totalNumbersFound == WIN_LIM) {
						gD.playerSuccessFlag[id] = true;
						gD.playerWonMiddle = true;
					}
					
					
				}
				gD.playerChanceFlag[id] = true;
				
				
				gD.lock.notifyAll();
				
			}
		}

	}
	
	private void printPlayerTicket() {
		System.out.print("Player "+ (this.id+1) + "'s ticket: [");
		for(int i = 0; i < ticket.size()-1; i++) {
			System.out.print(ticket.get(i) +", ");
		}
		System.out.print(ticket.get(ticket.size() - 1) + "]");
		System.out.println();
	}
	
	public void printFinalState() {
		System.out.println("Matches found for Player " + (this.id +1) + ": " + this.totalNumbersFound);
	}

}
