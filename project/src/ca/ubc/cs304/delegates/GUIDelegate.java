package ca.ubc.cs304.delegates;

import java.util.List;

import ca.ubc.cs304.model.Bet;
import ca.ubc.cs304.model.GameInfo;
import ca.ubc.cs304.model.GameRefs;
import ca.ubc.cs304.model.PlayerInfo;
import java.util.ArrayList;

public interface GUIDelegate {
    public void insert(Bet bet)  throws Exception;
    public void delete(int userId, int gameID) throws Exception;
    public void update(int userId, int gameID, int teamID, int amount) throws Exception;
    public Bet[] displayBets(String query) throws Exception;
    public GameRefs[] displayGamesJoin(String query) throws Exception;
    public PlayerInfo[] displayGroupBy(String query) throws Exception;
    public Integer[] displayDivide(String query) throws Exception;
    public PlayerInfo[] displayNest(String query) throws Exception;
    public void finish();
    public PlayerInfo[] displayPlayerInfo(String query) throws Exception;
    public GameInfo[] displayGameInfo(String query) throws Exception;
    public String findAttributes(String query) throws Exception;
    public ArrayList<ArrayList<String>> displayProject(String query, List<String> attributeList) throws Exception;
    
}
