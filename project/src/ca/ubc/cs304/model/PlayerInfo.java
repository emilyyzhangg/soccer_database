package ca.ubc.cs304.model;

public class PlayerInfo {
    private final int playerID;
    private final String firstName;
    private final String lastName;
    private final int positionID;
    private final int age;
    private final int number;
    private final int teamID;

    public PlayerInfo(int playerID, int positionID, String firstName, String lastName, int age, int number, int teamID) {
        this.playerID = playerID;
        this.positionID = positionID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.number = number;
        this.teamID = teamID;
    }
    
    public int getPlayerID() {
        return this.playerID;
    }

    public String getFirstName() {
        return this.firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }

    public int getPositionID() {
        return this.positionID;
    }

    public int getAge() {
        return this.age;
    }

    public int getNumber() {
        return this.number;
    }
    
    public int getTeamID() {
        return this.teamID;
    }
}
