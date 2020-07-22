import java.util.ArrayList;
import java.util.HashSet;

public class Player implements Runnable {

	private GameData gD;
	private int id;
	private final int MAXNO;
	private int totalNumbersFound;
	private final int WIN_LIM;
	private HashSet<Integer> removedNums = new HashSet<Integer>(); 
	
	private ArrayList<Integer> ticket;
	
	public Player(GameData gameData, int id) {
		this.id = id;
		this.gD = gameData;
		this.totalNumbersFound = 0;
		
		MAXNO = GameSettings.getMAXNO();
		WIN_LIM = GameSettings.getWIN_LIM();
		
		Builder builder = new TicketBuilder(MAXNO);
		this.ticket = builder.getTicket();
		printPlayerTicket();
//		this.ticket = Use TicketBuilder; 
	}
	
	
	@Override
	public void run() {
		synchronized(gD.lock) {
			while(!gD.getGameCompleteFlag()) {
				while(!gD.getGameCompleteFlag() && (!gD.getNoAnnouncedFlag() || gD.getPlayerChanceFlag(id)) ) {
					try {
						gD.lock.wait();
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if(!gD.getGameCompleteFlag() && !gD.getSomePlayerWon()) {
					
					for(int i = 0; i < MAXNO; i++) {
						if(!removedNums.contains(i) && ticket.get(i) == gD.getLastNumAnnounced()) {
							this.totalNumbersFound++;
							removedNums.add(i);
							break;
						}
					}
					
					if(this.totalNumbersFound == WIN_LIM) {
						gD.setWinnerId(id);
						gD.setSomePlayerWon(true);
					}
					
					
				}
				gD.setPlayerChanceFlag(true, id);
				
				
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
