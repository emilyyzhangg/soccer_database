package ca.ubc.cs304.model;

public class Bet {
    private final int gameID;
    private final int userID;
    private final int teamID;
    private final int amount;

    public Bet(int userID, int gameID, int teamID, int amount) {
        this.gameID = gameID;
        this.userID = userID;
        this.teamID = teamID;
        this.amount = amount;
    }

    public int getGameID() {
        return this.gameID;
    }

    public int getUserID() {
        return this.userID;
    }

    public int getTeamID() {
        return this.teamID;
    }

    public int getAmount() {
        return this.amount;
    }
}
