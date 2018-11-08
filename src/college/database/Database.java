package college.database;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;

import java.io.IOException;

import java.util.HashMap;

import java.io.BufferedReader;

public class Database
{
    private static Connection conn;
    private static Statement stmt;
    private static PreparedStatement prepStmt;

    protected static HashMap<String, Integer> daysMap = new HashMap<>();
    static {
        daysMap.put("Mon", 1);
        daysMap.put("Tue", 2);
        daysMap.put("Wed", 3);
        daysMap.put("Thu", 4);
        daysMap.put("Fri", 5);
    }

    private final String DB_NAME = "bunkers";
    private final String DB_PATH = "jdbc:mariadb://localhost/" + DB_NAME;
    private final String SCHEMA_PATH = "/college/database/schema/";


    public Database()
            throws SQLException, IOException
    {
        conn = DriverManager.getConnection(DB_PATH, "root", "");
        stmt = conn.createStatement();

        stmt.executeQuery(readFromSQLFile("CSE_1.sql"));
        stmt.executeQuery(readFromSQLFile("CSE_2.sql"));
        stmt.executeQuery(readFromSQLFile("CSE_3.sql"));

        stmt.executeQuery(readFromSQLFile("Mon.sql"));
        stmt.executeQuery(readFromSQLFile("Tue.sql"));
        stmt.executeQuery(readFromSQLFile("Wed.sql"));
        stmt.executeQuery(readFromSQLFile("Thu.sql"));
        stmt.executeQuery(readFromSQLFile("Fri.sql"));

        stmt.executeQuery(readFromSQLFile("Rooms.sql"));
    }

    private String readFromSQLFile(String filename)
            throws IOException
    {
        StringBuilder s = new StringBuilder();
        InputStream in = Database.class.getResourceAsStream(SCHEMA_PATH + filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            s.append(line);
        }

        return s.toString();
    }

    public void initialize_tables(String table, String sno)
            throws SQLException
    {
        final String QUERY = "INSERT INTO " + table +
                "(sno)" + " VALUES(?)";
        prepStmt = conn.prepareStatement(QUERY);
        prepStmt.setString(1, sno);
        prepStmt.executeUpdate();
    }

    public void fillChoices(String sec, String day, String f1, String f2, String f3,
                            String f4, String f5, String f6, String f7)
            throws SQLException
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("first", f1);
        map.put("second", f2);
        map.put("third", f3);
        map.put("fourth", f4);
        map.put("fifth", f5);
        map.put("sixth", f6);
        map.put("seventh", f7);

        String QUERY;
        for (String x : map.keySet()) {
            QUERY = "UPDATE " + day +
                    " set " + x + "='True' where sno=?";
            prepStmt = conn.prepareStatement(QUERY);
            prepStmt.setString(1, map.get(x));
            prepStmt.executeUpdate();
        }

        // Update the ClassRoutine Table
        QUERY = "SELECT * from " + sec + " WHERE sno=" + Database.daysMap.get(day);
        ResultSet r = stmt.executeQuery(QUERY);
        if (r.next())
            QUERY = "UPDATE " + sec +
                    " SET first=?, second=?, third=?, fourth=?, fifth=?, sixth=?, seventh=? " +
                    " WHERE sno=?";
        else
            QUERY = "INSERT INTO " + sec +
                    "(first, second, third, fourth, fifth, sixth, seventh, sno)" +
                    " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        prepStmt = conn.prepareStatement(QUERY);

        prepStmt.setString(1, f1);
        prepStmt.setString(2, f2);
        prepStmt.setString(3, f3);
        prepStmt.setString(4, f4);
        prepStmt.setString(5, f5);
        prepStmt.setString(6, f6);
        prepStmt.setString(7, f7);
        prepStmt.setInt(8, Database.daysMap.get(day));

        prepStmt.executeUpdate();
    }

    public ResultSet readRoomField(String sec, String day)
            throws SQLException
    {
        final String QUERY = "SELECT * from " + sec +
                " WHERE sno=" + Database.daysMap.get(day);
        return stmt.executeQuery(QUERY);
    }

   public ResultSet getEmptyRooms(String day, String field)
           throws SQLException
   {
       final String QUERY = "SELECT * from " + day +
               " where "+ field + "='FALSE'";
       System.out.println(QUERY);
       return stmt.executeQuery(QUERY);
   }

   public void addRoom(String room)
       throws SQLException
   {
       final String QUERY = "INSERT INTO Rooms(room) VALUES(?)";
       prepStmt = conn.prepareStatement(QUERY);
       prepStmt.setString(1, room);
       prepStmt.executeUpdate();
   }

   public ResultSet getRooms()
           throws SQLException
   {
       return stmt.executeQuery("SELECT * from Rooms");
   }
}
