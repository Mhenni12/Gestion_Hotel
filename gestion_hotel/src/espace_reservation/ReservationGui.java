package espace_reservation;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import admin_dashboard.AdminSettings;
import chambre.ChambreGui;
import espace_paiement.PaiementGui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import gestion_base_donnees.Connect;
import utilisateur.GererUtilisateurs;

public class ReservationGui extends JFrame {
    private JTable reservationsTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterField;
    private JTextField searchField;
    private JPanel reservationsHeaderPanel;
    private String currentFilter = "All";
    private List<Object[]> allReservationData;

    public ReservationGui() {
        setTitle("RestHive - Reservations");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        createHeader(mainPanel);
        createContent(mainPanel);
        setContentPane(mainPanel);
        setVisible(true);
    }

    private void createHeader(JPanel mainPanel) {
        // Header panel with yellow background
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 204, 0));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Logo panel
        // Load the logo from file
        

        
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(new Color(255, 204, 0));
        ImageIcon logoIcon = new ImageIcon("gestion_hotel/src/images/hotelLogo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);


        // Menu buttons
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        menuPanel.setBackground(new Color(255, 204, 0));

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
        mainPanel.add(headerPanel, BorderLayout.NORTH);
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

    private ImageIcon createLogoIcon() {
        int size = 40;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Draw hexagon
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * i + Math.PI / 6;
            xPoints[i] = (int) (size / 2 + size / 2 * 0.8 * Math.cos(angle));
            yPoints[i] = (int) (size / 2 + size / 2 * 0.8 * Math.sin(angle));
        }

        g2d.setColor(new Color(255, 204, 0));
        g2d.fillPolygon(xPoints, yPoints, 6);
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawPolygon(xPoints, yPoints, 6);

        // Draw house icon
        int[] houseX = {size/2 - 8, size/2, size/2 + 8};
        int[] houseY = {size/2, size/2 - 8, size/2};
        g2d.setColor(Color.WHITE);
        g2d.fillPolygon(houseX, houseY, 3);
        g2d.fillRect(size/2 - 5, size/2, 10, 8);

        g2d.dispose();
        return new ImageIcon(image);
    }

    private void createContent(JPanel mainPanel) {
        // Background panel
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.setBackground(new Color(240, 240, 240));

        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        contentPanel.setOpaque(false);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        // Reservations header panel
        reservationsHeaderPanel = new JPanel(new BorderLayout());
        reservationsHeaderPanel.setOpaque(false);

        JLabel reservationsLabel = new JLabel("Reservations");
        reservationsLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        reservationsLabel.setForeground(new Color(255, 180, 0));

        // Search and filter panel
        JPanel searchFilterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchFilterPanel.setOpaque(false);

        // Search field
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(new Color(240, 240, 240));
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        searchPanel.setPreferredSize(new Dimension(200, 35));

        JLabel searchIcon = new JLabel("🔍");
        searchField = new JTextField("Search");
        searchField.setBorder(null);
        searchField.setBackground(new Color(240, 240, 240));

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search")) searchField.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) searchField.setText("Search");
            }
        });

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
        });

        searchPanel.add(searchIcon);
        searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        searchPanel.add(searchField);

        // Filter dropdown
        String[] filterOptions = {"All", "ID", "Client ID", "Room No", "Date"};
        filterField = new JComboBox<>(filterOptions);
        filterField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        filterField.setBackground(Color.WHITE);
        filterField.setPreferredSize(new Dimension(120, 35));
        filterField.addActionListener(e -> {
            currentFilter = (String) filterField.getSelectedItem();
            filterTable();
        });

        // Add Reservation button
        JButton addReservationBtn = new JButton("+ New Reservation");
        addReservationBtn.setBackground(new Color(255, 204, 0));
        addReservationBtn.setForeground(Color.BLACK);
        addReservationBtn.setFocusPainted(false);
        addReservationBtn.addActionListener(e -> addNewReservation());

        searchFilterPanel.add(searchPanel);
        searchFilterPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        searchFilterPanel.add(filterField);
        searchFilterPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        searchFilterPanel.add(addReservationBtn);

        reservationsHeaderPanel.add(reservationsLabel, BorderLayout.WEST);
        reservationsHeaderPanel.add(searchFilterPanel, BorderLayout.EAST);

        // Create and configure table
        createReservationsTable();
        JScrollPane scrollPane = new JScrollPane(reservationsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        contentPanel.add(reservationsHeaderPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel paddingPanel = new JPanel(new BorderLayout());
        paddingPanel.setOpaque(false);
        paddingPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        paddingPanel.add(contentPanel, BorderLayout.CENTER);

        backgroundPanel.add(paddingPanel, BorderLayout.CENTER);
        mainPanel.add(backgroundPanel, BorderLayout.CENTER);
    }

    private void createReservationsTable() {
        String[] columnNames = {"Reservation ID", "Date", "Check-In", "Check-Out",
                "Client ID", "Room No", "", ""};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column < 6; // Only button columns are not editable
            }
        };

        // Load data from database
        allReservationData = new ArrayList<>();
        loadReservationsFromDatabase();

        // Add data to table
        for (Object[] row : allReservationData) {
            tableModel.addRow(row);
        }

        reservationsTable = new JTable(tableModel);
        reservationsTable.setRowHeight(50);
        reservationsTable.setShowGrid(true);
        reservationsTable.setGridColor(new Color(220, 220, 220));
        reservationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Table header styling
        JTableHeader header = reservationsTable.getTableHeader();
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        // Custom renderers
        reservationsTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((JLabel) c).setFont(new Font("SansSerif", Font.BOLD, 12));
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        // Action buttons
        reservationsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("✏️"));
        reservationsTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("🗑️"));

        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 6; i++) {
            reservationsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set column widths
        reservationsTable.getColumnModel().getColumn(0).setPreferredWidth(100); // ID
        reservationsTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Date
        reservationsTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Check-In
        reservationsTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Check-Out
        reservationsTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Client ID
        reservationsTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Room No
        reservationsTable.getColumnModel().getColumn(6).setPreferredWidth(50);  // Edit
        reservationsTable.getColumnModel().getColumn(7).setPreferredWidth(50);  // Delete

        // Add mouse listener for buttons
        reservationsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int col = reservationsTable.columnAtPoint(e.getPoint());
                int row = reservationsTable.rowAtPoint(e.getPoint());
                if (row >= 0 && row < reservationsTable.getRowCount()) {
                    if (col == 6) editReservation(row);
                    else if (col == 7) deleteReservation(row);
                }
            }
        });
    }

    private void loadReservationsFromDatabase() {
        String query = "SELECT id_reservation, date_reservation, check_in_date, " +
                "check_out_date, id_client, num_chambre " +
                "FROM reservation ORDER BY date_reservation DESC";

        try (Connection connection = new Connect().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat displayFormat = new SimpleDateFormat("MM/dd/yyyy");

            while (rs.next()) {
                Date resDate = dateFormat.parse(rs.getString("date_reservation"));
                Date checkIn = dateFormat.parse(rs.getString("check_in_date"));
                Date checkOut = dateFormat.parse(rs.getString("check_out_date"));

                Object[] reservation = {
                        rs.getString("id_reservation"),
                        displayFormat.format(resDate),
                        displayFormat.format(checkIn),
                        displayFormat.format(checkOut),
                        rs.getString("id_client"),
                        rs.getString("num_chambre"),
                        "Edit",
                        "Delete"
                };
                allReservationData.add(reservation);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading reservations: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addNewReservation() {
        JDialog addDialog = new JDialog(this, "New Reservation", true);
        addDialog.setSize(500, 350);
        addDialog.setLocationRelativeTo(this);
        addDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        String[] labels = {"Client ID:", "Room Number:", "Reservation Date:",
                "Check-In Date:", "Check-Out Date:"};
        JComponent[] fields = new JComponent[5];

        // Client ID
        fields[0] = new JTextField();

        // Room Number
        fields[1] = new JTextField();

        // Date fields
        for (int i = 2; i <= 4; i++) {
            JTextField dateField = new JTextField();
            dateField.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
            fields[i] = dateField;
        }

        for (int i = 0; i < labels.length; i++) {
            formPanel.add(new JLabel(labels[i]));
            formPanel.add(fields[i]);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                // Validate inputs
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i] instanceof JTextField && ((JTextField)fields[i]).getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(addDialog, "Please fill all required fields",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Create reservation object
                Object[] newReservation = new Object[8];
                newReservation[0] = null; // Will be set by database
                newReservation[1] = ((JTextField)fields[2]).getText();
                newReservation[2] = ((JTextField)fields[3]).getText();
                newReservation[3] = ((JTextField)fields[4]).getText();
                newReservation[4] = ((JTextField)fields[0]).getText();
                newReservation[5] = ((JTextField)fields[1]).getText();
                newReservation[6] = "Edit";
                newReservation[7] = "Delete";

                // Save to database
                if (saveReservationToDatabase(newReservation)) {
                    allReservationData.add(newReservation);
                    tableModel.addRow(newReservation);
                    filterTable();
                    addDialog.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addDialog,
                        "Error creating reservation: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> addDialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        addDialog.add(formPanel, BorderLayout.CENTER);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);
        addDialog.setVisible(true);
    }

    private boolean saveReservationToDatabase(Object[] reservation) {
        String query = "INSERT INTO reservation (date_reservation, check_in_date, " +
                "check_out_date, id_client, num_chambre) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = new Connect().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat displayFormat = new SimpleDateFormat("MM/dd/yyyy");

            stmt.setString(1, dbFormat.format(displayFormat.parse((String)reservation[1])));
            stmt.setString(2, dbFormat.format(displayFormat.parse((String)reservation[2])));
            stmt.setString(3, dbFormat.format(displayFormat.parse((String)reservation[3])));
            stmt.setString(4, (String) reservation[4]);
            stmt.setString(5, (String) reservation[5]);

            int result = stmt.executeUpdate();
            
            // Get the auto-generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation[0] = generatedKeys.getString(1);
                }
            }
            
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error saving reservation: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void editReservation(int row) {
        JDialog editDialog = new JDialog(this, "Edit Reservation", true);
        editDialog.setSize(500, 350);
        editDialog.setLocationRelativeTo(this);
        editDialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        String[] labels = {"Reservation ID:", "Date:", "Check-In:", "Check-Out:",
                "Client ID:", "Room No:"};
        JComponent[] fields = new JComponent[6];

        // Reservation ID (read-only)
        fields[0] = new JLabel((String) tableModel.getValueAt(row, 0));
        ((JLabel)fields[0]).setHorizontalAlignment(JLabel.LEFT);

        // Other fields
        for (int i = 1; i <= 5; i++) {
            fields[i] = new JTextField((String) tableModel.getValueAt(row, i));
        }

        for (int i = 0; i < labels.length; i++) {
            formPanel.add(new JLabel(labels[i]));
            formPanel.add(fields[i]);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                // Update the reservation data
                Object[] updatedReservation = new Object[8];
                updatedReservation[0] = ((JLabel)fields[0]).getText();
                for (int i = 1; i <= 5; i++) {
                    updatedReservation[i] = ((JTextField)fields[i]).getText();
                }
                updatedReservation[6] = "Edit";
                updatedReservation[7] = "Delete";

                // Update database
                if (updateReservationInDatabase(updatedReservation)) {
                    // Update table model
                    for (int i = 0; i < 6; i++) {
                        tableModel.setValueAt(updatedReservation[i], row, i);
                        allReservationData.get(row)[i] = updatedReservation[i];
                    }
                    filterTable();
                    editDialog.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editDialog,
                        "Error updating reservation: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> editDialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        editDialog.add(formPanel, BorderLayout.CENTER);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);
        editDialog.setVisible(true);
    }

    private boolean updateReservationInDatabase(Object[] reservation) {
        String query = "UPDATE reservation SET date_reservation = ?, check_in_date = ?, " +
                "check_out_date = ?, id_client = ?, num_chambre = ? " +
                "WHERE id_reservation = ?";

        try (Connection connection = new Connect().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat displayFormat = new SimpleDateFormat("MM/dd/yyyy");

            stmt.setString(1, dbFormat.format(displayFormat.parse((String)reservation[1])));
            stmt.setString(2, dbFormat.format(displayFormat.parse((String)reservation[2])));
            stmt.setString(3, dbFormat.format(displayFormat.parse((String)reservation[3])));
            stmt.setString(4, (String) reservation[4]);
            stmt.setString(5, (String) reservation[5]);
            stmt.setString(6, (String) reservation[0]);

            int result = stmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error updating reservation: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void deleteReservation(int row) {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this reservation?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {
            String reservationId = (String) tableModel.getValueAt(row, 0);
            if (deleteReservationFromDatabase(reservationId)) {
                allReservationData.remove(row);
                tableModel.removeRow(row);
                filterTable();
            }
        }
    }

private boolean deleteReservationFromDatabase(String reservationId) {
    // First delete from payment table
    String deletePaymentQuery = "DELETE FROM paiement WHERE id_reservation = ?";
    // Then delete from reservation table
    String deleteReservationQuery = "DELETE FROM reservation WHERE id_reservation = ?";
    
    Connection connection = null;
    
    try {
        connection = new Connect().getConnection();
        // Start transaction to ensure both operations succeed or fail together
        connection.setAutoCommit(false);
        
        // Delete payments first
        try (PreparedStatement paymentStmt = connection.prepareStatement(deletePaymentQuery)) {
            paymentStmt.setString(1, reservationId);
            paymentStmt.executeUpdate();
        }
        
        // Then delete reservation
        try (PreparedStatement reservationStmt = connection.prepareStatement(deleteReservationQuery)) {
            reservationStmt.setString(1, reservationId);
            int result = reservationStmt.executeUpdate();
            
            // Commit transaction if everything went well
            connection.commit();
            
            return result > 0;
        }
    } catch (SQLException e) {
        // Rollback transaction in case of error
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Error deleting reservation: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        return false;
    } finally {
        // Reset auto-commit and close connection
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

    private void filterTable() {
        tableModel.setRowCount(0);
        String searchText = searchField.getText().toLowerCase();
        if (searchText.equals("search")) searchText = "";

        for (Object[] reservation : allReservationData) {
            boolean matches = true;
            if (!"All".equals(currentFilter) && !searchText.isEmpty()) {
                int col = getColumnIndexForFilter(currentFilter);
                if (col >= 0) {
                    String cellValue = reservation[col].toString().toLowerCase();
                    matches = cellValue.contains(searchText);
                }
            }
            if (matches) tableModel.addRow(reservation);
        }
    }

    private int getColumnIndexForFilter(String filter) {
        switch (filter) {
            case "ID": return 0;
            case "Client ID": return 4;
            case "Room No": return 5;
            case "Date": return 1;
            default: return -1;
        }
    }

    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text) {
            setText(text);
            setOpaque(true);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setHorizontalAlignment(JLabel.CENTER);
            setFont(new Font("SansSerif", Font.PLAIN, 16));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new ReservationGui();
        });
    }
}