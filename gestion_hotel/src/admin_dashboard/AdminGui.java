package admin_dashboard;

import utilisateur.Utilisateur;
import gestion_base_donnees.Connect;

// import code.Clients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;      // Interface for List
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;


public class AdminGui extends JFrame{

    public AdminGui(Utilisateur user) {
        //TODO: add addictional functionalities
        AdminGui dashboard = new AdminGui();
        dashboard.setVisible(true);
    }
    
    public AdminGui() {
        setTitle("RestHive");
        //TODO: revist size across all windows
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header panel with yellow background
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 204, 0));
        headerPanel.setPreferredSize(new Dimension(800, 50));

        // Load the logo from file
        ImageIcon logoIcon = new ImageIcon("gestion_hotel/src/images/hotelLogo.png");

        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setPreferredSize(new Dimension(40, 40));
        logoLabel.setBackground(new Color(255, 204, 0));

        // Create a label for RestHive text
        JLabel titleLabel = new JLabel("RestHive");
        titleLabel.setFont(new Font("Sans-serif", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        // Menu items
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        menuPanel.setBackground(new Color(255, 204, 0));

        String[] menuItems = {"Clients", "Reservation", "Rooms", "Payment", "Settings"};

        for (String item : menuItems) {
            JLabel menuLabel = new JLabel(item);
            menuLabel.setFont(new Font("Sans-serif", Font.BOLD, 14));
            menuLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
            
            // Make the label clickable
            menuLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            // Add click action for Clients
            if (item.equals("Clients")) {
                menuLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Close current window
                        Window currentWindow = SwingUtilities.getWindowAncestor(menuPanel);
                        currentWindow.dispose();
                        
                        // Open Clients window
                        // TODO: open clients window: uncomment the following code
                        // SwingUtilities.invokeLater(() -> {
                        //     new code.Clients().setVisible(true);
                        // });
                    }
                    
                    // Optional: Add hover effects
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        menuLabel.setForeground(Color.BLUE);
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        menuLabel.setForeground(Color.BLACK);
                    }
                });
            }
            
            menuPanel.add(menuLabel);
        }

        // Add components to header
        JPanel leftHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftHeaderPanel.setBackground(new Color(255, 204, 0));
        leftHeaderPanel.add(logoLabel);
        leftHeaderPanel.add(titleLabel);
        
        headerPanel.add(leftHeaderPanel, BorderLayout.WEST);
        headerPanel.add(menuPanel, BorderLayout.CENTER); // Center the Menu

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));
        
        // Welcome message
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        welcomePanel.setBackground(Color.WHITE);
        JLabel heyLabel = new JLabel("Hey, ");
        heyLabel.setFont(new Font("Sans-serif", Font.BOLD, 18));
        JLabel adminLabel = new JLabel("Admin");
        adminLabel.setFont(new Font("Sans-serif", Font.BOLD, 18));
        adminLabel.setForeground(new Color(255, 165, 0));
        JLabel exclamationLabel = new JLabel("!");
        exclamationLabel.setFont(new Font("Sans-serif", Font.PLAIN, 18));
        
        welcomePanel.add(heyLabel);
        welcomePanel.add(adminLabel);
        welcomePanel.add(exclamationLabel);
        
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statsPanel.setBackground(Color.WHITE);
        JLabel statsLabel = new JLabel("Here are your stats for today");
        statsLabel.setFont(new Font("Sans-serif", Font.BOLD, 14));
        statsPanel.add(statsLabel);
        
        // Add welcome components to content
        contentPanel.add(welcomePanel);
        contentPanel.add(statsPanel);

        // Top clients section
        JPanel topClientsHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topClientsHeaderPanel.setBackground(Color.WHITE);
        JLabel topClientsLabel = new JLabel("Top clients");
        topClientsLabel.setFont(new Font("Sans-serif", Font.BOLD, 16));
        topClientsHeaderPanel.add(topClientsLabel);
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(topClientsHeaderPanel);
        
        // Top clients cards
        JPanel clientsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        clientsPanel.setBackground(Color.WHITE);

        // Add client cards
        //TODO: alka hal
        List<Integer> counts = getTopClientBookingCounts();
        List<String> topClients = getTopClientsWithMostReservations(3);
        clientsPanel.add(createClientCard("1", topClients.get(0), counts.get(0),Color.GREEN));
        clientsPanel.add(createClientCard("2", topClients.get(1), counts.get(1), Color.BLACK));
        clientsPanel.add(createClientCard("3", topClients.get(2), counts.get(2), Color.BLACK));

        contentPanel.add(clientsPanel);

        // Top rooms section
        JPanel topRoomsHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topRoomsHeaderPanel.setBackground(Color.WHITE);
        JLabel topRoomsLabel = new JLabel("Top rooms");
        topRoomsLabel.setFont(new Font("Sans-serif", Font.BOLD, 16));
        topRoomsHeaderPanel.add(topRoomsLabel);
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(topRoomsHeaderPanel);
        
        // Top rooms cards
        JPanel roomsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        roomsPanel.setBackground(Color.WHITE);
        
        // Add room cards
        List<String> topRooms = getTopBookedRooms();
        //Get the count
        List<String> count = getCount();

        // add more rooms!!!!!!
        roomsPanel.add(createRoomCard("1", topRooms.get(0), count.get(0), Color.GREEN));
        roomsPanel.add(createRoomCard("2", topRooms.get(1), count.get(1), Color.BLACK));
        roomsPanel.add(createRoomCard("3", "lastRoom", "0", Color.BLACK));
        
        contentPanel.add(roomsPanel);
        
        // Activity section
        JPanel activityHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        activityHeaderPanel.setBackground(Color.WHITE);
        JLabel activityLabel = new JLabel("Activity");
        activityLabel.setFont(new Font("Sans-serif", Font.BOLD, 16));
        activityHeaderPanel.add(activityLabel);
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(activityHeaderPanel);
        
        // Activity circles
        JPanel activityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 10));
        activityPanel.setBackground(Color.WHITE);
        
        // Add activity circles with black text
        int[] roomStatusCounts = getRoomStatusCounts();
        activityPanel.add(createActivityCircle(Integer.toString(roomStatusCounts[0]), "free", new Color(141, 214, 103), Color.BLACK));
        activityPanel.add(createActivityCircle(Integer.toString(roomStatusCounts[1]), "booked", new Color(255, 219, 103), Color.BLACK));
        activityPanel.add(createActivityCircle(Integer.toString(roomStatusCounts[1]), "refurb", new Color(239, 131, 131), Color.BLACK));
        
        contentPanel.add(activityPanel);
        
        // Add panels to frame
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        
        setLocationRelativeTo(null);
    }

    // getting top clients
    private List<String> getTopClientsWithMostReservations(int limit) {
        List<String> topClients = new ArrayList<>();
        String query = "SELECT CONCAT(c.nom_client, ' ', c.prenom_client) AS full_name " + // ! MySQL
                    "FROM client c " + 
                    "JOIN reservation r ON c.id_client = r.id_client " +
                    "GROUP BY c.nom_client, c.prenom_client " +
                    "ORDER BY COUNT(r.id_reservation) DESC " +
                    "LIMIT ?; "; // ! MYSQL
        
        try (Connection connection = new Connect().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                topClients.add(rs.getString("full_name"));  // Get pre-formatted name
            }
            //TODO: close connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topClients;
    }

    // getting top rooms
    public List<String> getTopBookedRooms() {
        List<String> topRooms = new ArrayList<>();
        String query = "SELECT c.num_chambre, c.type_chambre, COUNT(r.id_reservation) AS booking_count " +
                    "FROM chambre c " +
                    "JOIN reservation r ON c.num_chambre = r.num_chambre " +
                    "GROUP BY c.num_chambre, c.type_chambre " +
                    "ORDER BY booking_count DESC " +
                    "LIMIT 3; "; //! MySQL

        try (Connection connection = new Connect().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String roomInfo = String.format("Room %d",
                    rs.getInt("num_chambre"));
                topRooms.add(roomInfo);
            }

            //TODO: close connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            topRooms.add("Error retrieving room data");
        }
        return topRooms;
    }

    //TODO: fech ta3mel??
    // get the count of the top booked rooms
    public List<String> getCount() {
        List<String> topRooms = new ArrayList<>();
        String query = "SELECT c.num_chambre, c.type_chambre, COUNT(r.id_reservation) AS booking_count " +
                    "FROM chambre c " +
                    "JOIN reservation r ON c.num_chambre = r.num_chambre " +
                    "GROUP BY c.num_chambre, c.type_chambre " +
                    "ORDER BY booking_count DESC " +
                    "LIMIT 3; "; // !MySQL

        try (Connection connection = new Connect().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String roomInfo = String.format("%d",
                    rs.getInt("booking_count"));
                topRooms.add(roomInfo);
            }
            //TODO: close connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            topRooms.add("Error retrieving room data");
        }
        return topRooms;
    }


    //getting activity 
    public static int[] getRoomStatusCounts() {
        int[] counts = new int[3]; // [libre, reservee, en renovation]
        String query = "SELECT etat_chambre, COUNT(*) as count " +
                    "FROM chambre " +
                    "GROUP BY etat_chambre; ";

        try (Connection connection = new Connect().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            // Initialize all counts to 0
            counts[0] = 0; // libre
            counts[1] = 0; // reservee
            counts[2] = 0; // en renovation

            // Process results
            while (rs.next()) {
                String status = rs.getString("etat_chambre");
                int count = rs.getInt("count");
                
                switch (status) {
                    case "libre":
                        counts[0] = count;
                        break;
                    case "reservee":
                        counts[1] = count;
                        break;
                    case "en renovation":
                        counts[2] = count;
                        break;
                }
            }
            //TODO: close connection
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error getting room status counts:");
            e.printStackTrace();
        }
        return counts;
    }

    // booking counts
    public List<Integer> getTopClientBookingCounts() {
        List<Integer> bookingCounts = new ArrayList<>();
        String query = "SELECT COUNT(r.id_reservation) AS booking_count " +
                    "FROM client c " +
                    "JOIN reservation r ON c.id_client = r.id_client " +
                    "GROUP BY c.id_client " +
                    "ORDER BY booking_count DESC " +
                    "LIMIT 3; "; //!MySQL

        try (Connection connection = new Connect().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bookingCounts.add(rs.getInt("booking_count"));
            }
            //TODO: close connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingCounts;
    }

    private JPanel createClientCard(String number, String name, int count, Color numColor) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(150, 70));
        card.setBackground(new Color(240, 240, 240));
        card.setBorder(new RoundedBorder(15));
        
        GridBagConstraints gbc = new GridBagConstraints();        
        // Client name label - en gras
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Sans-serif", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 2, 0);
        card.add(nameLabel, gbc);
        
        // Number
        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(new Font("Sans-serif", Font.BOLD, 24));
        numberLabel.setForeground(numColor);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 5, 20);
        card.add(numberLabel, gbc);
        
        // Percentage panel avec icône calendrier
        JPanel percentagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
        percentagePanel.setBackground(new Color(255, 204, 0));
        percentagePanel.setBorder(new RoundedBorder(10));
        
        //TODO: add calendar icon
        // Ajouter l'icône calendrier
        JLabel calendarIcon = new JLabel("\uD83D\uDCC5"); // Utilisation d'un simple carré comme icône de remplacement
        calendarIcon.setFont(new Font("Dialog", Font.PLAIN, 10));
        percentagePanel.add(calendarIcon);
        
        // Reservation count
        JLabel percentageLabel = new JLabel(Integer.toString(count));
        percentageLabel.setFont(new Font("Sans-serif", Font.ITALIC, 13));
        percentagePanel.add(percentageLabel);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 5, 10);
        card.add(percentagePanel, gbc);
        
        return card;
    }
    
    private JPanel createRoomCard(String number, String room, String count, Color numColor) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(150, 70));
        card.setBackground(new Color(240, 240, 240));
        card.setBorder(new RoundedBorder(15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Client name label - en gras
        JLabel nameLabel = new JLabel(room);
        nameLabel.setFont(new Font("Sans-serif", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 2, 0);
        card.add(nameLabel, gbc);
        
        // Number
        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(new Font("Sans-serif", Font.BOLD, 24));
        numberLabel.setForeground(numColor);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 5, 20);
        card.add(numberLabel, gbc);
        
        // Percentage panel avec icône calendrier
        JPanel percentagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
        percentagePanel.setBackground(new Color(255, 204, 0));
        percentagePanel.setBorder(new RoundedBorder(10));
        
        // Ajouter l'icône calendrier
        //TODO add calendar icon
        JLabel calendarIcon = new JLabel("\uD83D\uDCC5"); // Utilisation d'un simple carré comme icône de remplacement
        calendarIcon.setFont(new Font("Dialog", Font.PLAIN, 10));
        percentagePanel.add(calendarIcon);
        
        // Percentage label
        JLabel percentageLabel = new JLabel(count);
        percentageLabel.setFont(new Font("Sans-serif", Font.ITALIC, 10));
        percentagePanel.add(percentageLabel);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 5, 10);
        card.add(percentagePanel, gbc);
        
        return card;
    }
    
    private JPanel createActivityCircle(String number, String text, Color bgColor, Color textColor) {
        JPanel circlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(bgColor);
                g2d.fillOval(0, 0, 80, 80);
                g2d.dispose();
            }
        };
        
        circlePanel.setLayout(new BoxLayout(circlePanel, BoxLayout.Y_AXIS));
        circlePanel.setPreferredSize(new Dimension(80, 80));
        circlePanel.setOpaque(false);
        
        JLabel numberLabel = new JLabel(number);
        numberLabel.setForeground(Color.WHITE);
        numberLabel.setFont(new Font("Sans-serif", Font.BOLD, 24));
        numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(textColor); // Couleur du texte en noir
        textLabel.setFont(new Font("Sans-serif", Font.PLAIN, 12));
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        circlePanel.add(Box.createVerticalGlue());
        circlePanel.add(numberLabel);
        circlePanel.add(textLabel);
        circlePanel.add(Box.createVerticalGlue());
        
        return circlePanel;
    }
    
    // Custom rounded border
    static class RoundedBorder implements Border {
        private int radius;
        
        RoundedBorder(int radius) {
            this.radius = radius;
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius/2, this.radius/2, this.radius/2, this.radius/2);
        }
        
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawRoundRect(x, y, width-1, height-1, radius, radius);
            g2d.dispose();
        }
    }
    
    // Icône calendrier
    static class CalendarIcon extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int w = getWidth();
            int h = getHeight();
            
            // Dessiner le calendrier
            g2d.setColor(Color.BLACK);
            g2d.drawRect(2, 2, w-4, h-4);
            g2d.drawLine(2, h/3, w-2, h/3);
            
            g2d.dispose();
        }
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(12, 12);
        }
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
                AdminGui dashboard = new AdminGui();
                dashboard.setVisible(true);
            }
        });
    }
}
