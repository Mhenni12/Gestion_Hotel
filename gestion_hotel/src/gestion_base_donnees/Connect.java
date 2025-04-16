package gestion_base_donnees;


import java.sql.*;


public class Connect {
    public static void main(String[] args) {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/hotel";
        String username = "aziz";
        String password = "azerty";

        // Load and register MySQL JDBC driver
        try {
            // Load and register MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the MySQL database!");

            // SQL statement to display a table
            String viewClientTableSQL = "SELECT * FROM client";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(viewClientTableSQL);

            // Get metadata to dynamically retrieve column count and names
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Print column headers
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println();

            // Print each row of data
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }

            // Clean up
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed or SQL error occurred.");
            e.printStackTrace();
        }
    }
}