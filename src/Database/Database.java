package Database;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection con = null;


    public static void Database () {
        String URL = "jdbc:postgresql://localhost:5432/baticuisine";
        String USERNAME = "postgres";
        String PASSWORD = "root";

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static  Connection getConnection() {
        if (con == null) {
            Database();
            return con;
        }else {
            return con;
        }
    }



}
