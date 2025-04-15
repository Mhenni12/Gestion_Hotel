package login;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class SignUp extends JFrame {
    
    private JTextField firstNameField, lastNameField, telephoneField, regionField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton joinNowButton, loginButton;
    private JPanel headerPanel, formPanel;
    
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
        setSize(800, 600);
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

        //TODO: add action listener
        //! Don't forget to rename it if you renamed the file
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open login form
                new Login();
                dispose(); // Close login window
            }
        });
        
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
        
        JPanel regionPanel = new JPanel(new BorderLayout(0, 5));
        JLabel regionLabel = new JLabel("Adress");
        regionField = new JTextField();
        regionField.setPreferredSize(new Dimension(200, 30));
        regionPanel.add(regionLabel, BorderLayout.NORTH);
        regionPanel.add(regionField, BorderLayout.CENTER);
        
        contactPanel.add(telephonePanel);
        contactPanel.add(regionPanel);
        
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
        joinNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Registration logic would go here
                JOptionPane.showMessageDialog(SignUp.this, 
                    "Registration submitted!", "RestHive", JOptionPane.INFORMATION_MESSAGE);
            }
        });
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
}

