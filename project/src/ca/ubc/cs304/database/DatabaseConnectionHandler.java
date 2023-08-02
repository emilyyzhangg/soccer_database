package ca.ubc.cs304.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import ca.ubc.cs304.model.Bet;
import ca.ubc.cs304.model.GameInfo;
import ca.ubc.cs304.model.GameRefs;
import ca.ubc.cs304.model.PlayerInfo;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	// Use this version of the ORACLE_URL if you are running the code off of the server
//	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
	// Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	
	private Connection connection = null;
	
	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
	
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
	
	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}
	
			connection = DriverManager.getConnection(ORACLE_URL, username, password);
            //runSQL(connection);
			connection.setAutoCommit(false);
			System.out.println("\nConnected to Oracle!");

			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
    }


	public void insert(Bet bet) throws Exception {
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO Bet VALUES(?, ?, ?, ?)");
			statement.setInt(1, bet.getUserID());
			statement.setInt(2, bet.getGameID());
			statement.setInt(3, bet.getTeamID());
			statement.setInt(4, bet.getAmount());
			statement.executeUpdate();
            connection.commit();
            statement.close();
		} catch (SQLException exception) {
			System.out.println(EXCEPTION_TAG + " " + exception.getMessage());
            SQLException exc = new SQLException("Error with insert, please try again" + exception.getMessage());
            rollbackConnection();
		}
	}

    public void delete(int userId, int gameID) throws Exception {
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM Bet WHERE userID = ? AND gameID = ?");
			statement.setInt(1, userId);
			statement.setInt(2, gameID);
			int rowCount = statement.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Bet " + userId + ", " + gameID + " does not exist!");
            }
            connection.commit();
            statement.close();
		} catch (SQLException exception) {
			System.out.println(EXCEPTION_TAG + " " + exception.getMessage());
            SQLException ex = new SQLException("Error with delete, please try again" + exception.getMessage());
            rollbackConnection();
            throw ex;
		}
    }

    public void update(int userId, int gameID, int teamID, int amount) throws Exception {
		try {
			if (teamID != 9999) {
				PreparedStatement statement = connection.prepareStatement("UPDATE Bet SET teamID = ? WHERE gameID = ? AND userID = ?");
				statement.setInt(1, teamID);
				statement.setInt(2, gameID);
				statement.setInt(3, userId);
				int rowCount = statement.executeUpdate();
				if (rowCount == 0) {
					System.out.println(WARNING_TAG + " Bet " + userId + ", " + gameID + " does not exist!");
				}

				connection.commit();
				statement.close();
			} else if (amount != 9999) {
				PreparedStatement statement = connection.prepareStatement("UPDATE Bet SET amount = ? WHERE gameID = ? AND userID = ?");
				statement.setInt(1, amount);
				statement.setInt(2, gameID);
				statement.setInt(3, userId);
				int rowCount = statement.executeUpdate();
				if (rowCount == 0) {
					System.out.println(WARNING_TAG + " Bet " + userId + ", " + gameID + " does not exist!");
				}

				connection.commit();
				statement.close();
			}
		} catch (SQLException exception) {
			System.out.println(EXCEPTION_TAG + " " + exception.getMessage());
            SQLException ex = new SQLException("Error with update, please try again" + exception.getMessage());
            rollbackConnection();
            throw ex;
		}
    }

    public String findAttributes(String query) throws Exception {
        StringBuilder result = new StringBuilder();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            result.append("\n");
            while (rs.next()) {
                result.append(rs.getString("COLUMN_NAME")).append("\n");  
            }

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            SQLException ex = new SQLException("Error with findAttributes, please try again" + e.getMessage());
            rollbackConnection();
            throw ex;
        }
        return String.valueOf(result);
    }

    public Bet[] displayBets(String query) throws Exception {
        ArrayList<Bet> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()) {
                Bet bet = new Bet(rs.getInt("userID"),
                        rs.getInt("gameID"),
                        rs.getInt("teamID"),
                        rs.getInt("amount"));
                result.add(bet);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            SQLException ex = new SQLException("Error with displayBets, please try again" + e.getMessage());
            rollbackConnection();
            throw ex;
        }
        return result.toArray(new Bet[result.size()]);
    }

    public PlayerInfo[] displayPlayerInfo(String query) throws Exception {
        ArrayList<PlayerInfo> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()) {
                PlayerInfo player = new PlayerInfo(rs.getInt("playerID"),
                                                rs.getInt("positionID"),
                                                rs.getString("firstName"),
                                                rs.getString("lastName"),
                                                rs.getInt("age"),
                                                rs.getInt("jerseyNumber"),
                                                rs.getInt("teamID"));
                result.add(player);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            SQLException ex = new SQLException("Error with displayPlayerInfo, please try again" + e.getMessage());
            rollbackConnection();
            throw ex;
        }
        return result.toArray(new PlayerInfo[result.size()]);
    }

    public GameInfo[] displayGameInfo(String query) throws Exception {
        ArrayList<GameInfo> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()) {
                GameInfo game = new GameInfo(rs.getInt("gameID"),
                                                rs.getInt("awayTeam"),
                                                rs.getInt("homeTeam"),
                                                rs.getString("gameDay"),
                                                rs.getString("gameLocation"));
                result.add(game);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            SQLException ex = new SQLException("Error with displayGameInfo, please try again" + e.getMessage());
            rollbackConnection();
            throw ex;
        }
        return result.toArray(new GameInfo[result.size()]);
    }

    public ArrayList<ArrayList<String>> displayProject(String query, List<String> attributeList) throws Exception {
        ArrayList<ArrayList<String>> values = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()) {
                ArrayList<String> val = new ArrayList<>();
                for (String attribute: attributeList) {
                    val.add(rs.getString(attribute));
                }
                values.add(val);
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            SQLException ex = new SQLException("Error with displayProject, please try again" + e.getMessage());
            rollbackConnection();
            throw ex;
        }
        return values;
    }

    public GameRefs[] displayGamesJoin(String query) {
        ArrayList<GameRefs> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()) {
                GameRefs ref = new GameRefs(
                    rs.getInt("gameID"),
					rs.getInt("refereeID")
                );
                result.add(ref);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result.toArray(new GameRefs[result.size()]);
    }

    public PlayerInfo[] displayGroupBy(String query) {
        ArrayList<PlayerInfo> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while(rs.next()) {
                PlayerInfo player = new PlayerInfo(
                    0, 0, " ", " ",
					rs.getInt("min_age"),
					0, rs.getInt("teamID")
                );
                result.add(player);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result.toArray(new PlayerInfo[result.size()]);
    }

    public Integer[] displayDivide(String query) {
        ArrayList<Integer> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()) {
                int userId = rs.getInt("userID");
                result.add(userId);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result.toArray(new Integer[result.size()]);
    }

    public PlayerInfo[] displayNest(String query) throws Exception {
		ArrayList<PlayerInfo> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                PlayerInfo player = new PlayerInfo (
                        0,
                        0,
                        "",
						"",
                        rs.getInt("avg_age"),
                        0,
                        rs.getInt("teamID")
                );
                result.add(player);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw new Exception("Error with displayNest, please try again" + e.getMessage());
        }
        return result.toArray(new PlayerInfo[result.size()]);
    }

	private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
    
    public boolean executeDBScripts(String aSQLScriptFilePath, Statement stmt) throws IOException,SQLException {
        boolean isScriptExecuted = false;
        try {
            BufferedReader in = new BufferedReader(new FileReader(aSQLScriptFilePath));
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str = in.readLine()) != null) {
                sb.append(str + "\n ");
            }
                in.close();
                stmt.executeUpdate(sb.toString());
                isScriptExecuted = true;
        } catch (Exception e) {
            System.err.println("Failed to Execute" + aSQLScriptFilePath +". The error is"+ e.getMessage());
        }
        return isScriptExecuted;
        }

    public void executeSqlScript(String sqlFilePath) {
        try {
            // Create a statement object for executing SQL statements
            Statement stmt = connection.createStatement();

            // Execute the SQL script file
            executeDBScripts(sqlFilePath, stmt);

            // Close the statement object
            stmt.close();
        } catch (IOException | SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
