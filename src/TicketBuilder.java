import java.util.ArrayList;
import java.util.Random;

public class TicketBuilder implements Builder {
	private ArrayList<Integer> ticket;
	
	TicketBuilder(int maxNum){
		this.buildTicket(maxNum);
	}
	
	public void buildTicket(int maxNum) {
		this.ticket = new ArrayList<Integer>();
		for(int i = 0; i < maxNum; i++) {
			int p = randInt(0, 50); 
			this.ticket.add(p);
		}
		
	}
	
	public ArrayList<Integer> getTicket() {
		return this.ticket; //TO-DO 
	}
	
	private static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}
