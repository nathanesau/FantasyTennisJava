package fantasytennisjava;

import java.sql.*;
import java.util.*;
import org.apache.commons.lang3.tuple.*;

public class TennisDatabase {

    Connection conn;
    Statement stmt;

    TennisDatabase() {
        conn = null;
        stmt = null;
    }    

    private void createTableDraw() {
        try {
            stmt.executeUpdate("CREATE TABLE DRAW (id INTEGER PRIMARY KEY, Round INTEGER, Player1 TEXT, Player2 TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTablePlayer() {
        try {
            stmt.executeUpdate("CREATE TABLE PLAYER (id INTEGER PRIMARY KEY, Player TEXT, Seed TEXT, Country TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateTableDraw(ArrayList<Triple<Integer, String, String>> drawRowList) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO DRAW(Round, Player1, Player2) VALUES(?,?,?)");
            for(Triple<Integer, String, String> row : drawRowList) {
                pstmt.setInt(1, row.getLeft());
                pstmt.setString(2, row.getMiddle());
                pstmt.setString(3, row.getRight());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateTablePlayer(ArrayList<Triple<String, String, String>> playerRowList) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Player(Player, Seed, Country) VALUES(?, ?, ?)");
            for(Triple<String, String, String> row : playerRowList) {
                pstmt.setString(1, row.getLeft());
                pstmt.setString(2, row.getMiddle());
                pstmt.setString(3, row.getRight());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTableDraw(ArrayList<Triple<Integer, String, String>> drawRowList) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT Round, Player1, Player2 FROM DRAW");
            while(rs.next()) {
                Integer round = rs.getInt(1);
                String player1 = rs.getString(2);
                String player2 = rs.getString(3);
                drawRowList.add(Triple.of(round, player1, player2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTablePlayer(ArrayList<Triple<String, String, String>> playerRowList) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT Player, Seed, Country FROM PLAYER");
            while(rs.next()) {
                String player = rs.getString(1);
                String seed = rs.getString(2);
                String country = rs.getString(3);
                playerRowList.add(Triple.of(player, seed, country));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveDrawToDb(String dbName, TennisData data) {
        try {
            // create connection
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            stmt = conn.createStatement();
            
            // create tables
            this.createTableDraw();
            this.createTablePlayer();

            // populate tables
            this.populateTableDraw(data.drawRowList);
            this.populateTablePlayer(data.playerRowList);

            // close connection
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadDrawFromDb(String dbName, TennisData data) {
        try {
            // create connection
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            stmt = conn.createStatement();

            // load tables
            this.loadTableDraw(data.drawRowList);
            this.loadTablePlayer(data.playerRowList);
            
            // close connection
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}