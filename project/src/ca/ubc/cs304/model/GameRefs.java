package ca.ubc.cs304.model;

public class GameRefs {
    private int gameID;
    private int refereeID;

    public GameRefs(int gameID, int refereeID) {
        this.gameID = gameID;
        this.refereeID = refereeID;
    }

    public int getGameID() {
        return this.gameID;
    }

    public int getRefereeID() {
        return this.refereeID;
    }
    
}
