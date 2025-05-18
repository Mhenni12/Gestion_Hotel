package chambre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import gestion_base_donnees.Connect;

public class Chambre {
    private int numChambre;
    private int capacity;
    private String typeChambre;
    private String status;
    private int etage;
    private double prix_par_jour; 

    public Chambre() {
    	
    }

    //TODO: prix par jour
    public Chambre(int numChambre, int capacity, String typeChambre, String status, int etage) {
        this.numChambre = numChambre;
        this.capacity = capacity;
        this.typeChambre = typeChambre;
        this.status = status;
        this.etage = etage;
    }

    public int getNumChambre() {
        return this.numChambre;
    }

    public void setNumChambre(int numChambre) {
        this.numChambre = numChambre;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getTypeChambre() {
        return this.typeChambre;
    }

    public void setTypeChambre(String typeChambre) {
        this.typeChambre = typeChambre;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrix_par_jour() {
        return this.prix_par_jour;
    }

    public void setPrix_par_jour(double prix_par_jour) {
        this.prix_par_jour = prix_par_jour;
    }

    public int getEtage() {
        return this.etage;
    }

    public void setEtage(int etage) {
        this.etage = etage;
    }

    public static ArrayList<Chambre> getAvailableRoomsFromDB(String CheckIn, String CheckOut, int capacity) {
        ArrayList<Chambre> listRooms = null;
        try {
            String query = "SELECT *\r\n"
    				+ "FROM chambre\r\n"
    				+ "WHERE etat_chambre = 'reservee'\r\n"
    				+ "AND capacite >= ? \r\n"
    				+ "AND num_chambre NOT IN (\r\n"
    				+ "    SELECT num_chambre\r\n"
    				+ "    FROM reservation\r\n"
    				+ "    WHERE (check_in_date <= ? AND check_out_date >= ?)\r\n"
    				+ "    OR (check_in_date BETWEEN ? AND ?)\r\n"
    				+ "    OR (check_out_date BETWEEN ? AND ?)\r\n"
    				+ ")\r\n"
    				+ "UNION\r\n"
    				+ "SELECT *\r\n"
    				+ "FROM chambre\r\n"
    				+ "WHERE etat_chambre = 'libre'\r\n"
    				+ "AND capacite >= ?";
            Connection connection = new Connect().getConnection();

            listRooms = new ArrayList<Chambre>();

            PreparedStatement preparedStmt = connection.prepareStatement(query);

            preparedStmt.setInt(1,capacity);
    		preparedStmt.setInt(8,capacity);
            preparedStmt.setString(2,CheckOut);
    		preparedStmt.setString(5,CheckOut);
    		preparedStmt.setString(7,CheckOut);
    		preparedStmt.setString(3, CheckIn);
    		preparedStmt.setString(4, CheckIn);
    		preparedStmt.setString(6, CheckIn);

            ResultSet resultSet = preparedStmt.executeQuery();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return listRooms;

    }
}
