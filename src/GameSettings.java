
public class GameSettings {
	private static int numPlayers = 5;
	private final static int MAXNO = 10;
	private final static int WIN_LIM = 3;
	public static void setNumPlayers(int num){
		numPlayers = num;
	}
	public static int getNumPlayers() {
		return numPlayers;
	}
	public static int getMAXNO() {
		return MAXNO;
	}
	public static int getWIN_LIM() {
		return WIN_LIM;
	}
}
