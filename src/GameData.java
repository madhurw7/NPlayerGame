import java.util.ArrayList;

public class GameData {
	
	private static GameData gameData = null;
	private ArrayList<Integer> numsAnnounced;
	private boolean noAnnouncedFlag = false;
	private final int numPlayers = GameSettings.getNumPlayers();
	private boolean[] playerChanceFlag;
	private boolean gameCompleteFlag = false;
	private boolean somePlayerWonFlag = false;
	private int winnerId;
	
	public Object lock = new Object();
	
	
	private GameData() {
		playerChanceFlag = new boolean[numPlayers];
		numsAnnounced = new ArrayList<Integer>();
		
	}
	
	public static synchronized GameData getInstance() {
		if(gameData == null) {
			gameData = new GameData();
		}
		return gameData;
	}
	
	
	public void setNoAnnouncedFlag(boolean x) {
		this.noAnnouncedFlag = x;
	}
	
	public boolean getNoAnnouncedFlag() {
		return this.noAnnouncedFlag;
	}
	
	public int getWinnedId() {
		return this.winnerId;
	}
	
	public void setWinnerId(int id) {
		this.winnerId = id;
	}
	
	public void setGameCompleteFlag(boolean x) {
		this.gameCompleteFlag = x;
	}
	
	public boolean getGameCompleteFlag() {
		return this.gameCompleteFlag;
	}
	
	public boolean getSomePlayerWon() {
		return this.somePlayerWonFlag;
	}
	
	public void setSomePlayerWon(boolean x) {
		this.somePlayerWonFlag = x;
	}
	
	public boolean[] getPlayerChanceFlagArray() {
		return this.playerChanceFlag;
	}
	
	public boolean getPlayerChanceFlag(int id) {
		return this.playerChanceFlag[id];
	}
	
	public void setPlayerChanceFlag(boolean val, int idx) {
		playerChanceFlag[idx] = val;
	}
	
	public int getLastNumAnnounced() {
		return numsAnnounced.get(numsAnnounced.size() - 1);
	}
	
	public void setNumAnnounced(int num) {
		numsAnnounced.add(num);
	}
	
}
