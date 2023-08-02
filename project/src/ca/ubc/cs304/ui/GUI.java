package ca.ubc.cs304.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ca.ubc.cs304.delegates.GUIDelegate;
import ca.ubc.cs304.model.Bet;
import ca.ubc.cs304.model.GameInfo;
import ca.ubc.cs304.model.GameRefs;
import ca.ubc.cs304.model.PlayerInfo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class GUI extends JFrame implements ActionListener {
    private static final String Quit = "quit";
    private static final String Insert = "insert";
    private static final String Delete = "delete";
    private static final String Update = "update";
    private static final String Select = "select";
    private static final String Project = "project";
    private static final String Join = "join";
    private static final String Divide = "divide";
    private static final String GroupBy = "group by";
    private static final String Having = "having";
    private static final String Nest = "nest";
    int userID;

    JTextArea playersTable;
    JTextArea gamesTable;
    JTextArea betsTable;
    JTextArea queryResults;
    JLabel resultsTitle;
    JTextArea projectBox;

    GUIDelegate delegate;


    // constructor
    public GUI() {
        super("Soccer Database");
    }

    public void setUp(GUIDelegate delegate) {
        this.delegate = delegate;
    }

    // sets up the GUI
    public void setupGUI(GUIDelegate delegate) {
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
        setMinimumSize(new Dimension(1000, 800));
        setMaximumSize(new Dimension(4000, 3500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
        this.setVisible(true);

        addButtons();
        addTables();

        try {   
            this.userID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter your User ID: "));
        } catch (Exception exp) {
            this.userID = Integer.parseInt(JOptionPane.showInputDialog(this, "That's the wrong userID, try again: "));
        }
    
        displayBets(this.userID);
        displayPlayerInfo();
        displayGames();
    }

    private void addButtons() {
        JButton quitButton = new JButton("Quit");
        quitButton.setActionCommand(Quit);
        quitButton.addActionListener(this);
        add(quitButton);

        JButton insertButton = new JButton("Insert Row");
        insertButton.setActionCommand(Insert);
        insertButton.addActionListener(this);
        add(insertButton);

        JButton deleteButton = new JButton("Delete Row");
        deleteButton.setActionCommand(Delete);
        deleteButton.addActionListener(this);
        add(deleteButton);

        JButton updateButton = new JButton("Update table");
        updateButton.setActionCommand(Update);
        updateButton.addActionListener(this);
        add(updateButton);

        JButton selectButton = new JButton("Select Rows");
        selectButton.setActionCommand(Select);
        selectButton.addActionListener(this);
        add(selectButton);

        JButton projectButton = new JButton("Project Rows");
        projectButton.setActionCommand(Project);
        projectButton.addActionListener(this);
        add(projectButton);

        JButton joinButton = new JButton("Join Tables");
        joinButton.setActionCommand(Join);
        joinButton.addActionListener(this);
        add(joinButton);

        JButton divideButton = new JButton("Divide");
        divideButton.setActionCommand(Divide);
        divideButton.addActionListener(this);
        add(divideButton);

        JButton groupByButton = new JButton("Group By");
        groupByButton.setActionCommand(GroupBy);
        groupByButton.addActionListener(this);
        add(groupByButton);

        JButton havingButton = new JButton("Having");
        havingButton.setActionCommand(Having);
        havingButton.addActionListener(this);
        add(havingButton);

        JButton nestButton = new JButton("Nest Query");
        nestButton.setActionCommand(Nest);
        nestButton.addActionListener(this);
        add(nestButton);
    }

    private void addTables() {
        playersTable = new JTextArea(20, 15);
        playersTable.setEditable(false);
        JScrollPane playersScroll = new JScrollPane(playersTable);
        playersScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(playersScroll);

        gamesTable = new JTextArea(20, 15);
        gamesTable.setEditable(false);
        JScrollPane gamesScroll = new JScrollPane(gamesTable);
        gamesScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(gamesScroll);

        betsTable = new JTextArea(20, 15);
        betsTable.setEditable(false);
        JScrollPane betsScroll = new JScrollPane(betsTable);
        betsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(betsScroll);

        queryResults = new JTextArea(18, 30);
        queryResults.setEditable(false);
        JScrollPane queryScroll = new JScrollPane(queryResults);
        queryScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(queryScroll);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        handleQuit(e);
        handleInsert(e);
        handleDelete(e);
        handleUpdate(e);
        handleSelect(e);
        handleProject(e);
        handleJoin(e);
        handleDivide(e);
        handleGroupBy(e);
        handleHaving(e);
        handleNest(e);
    }

    private void handleQuit(ActionEvent event) {
        if (event.getActionCommand().equals(Quit)) {
            this.delegate.finish();
        }
    }

    private void handleInsert(ActionEvent event) {
        if (event.getActionCommand().equals(Insert)) {
            try {
                int gameID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the ID of the game you're betting on: "));
                int team = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the id of the team that you think will win: "));
                int amount = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the amount (CAD) that you'd like to bet: "));

                Bet bet = new Bet(this.userID, gameID, team, amount);
                delegate.insert(bet);
                displayBets(userID);
            } catch (Exception err) {
                showError(err);
            }
        }
    }

    private void handleDelete(ActionEvent event) {
        if (event.getActionCommand().equals(Delete)) {
            try {
                int gameID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the gameID of the bet you'd like to delete: "));

                if (gameID == 0) {
                    displayBets(this.userID);
                } else {
                    delegate.delete(this.userID, gameID);
                }
                displayBets(userID);
            } catch (Exception err) {
                System.out.println("In here 2");
                showError(err);
            }
        }
    }

    private void handleUpdate(ActionEvent event) {
        if (event.getActionCommand().equals(Update)) {
            try {
                int gameID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the gameID of the bet you'd like to update: "));
                if (gameID == 0) {
                    displayBets(this.userID);
                } else {
                    String change = JOptionPane.showInputDialog(this, "Would you like to edit the 'team' you bet on, or the 'amount'? Enter your choice: ");
                    if (change.equals("team")) {
                        int team = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the teamID of the new team you're betting on: "));
                        delegate.update(this.userID, gameID, team, 9999);
                    } else if (change.equals("amount")) {
                        int amount = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the new amount that you're betting: "));
                        delegate.update(this.userID, gameID, 9999, amount);
                    } else {
                        showError(new Exception("Error w/ handleUpdate"));
                    }
                }
                displayBets(this.userID);
            } catch(Exception err) {
                showError(err);
            }
        }
    }

    private void displayBets(int userID) {
        try{
            Bet[] bets = delegate.displayBets("SELECT * from Bet WHERE userID = " + userID );
            StringBuilder res = new StringBuilder();
            res.append("Bets you've made: \n");
            for (Bet bet: bets) {
                res.append("GameID: ")
                    .append(bet.getGameID())
                    .append("\n")
                    .append("Team bet on: ")
                    .append(bet.getTeamID())
                    .append("\n")
                    .append("Amount: ")
                    .append(bet.getAmount())
                    .append("\n")
                    .append("\n");
            }
            betsTable.setText(String.valueOf(res));
        } catch (Exception err) {
            showError(err);
        }
    }

    private void displayPlayerInfo() {
        try{
            PlayerInfo[] playerInfos = delegate.displayPlayerInfo("SELECT * FROM PlayerInfo");
            StringBuilder res = new StringBuilder();
            res.append("Players in the Database \n");
            for (PlayerInfo player: playerInfos) {
                res.append("PlayerID: ")
                    .append(player.getPlayerID()).append("\n")
                    .append("Position ID: ")
                    .append(player.getPositionID()).append("\n")
                    .append("Team ID: ")
                    .append(player.getTeamID()).append("\n")
                    .append("\n");
            }
            playersTable.setText(String.valueOf(res));
        } catch (Exception err) {
            showError(err);
        }
    }

    private void displayGames() {
        try {
            GameInfo[] gameInfos = delegate.displayGameInfo("SELECT * FROM GameInfo");
            StringBuilder res = new StringBuilder();
            res.append("Games in the Database \n");

            for (GameInfo game: gameInfos) {
                res.append("GameID: ").append(game.getGameID()).append("\n")
                .append("Away Team: ").append(game.getAwayTeam()).append("\n")
                .append("Home Team: ").append(game.getHomeTeam()).append("\n")
                .append("Game Day: ").append(game.getGameDay()).append("\n")
                .append("Game Location: ").append(game.getGameLocation()).append("\n")
                .append("\n");
            }
            gamesTable.setText(String.valueOf(res));
        } catch (Exception err) {
            showError(err);
        }
    }

    private void handleSelect(ActionEvent event) {
       if (event.getActionCommand().equals(Select)) {
            try {
                int teamID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the teamID associated with the bet you'd like to view: "));
                String query = "SELECT * FROM Bet WHERE teamID = " + teamID + " AND userId = " + this.userID;
                Bet[] bets = delegate.displayBets(query);
                StringBuilder res = new StringBuilder();
                res.append("Bets with teamID ").append(teamID);
                for (Bet bet: bets) {
                    res.append("\n")
                        .append("GameID: ")
                        .append(bet.getGameID())
                        .append("\n")
                        .append("Team bet on: ")
                        .append(bet.getTeamID())
                        .append("\n")
                        .append("Amount: ")
                        .append(bet.getAmount())
                        .append("\n")
                        .append("\n");
                }
                queryResults.setText(String.valueOf(res));
            } catch (Exception err) {
                showError(err);
            }
        }
    }

    private void handleProject(ActionEvent event) {
        try {
            if (event.getActionCommand().equals(Project)) {
                String selection = JOptionPane.showInputDialog(this, "Would you like to look at 'League', 'Team', 'RefereeInfo', 'RefereeExperience', 'Coach', 'Position', \n'PlayerInfo', 'PlayerGoals', 'PlayerExperience', 'Goalie', 'Defender','Midfielder', 'Striker', \n'GameRefs', 'GameInfo', 'DBUser', 'Bet', 'Participates', 'PlaysFor', 'Plays'?: ");

                String query = "select column_name from USER_TAB_COLUMNS WHERE table_name='" + selection.toUpperCase() + "'";
                String attributes = delegate.findAttributes(query);
    
                String attribute = JOptionPane.showInputDialog(this, "Enter the list of attribute you'd like to look at in this exact form: 'att1, att2, att3' using CAMEL CASE: " + attributes);

                List<String> attributeList = Arrays.asList(attribute.split(", "));

                String query2 = "SELECT " + attribute + " FROM " + selection;
                ArrayList<ArrayList<String>> res = delegate.displayProject(query2, attributeList);
    
                StringBuilder result = new StringBuilder();
                result.append("Projection results: \n");
    
                for (ArrayList<String> list: res) {
                    result.append("Row: \n");
                    for (String str: list) {          
                        result.append(str).append(", ");
                    }
                    result.append("\n").append("\n");
                }
                queryResults.setText(String.valueOf(result));
            }
        } catch (Exception e) {
            showError(e);
        }   
    }


    // finds all games refereed by each referee with a minimum amount of experience
    private void handleJoin(ActionEvent event) {
        if (event.getActionCommand().equals(Join)) {
            try {
                String yearsExperience = JOptionPane.showInputDialog(this, "We'll be looking at all the games refereed by each referee. Enter the minimum years of experience you'd like each referee to have");
                String query = "SELECT DISTINCT g.gameID, g.refereeID FROM RefereeExperience r, GameRefs g WHERE g.refereeID = r.refereeID AND r.yearsOfExperience >= " + yearsExperience;

                GameRefs[] gameRefs = delegate.displayGamesJoin(query);

                StringBuilder str = new StringBuilder();
                str.append("Games Refereed by Referees with ").append(yearsExperience).append(" years of experience: \n");
                for (GameRefs game: gameRefs) {
                    str.append("GameID: ").append(game.getGameID()).append("\n");
                }

                queryResults.setText(String.valueOf(str));
            } catch (Exception err) {
                showError(err);
            }
        }
    }

    // finds users who have placed bets on all games that have atleast minAccountBalance in their balance.
    private void handleDivide(ActionEvent event) {
        if (event.getActionCommand().equals(Divide)) {
            try {
                String minAccountBalance = JOptionPane.showInputDialog(this, "Enter the minimum account balance for the users");
                String query = "SELECT DISTINCT userID " +
                        "FROM Bet " +
                        "WHERE NOT EXISTS ( " +
                        "    SELECT gameID " +
                        "    FROM GameInfo " +
                        "    WHERE NOT EXISTS ( " +
                        "        SELECT userID " +
                        "        FROM Bet b " +
                        "        WHERE b.userID = Bet.userID AND b.gameID = GameInfo.gameID " +
                        "    ) " +
                        ") AND userID IN (SELECT userID FROM DBUser WHERE accountBalance >= " + minAccountBalance + ")";

                Integer[] userIds = delegate.displayDivide(query);

                StringBuilder str = new StringBuilder();
                str.append("Users who have bet on all games and have a minimum of $" + minAccountBalance + " in their account\n");
                for (Integer userId : userIds) {
                    str.append("User ID: ").append(userId).append("\n");
                }
                str.append("\n");
                queryResults.setText(String.valueOf(str));
            } catch (Exception err) {
                showError(err);
            }
        }
    }


    // finds the age of the youngest player in each team
    private void handleGroupBy(ActionEvent event) {
        if (event.getActionCommand().equals(GroupBy)) {
            try {
                String query = "SELECT teamID, MIN(age) AS min_age FROM PlayerInfo GROUP BY teamID";
                PlayerInfo[] players = delegate.displayGroupBy(query);

                StringBuilder str = new StringBuilder();
                str.append("The age of the youngest player in each team: ").append("\n");
                for (PlayerInfo player: players) {
                    str.append("TeamID ").append(player.getTeamID()).append(", age: ").append(player.getAge()).append("\n");
                }
                queryResults.setText(String.valueOf(str));
            } catch (Exception err) {
                showError(err);
            }
        }
    }

    // finds youngest player of large teams (>10 players) only
    private void handleHaving(ActionEvent event) {
        if (event.getActionCommand().equals(Having)) {
            try {
                String query = "SELECT teamID, MIN(age) as min_age FROM PlayerInfo GROUP BY teamID HAVING COUNT(*) > 10";
                PlayerInfo[] players = delegate.displayGroupBy(query);

                StringBuilder str = new StringBuilder();
                str.append("The age of the youngest player of large teams with over 10 players: ").append("\n");
                for (PlayerInfo player: players) {
                    str.append("TeamID ").append(player.getTeamID()).append(", age: ").append(player.getAge()).append("\n");
                }
                queryResults.setText(String.valueOf(str));
            } catch (Exception err) {
                showError(err);
            }
        }
    }

    // finds team with the oldest avg age
    private void handleNest(ActionEvent event) {
        if (event.getActionCommand().equals(Nest)) {
            try {
                String query = "SELECT AVG(age) AS avg_age, teamID FROM PlayerInfo GROUP BY teamID HAVING AVG(age) = (SELECT MAX(AVG(age)) as max_age FROM PlayerInfo GROUP BY teamID)";
                PlayerInfo[] players = delegate.displayNest(query);

                StringBuilder str = new StringBuilder();
                str.append("Team with the oldest average age: ").append("\n");
                for (PlayerInfo player: players) {
                    str.append("teamID ").append(player.getTeamID()).append(" has the oldest avg age with age: ").append(player.getAge()).append("\n");
                }
                queryResults.setText(String.valueOf(str));
            } catch (Exception err) {
                showError(err);
            }
        }
    }

    private void showError(Exception err) {
        JOptionPane.showMessageDialog(this, "Sorry, there may be a problem with your entry or a problem on our end with the following error. Please try again\n" + err);
    }

    
}
