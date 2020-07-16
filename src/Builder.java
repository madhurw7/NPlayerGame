import java.util.ArrayList;

public interface Builder {
	public ArrayList<Integer> getTicket();
	public ArrayList<Integer> ticket = null;
	void buildTicket(int maxNum);
}
