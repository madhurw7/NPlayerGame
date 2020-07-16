
public class GameData {
	
	public static GameData gameData = new GameData();
	public int numberAnnounced = 0;
	public boolean noAnnouncedFlag = false;
	public int numPlayers = NumPlayers.numPlayers;
	public boolean[] playerSuccessFlag;
	public boolean[] playerChanceFlag;
	public boolean gameCompleteFlag = false;
	public boolean playerWonMiddle = false;
	
	public Object lock = new Object();
	
	
	private GameData() {
		playerSuccessFlag = new boolean[numPlayers];
		playerChanceFlag = new boolean[numPlayers];
		
	}
	
	public static GameData getInstance() {
		return gameData;
	}
}
