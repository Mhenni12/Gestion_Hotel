package client_dashboard;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.image.BufferedImage;

public class ClientSettings extends JFrame {
    // User profile information
    private String email = "Client@gmail.com";
    private String password = "•••••••";
    private String firstName = "Exemple";
    private String lastName = "Exemple";
    private String phone = "+216 98300666";
    private String address = "123 Main Street, Suite 500";

    // UI Components
    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;

    public ClientSettings() {
        // Basic frame setup
        setTitle("RestHive - User Profile");
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Setup main layout
        mainPanel = new JPanel(new BorderLayout());
        setupUIComponents();
        setContentPane(mainPanel);
    }

    private void setupUIComponents() {
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        
        // Create sidebar panel
        sidebarPanel = createSidebarPanel();
        
        // Create content panel
        contentPanel = createContentPanel();
        
        // Add all panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        
        // Logo panel (left)
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        logoPanel.setBackground(Color.WHITE);
        
        ImageIcon logoIcon = createHexagonIcon(30, Color.ORANGE);
        JLabel logoLabel = new JLabel(logoIcon);
        JLabel nameLabel = new JLabel("RestHive");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        logoPanel.add(logoLabel);
        logoPanel.add(nameLabel);
        
        // Navigation panel (right)
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        navPanel.setBackground(Color.WHITE);
        
        JLabel reservationLabel = new JLabel("Reservation");
        reservationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        navPanel.add(reservationLabel);
        navPanel.add(settingsLabel);
        
        // Add components to header
        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(navPanel, BorderLayout.EAST);
        
        return headerPanel;
    }

    private JPanel createSidebarPanel() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(252, 191, 24)); // Golden/Yellow background
        sidebarPanel.setPreferredSize(new Dimension(200, 0)); // Reduced width
        
        // Add space at the top
        sidebarPanel.add(Box.createVerticalStrut(50)); // Reduced space to align buttons to top
        
        // Delete account button
        JPanel deleteAccountPanel = createSidebarButtonPanel("Delete account", new ImageIcon());
        deleteAccountPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDeleteAccountDialog();
            }
        });
        
        // Logout button
        JPanel logoutPanel = createSidebarButtonPanel("Log out", new ImageIcon());
        logoutPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logout();
            }
        });
        
        sidebarPanel.add(deleteAccountPanel);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(logoutPanel);
        sidebarPanel.add(Box.createVerticalGlue()); // Push everything to the top
        
        return sidebarPanel;
    }

    private JPanel createSidebarButtonPanel(String text, ImageIcon icon) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(252, 191, 24)); // Golden/Yellow background
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        panel.setMaximumSize(new Dimension(200, 50)); // Match sidebar width
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Use a custom icon (user or logout)
        JLabel iconLabel;
        if (text.equals("Delete account")) {
            iconLabel = new JLabel(createUserIcon());
        } else {
            iconLabel = new JLabel(createLogoutIcon());
        }
        
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        panel.add(iconLabel);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(textLabel);
        panel.add(Box.createHorizontalGlue());
        
        return panel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Greeting panel
        JPanel greetingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        greetingPanel.setBackground(Color.WHITE);
        greetingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel heyLabel = new JLabel("Hey, ");
        heyLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JLabel clientLabel = new JLabel("Client");
        clientLabel.setFont(new Font("Arial", Font.BOLD, 24));
        clientLabel.setForeground(new Color(252, 191, 24)); // Golden/Yellow color
        
        JLabel exclamationLabel = new JLabel("!");
        exclamationLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        greetingPanel.add(heyLabel);
        greetingPanel.add(clientLabel);
        greetingPanel.add(exclamationLabel);
        
        // Create a panel to contain all form panels
        JPanel formContainer = new JPanel();
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setBackground(Color.WHITE);
        formContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Add form panels
        JPanel emailPanel = createFormPanel("Email", email, false);
        JPanel passwordPanel = createFormPanel("Password", password, true);
        JPanel firstNamePanel = createFormPanel("First name", firstName, true);
        JPanel lastNamePanel = createFormPanel("Last name", lastName, true);
        JPanel contactsPanel = createContactsPanel();
        
        // Add all panels to form container with spacing
        formContainer.add(emailPanel);
        formContainer.add(Box.createVerticalStrut(10));
        formContainer.add(passwordPanel);
        formContainer.add(Box.createVerticalStrut(10));
        formContainer.add(firstNamePanel);
        formContainer.add(Box.createVerticalStrut(10));
        formContainer.add(lastNamePanel);
        formContainer.add(Box.createVerticalStrut(10));
        formContainer.add(contactsPanel);
        
        // Add all components to content panel
        contentPanel.add(greetingPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(formContainer);
        
        return contentPanel;
    }
    
    private JPanel createContactsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 15, 10, 15) // Reduced padding
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110)); // Reduced height
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Left panel for label and data
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        
        JLabel contactsLabel = new JLabel("Contacts");
        contactsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        contactsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setBackground(Color.WHITE);
        dataPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        dataPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel phoneLabel = new JLabel("Phone : " + phone);
        phoneLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel addressLabel = new JLabel("Address : " + address);
        addressLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        dataPanel.add(phoneLabel);
        dataPanel.add(Box.createVerticalStrut(5));
        dataPanel.add(addressLabel);
        
        leftPanel.add(contactsLabel);
        leftPanel.add(dataPanel);
        
        // Right panel for edit button
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);
        
        JButton editContactsBtn = createEditButton();
        editContactsBtn.addActionListener(e -> editContacts());
        
        rightPanel.add(editContactsBtn);
        
        panel.add(leftPanel, BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.EAST);
        
        return panel;
    }

    private JPanel createFormPanel(String labelText, String value, boolean editable) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 15, 10, 15) // Reduced padding
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); // Reduced height
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Left panel for label and value
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        
        leftPanel.add(label);
        leftPanel.add(valueLabel);
        
        panel.add(leftPanel, BorderLayout.CENTER);
        
        // Add edit button if editable
        if (editable) {
            JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            rightPanel.setBackground(Color.WHITE);
            
            JButton editBtn = createEditButton();
            editBtn.addActionListener(e -> {
                if (labelText.equals("Password")) {
                    editPassword();
                } else if (labelText.equals("First name")) {
                    editFirstName();
                } else if (labelText.equals("Last name")) {
                    editLastName();
                }
            });
            
            rightPanel.add(editBtn);
            panel.add(rightPanel, BorderLayout.EAST);
        }
        
        return panel;
    }

    private JButton createEditButton() {
        JButton editBtn = new JButton("Edit");
        editBtn.setBackground(new Color(252, 191, 24)); // Golden/Yellow button
        editBtn.setForeground(Color.WHITE);
        editBtn.setBorderPainted(false);
        editBtn.setFocusPainted(false);
        editBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Make the button smaller
        editBtn.setPreferredSize(new Dimension(60, 25));
        return editBtn;
    }

    private void editPassword() {
        String newPassword = JOptionPane.showInputDialog(this, "Enter new password:", "Change Password", JOptionPane.PLAIN_MESSAGE);
        if (newPassword != null && !newPassword.isEmpty()) {
            password = "•••••••"; // Always display dots for password
            JOptionPane.showMessageDialog(this, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshContentPanel();
        }
    }

    private void editFirstName() {
        String newFirstName = JOptionPane.showInputDialog(this, "Enter new first name:", "Change First Name", JOptionPane.PLAIN_MESSAGE);
        if (newFirstName != null && !newFirstName.isEmpty()) {
            firstName = newFirstName;
            refreshContentPanel();
        }
    }

    private void editLastName() {
        String newLastName = JOptionPane.showInputDialog(this, "Enter new last name:", "Change Last Name", JOptionPane.PLAIN_MESSAGE);
        if (newLastName != null && !newLastName.isEmpty()) {
            lastName = newLastName;
            refreshContentPanel();
        }
    }

    private void editContacts() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField(phone);
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField(address);
        
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(addressLabel);
        panel.add(addressField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Contacts", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String newPhone = phoneField.getText();
            String newAddress = addressField.getText();
            
            if (!newPhone.isEmpty()) {
                phone = newPhone;
            }
            
            if (!newAddress.isEmpty()) {
                address = newAddress;
            }
            
            refreshContentPanel();
        }
    }

    private void showDeleteAccountDialog() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete your account?\nThis action cannot be undone.",
                "Delete Account",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(
                    this,
                    "Your account has been deleted successfully.",
                    "Account Deleted",
                    JOptionPane.INFORMATION_MESSAGE
            );
            
            // Close this frame and open login frame
            logout();
        }
    }

    private void logout() {
        // Close the current frame
        this.dispose();
        
        // Open the login frame
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }

    private void refreshContentPanel() {
        // Remove the current contentPanel
        mainPanel.remove(contentPanel);
        
        // Create a new contentPanel with updated data
        contentPanel = createContentPanel();
        
        // Add the new contentPanel
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Refresh the UI
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // Helper method to create hexagon icon for logo
    private ImageIcon createHexagonIcon(int size, Color color) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        // Enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw hexagon
        g2d.setColor(color);
        
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        int centerX = size / 2;
        int centerY = size / 2;
        int radius = size / 2 - 2;
        
        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * i + Math.PI / 6;
            xPoints[i] = (int) (centerX + radius * Math.cos(angle));
            yPoints[i] = (int) (centerY + radius * Math.sin(angle));
        }
        
        g2d.fillPolygon(xPoints, yPoints, 6);
        g2d.dispose();
        
        return new ImageIcon(image);
    }

    // Helper method to create user icon
    private ImageIcon createUserIcon() {
        int size = 24;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        
        // Draw circle for head
        int headSize = size / 2;
        g2d.fillOval(size / 4, 0, headSize, headSize);
        
        // Draw body
        g2d.fillOval(size / 6, size / 2, size * 2 / 3, size / 2);
        
        g2d.dispose();
        return new ImageIcon(image);
    }

    // Helper method to create logout icon
    private ImageIcon createLogoutIcon() {
        int size = 24;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        
        // Draw arrow
        int[] xPoints = {size / 2, size, size / 2};
        int[] yPoints = {0, size / 2, size};
        g2d.fillPolygon(xPoints, yPoints, 3);
        
        // Draw rectangle
        g2d.fillRect(0, size / 4, size / 2, size / 2);
        
        g2d.dispose();
        return new ImageIcon(image);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            ClientSettings profile = new ClientSettings();
            profile.setVisible(true);
        });
    }
}

// Simple Login Frame class (would be opened after logout)
class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("RestHive - Login");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        
        JLabel loginLabel = new JLabel("Login Screen");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        mainPanel.add(loginLabel);
        
        setContentPane(mainPanel);
    }
}












// package client_dashboard;

// import javax.swing.*;
// import javax.swing.border.*;
// import java.awt.*;
// import java.awt.event.*;
// import java.awt.image.BufferedImage;
// import java.sql.*;
// import java.util.regex.Pattern;

// import gestion_base_donnees.Connect;

// public class ClientSettings extends JFrame {
//     // User profile information
//     private String email = "Client@gmail.com";
//     private String password = "•••••••";
//     private String firstName = "Exemple";
//     private String lastName = "Exemple";
//     private String phone = "+216 98300666";
//     private String address = "123 Main Street, Suite 500";
//     private int clientId; // To store the client ID for database operations

//     // UI Components
//     private JPanel mainPanel;
//     private JPanel sidebarPanel;
//     private JPanel contentPanel;

//     public ClientSettings(int clientId) {
//         this.clientId = clientId;
//         loadClientData(); // Load client data from database on initialization

//         // Basic frame setup
//         setTitle("RestHive - User Profile");
//         setSize(800, 600);
//         setMinimumSize(new Dimension(800, 600));
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);

//         // Setup main layout
//         mainPanel = new JPanel(new BorderLayout());
//         setupUIComponents();
//         setContentPane(mainPanel);
//     }

//     private void loadClientData() {
//         try (Connection connection = new Connect().getConnection()) {
//             // Get client data
//             String clientQuery = "SELECT nom_client, prenom_client, adresse_client, tel_client FROM client WHERE id_client = ?";
//             try (PreparedStatement stmt = connection.prepareStatement(clientQuery)) {
//                 stmt.setInt(1, clientId);
//                 ResultSet rs = stmt.executeQuery();
//                 if (rs.next()) {
//                     lastName = rs.getString("nom_client");
//                     firstName = rs.getString("prenom_client");
//                     address = rs.getString("adresse_client") != null ? rs.getString("adresse_client") : "";
//                     phone = rs.getString("tel_client") != null ? rs.getString("tel_client") : "";
//                 }
//             }

//             // Get user data (email and password)
//             String userQuery = "SELECT email_utilisateur FROM utilisateur WHERE id_client = ?";
//             try (PreparedStatement stmt = connection.prepareStatement(userQuery)) {
//                 stmt.setInt(1, clientId);
//                 ResultSet rs = stmt.executeQuery();
//                 if (rs.next()) {
//                     email = rs.getString("email_utilisateur");
//                 }
//             }
//         } catch (SQLException e) {
//             JOptionPane.showMessageDialog(this, "Error loading client data: " + e.getMessage(), 
//                 "Database Error", JOptionPane.ERROR_MESSAGE);
//             e.printStackTrace();
//         }
//     }

//     // ... [Keep all the existing UI setup methods unchanged until the edit methods]

//     private void editPassword() {
//         String newPassword = JOptionPane.showInputDialog(this, "Enter new password:", "Change Password", JOptionPane.PLAIN_MESSAGE);
//         if (newPassword != null && !newPassword.isEmpty()) {
//             if (newPassword.length() < 6) {
//                 JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long", 
//                     "Invalid Password", JOptionPane.WARNING_MESSAGE);
//                 return;
//             }

//             try (Connection connection = new Connect().getConnection()) {
//                 String query = "UPDATE utilisateur SET mot_de_passe = ? WHERE id_client = ?";
//                 try (PreparedStatement stmt = connection.prepareStatement(query)) {
//                     stmt.setString(1, newPassword);
//                     stmt.setInt(2, clientId);
//                     int rowsAffected = stmt.executeUpdate();
                    
//                     if (rowsAffected > 0) {
//                         password = "•••••••"; // Always display dots for password
//                         JOptionPane.showMessageDialog(this, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
//                         refreshContentPanel();
//                     } else {
//                         JOptionPane.showMessageDialog(this, "Failed to update password", 
//                             "Error", JOptionPane.ERROR_MESSAGE);
//                     }
//                 }
//             } catch (SQLException e) {
//                 JOptionPane.showMessageDialog(this, "Error updating password: " + e.getMessage(), 
//                     "Database Error", JOptionPane.ERROR_MESSAGE);
//                 e.printStackTrace();
//             }
//         }
//     }

//     private void editFirstName() {
//         String newFirstName = JOptionPane.showInputDialog(this, "Enter new first name:", "Change First Name", JOptionPane.PLAIN_MESSAGE);
//         if (newFirstName != null && !newFirstName.isEmpty()) {
//             try (Connection connection = new Connect().getConnection()) {
//                 String query = "UPDATE client SET prenom_client = ? WHERE id_client = ?";
//                 try (PreparedStatement stmt = connection.prepareStatement(query)) {
//                     stmt.setString(1, newFirstName);
//                     stmt.setInt(2, clientId);
//                     int rowsAffected = stmt.executeUpdate();
                    
//                     if (rowsAffected > 0) {
//                         firstName = newFirstName;
//                         refreshContentPanel();
//                     } else {
//                         JOptionPane.showMessageDialog(this, "Failed to update first name", 
//                             "Error", JOptionPane.ERROR_MESSAGE);
//                     }
//                 }
//             } catch (SQLException e) {
//                 JOptionPane.showMessageDialog(this, "Error updating first name: " + e.getMessage(), 
//                     "Database Error", JOptionPane.ERROR_MESSAGE);
//                 e.printStackTrace();
//             }
//         }
//     }

//     private void editLastName() {
//         String newLastName = JOptionPane.showInputDialog(this, "Enter new last name:", "Change Last Name", JOptionPane.PLAIN_MESSAGE);
//         if (newLastName != null && !newLastName.isEmpty()) {
//             try (Connection connection = new Connect().getConnection()) {
//                 String query = "UPDATE client SET nom_client = ? WHERE id_client = ?";
//                 try (PreparedStatement stmt = connection.prepareStatement(query)) {
//                     stmt.setString(1, newLastName);
//                     stmt.setInt(2, clientId);
//                     int rowsAffected = stmt.executeUpdate();
                    
//                     if (rowsAffected > 0) {
//                         lastName = newLastName;
//                         refreshContentPanel();
//                     } else {
//                         JOptionPane.showMessageDialog(this, "Failed to update last name", 
//                             "Error", JOptionPane.ERROR_MESSAGE);
//                     }
//                 }
//             } catch (SQLException e) {
//                 JOptionPane.showMessageDialog(this, "Error updating last name: " + e.getMessage(), 
//                     "Database Error", JOptionPane.ERROR_MESSAGE);
//                 e.printStackTrace();
//             }
//         }
//     }

//     private void editContacts() {
//         JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
//         JLabel phoneLabel = new JLabel("Phone:");
//         JTextField phoneField = new JTextField(phone);
//         JLabel addressLabel = new JLabel("Address:");
//         JTextField addressField = new JTextField(address);
        
//         panel.add(phoneLabel);
//         panel.add(phoneField);
//         panel.add(addressLabel);
//         panel.add(addressField);
        
//         int result = JOptionPane.showConfirmDialog(this, panel, "Edit Contacts", 
//                 JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
//         if (result == JOptionPane.OK_OPTION) {
//             String newPhone = phoneField.getText();
//             String newAddress = addressField.getText();
            
//             // Validate phone number format (8-15 digits)
//             if (!newPhone.isEmpty() && !Pattern.matches("^[0-9]{8,15}$", newPhone)) {
//                 JOptionPane.showMessageDialog(this, "Phone number must be 8-15 digits", 
//                     "Invalid Phone", JOptionPane.WARNING_MESSAGE);
//                 return;
//             }
            
//             try (Connection connection = new Connect().getConnection()) {
//                 String query = "UPDATE client SET tel_client = ?, adresse_client = ? WHERE id_client = ?";
//                 try (PreparedStatement stmt = connection.prepareStatement(query)) {
//                     stmt.setString(1, newPhone.isEmpty() ? null : newPhone);
//                     stmt.setString(2, newAddress.isEmpty() ? null : newAddress);
//                     stmt.setInt(3, clientId);
//                     int rowsAffected = stmt.executeUpdate();
                    
//                     if (rowsAffected > 0) {
//                         phone = newPhone;
//                         address = newAddress;
//                         refreshContentPanel();
//                     } else {
//                         JOptionPane.showMessageDialog(this, "Failed to update contacts", 
//                             "Error", JOptionPane.ERROR_MESSAGE);
//                     }
//                 }
//             } catch (SQLException e) {
//                 JOptionPane.showMessageDialog(this, "Error updating contacts: " + e.getMessage(), 
//                     "Database Error", JOptionPane.ERROR_MESSAGE);
//                 e.printStackTrace();
//             }
//         }
//     }

//     private void showDeleteAccountDialog() {
//         int choice = JOptionPane.showConfirmDialog(
//                 this,
//                 "Are you sure you want to delete your account?\nThis action cannot be undone.",
//                 "Delete Account",
//                 JOptionPane.YES_NO_OPTION,
//                 JOptionPane.WARNING_MESSAGE
//         );
        
//         if (choice == JOptionPane.YES_OPTION) {
//             try (Connection connection = new Connect().getConnection()) {
//                 // First delete the user (child table)
//                 String deleteUserQuery = "DELETE FROM utilisateur WHERE id_client = ?";
//                 try (PreparedStatement stmt = connection.prepareStatement(deleteUserQuery)) {
//                     stmt.setInt(1, clientId);
//                     stmt.executeUpdate();
//                 }
                
//                 // Then delete the client
//                 String deleteClientQuery = "DELETE FROM client WHERE id_client = ?";
//                 try (PreparedStatement stmt = connection.prepareStatement(deleteClientQuery)) {
//                     stmt.setInt(1, clientId);
//                     int rowsAffected = stmt.executeUpdate();
                    
//                     if (rowsAffected > 0) {
//                         JOptionPane.showMessageDialog(
//                                 this,
//                                 "Your account has been deleted successfully.",
//                                 "Account Deleted",
//                                 JOptionPane.INFORMATION_MESSAGE
//                         );
                        
//                         // Close this frame and open login frame
//                         logout();
//                     } else {
//                         JOptionPane.showMessageDialog(this, "Failed to delete account", 
//                             "Error", JOptionPane.ERROR_MESSAGE);
//                     }
//                 }
//             } catch (SQLException e) {
//                 JOptionPane.showMessageDialog(this, "Error deleting account: " + e.getMessage(), 
//                     "Database Error", JOptionPane.ERROR_MESSAGE);
//                 e.printStackTrace();
//             }
//         }
//     }

//     // ... [Keep all the remaining methods unchanged]

//     public static void main(String[] args) {
//         try {
//             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
        
//         SwingUtilities.invokeLater(() -> {
//             // For testing purposes, we'll use client ID 1
//             // In a real application, this would come from the login process
//             ClientSettings profile = new ClientSettings(1);
//             profile.setVisible(true);
//         });
//     }
// }

// // ... [Keep the LoginFrame class unchanged]