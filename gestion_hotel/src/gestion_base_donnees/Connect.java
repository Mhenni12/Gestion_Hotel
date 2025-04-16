package gestion_base_donnees;

import java.sql.*;
import javax.swing.JOptionPane;


public class Connect {
    static Connection connection;

    public Connect() {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/hotel";
        String username = "aziz";
        String password = "azerty";

        try {
            // Load and register MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection(url, username, password);

            //! To test connectitvity
            //TODO: remove this part
            // //SQL statement to display a table
            // String viewClientTableSQL = "SELECT * FROM client";
            // Statement stmt = connection.createStatement();
            // ResultSet rs = stmt.executeQuery(viewClientTableSQL);

            // while (rs.next()) {
            //     System.out.println(rs.getString(1));
            // }
        } 
        catch(SQLException e){ 
            JOptionPane.showMessageDialog(null, "Connection failed or SQL error occurred.","Error", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "MySQL JDBC Driver not found.","Error", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace(); 
        }
    }

    //getter
    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) throws SQLException {
        new Connect();
    }

}