package gestion_compte;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.image.BufferedImage;

public class Settings extends JFrame {
    
    private Color primaryYellow = new Color(255, 196, 0);
    private Color lightGray = new Color(245, 245, 245);
    private Color darkGray = new Color(100, 100, 100);
    private Color redButton = new Color(255, 87, 87);
    
    public Settings() {
        setTitle("RestHive");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create the components
        JPanel headerPanel = createHeaderPanel();
        JPanel sidebarPanel = createSidebarPanel();
        JPanel contentPanel = createContentPanel();
        
        // Add components to frame
        add(headerPanel, BorderLayout.NORTH);
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));
        
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    private JPanel createSidebarPanel() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(Color.WHITE);
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        
        // Logo panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setMaximumSize(new Dimension(200, 60));
        
        // Create logo icon
        ImageIcon logoIcon = createLogoIcon();
        JLabel logoImageLabel = new JLabel(logoIcon);
        
        JLabel logoTextLabel = new JLabel("RestHive");
        logoTextLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        logoPanel.add(logoImageLabel);
        logoPanel.add(logoTextLabel);
        
        sidebarPanel.add(logoPanel);
        sidebarPanel.add(Box.createVerticalStrut(20));
        
        // Menu items
        String[] menuItems = {"Dashboard", "Clients", "Rooms", "Payment", "Reservations", "Users", "Log out"};
        String[] menuIcons = {"📊", "👥", "🏠", "💳", "📝", "👤", "🚪"};
        
        for (int i = 0; i < menuItems.length; i++) {
            JPanel menuItemPanel = createMenuItem(menuItems[i], menuIcons[i]);
            sidebarPanel.add(menuItemPanel);
            sidebarPanel.add(Box.createVerticalStrut(10));
        }
        
        return sidebarPanel;
    }
    
    private ImageIcon createLogoIcon() {
        // Create a simple yellow square as logo
        int size = 30;
        BufferedImage logoImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = logoImage.createGraphics();
        g2d.setColor(primaryYellow);
        g2d.fillRect(0, 0, size, size);
        g2d.setColor(new Color(220, 170, 0));
        g2d.drawRect(0, 0, size-1, size-1);
        g2d.dispose();
        
        return new ImageIcon(logoImage);
    }
    
    private JPanel createMenuItem(String text, String icon) {
        JPanel menuItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        menuItemPanel.setBackground(Color.WHITE);
        menuItemPanel.setMaximumSize(new Dimension(200, 40));
        
        JLabel iconLabel = new JLabel(icon);
        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(darkGray);
        
        menuItemPanel.add(iconLabel);
        menuItemPanel.add(textLabel);
        
        menuItemPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuItemPanel.setBackground(new Color(245, 245, 245));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                menuItemPanel.setBackground(Color.WHITE);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        return menuItemPanel;
    }
    
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(lightGray);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Profile sections
        contentPanel.add(createProfileSection("Name", "Alex Jackson"));
        contentPanel.add(Box.createVerticalStrut(15));
        
        contentPanel.add(createProfileSection("Username", "finalui"));
        contentPanel.add(Box.createVerticalStrut(15));
        
        contentPanel.add(createProfileSection("Password", "••••"));
        contentPanel.add(Box.createVerticalStrut(15));
        
        // Contacts section
        JPanel contactsPanel = new JPanel();
        contactsPanel.setLayout(new BoxLayout(contactsPanel, BoxLayout.Y_AXIS));
        contactsPanel.setBackground(Color.WHITE);
        contactsPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));
        contactsPanel.setMaximumSize(new Dimension(800, 150));
        contactsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel contactHeaderPanel = new JPanel(new BorderLayout());
        contactHeaderPanel.setBackground(Color.WHITE);
        
        JLabel contactsLabel = new JLabel("Contacts");
        contactsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton contactsEditButton = createEditButton();
        contactHeaderPanel.add(contactsLabel, BorderLayout.WEST);
        contactHeaderPanel.add(contactsEditButton, BorderLayout.EAST);
        
        JPanel contactDetailsPanel = new JPanel();
        contactDetailsPanel.setLayout(new BoxLayout(contactDetailsPanel, BoxLayout.Y_AXIS));
        contactDetailsPanel.setBackground(Color.WHITE);
        contactDetailsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        JLabel phoneLabel = new JLabel("Phone:   +12312321792");
        JLabel emailLabel = new JLabel("Email:    finalui@yandex.com");
        JLabel addressLabel = new JLabel("Address: 123 Main Street, Suite 500");
        
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        contactDetailsPanel.add(phoneLabel);
        contactDetailsPanel.add(Box.createVerticalStrut(5));
        contactDetailsPanel.add(emailLabel);
        contactDetailsPanel.add(Box.createVerticalStrut(5));
        contactDetailsPanel.add(addressLabel);
        
        contactsPanel.add(contactHeaderPanel);
        contactsPanel.add(contactDetailsPanel);
        
        contentPanel.add(contactsPanel);
        contentPanel.add(Box.createVerticalStrut(30));
        
        // Bottom buttons section with headers above
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        bottomPanel.setMaximumSize(new Dimension(800, 100));
        bottomPanel.setBackground(lightGray);
        bottomPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Manage Access section
        JPanel manageAccessPanel = new JPanel();
        manageAccessPanel.setLayout(new BoxLayout(manageAccessPanel, BoxLayout.Y_AXIS));
        manageAccessPanel.setBackground(lightGray);
        manageAccessPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel manageLabel = new JLabel("Manage Access");
        manageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        // manageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel manageButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        manageButtonsPanel.setBackground(lightGray);
        
        JButton addReceptionistsButton = createYellowButton("Add receptionists");
        JButton addClientsButton = createYellowButton("Add clients");
        
        manageButtonsPanel.add(addReceptionistsButton);
        manageButtonsPanel.add(addClientsButton);
        
        manageAccessPanel.add(manageLabel);
        manageAccessPanel.add(Box.createVerticalStrut(10));
        manageAccessPanel.add(manageButtonsPanel);
        
        // Delete Account section
        JPanel deleteAccountPanel = new JPanel();
        deleteAccountPanel.setLayout(new BoxLayout(deleteAccountPanel, BoxLayout.Y_AXIS));
        deleteAccountPanel.setBackground(lightGray);
        deleteAccountPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel deleteLabel = new JLabel("Delete Account");
        deleteLabel.setFont(new Font("Arial", Font.BOLD, 16));
        deleteLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel deleteButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        deleteButtonsPanel.setBackground(lightGray);
        
        JButton deleteMyAccountButton = createRedButton("Delete my account");
        JButton deleteOtherAccountsButton = createRedButton("Delete other accounts");
        
        deleteButtonsPanel.add(deleteMyAccountButton);
        deleteButtonsPanel.add(deleteOtherAccountsButton);
        
        deleteAccountPanel.add(deleteLabel);
        deleteAccountPanel.add(Box.createVerticalStrut(10));
        deleteAccountPanel.add(deleteButtonsPanel);
        
        bottomPanel.add(manageAccessPanel);
        bottomPanel.add(deleteAccountPanel);
        
        contentPanel.add(bottomPanel);
        
        return contentPanel;
    }
    
    private JPanel createProfileSection(String label, String value) {
        JPanel sectionPanel = new JPanel(new BorderLayout());
        sectionPanel.setBackground(Color.WHITE);
        sectionPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        sectionPanel.setMaximumSize(new Dimension(800, 70));
        sectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(label);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        valueLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        JButton editButton = createEditButton();
        
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(titleLabel);
        leftPanel.add(valueLabel);
        
        sectionPanel.add(leftPanel, BorderLayout.WEST);
        sectionPanel.add(editButton, BorderLayout.EAST);
        
        return sectionPanel;
    }
    
    private JButton createEditButton() {
        JButton editButton = new JButton("Edit");
        editButton.setForeground(primaryYellow);
        editButton.setBackground(Color.WHITE);
        // editButton.setBorder(BorderFactory.createLineBorder(primaryYellow));
        editButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        editButton.setFocusPainted(false);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add padding to the edit button
        editButton.setMargin(new Insets(5, 15, 5, 15));
        
        return editButton;
    }
    
    private JButton createYellowButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(primaryYellow);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private JButton createRedButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(redButton);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    public static void main(String[] args) {
        // Set the look and feel to the system look and feel
        // try {
        //     UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        
        SwingUtilities.invokeLater(() -> new Settings());
    }
}
