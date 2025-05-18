package admin_dashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import chambre.ChambreGui;
import espace_paiement.PaiementGui;
import espace_reservation.ReservationGui;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import gestion_base_donnees.Connect;
import login.Login;
import utilisateur.GererUtilisateurs;

public class AdminSettings extends JFrame {
    
    private int currentUserId; // Store the current user's ID
    private String userEmail; // Store the current user's email
    
    public AdminSettings() {
        this(0, "Admin@gmail.com"); // Default constructor for testing
    }
    
    public AdminSettings(int userId, String email) {
        this.currentUserId = userId;
        this.userEmail = email;
        
        setTitle("RestHive");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Main panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Creating the header panel with yellow background
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 245));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        // Logo on the left
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(new Color(245, 245, 245));
        
        JLabel logoLabel = new JLabel("RestHive");
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        logoLabel.setIcon(createHouseIcon());
        logoPanel.add(logoLabel);
        
        // Menu buttons
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 11));
        menuPanel.setBackground(new Color(245, 245, 245));
        
        JButton clientsBtn = createMenuButton("Clients", false);

        String[] menuItems = {"Clients", "Reservation", "Rooms", "Payment", "Settings"};

        for (String item : menuItems) {
            JLabel menuLabel = new JLabel(item);
            menuLabel.setFont(new Font("Sans-serif", Font.BOLD, 14));
            menuLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
            
            // Make the label clickable
            menuLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            // Add click action for each menu item
            switch (item) {
                case "Clients":
                    menuLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            // Close current window
                            Window currentWindow = SwingUtilities.getWindowAncestor(menuPanel);
                            currentWindow.dispose();
                            
                            // Open Clients window
                            SwingUtilities.invokeLater(() -> {
                                new GererUtilisateurs().setVisible(true);
                            });
                        }
                        
                        // Hover effects
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            menuLabel.setForeground(Color.BLUE);
                        }
                        
                        @Override
                        public void mouseExited(MouseEvent e) {
                            menuLabel.setForeground(Color.BLACK);
                        }
                    });
                    break;
                    
                case "Reservation":
                    menuLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            // Close current window
                            Window currentWindow = SwingUtilities.getWindowAncestor(menuPanel);
                            currentWindow.dispose();
                            
                            // Open Reservation window
                            SwingUtilities.invokeLater(() -> {
                                new ReservationGui().setVisible(true);
                            });
                        }
                        
                        // Hover effects
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            menuLabel.setForeground(Color.BLUE);
                        }
                        
                        @Override
                        public void mouseExited(MouseEvent e) {
                            menuLabel.setForeground(Color.BLACK);
                        }
                    });
                    break;
                    
                case "Rooms":
                    menuLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            // Close current window
                            Window currentWindow = SwingUtilities.getWindowAncestor(menuPanel);
                            currentWindow.dispose();
                            
                            // Open Rooms window
                            SwingUtilities.invokeLater(() -> {
                                new ChambreGui().setVisible(true);
                            });
                        }
                        
                        // Hover effects
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            menuLabel.setForeground(Color.BLUE);
                        }
                        
                        @Override
                        public void mouseExited(MouseEvent e) {
                            menuLabel.setForeground(Color.BLACK);
                        }
                    });
                    break;
                    
                case "Payment":
                    menuLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            // Close current window
                            Window currentWindow = SwingUtilities.getWindowAncestor(menuPanel);
                            currentWindow.dispose();
                            
                            // Open Payment window
                            SwingUtilities.invokeLater(() -> {
                                new PaiementGui().setVisible(true);
                            });
                        }
                        
                        // Hover effects
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            menuLabel.setForeground(Color.BLUE);
                        }
                        
                        @Override
                        public void mouseExited(MouseEvent e) {
                            menuLabel.setForeground(Color.BLACK);
                        }
                    });
                    break;
                    
                case "Settings":
                    menuLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            // Close current window
                            Window currentWindow = SwingUtilities.getWindowAncestor(menuPanel);
                            currentWindow.dispose();
                            
                            // Open Settings window
                            SwingUtilities.invokeLater(() -> {
                                new AdminSettings().setVisible(true);
                            });
                        }
                        
                        // Hover effects
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            menuLabel.setForeground(Color.BLUE);
                        }
                        
                        @Override
                        public void mouseExited(MouseEvent e) {
                            menuLabel.setForeground(Color.BLACK);
                        }
                    });
                    break;
            }
            
            menuPanel.add(menuLabel);
        }
        
        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(menuPanel, BorderLayout.CENTER);
        
        // Main content area with sidebar and settings content
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(new Color(240, 240, 240)); // Light gray background
        
        // Yellow sidebar
        JPanel sidebar = createSidebar();
        
        // Settings content panel - white background with rounded corners
        JPanel settingsContentPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        settingsContentPanel.setOpaque(false);
        settingsContentPanel.setBackground(Color.WHITE);
        settingsContentPanel.setBorder(new EmptyBorder(30, 50, 30, 50));
        
        // Admin greeting and info
        JPanel adminInfoPanel = new JPanel();
        adminInfoPanel.setLayout(new BoxLayout(adminInfoPanel, BoxLayout.Y_AXIS));
        adminInfoPanel.setOpaque(false);
        
        JPanel greetingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        greetingPanel.setOpaque(false);
        
        JLabel heyLabel = new JLabel("Hey, ");
        heyLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        
        JLabel adminLabel = new JLabel("Admin");
        adminLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        adminLabel.setForeground(new Color(255, 180, 0));
        
        JLabel exclamationLabel = new JLabel(" !");
        exclamationLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        
        greetingPanel.add(heyLabel);
        greetingPanel.add(adminLabel);
        greetingPanel.add(exclamationLabel);
        
        adminInfoPanel.add(greetingPanel);
        adminInfoPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        // Email field
        JPanel emailPanel = createSettingsField("Email", userEmail, false);
        adminInfoPanel.add(emailPanel);
        adminInfoPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Password field
        JPanel passwordPanel = createPasswordField("Password", "********", true);
        adminInfoPanel.add(passwordPanel);
        
        settingsContentPanel.add(adminInfoPanel, BorderLayout.NORTH);
        
        // Add padding around the settings content panel
        JPanel paddingPanel = new JPanel(new BorderLayout());
        paddingPanel.setOpaque(false);
        paddingPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        paddingPanel.add(settingsContentPanel, BorderLayout.CENTER);
        
        // Add components to the content area
        contentArea.add(sidebar, BorderLayout.WEST);
        contentArea.add(paddingPanel, BorderLayout.CENTER);
        
        // Add all panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentArea, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        
        // Add action listeners for navigation
        clientsBtn.addActionListener(e -> {
            new GererUtilisateurs().setVisible(true);
            this.dispose();
        });
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(255, 204, 0));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setBorder(new EmptyBorder(20, 15, 20, 15));
        
        // Manage access section
        JPanel manageAccessPanel = new JPanel();
        manageAccessPanel.setLayout(new BoxLayout(manageAccessPanel, BoxLayout.Y_AXIS));
        manageAccessPanel.setOpaque(false);
        manageAccessPanel.setBorder(new EmptyBorder(0, 0, 30, 0));
        
        JPanel manageAccessLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        manageAccessLabelPanel.setOpaque(false);
        
        JLabel userIcon = new JLabel(createUserIcon());
        JLabel manageAccessLabel = new JLabel("Manage access");
        manageAccessLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        manageAccessLabelPanel.add(userIcon);
        manageAccessLabelPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        manageAccessLabelPanel.add(manageAccessLabel);
        
        JPanel addAdminPanel = createSidebarOption(" > Add admin");
        addAdminPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showAddAdminDialog();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                addAdminPanel.setBackground(new Color(255, 220, 100));
                addAdminPanel.setOpaque(true);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                addAdminPanel.setOpaque(false);
                addAdminPanel.repaint();
            }
        });
        JPanel addClientPanel = createSidebarOption("> Add client");
        addClientPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showAddClientDialog();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                addClientPanel.setBackground(new Color(255, 220, 100));
                addClientPanel.setOpaque(true);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                addClientPanel.setOpaque(false);
                addClientPanel.repaint();
            }
        });
        
        manageAccessPanel.add(manageAccessLabelPanel);
        manageAccessPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        manageAccessPanel.add(addAdminPanel);
        manageAccessPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        manageAccessPanel.add(addClientPanel);
        
        // Delete account section
        JPanel deleteAccountPanel = new JPanel();
        deleteAccountPanel.setLayout(new BoxLayout(deleteAccountPanel, BoxLayout.Y_AXIS));
        deleteAccountPanel.setOpaque(false);
        deleteAccountPanel.setBorder(new EmptyBorder(0, 0, 30, 0));
        
        JPanel deleteAccountLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deleteAccountLabelPanel.setOpaque(false);
        
        JLabel deleteIcon = new JLabel(createUserIcon()); // Using same icon for simplicity
        JLabel deleteAccountLabel = new JLabel("Delete account");
        deleteAccountLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        deleteAccountLabelPanel.add(deleteIcon);
        deleteAccountLabelPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        deleteAccountLabelPanel.add(deleteAccountLabel);
        
        JPanel deleteAccountOptionPanel = createSidebarOption("> Delete account");
        JPanel deleteClientOptionPanel = createSidebarOption("> Delete client");
        
        // Add action listener to delete account option to delete the current user account
        deleteAccountOptionPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    AdminSettings.this,
                    "Are you sure you want to delete your account? This action cannot be undone.",
                    "Confirm Account Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    if (deleteCurrentAccount()) {
                        JOptionPane.showMessageDialog(
                            AdminSettings.this,
                            "Your account has been successfully deleted.",
                            "Account Deleted",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        logout();
                    } else {
                        JOptionPane.showMessageDialog(
                            AdminSettings.this,
                            "Failed to delete your account. Please try again later.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteAccountOptionPanel.setBackground(new Color(255, 220, 100));
                deleteAccountOptionPanel.setOpaque(true);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                deleteAccountOptionPanel.setOpaque(false);
                deleteAccountOptionPanel.repaint();
            }
        });
        
        // Add action listener to delete client option panel to navigate to Clients.java
        deleteClientOptionPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new GererUtilisateurs().setVisible(true);
                ((JFrame) SwingUtilities.getWindowAncestor(deleteClientOptionPanel)).dispose();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                deleteClientOptionPanel.setBackground(new Color(255, 220, 100));
                deleteClientOptionPanel.setOpaque(true);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                deleteClientOptionPanel.setOpaque(false);
                deleteClientOptionPanel.repaint();
            }
        });
        
        deleteAccountPanel.add(deleteAccountLabelPanel);
        deleteAccountPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        deleteAccountPanel.add(deleteAccountOptionPanel);
        deleteAccountPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        deleteAccountPanel.add(deleteClientOptionPanel);
        
        // Logout option
        JPanel logoutPanel = new JPanel();
        logoutPanel.setLayout(new BoxLayout(logoutPanel, BoxLayout.Y_AXIS));
        logoutPanel.setOpaque(false);
        
        JPanel logoutLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoutLabelPanel.setOpaque(false);
        
        JLabel logoutIcon = new JLabel(createLogoutIcon());
        JLabel logoutLabel = new JLabel("Log out");
        logoutLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        logoutLabelPanel.add(logoutIcon);
        logoutLabelPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        logoutLabelPanel.add(logoutLabel);
        
        // Add action listener to logout panel
        logoutLabelPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logout();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutLabelPanel.setBackground(new Color(255, 220, 100));
                logoutLabelPanel.setOpaque(true);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                logoutLabelPanel.setOpaque(false);
                logoutLabelPanel.repaint();
            }
        });
        
        logoutPanel.add(logoutLabelPanel);
        
        // Add all sections to sidebar with spacing
        sidebar.add(manageAccessPanel);
        sidebar.add(deleteAccountPanel);
        sidebar.add(Box.createVerticalGlue()); // Push logout to bottom
        sidebar.add(logoutPanel);
        
        return sidebar;
    }
    
    /**
     * Deletes the current user account from the database
     * @return true if deletion was successful, false otherwise
     */
 private boolean deleteCurrentAccount() {
    Connection conn = null;
    PreparedStatement stmt = null;
    PreparedStatement checkClientStmt = null;
    PreparedStatement deleteReservationsStmt = null;
    PreparedStatement deletePaymentsStmt = null;
    PreparedStatement deleteUserStmt = null;
    PreparedStatement deleteClientStmt = null;
    ResultSet rs = null;
    boolean success = false;
    
    try {
        conn = new Connect().getConnection();
        conn.setAutoCommit(false); // Start transaction
        
        // 1. First get the user details to check if it's a client user
        String getUserSql = "SELECT id_client, type_utilisateur FROM utilisateur WHERE id_utilisateur = ?";
        stmt = conn.prepareStatement(getUserSql);
        stmt.setInt(1, currentUserId);
        rs = stmt.executeQuery();
        
        if (rs.next()) {
            Integer clientId = rs.getObject("id_client", Integer.class);
            String userType = rs.getString("type_utilisateur");
            
            // 2. If this is a client user, we need to handle client deletion
            if (clientId != null && "client".equals(userType)) {
                // First delete payments for this client's reservations
                String deletePaymentsSql = "DELETE p FROM paiement p " +
                                       "JOIN reservation r ON p.id_reservation = r.id_reservation " +
                                       "WHERE r.id_client = ?";
                deletePaymentsStmt = conn.prepareStatement(deletePaymentsSql);
                deletePaymentsStmt.setInt(1, clientId);
                deletePaymentsStmt.executeUpdate();
                
                // Then delete reservations for this client
                String deleteReservationsSql = "DELETE FROM reservation WHERE id_client = ?";
                deleteReservationsStmt = conn.prepareStatement(deleteReservationsSql);
                deleteReservationsStmt.setInt(1, clientId);
                deleteReservationsStmt.executeUpdate();
                
                // Finally delete the client record
                String deleteClientSql = "DELETE FROM client WHERE id_client = ?";
                deleteClientStmt = conn.prepareStatement(deleteClientSql);
                deleteClientStmt.setInt(1, clientId);
                deleteClientStmt.executeUpdate();
            }
            
            // 3. Delete the user account
            String deleteUserSql = "DELETE FROM utilisateur WHERE id_utilisateur = ?";
            deleteUserStmt = conn.prepareStatement(deleteUserSql);
            deleteUserStmt.setInt(1, currentUserId);
            int rowsAffected = deleteUserStmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit(); // Commit transaction if all deletions succeeded
                success = true;
            } else {
                conn.rollback(); // Rollback if user deletion failed
            }
        } else {
            conn.rollback(); // User not found
        }
        
    } catch (SQLException e) {
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
            "Error deleting account: " + e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE);
    } finally {
        // Close all resources
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (deletePaymentsStmt != null) deletePaymentsStmt.close();
            if (deleteReservationsStmt != null) deleteReservationsStmt.close();
            if (deleteClientStmt != null) deleteClientStmt.close();
            if (deleteUserStmt != null) deleteUserStmt.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    return success;
}
    
    /**
     * Logs out the current user and returns to the login screen
     */
    private void logout() {
        // Here you would redirect to your login screen
        // For example:
        try {

            JFrame loginFrame = new Login(); // Replace with your actual login frame
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setSize(800, 600);
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
            
            // Close the current window
            this.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error during logout: " + e.getMessage(),
                "Logout Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private JPanel createSidebarOption(String text) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 20, 0, 0));
        
        JLabel arrowLabel = new JLabel("›");
        arrowLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        panel.add(arrowLabel);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(textLabel);
        
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return panel;
    }
    
    private JPanel createSettingsField(String label, String value, boolean editable) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)));
        
        JLabel titleLabel = new JLabel(label);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        
        if (editable) {
            JButton editButton = new JButton("Edit");
            editButton.setFont(new Font("SansSerif", Font.BOLD, 12));
            editButton.setBackground(new Color(255, 204, 0));
            editButton.setForeground(Color.BLACK);
            editButton.setBorderPainted(false);
            editButton.setFocusPainted(false);
            editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            editButton.setPreferredSize(new Dimension(60, 25));
            
            JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            rightPanel.setOpaque(false);
            rightPanel.add(editButton);
            
            panel.add(rightPanel, BorderLayout.EAST);
        }
        
        return panel;
    }
    
// Replace your existing createPasswordField method with this one
private JPanel createPasswordField(String label, String value, boolean editable) {
    JPanel panel = createSettingsField(label, value, editable);
    
    // If editable, add action listener to the Edit button
    if (editable) {
        // Find the edit button in the panel (it's in the east position in a BorderLayout)
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel rightPanel = (JPanel) component;
                Component[] rightComponents = rightPanel.getComponents();
                for (Component rightComponent : rightComponents) {
                    if (rightComponent instanceof JButton) {
                        JButton editButton = (JButton) rightComponent;
                        
                        // Add action listener
                        editButton.addActionListener(e -> showEditPasswordDialog());
                    }
                }
            }
        }
    }
    
    return panel;
}

// Add this method to your Settings class to handle password editing
private void showEditPasswordDialog() {
    // Create a dialog for changing password
    JDialog passwordDialog = new JDialog(this, "Change Password", true);
    passwordDialog.setSize(400, 300);
    passwordDialog.setLocationRelativeTo(this);
    passwordDialog.setLayout(new BorderLayout());
    
    // Create a panel with a form layout for password fields
    JPanel formPanel = new JPanel();
    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    formPanel.setBackground(Color.WHITE);
    
    // Add title
    JLabel titleLabel = new JLabel("Change Your Password");
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
    titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    // Create password fields
    JPasswordField currentPasswordField = createPasswordInputField("Current Password:");
    JPasswordField newPasswordField = createPasswordInputField("New Password:");
    JPasswordField confirmPasswordField = createPasswordInputField("Confirm New Password:");
    
    // Add all components to the form panel
    formPanel.add(titleLabel);
    formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    formPanel.add(currentPasswordField.getParent());
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    formPanel.add(newPasswordField.getParent());
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    formPanel.add(confirmPasswordField.getParent());
    
    // Create buttons panel
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonsPanel.setBackground(Color.WHITE);
    
    JButton cancelButton = new JButton("Cancel");
    cancelButton.setPreferredSize(new Dimension(100, 35));
    cancelButton.setBackground(new Color(240, 240, 240));
    cancelButton.setFocusPainted(false);
    
    JButton saveButton = new JButton("Save");
    saveButton.setPreferredSize(new Dimension(100, 35));
    saveButton.setBackground(new Color(255, 204, 0));
    saveButton.setFocusPainted(false);
    
    buttonsPanel.add(cancelButton);
    buttonsPanel.add(saveButton);
    
    // Add components to dialog
    passwordDialog.add(formPanel, BorderLayout.CENTER);
    passwordDialog.add(buttonsPanel, BorderLayout.SOUTH);
    
    // Add action listeners for buttons
    cancelButton.addActionListener(e -> passwordDialog.dispose());
    
    saveButton.addActionListener(e -> {
        // Get password values
        String currentPassword = new String(currentPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        // Validate inputs
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(passwordDialog, 
                "All password fields are required.",
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if new passwords match
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(passwordDialog, 
                "New passwords do not match.",
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if new password is same as current
        if (currentPassword.equals(newPassword)) {
            JOptionPane.showMessageDialog(passwordDialog, 
                "New password must be different from current password.",
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verify current password and update to new password
        if (updatePassword(currentPassword, newPassword)) {
            JOptionPane.showMessageDialog(passwordDialog, 
                "Password updated successfully!",
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            passwordDialog.dispose();
        }
    });
    
    passwordDialog.setVisible(true);
}

// Method to verify current password and update to new password
private boolean updatePassword(String currentPassword, String newPassword) {
    Connection conn = null;
    PreparedStatement verifyStmt = null;
    PreparedStatement updateStmt = null;
    ResultSet rs = null;
    boolean success = false;
    
    try {
        conn = new Connect().getConnection();
        
        // First verify that current password is correct
        String verifySql = "SELECT id_utilisateur FROM utilisateur WHERE email_utilisateur = ? AND mot_de_passe = ?";
        verifyStmt = conn.prepareStatement(verifySql);
        verifyStmt.setString(1, userEmail);
        verifyStmt.setString(2, currentPassword);
        rs = verifyStmt.executeQuery();
        
        if (rs.next()) {
            // Current password is correct, proceed with update
            String updateSql = "UPDATE utilisateur SET mot_de_passe = ? WHERE email_utilisateur = ?";
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, newPassword);
            updateStmt.setString(2, userEmail);
            
            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }
        } else {
            // Current password is incorrect
            JOptionPane.showMessageDialog(this,
                "Current password is incorrect.",
                "Authentication Error",
                JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
            "Database error: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (rs != null) rs.close();
            if (verifyStmt != null) verifyStmt.close();
            if (updateStmt != null) updateStmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    return success;
}
    
    private JButton createMenuButton(String text, boolean selected) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(selected ? Color.BLACK : Color.DARK_GRAY);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if (selected) {
            button.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        }
        
        return button;
    }
    
    private Icon createHouseIcon() {
        // Simple house icon implementation
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // House color (yellow)
                g2d.setColor(new Color(255, 204, 0));
                
                // Draw house body
                int[] xPoints = {x + 10, x + 20, x + 30};
                int[] yPoints = {y + 5, y - 5, y + 5};
                g2d.fillPolygon(xPoints, yPoints, 3);
                g2d.fillRect(x + 12, y + 5, 16, 15);
                
                // Draw door
                g2d.setColor(new Color(120, 80, 20));
                g2d.fillRect(x + 18, y + 12, 4, 8);
                
                g2d.dispose();
            }
            
            @Override
            public int getIconWidth() {
                return 40;
            }
            
            @Override
            public int getIconHeight() {
                return 25;
            }
        };
    }
    
    private Icon createUserIcon() {
        // Simple user icon implementation
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Head
                g2d.setColor(Color.BLACK);
                g2d.fillOval(x + 3, y, 14, 14);
                
                // Body
                g2d.fillOval(x, y + 14, 20, 10);
                
                g2d.dispose();
            }
            
            @Override
            public int getIconWidth() {
                return 20;
            }
            
            @Override
            public int getIconHeight() {
                return 24;
            }
        };
    }
    
    private Icon createLogoutIcon() {
        // Simple logout icon implementation
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Circle
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x, y, 20, 20);
                
                // Arrow
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(x + 10, y + 5, x + 10, y + 15);
                g2d.drawLine(x + 10, y + 5, x + 15, y + 10);
                g2d.drawLine(x + 10, y + 5, x + 5, y + 10);
                
                g2d.dispose();
            }
            
            @Override
            public int getIconWidth() {
                return 20;
            }
            
            @Override
            public int getIconHeight() {
                return 20;
            }
        };
    }
    
// Add this method to your Settings class to create the Add Client dialog
private void showAddClientDialog() {
    // Create a dialog for adding a new client
    JDialog addClientDialog = new JDialog(this, "Add New Client", true);
    addClientDialog.setSize(500, 500);
    addClientDialog.setLocationRelativeTo(this);
    addClientDialog.setLayout(new BorderLayout());
    
    // Create a panel with a form layout for client details
    JPanel formPanel = new JPanel();
    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    formPanel.setBackground(Color.WHITE);
    
    // Add title
    JLabel titleLabel = new JLabel("Add New Client");
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
    titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    // Create input fields
    JTextField nomField = createInputField("Last Name:");
    JTextField prenomField = createInputField("First Name:");
    JTextField emailField = createInputField("Email:");
    JTextField adresseField = createInputField("Address:");
    JTextField telField = createInputField("Phone Number:");
    JPasswordField passwordField = createPasswordInputField("Password:");
    JPasswordField confirmPasswordField = createPasswordInputField("Confirm Password:");
    
    // Add all components to the form panel
    formPanel.add(titleLabel);
    formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    formPanel.add(nomField.getParent());
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    formPanel.add(prenomField.getParent());
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    formPanel.add(emailField.getParent());
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    formPanel.add(adresseField.getParent());
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    formPanel.add(telField.getParent());
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    formPanel.add(passwordField.getParent());
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    formPanel.add(confirmPasswordField.getParent());
    
    // Create buttons panel
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonsPanel.setBackground(Color.WHITE);
    
    JButton cancelButton = new JButton("Cancel");
    cancelButton.setPreferredSize(new Dimension(100, 35));
    cancelButton.setBackground(new Color(240, 240, 240));
    cancelButton.setFocusPainted(false);
    
    JButton saveButton = new JButton("Save");
    saveButton.setPreferredSize(new Dimension(100, 35));
    saveButton.setBackground(new Color(255, 204, 0));
    saveButton.setFocusPainted(false);
    
    buttonsPanel.add(cancelButton);
    buttonsPanel.add(saveButton);
    
    // Add components to dialog
    addClientDialog.add(formPanel, BorderLayout.CENTER);
    addClientDialog.add(buttonsPanel, BorderLayout.SOUTH);
    
    // Add action listeners for buttons
    cancelButton.addActionListener(e -> addClientDialog.dispose());
    
    saveButton.addActionListener(e -> {
        // Validate inputs
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String adresse = adresseField.getText().trim();
        String tel = telField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        // Validate required fields
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(addClientDialog, 
                "Last name, first name, email, and password fields are required.",
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate email format
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(addClientDialog, 
                "Please enter a valid email address.",
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate phone number format
        if (!tel.isEmpty() && !isValidPhoneNumber(tel)) {
            JOptionPane.showMessageDialog(addClientDialog, 
                "Phone number should contain 8-15 digits only.",
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(addClientDialog, 
                "Passwords do not match.",
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Save client to database
        if (addClientToDatabase(nom, prenom, email, adresse, tel, password)) {
            JOptionPane.showMessageDialog(addClientDialog, 
                "Client added successfully!",
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            addClientDialog.dispose();
        } else {
            JOptionPane.showMessageDialog(addClientDialog, 
                "Failed to add client. Please try again.",
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    });
    
    addClientDialog.setVisible(true);
}

// Helper method to create an input field with a label
private JTextField createInputField(String labelText) {
    JPanel panel = new JPanel(new BorderLayout(10, 5));
    panel.setOpaque(false);
    panel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    JLabel label = new JLabel(labelText);
    label.setFont(new Font("SansSerif", Font.PLAIN, 14));
    
    JTextField textField = new JTextField();
    textField.setPreferredSize(new Dimension(300, 30));
    
    panel.add(label, BorderLayout.NORTH);
    panel.add(textField, BorderLayout.CENTER);
    
    return textField;
}

// Helper method to create a password field with a label
private JPasswordField createPasswordInputField(String labelText) {
    JPanel panel = new JPanel(new BorderLayout(10, 5));
    panel.setOpaque(false);
    panel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    JLabel label = new JLabel(labelText);
    label.setFont(new Font("SansSerif", Font.PLAIN, 14));
    
    JPasswordField passwordField = new JPasswordField();
    passwordField.setPreferredSize(new Dimension(300, 30));
    
    panel.add(label, BorderLayout.NORTH);
    panel.add(passwordField, BorderLayout.CENTER);
    
    return passwordField;
}

// Method to validate email format
private boolean isValidEmail(String email) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    return email.matches(emailRegex);
}

// Method to validate phone number format
private boolean isValidPhoneNumber(String phoneNumber) {
    // Check if phone number contains only digits and has length between 8 and 15
    return phoneNumber.matches("^[0-9]{8,15}$");
}

// Method to add a client to the database
// Method to add a client to the database
private boolean addClientToDatabase(String nom, String prenom, String email, 
                                  String adresse, String tel, String password) {
    Connection conn = null;
    PreparedStatement clientStmt = null;
    PreparedStatement userStmt = null;
    PreparedStatement getIdStmt = null;
    ResultSet rs = null;
    boolean success = false;
    
    try {
        conn = new Connect().getConnection();
        
        // Begin transaction
        conn.setAutoCommit(false);
        
        // 1. Insert into client table - FIX THE COLUMN COUNT TO MATCH YOUR TABLE STRUCTURE
        String clientSql = "INSERT INTO client (nom_client, prenom_client, adresse_client, tel_client) " +
                        "VALUES (?, ?, ?, ?)";
        clientStmt = conn.prepareStatement(clientSql);
        clientStmt.setString(1, nom);
        clientStmt.setString(2, prenom);
        clientStmt.setString(3, adresse);
        clientStmt.setString(4, tel);
        
        int clientRowsAffected = clientStmt.executeUpdate();
        
        if (clientRowsAffected > 0) {
            // Instead of getGeneratedKeys, query for the client ID using a SELECT statement
            String getIdSql = "SELECT id_client FROM client WHERE nom_client = ? AND prenom_client = ? AND tel_client = ?";
            getIdStmt = conn.prepareStatement(getIdSql);
            getIdStmt.setString(1, nom);
            getIdStmt.setString(2, prenom);
            getIdStmt.setString(3, tel);
            
            rs = getIdStmt.executeQuery();
            
            if (rs.next()) {
                int clientId = rs.getInt("id_client");
                
                // 2. Insert into utilisateur table
                String userSql = "INSERT INTO utilisateur (email_utilisateur, mot_de_passe, id_client, type_utilisateur) " +
                                "VALUES (?, ?, ?, 'client')";
                userStmt = conn.prepareStatement(userSql);
                userStmt.setString(1, email);
                userStmt.setString(2, password);
                userStmt.setInt(3, clientId);
                
                int userRowsAffected = userStmt.executeUpdate();
                
                if (userRowsAffected > 0) {
                    // Both insertions successful, commit transaction
                    conn.commit();
                    success = true;
                } else {
                    // User insertion failed, rollback
                    conn.rollback();
                }
            } else {
                // Failed to get client ID, rollback
                conn.rollback();
            }
        } else {
            // Client insertion failed, rollback
            conn.rollback();
        }
        
    } catch (SQLException e) {
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        // Check for unique constraint violation (email already exists)
        if (e.getErrorCode() == 1 || (e.getMessage() != null && e.getMessage().contains("unique constraint"))) {
            JOptionPane.showMessageDialog(this,
                "A client with this email already exists.",
                "Duplicate Email",
                JOptionPane.ERROR_MESSAGE);
        } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Database error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    } finally {
        try {
            if (rs != null) rs.close();
            if (clientStmt != null) clientStmt.close();
            if (getIdStmt != null) getIdStmt.close();
            if (userStmt != null) userStmt.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    return success;
}


// Add this method to your Settings class to create the Add Admin dialog
private void showAddAdminDialog() {
    // Create a dialog for adding a new admin
    JDialog addAdminDialog = new JDialog(this, "Add New Admin", true);
    addAdminDialog.setSize(500, 350);
    addAdminDialog.setLocationRelativeTo(this);
    addAdminDialog.setLayout(new BorderLayout());
    
    // Create a panel with a form layout for admin details
    JPanel formPanel = new JPanel();
    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    formPanel.setBackground(Color.WHITE);
    
    // Add title
    JLabel titleLabel = new JLabel("Add New Admin");
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
    titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    // Create input fields - admins only need email and password
    JTextField emailField = createInputField("Email:");
    JPasswordField passwordField = createPasswordInputField("Password:");
    JPasswordField confirmPasswordField = createPasswordInputField("Confirm Password:");
    
    // Add all components to the form panel
    formPanel.add(titleLabel);
    formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    formPanel.add(emailField.getParent());
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    formPanel.add(passwordField.getParent());
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    formPanel.add(confirmPasswordField.getParent());
    
    // Create buttons panel
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonsPanel.setBackground(Color.WHITE);
    
    JButton cancelButton = new JButton("Cancel");
    cancelButton.setPreferredSize(new Dimension(100, 35));
    cancelButton.setBackground(new Color(240, 240, 240));
    cancelButton.setFocusPainted(false);
    
    JButton saveButton = new JButton("Save");
    saveButton.setPreferredSize(new Dimension(100, 35));
    saveButton.setBackground(new Color(255, 204, 0));
    saveButton.setFocusPainted(false);
    
    buttonsPanel.add(cancelButton);
    buttonsPanel.add(saveButton);
    
    // Add components to dialog
    addAdminDialog.add(formPanel, BorderLayout.CENTER);
    addAdminDialog.add(buttonsPanel, BorderLayout.SOUTH);
    
    // Add action listeners for buttons
    cancelButton.addActionListener(e -> addAdminDialog.dispose());
    
    saveButton.addActionListener(e -> {
        // Validate inputs
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        // Validate required fields
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(addAdminDialog, 
                "Email and password fields are required.",
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate email format
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(addAdminDialog, 
                "Please enter a valid email address.",
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(addAdminDialog, 
                "Passwords do not match.",
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Save admin to database
        if (addAdminToDatabase(email, password)) {
            JOptionPane.showMessageDialog(addAdminDialog, 
                "Admin added successfully!",
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            addAdminDialog.dispose();
        } else {
            JOptionPane.showMessageDialog(addAdminDialog, 
                "Failed to add admin. Please try again.",
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    });
    
    addAdminDialog.setVisible(true);
}

// Method to add an admin to the database
private boolean addAdminToDatabase(String email, String password) {
    Connection conn = null;
    PreparedStatement stmt = null;
    boolean success = false;
    
    try {
        conn = new Connect().getConnection();
        
        // Insert into utilisateur table with type='admin'
        String sql = "INSERT INTO utilisateur (email_utilisateur, mot_de_passe, id_client, type_utilisateur) " +
                      "VALUES (?, ?, NULL, 'admin')";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setString(2, password);
        
        int rowsAffected = stmt.executeUpdate();
        
        if (rowsAffected > 0) {
            // Admin added successfully
            success = true;
        }
        
    } catch (SQLException e) {
        // Check for unique constraint violation (email already exists)
        if (e.getErrorCode() == 1 || (e.getMessage() != null && e.getMessage().contains("unique constraint"))) {
            JOptionPane.showMessageDialog(this,
                "An admin with this email already exists.",
                "Duplicate Email",
                JOptionPane.ERROR_MESSAGE);
        } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Database error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    } finally {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    return success;
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new AdminSettings().setVisible(true);
        });
    }
}