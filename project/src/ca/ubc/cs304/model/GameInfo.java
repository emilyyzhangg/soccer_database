package ca.ubc.cs304.model;

public class GameInfo {
    private int gameID;
    private int awayTeam;
    private int homeTeam;
    private String gameDay;
    private String gameLocation;

    public GameInfo(int gameID, int awayTeam, int homeTeam, String gameDay, String gameLocation) {
        this.gameID = gameID;
        this.awayTeam = awayTeam;
        this.homeTeam = homeTeam;
        this.gameDay = gameDay;
        this.gameLocation = gameLocation;
    }

    public int getGameID() {
        return this.gameID;
    }

    public int getAwayTeam() {
        return this.awayTeam;
    }

    public int getHomeTeam() {
        return this.homeTeam;
    }

    public String getGameDay() {
        return this.gameDay;
    }

    public String getGameLocation() {
        return this.gameLocation;
    }
}
