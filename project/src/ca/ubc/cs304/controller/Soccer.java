package ca.ubc.cs304.controller;

import java.util.List;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.GUIDelegate;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.model.Bet;
import ca.ubc.cs304.model.GameInfo;
import ca.ubc.cs304.model.GameRefs;
import ca.ubc.cs304.model.PlayerInfo;
import ca.ubc.cs304.ui.GUI;
import ca.ubc.cs304.ui.LoginWindow;
import java.util.ArrayList;

public class Soccer implements LoginWindowDelegate, GUIDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;

    public Soccer() {
        this.dbHandler = new DatabaseConnectionHandler();
    }

    private void start() {
        this.loginWindow = new LoginWindow();
        this.loginWindow.showFrame(this);
    }

    /**
     * LoginWindowDelegate Implementation
     *
     * connects to Oracle database with supplied username and password
     */
    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window
            loginWindow.dispose();

            dbHandler.executeSqlScript("/Users/emilyzhang/project_m8t7n_q4g1w_r7y2b/soccer_database/src/ca/ubc/cs304/database/databaseSetup.sql");

            GUI gui = new GUI();
            gui.setUp(this);
            gui.setupGUI(this);

        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }
    
    public void insert(Bet bet) throws Exception {
        this.dbHandler.insert(bet);
    }

    public void delete(int userId, int gameID) throws Exception {
        this.dbHandler.delete(userId, gameID);
    }

    public void update(int userId, int gameID, int teamID, int amount) throws Exception {
        this.dbHandler.update(userId, gameID, teamID, amount);
    }

    public Bet[] displayBets(String query) throws Exception {
        return this.dbHandler.displayBets(query);
    }

    public PlayerInfo[] displayPlayerInfo(String query) throws Exception {
        return this.dbHandler.displayPlayerInfo(query);
    }

    public GameInfo[] displayGameInfo(String query) throws Exception {
        return this.dbHandler.displayGameInfo(query);
    }

    public GameRefs[] displayGamesJoin(String query) throws Exception {
        return this.dbHandler.displayGamesJoin(query);
    }

    public PlayerInfo[] displayGroupBy(String query) throws Exception {
        return this.dbHandler.displayGroupBy(query);
    }

    public Integer[] displayDivide(String query) {
        return this.dbHandler.displayDivide(query);
    }
 
    public PlayerInfo[] displayNest(String query) throws Exception{
        return this.dbHandler.displayNest(query);
    }

    public String findAttributes(String query) throws Exception {
        return this.dbHandler.findAttributes(query);
    }

    public ArrayList<ArrayList<String>> displayProject(String query, List<String> attributeList) throws Exception {
        return this.dbHandler.displayProject(query, attributeList);
    }

    public void finish() {
        this.dbHandler.close();
        this.dbHandler = null;
        System.exit(0);
    }

    public static void main(String[] args) {
        Soccer soccer = new Soccer();
        soccer.start();
    }
    
}
