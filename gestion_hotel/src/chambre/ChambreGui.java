package chambre;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import admin_dashboard.AdminSettings;
import espace_paiement.PaiementGui;
import espace_reservation.ReservationGui;
import utilisateur.GererUtilisateurs;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.awt.image.BufferedImage;

public class ChambreGui extends JFrame {
    private JTable roomsTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterField;
    private JTextField searchField;
    private JPanel roomsHeaderPanel;
    private String currentFilter = "All";
    
    private List<Object[]> allRoomData;
    
    public ChambreGui() {
        setTitle("RestHive");
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
        // Creating the header panel with yellow background
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 204, 0)); // Yellow color
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        // Logo panel with the "RestHive" text
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(new Color(255, 204, 0));
        
        // Create logo icon
        JLabel logoIcon = new JLabel(createLogoIcon());
        
        // Create title label
        JLabel titleLabel = new JLabel("RestHive");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        
        logoPanel.add(logoIcon);
        logoPanel.add(titleLabel);
        
        // Menu buttons - centered in header
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
        
        // JButton clientsBtn = createMenuButton("Clients", false);
        // JButton reservationBtn = createMenuButton("Reservation", false);
        // JButton roomsBtn = createMenuButton("Rooms", true);
        // JButton paymentBtn = createMenuButton("Payment", false);
        // JButton settingsBtn = createMenuButton("Settings", false);
        
        // menuPanel.add(clientsBtn);
        // menuPanel.add(reservationBtn);
        // menuPanel.add(roomsBtn);
        // menuPanel.add(paymentBtn);
        // menuPanel.add(settingsBtn);
        
        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(menuPanel, BorderLayout.CENTER);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }
    
    private void createContent(JPanel mainPanel) {
        // Background panel
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        
        // Content panel - white background with rounded corners
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
        
        // Rooms title and search panel
        roomsHeaderPanel = new JPanel(new BorderLayout());
        roomsHeaderPanel.setOpaque(false);
        
        JLabel roomsLabel = new JLabel("Rooms");
        roomsLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        roomsLabel.setForeground(new Color(255, 180, 0));
        
        JPanel searchFilterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchFilterPanel.setOpaque(false);
        
        // Search field with icon
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
        
        // Add focus listener to clear default text
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search")) {
                    searchField.setText("");
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search");
                }
            }
        });
        
        // Add document listener to filter table based on search text
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterTable();
            }
            
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterTable();
            }
            
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterTable();
            }
        });
        
        searchPanel.add(searchIcon);
        searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        searchPanel.add(searchField);
        
        // Filter dropdown
        String[] filterOptions = {"All", "Num", "Capacity", "Status", "Type", "Floor"};
        filterField = new JComboBox<>(filterOptions);
        filterField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        filterField.setBackground(Color.WHITE);
        filterField.setPreferredSize(new Dimension(100, 35));
        filterField.setFocusable(false);
        
        // Add action listener to update filtering
        filterField.addActionListener(e -> {
            currentFilter = (String) filterField.getSelectedItem();
            
            // If Status filter is selected, populate search field with dropdown
            if ("Status".equals(currentFilter)) {
                // Replace search field with a dropdown for status options
                searchPanel.remove(searchField);
                JComboBox<String> statusDropdown = new JComboBox<>(new String[]{"All", "Free", "Booked", "Refurb"});
                statusDropdown.setBorder(null);
                statusDropdown.setBackground(new Color(240, 240, 240));
                statusDropdown.addActionListener(event -> filterTable());
                searchPanel.add(statusDropdown);
                searchPanel.revalidate();
                searchPanel.repaint();
            } else {
                // Restore text search field if it's not there
                if (searchPanel.getComponentCount() < 3) {
                    searchPanel.removeAll();
                    searchPanel.add(searchIcon);
                    searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                    searchPanel.add(searchField);
                    searchPanel.revalidate();
                    searchPanel.repaint();
                }
            }
            
            filterTable();
        });
        
        searchFilterPanel.add(searchPanel);
        searchFilterPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        searchFilterPanel.add(filterField);
        
        roomsHeaderPanel.add(roomsLabel, BorderLayout.WEST);
        roomsHeaderPanel.add(searchFilterPanel, BorderLayout.EAST);
        
        // Create table for rooms
        createRoomsTable();
        
        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(roomsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // Add components to content panel
        contentPanel.add(roomsHeaderPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add padding around the content panel
        JPanel paddingPanel = new JPanel(new BorderLayout());
        paddingPanel.setOpaque(false);
        paddingPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        paddingPanel.add(contentPanel, BorderLayout.CENTER);
        
        backgroundPanel.add(paddingPanel, BorderLayout.CENTER);
        mainPanel.add(backgroundPanel, BorderLayout.CENTER);
    }
    
    private void createRoomsTable() {
        String[] columnNames = {"Num", "Capacity", "Status", "Type", "Floor", "Daily-price", "", ""};
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column < 6; // Allow editing all columns except button columns
            }
        };
        
        // Sample data
        allRoomData = new ArrayList<>(Arrays.asList(
            new Object[]{"101", "2", "Free", "Standard", "1", "120", "Edit", "Delete"},
            new Object[]{"102", "3", "Refurb", "Deluxe", "1", "180", "Edit", "Delete"},
            new Object[]{"201", "4", "Booked", "Suite", "2", "250", "Edit", "Delete"},
            new Object[]{"202", "2", "Free", "Standard", "2", "120", "Edit", "Delete"},
            new Object[]{"301", "2", "Booked", "Standard", "3", "120", "Edit", "Delete"},
            new Object[]{"302", "3", "Free", "Deluxe", "3", "180", "Edit", "Delete"}
        ));
        
        // Add all data to the table
        for (Object[] row : allRoomData) {
            tableModel.addRow(row);
        }
        
        roomsTable = new JTable(tableModel);
        roomsTable.setRowHeight(50);
        roomsTable.setShowGrid(true);
        roomsTable.setGridColor(new Color(220, 220, 220));
        roomsTable.setIntercellSpacing(new Dimension(0, 0));
        roomsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Table header styling
        JTableHeader header = roomsTable.getTableHeader();
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        
        // Custom cell renderers
        // Num column (bold text)
        roomsTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((JLabel) c).setFont(new Font("SansSerif", Font.BOLD, 12));
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });
        
        // Custom renderer for status column
        roomsTable.getColumnModel().getColumn(2).setCellRenderer(new StatusCellRenderer());
        
        // Custom renderer for type column
        roomsTable.getColumnModel().getColumn(3).setCellRenderer(new TypeCellRenderer());
        
        // Edit button renderer
        roomsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("✏️"));
        
        // Delete button renderer
        roomsTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("🗑️"));
        
        // Set column widths
        roomsTable.getColumnModel().getColumn(0).setPreferredWidth(70);  // Num
        roomsTable.getColumnModel().getColumn(1).setPreferredWidth(70);  // Capacity
        roomsTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Status
        roomsTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Type
        roomsTable.getColumnModel().getColumn(4).setPreferredWidth(70);  // Floor
        roomsTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Daily-price
        roomsTable.getColumnModel().getColumn(6).setPreferredWidth(50);  // Edit
        roomsTable.getColumnModel().getColumn(7).setPreferredWidth(50);  // Delete
        
        // Center all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < 6; i++) {
            if (i != 2 && i != 3) { // Skip Status and Type columns which have custom renderers
                roomsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        
        // Add mouse listener for edit and delete buttons
        roomsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int col = roomsTable.columnAtPoint(e.getPoint());
                int row = roomsTable.rowAtPoint(e.getPoint());
                
                if (row >= 0 && row < roomsTable.getRowCount()) {
                    if (col == 6) { // Edit button
                        editRow(row);
                    } else if (col == 7) { // Delete button
                        deleteRow(row);
                    }
                }
            }
        });
    }
    
    private void editRow(int row) {
        // Create a dialog to edit the row data
        JDialog editDialog = new JDialog(this, "Edit Room", true);
        editDialog.setSize(400, 300);
        editDialog.setLocationRelativeTo(this);
        editDialog.setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        String[] labels = {"Num:", "Capacity:", "Status:", "Type:", "Floor:", "Daily Price:"};
        JTextField[] fields = new JTextField[6];
        
        // For Status dropdown
        String[] statusOptions = {"Free", "Booked", "Refurb"};
        JComboBox<String> statusField = new JComboBox<>(statusOptions);
        statusField.setSelectedItem(tableModel.getValueAt(row, 2));
        
        // For Type dropdown
        String[] typeOptions = {"Standard", "Deluxe", "Suite", "Executive"};
        JComboBox<String> typeField = new JComboBox<>(typeOptions);
        typeField.setSelectedItem(tableModel.getValueAt(row, 3));
        
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("SansSerif", Font.BOLD, 12));
            
            formPanel.add(label);
            
            if (i == 2) { // Status field
                formPanel.add(statusField);
            } else if (i == 3) { // Type field
                formPanel.add(typeField);
            } else {
                fields[i] = new JTextField(tableModel.getValueAt(row, i).toString());
                formPanel.add(fields[i]);
            }
        }
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            // Update the table model with edited values
            tableModel.setValueAt(fields[0].getText(), row, 0);
            tableModel.setValueAt(fields[1].getText(), row, 1);
            tableModel.setValueAt(statusField.getSelectedItem(), row, 2);
            tableModel.setValueAt(typeField.getSelectedItem(), row, 3);
            tableModel.setValueAt(fields[4].getText(), row, 4);
            tableModel.setValueAt(fields[5].getText(), row, 5);
            
            // Update the data in our allRoomData list
            for (int i = 0; i < 6; i++) {
                if (i == 2) {
                    allRoomData.get(row)[i] = statusField.getSelectedItem();
                } else if (i == 3) {
                    allRoomData.get(row)[i] = typeField.getSelectedItem();
                } else {
                    allRoomData.get(row)[i] = fields[i].getText();
                }
            }
            
            editDialog.dispose();
            // Reapply filters to ensure consistency
            filterTable();
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> editDialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        editDialog.add(formPanel, BorderLayout.CENTER);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);
        editDialog.setVisible(true);
    }
    
    private void deleteRow(int row) {
        int option = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this room?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );
        
        if (option == JOptionPane.YES_OPTION) {
            // Remove from both tableModel and allRoomData
            Object[] rowData = allRoomData.get(row);
            allRoomData.remove(row);
            tableModel.removeRow(row);
            
            // Reapply filters to ensure consistency
            filterTable();
        }
    }
    
    private void filterTable() {
        // Clear existing table data
        tableModel.setRowCount(0);
        
        // Get selected filter and search text
        String filterType = (String) filterField.getSelectedItem();
        String searchText = "";
        
        Component searchComponent = null;  //((JPanel) roomsHeaderPanel.getComponent(1)).getComponent(0).getComponentCount() > 2 ?
        //         ((JPanel) roomsHeaderPanel.getComponent(1)).getComponent(0).getComponent(2) : null;
        
        // Check if it's the status dropdown or text field
        if (searchComponent instanceof JComboBox) {
            searchText = (String) ((JComboBox<?>) searchComponent).getSelectedItem();
        } else if (searchField != null && !searchField.getText().equals("Search")) {
            searchText = searchField.getText().toLowerCase();
        }
        
        // Apply filters
        for (Object[] room : allRoomData) {
            boolean include = true;
            
            if (!"All".equals(filterType) && !searchText.isEmpty() && !"All".equals(searchText)) {
                int colIndex = getColumnIndexForFilter(filterType);
                
                if (colIndex >= 0) {
                    String cellValue = room[colIndex].toString().toLowerCase();
                    
                    // For Status dropdown, exact match is required
                    if (filterType.equals("Status") && searchComponent instanceof JComboBox) {
                        include = cellValue.equals(searchText.toLowerCase());
                    } else {
                        // For other filters, check if cell contains search text
                        include = cellValue.contains(searchText);
                    }
                }
            }
            
            if (include) {
                tableModel.addRow(room);
            }
        }
    }
    
    private int getColumnIndexForFilter(String filterType) {
        switch (filterType) {
            case "Num": return 0;
            case "Capacity": return 1;
            case "Status": return 2;
            case "Type": return 3;
            case "Floor": return 4;
            case "Daily-price": return 5;
            default: return -1;
        }
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
        // Create a simple hexagonal logo
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
        
        // Draw a simple house
        int[] houseX = {size/2 - 8, size/2, size/2 + 8};
        int[] houseY = {size/2, size/2 - 8, size/2};
        g2d.setColor(Color.WHITE);
        g2d.fillPolygon(houseX, houseY, 3);
        
        g2d.fillRect(size/2 - 5, size/2, 10, 8);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    // Custom renderer for the status cell
    static class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 15));
            panel.setBorder(BorderFactory.createEmptyBorder());
            
            JLabel statusLabel = new JLabel(value.toString());
            statusLabel.setOpaque(true);
            statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            statusLabel.setForeground(Color.WHITE);
            statusLabel.setHorizontalAlignment(JLabel.CENTER);
            
            // Set different colors based on status
            if ("Free".equals(value)) {
                statusLabel.setBackground(new Color(120, 200, 120));
            } else if ("Refurb".equals(value)) {
                statusLabel.setBackground(new Color(255, 100, 100));
            } else if ("Booked".equals(value)) {
                statusLabel.setBackground(new Color(255, 200, 50));
            }
            
            // Add a small dot before the text
            JPanel dotPanel = new JPanel();
            dotPanel.setPreferredSize(new Dimension(8, 8));
            dotPanel.setBackground(statusLabel.getBackground());
            dotPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            
            panel.add(dotPanel);
            panel.add(statusLabel);
            panel.setBackground(table.getBackground());
            
            return panel;
        }
    }
    
    // Custom renderer for the type cell with dropdown icon
    static class TypeCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            
            JLabel typeLabel = new JLabel(value.toString());
            typeLabel.setForeground(Color.GRAY);
            typeLabel.setHorizontalAlignment(JLabel.CENTER);
            
            JLabel arrowLabel = new JLabel("▼");
            arrowLabel.setForeground(Color.GRAY);
            arrowLabel.setFont(new Font("Arial", Font.PLAIN, 10));
            
            panel.add(typeLabel, BorderLayout.CENTER);
            panel.add(arrowLabel, BorderLayout.EAST);
            panel.setBackground(table.getBackground());
            
            return panel;
        }
    }
    
    // Custom renderer for buttons
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
            new ChambreGui();
        });
    }
}
