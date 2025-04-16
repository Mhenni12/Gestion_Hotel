package utilisateur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestion_base_donnees.Connect;

public class Utilisateur {

    // Attributes
    private int idUser;
    private String email;
    private String password;
    private int idClient;
    private String typeUser;

    // Constructors
    public Utilisateur() {}

    public Utilisateur(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Utilisateur(int idUser,  String email, String password, int idClient, String typeUser){
        this.idUser = idUser;
        this.email = email;
        this.password = password;
        this.idClient = idClient;
        this.typeUser = typeUser;
    }

    // Getters and Setters
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    
    // Interrogation de la base de données
    // Check whether an email exists in the data base
	public static boolean isEmailExists(String email) {
        try {
            Connection connection = new Connect().getConnection();

            String selectEmailSQL = "SELECT * FROM utilisateur WHERE email_utilisateur=?";

            PreparedStatement statement = connection.prepareStatement(selectEmailSQL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            boolean returnValue = resultSet.next();
            connection.close();
            return returnValue; 

        } catch (SQLException ex) {
            ex.printStackTrace();
            return true; 
        }
    }

    // Extract user from the data base knowing their email and password
    public Utilisateur getUserFromDB() {
        try {
            String querySQL = "SELECT * FROM utilisateur WHERE email_utilisateur=? and mot_de_passe=?";

            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(querySQL);
            preparedStmt.setString(1, getEmail());
            preparedStmt.setString(2, getPassword());
            ResultSet resultSet = preparedStmt.executeQuery();

            Utilisateur user = null;
            while (resultSet.next()) {
                user = new Utilisateur();
                user.setIdUser(resultSet.getInt("id_utilisateur"));
                user.setEmail(resultSet.getString("email_utilisateur"));
                user.setPassword(resultSet
                .getString("mot_de_passe"));
                user.setIdClient(resultSet.getInt("id_client"));
                user.setTypeUser(resultSet.getString("type_utilisateur"));
            }
            connection.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // toString method
    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUser=" + idUser +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", idClient=" + idClient +
                ", typeUser='" + typeUser + '\'' +
                '}';
    }

    //TODO: modify the main method
    public static void main(String[] args) {
        Utilisateur user = new Utilisateur("amine.benali@example.com", "mdp123");

        //System.out.println(user.isEmailExists(user.getEmail()));

        user = user.getUserFromDB();
        System.out.println(user);
    }
}
