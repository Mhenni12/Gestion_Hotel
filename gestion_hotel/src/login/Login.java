package login;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame {
    
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel mainPanel, headerPanel, contentPanel;
    
    public Login() {
        // Set frame properties
        setTitle("LOGIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create components
        createHeaderPanel();
        createContentPanel();
        
        // Add panels to main frame
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Set frame size and center on screen
        //TODO: keep the size homogenous across all windows
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void createHeaderPanel() {
        headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        headerPanel.setBackground(new Color(255, 193, 7)); // Yellow background
        
        // Load the logo from file
        ImageIcon logoIcon = new ImageIcon("gestion_hotel/src/images/hotelLogo.png");

        JLabel logoLabel = new JLabel(logoIcon);
        
        // Add RestHive text next to the logo
        JLabel titleLabel = new JLabel("RestHive");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        
        headerPanel.add(logoLabel);
        headerPanel.add(titleLabel);
    }
    
    private void createContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        
        // Create welcome text
        JLabel welcomeLabel = new JLabel("Welcome back!");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create "First time? Join us here." text
        JPanel joinPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        joinPanel.setBackground(Color.WHITE);
        JLabel firstTimeLabel = new JLabel("First time?");
        JButton joinButton = new JButton("Join us here.");
        joinButton.setBorderPainted(false);
        joinButton.setContentAreaFilled(false);
        joinButton.setFocusable(false);
        joinButton.setForeground(Color.BLUE);
        joinButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        joinButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        //TODO: add action listener
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open registration form
                new SignUp();
                dispose(); // Close login window
            }
        });
        
        joinPanel.add(firstTimeLabel);
        joinPanel.add(joinButton);
        
        // Create email panel
        JPanel emailPanel = new JPanel(new BorderLayout(0, 10));
        emailPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        emailPanel.setBackground(Color.WHITE);
        
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(300, 35));
        
        emailPanel.add(emailLabel, BorderLayout.NORTH);
        emailPanel.add(emailField, BorderLayout.CENTER);
        
        // Create password panel
        JPanel passwordPanel = new JPanel(new BorderLayout(0, 10));
        passwordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        passwordPanel.setBackground(Color.WHITE);
        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 35));
        
        // Create panel for password field and eye icon
        JPanel passwordInputPanel = new JPanel(new BorderLayout());
        passwordInputPanel.setBackground(Color.WHITE);
        
        // Try to load eye icon from file, otherwise create a simple one
        ImageIcon eyeIcon = new ImageIcon("gestion_hotel/src/images/icons8-eye-24.png");
        
        JButton showPasswordButton = new JButton(eyeIcon);
        showPasswordButton.setBorderPainted(false);
        showPasswordButton.setContentAreaFilled(false);
        showPasswordButton.setFocusPainted(false);
        showPasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        showPasswordButton.addActionListener(new ActionListener() {
            private boolean visible = false;
            
            @Override
            public void actionPerformed(ActionEvent e) {

                //TODO: add closed eye
                visible = !visible;
                passwordField.setEchoChar(visible ? (char)0 : '•');
            }
        });
        
        passwordInputPanel.add(passwordField, BorderLayout.CENTER);
        passwordInputPanel.add(showPasswordButton, BorderLayout.EAST);
        
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordPanel.add(passwordInputPanel, BorderLayout.CENTER);
        
        // Create login button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        loginButton.setBackground(new Color(255, 193, 7)); // Yellow background
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(150, 40));
        
        // Add rounded corners to the button
        loginButton.setBorder(new EmptyBorder(8, 30, 8, 30));
        
        // Add action listener
        //TODO: add action listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Login.this, 
                    "Login attempt processed!", "RestHive", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Create a container to center the form elements
        JPanel formContainer = new JPanel();
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setBackground(Color.WHITE);
        formContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        formContainer.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        
        // Add components to the form container with proper spacing
        formContainer.add(welcomeLabel);
        formContainer.add(Box.createVerticalStrut(10));
        formContainer.add(joinPanel);
        formContainer.add(Box.createVerticalStrut(20));
        formContainer.add(emailPanel);
        formContainer.add(Box.createVerticalStrut(20));
        formContainer.add(passwordPanel);
        formContainer.add(Box.createVerticalStrut(40));
        formContainer.add(loginButton);
        
        // Add the form container to the content panel
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(formContainer);
        contentPanel.add(Box.createVerticalGlue());
    }
    
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
                new Login();
            }
        });
    }
}
