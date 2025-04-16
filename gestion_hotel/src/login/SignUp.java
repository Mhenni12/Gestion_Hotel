package login;

import javax.swing.*;
import javax.swing.border.*;

import gestion_base_donnees.Connect;
import utilisateur.Utilisateur;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SignUp extends JFrame implements ActionListener{
    
    private JTextField firstNameField, lastNameField, telephoneField, addressField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton joinNowButton, loginButton;
    private JPanel headerPanel, formPanel;

    private boolean isValidEmail(String email) {
	    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	    return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
	    String phoneRegex = "^\\d{8}$"; 
	    return phoneNumber.matches(phoneRegex);
	}

    public SignUp() {
        // Set frame properties
        setTitle("RestHive");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create components
        createHeader();
        createWelcomeLabel();
        createLoginLink();
        createForm();
        createJoinButton();
        
        // Set frame size and center on screen
        //TODO: revist size across all windows
        setSize(850, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void createHeader() {
        headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        headerPanel.setBackground(new Color(255, 193, 7)); // Yellow background
        
        // Create logo and label
        ImageIcon logoIcon = new ImageIcon("gestion_hotel/src/images/hotelLogo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        JLabel titleLabel = new JLabel("RestHive");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        
        headerPanel.add(logoLabel);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
    }
    
    private void createWelcomeLabel() {
        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 28));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);
    }
    
    private void createLoginLink() {
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel alreadyLabel = new JLabel("Already a user?");
        loginButton = new JButton("Login Here.");
        loginButton.setBorder(null);
        loginButton.setFocusable(false);
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setForeground(Color.BLUE);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 12));

        // Add action listener
        loginButton.addActionListener(this);
        
        loginPanel.add(alreadyLabel);
        loginPanel.add(loginButton);
        
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));
        formPanel.add(loginPanel);
        
        add(formPanel, BorderLayout.SOUTH);
    }
    
    private void createForm() {
        // Create input fields with labels
        JPanel namePanel = new JPanel(new GridLayout(1, 2, 20, 0));
        
        JPanel firstNamePanel = new JPanel(new BorderLayout(0, 5));
        JLabel firstNameLabel = new JLabel("First Name");
        firstNameField = new JTextField();
        firstNameField.setPreferredSize(new Dimension(200, 30));
        firstNamePanel.add(firstNameLabel, BorderLayout.NORTH);
        firstNamePanel.add(firstNameField, BorderLayout.CENTER);
        
        JPanel lastNamePanel = new JPanel(new BorderLayout(0, 5));
        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameField = new JTextField();
        lastNameField.setPreferredSize(new Dimension(200, 30));
        lastNamePanel.add(lastNameLabel, BorderLayout.NORTH);
        lastNamePanel.add(lastNameField, BorderLayout.CENTER);
        
        namePanel.add(firstNamePanel);
        namePanel.add(lastNamePanel);
        
        // Contact info panel
        JPanel contactPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        
        JPanel telephonePanel = new JPanel(new BorderLayout(0, 5));
        JLabel telephoneLabel = new JLabel("Telephone");
        telephoneField = new JTextField();
        telephoneField.setPreferredSize(new Dimension(200, 30));
        telephonePanel.add(telephoneLabel, BorderLayout.NORTH);
        telephonePanel.add(telephoneField, BorderLayout.CENTER);
        
        JPanel addressPanel = new JPanel(new BorderLayout(0, 5));
        JLabel addressLabel = new JLabel("Address");
        addressField = new JTextField();
        addressField.setPreferredSize(new Dimension(200, 30));
        addressPanel.add(addressLabel, BorderLayout.NORTH);
        addressPanel.add(addressField, BorderLayout.CENTER);
        
        contactPanel.add(telephonePanel);
        contactPanel.add(addressPanel);
        
        // Email panel
        JPanel emailPanel = new JPanel(new BorderLayout(0, 5));
        JLabel emailLabel = new JLabel("Email");
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 30));
        emailPanel.add(emailLabel, BorderLayout.NORTH);
        emailPanel.add(emailField, BorderLayout.CENTER);
        
        // Password panel
        JPanel passwordsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        
        JPanel passwordPanel = new JPanel(new BorderLayout(0, 5));
        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        
        // Add eye icon for password visibility
        JPanel passwordFieldPanel = new JPanel(new BorderLayout());
        passwordFieldPanel.add(passwordField, BorderLayout.CENTER);

        JButton showPasswordButton = createEyeButton(passwordField);
        passwordFieldPanel.add(showPasswordButton, BorderLayout.EAST);
        
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordPanel.add(passwordFieldPanel, BorderLayout.CENTER);
        
        JPanel confirmPanel = new JPanel(new BorderLayout(0, 5));
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(200, 30));
        
        // Add eye icon for confirm password visibility
        JPanel confirmFieldPanel = new JPanel(new BorderLayout());
        confirmFieldPanel.add(confirmPasswordField, BorderLayout.CENTER);

        JButton showConfirmButton = createEyeButton(confirmPasswordField);
        confirmFieldPanel.add(showConfirmButton, BorderLayout.EAST);
        
        confirmPanel.add(confirmLabel, BorderLayout.NORTH);
        confirmPanel.add(confirmFieldPanel, BorderLayout.CENTER);
        
        passwordsPanel.add(passwordPanel);
        passwordsPanel.add(confirmPanel);
        
        // Add all panels to form
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(namePanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(contactPanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(emailPanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(passwordsPanel);
        formPanel.add(Box.createVerticalStrut(20));
    }
    
    private JButton createEyeButton(JPasswordField field) {
        JButton eyeButton = new JButton(new ImageIcon("gestion_hotel/src/images/icons8-eye-24.png"));
        eyeButton.setBorderPainted(false);
        eyeButton.setContentAreaFilled(false);
        eyeButton.setFocusPainted(false);
        eyeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        eyeButton.addActionListener(new ActionListener() {
            private boolean visible = false;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                visible = !visible;
                field.setEchoChar(visible ? (char)0 : '•');
            }
        });
        
        return eyeButton;
    }
    
    private void createJoinButton() {
        joinNowButton = new JButton("Join Now");
        joinNowButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        joinNowButton.setBackground(new Color(255, 193, 7)); // Yellow background
        joinNowButton.setForeground(Color.BLACK);
        joinNowButton.setFocusPainted(false);
        joinNowButton.setBorderPainted(false);
        joinNowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        joinNowButton.setPreferredSize(new Dimension(150, 40));
        
        // Round the corners of the button
        joinNowButton.setBorder(new EmptyBorder(5, 15, 5, 15));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(joinNowButton);
        
        formPanel.add(buttonPanel);
        formPanel.add(Box.createVerticalStrut(20));
        
        // Add action listener
        //TODO: add action listener
        joinNowButton.addActionListener(this);
        // joinNowButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         // Registration logic would go here
        //         JOptionPane.showMessageDialog(SignUp.this, 
        //             "Registration submitted!", "RestHive", JOptionPane.INFORMATION_MESSAGE);
        //     }
        // });
    }
    
    //TODO: remove the main() method
    public static void main(String[] args) {
        // Use the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create the application
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SignUp();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == joinNowButton) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String telephone = telephoneField.getText();
            String address = addressField.getText();

            String email = emailField.getText();
            String type = "client";

            String password = new String(passwordField.getPassword());
            String passwordConfirmation = new String(confirmPasswordField.getPassword());

            // check if passwords check
            if (!password.equals(passwordConfirmation)) {
                JOptionPane.showMessageDialog(null, "Passwords dosent match!", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // check whether any field is blank(contains either only whitespaces or is empty)
            if (
                firstName.isBlank() || lastName.isBlank() ||
                email.isBlank() || telephone.isBlank() ||
                address.isBlank() || password.isBlank() ||
                passwordConfirmation.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the required fields.", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // validate the email
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // validate PhoneNumber
            if (!isValidPhoneNumber(telephone)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid phone number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // check whether the user exists using their email 
            if (Utilisateur.isEmailExists(email)) {
                JOptionPane.showMessageDialog(null, "Email already exists !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }  
            PreparedStatement insertUserStatement = null; //TODO: find a better solution
            try {
                // create a connection
                Connection connection = new Connect().getConnection();

                int idClient = -1; //TODO: find a better solution

                // Create a new client (We know that it doesn't exist already because of the isEmailExists method)

                String insertClientQuery = "INSERT INTO client (nom_client, prenom_client, adresse_client, tel_client) VALUES (?, ?, ?, ?)";

                PreparedStatement insertClientStatement = connection.prepareStatement(insertClientQuery);

                insertClientStatement.setString(1, lastName);
                insertClientStatement.setString(2, firstName);
                insertClientStatement.setString(3, address);
                insertClientStatement.setString(4, telephone);

                insertClientStatement.executeUpdate();

                // retieve the client id generated by default
                //TODO: find another way to get the user id 
                String getClientIdQuery = "SELECT id_client FROM client WHERE tel_client=?";

                PreparedStatement getClientIdStatement = connection.prepareStatement(getClientIdQuery);
                getClientIdStatement.setString(1, telephone);
  
                ResultSet resultSet = getClientIdStatement.executeQuery();
                while (resultSet.next()) {
                    idClient = resultSet.getInt(1);
                    System.out.println(idClient);
                }

                //TODO: delete it
                // ResultSet generatedKeys = insertClientStatement.getGeneratedKeys();
                // if (generatedKeys.next()) {
                //     idClient = generatedKeys.getInt("id_client");

                //     System.out.println(idClient);
                // }
                insertClientStatement.close();


                // create user
                String insertUserQuery = "INSERT INTO utilisateur (email_utilisateur, mot_de_passe, id_client, type_utilisateur) VALUES (?, ?, ?, ?)";

                insertUserStatement = connection.prepareStatement(insertUserQuery);

                insertUserStatement.setString(1, email);
                insertUserStatement.setString(2, password);
                insertUserStatement.setInt(3, idClient);
                insertUserStatement.setString(4, type);

                insertUserStatement.executeUpdate();

                // Display successful signup message
                JOptionPane.showMessageDialog(null, "Welcome, account is successfully created", "Welcome", JOptionPane.INFORMATION_MESSAGE);

                connection.close();
                // TODO: add a gui and dispose of this frame
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, "An error occured while connecting to the data base", "Error", JOptionPane.ERROR_MESSAGE);

                exception.printStackTrace();
            } finally {
                if (insertUserStatement != null) {
                    try {
                        insertUserStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        }
        else if (e.getSource() == loginButton) {
            // Open login form
            new Login();
            dispose(); // Close login window
        }
    }
}

