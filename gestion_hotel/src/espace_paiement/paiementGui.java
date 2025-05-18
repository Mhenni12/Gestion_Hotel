package espace_paiement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import admin_dashboard.AdminSettings;
import chambre.ChambreGui;
import espace_reservation.ReservationGui;
import gestion_base_donnees.Connect;
import utilisateur.GererUtilisateurs;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaiementGui extends JFrame {
    private JTable paymentsTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterField;
    private JTextField searchField;
    private JPanel paymentsHeaderPanel;
    private String currentFilter = "All";
    
    private List<Object[]> allPaymentData;
    
    public PaiementGui() {
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
        
        // Payments title and search panel
        paymentsHeaderPanel = new JPanel(new BorderLayout());
        paymentsHeaderPanel.setOpaque(false);
        
        JLabel paymentsLabel = new JLabel("Payments");
        paymentsLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        paymentsLabel.setForeground(new Color(255, 180, 0));
        
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
        
        JLabel searchIcon = new JLabel("\uD83D\uDD01");
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
        String[] filterOptions = {"All", "ID", "Reservation ID", "Amount", "Date", "Method"};
        filterField = new JComboBox<>(filterOptions);
        filterField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        filterField.setBackground(Color.WHITE);
        filterField.setPreferredSize(new Dimension(120, 35));
        filterField.setFocusable(false);
        
        // Add action listener to update filtering
        filterField.addActionListener(e -> {
            currentFilter = (String) filterField.getSelectedItem();
            
            // If Method filter is selected, populate search field with dropdown
            if ("Method".equals(currentFilter)) {
                // Replace search field with a dropdown for payment method options
                searchPanel.remove(searchField);
                JComboBox<String> methodDropdown = new JComboBox<>(new String[]{"All", "Cash", "Card"});
                methodDropdown.setBorder(null);
                methodDropdown.setBackground(new Color(240, 240, 240));
                methodDropdown.addActionListener(event -> filterTable());
                searchPanel.add(methodDropdown);
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
        
        paymentsHeaderPanel.add(paymentsLabel, BorderLayout.WEST);
        paymentsHeaderPanel.add(searchFilterPanel, BorderLayout.EAST);
        
        // Create table for payments
        createPaymentsTable();
        
        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(paymentsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // Add components to content panel
        contentPanel.add(paymentsHeaderPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add padding around the content panel
        JPanel paddingPanel = new JPanel(new BorderLayout());
        paddingPanel.setOpaque(false);
        paddingPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        paddingPanel.add(contentPanel, BorderLayout.CENTER);
        
        backgroundPanel.add(paddingPanel, BorderLayout.CENTER);
        mainPanel.add(backgroundPanel, BorderLayout.CENTER);
    }
    

    private void createPaymentsTable() {
        String[] columnNames = {"ID", "Reservation ID", "Amount", "Date", "Method", "", ""};
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column < 5; // Allow editing all columns except button columns
            }
        };
        
        // Use sample data instead of database connection
        allPaymentData = new ArrayList<>(Arrays.asList(
            new Object[]{"PAY001", "RSV102", "$250.00", "2025-04-15", "Cash", "Edit", "Delete"},
            new Object[]{"PAY002", "RSV103", "$180.00", "2025-04-16", "Card", "Edit", "Delete"},
            new Object[]{"PAY003", "RSV105", "$320.00", "2025-04-17", "Cash", "Edit", "Delete"},
            new Object[]{"PAY004", "RSV109", "$420.00", "2025-04-18", "Card", "Edit", "Delete"},
            new Object[]{"PAY005", "RSV110", "$175.50", "2025-04-19", "Cash", "Edit", "Delete"},
            new Object[]{"PAY006", "RSV112", "$300.00", "2025-04-20", "Card", "Edit", "Delete"},
            new Object[]{"PAY007", "RSV115", "$450.00", "2025-04-21", "Cash", "Edit", "Delete"},
            new Object[]{"PAY008", "RSV118", "$220.00", "2025-04-22", "Card", "Edit", "Delete"}
        ));
        
        // Add all data to the table
        for (Object[] row : allPaymentData) {
            tableModel.addRow(row);
        }
        
        paymentsTable = new JTable(tableModel);
        paymentsTable.setRowHeight(50);
        paymentsTable.setShowGrid(true);
        paymentsTable.setGridColor(new Color(220, 220, 220));
        paymentsTable.setIntercellSpacing(new Dimension(0, 0));
        paymentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Table header styling
        JTableHeader header = paymentsTable.getTableHeader();
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        
        // Custom cell renderers
        // ID column (bold text)
        paymentsTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((JLabel) c).setFont(new Font("SansSerif", Font.BOLD, 12));
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });
        
        // Custom renderer for method column
        paymentsTable.getColumnModel().getColumn(4).setCellRenderer(new MethodCellRenderer());
        
        // Edit button renderer
        paymentsTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("\u270F\uFE0F"));
        
        // Delete button renderer
        paymentsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("\u2716"));
        
        // Set column widths
        paymentsTable.getColumnModel().getColumn(0).setPreferredWidth(100);  // ID
        paymentsTable.getColumnModel().getColumn(1).setPreferredWidth(120);  // Reservation ID
        paymentsTable.getColumnModel().getColumn(2).setPreferredWidth(100);  // Amount
        paymentsTable.getColumnModel().getColumn(3).setPreferredWidth(150);  // Date
        paymentsTable.getColumnModel().getColumn(4).setPreferredWidth(100);  // Method
        paymentsTable.getColumnModel().getColumn(5).setPreferredWidth(50);   // Edit
        paymentsTable.getColumnModel().getColumn(6).setPreferredWidth(50);   // Delete
        
        // Center all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < 5; i++) {
            if (i != 4) { // Skip Method column which has a custom renderer
                paymentsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        
        // Add mouse listener for edit and delete buttons
        paymentsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int col = paymentsTable.columnAtPoint(e.getPoint());
                int row = paymentsTable.rowAtPoint(e.getPoint());
                
                if (row >= 0 && row < paymentsTable.getRowCount()) {
                    if (col == 5) { // Edit button
                        editRow(row);
                    } else if (col == 6) { // Delete button
                        deleteRow(row);
                    }
                }
            }
        });
    }

// Add dummy data if database connection fails
private void addDummyData() {
    allPaymentData = new ArrayList<>(Arrays.asList(
        new Object[]{"PAY001", "RSV102", "$250.00", "2025-04-15", "Cash", "Edit", "Delete"},
        new Object[]{"PAY002", "RSV103", "$180.00", "2025-04-16", "Card", "Edit", "Delete"},
        new Object[]{"PAY003", "RSV105", "$320.00", "2025-04-17", "Cash", "Edit", "Delete"},
        new Object[]{"PAY004", "RSV109", "$420.00", "2025-04-18", "Card", "Edit", "Delete"}
    ));
    
    // Add all data to the table
    for (Object[] row : allPaymentData) {
        tableModel.addRow(row);
    }
}
    
    private void editRow(int row) {
        // Create a dialog to edit the row data
        JDialog editDialog = new JDialog(this, "Edit Payment", true);
        editDialog.setSize(400, 300);
        editDialog.setLocationRelativeTo(this);
        editDialog.setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        String[] labels = {"ID:", "Reservation ID:", "Amount:", "Date:", "Method:"};
        JTextField[] fields = new JTextField[5];
        
        // For Method dropdown
        String[] methodOptions = {"Cash", "Card"};
        JComboBox<String> methodField = new JComboBox<>(methodOptions);
        methodField.setSelectedItem(tableModel.getValueAt(row, 4));
        
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("SansSerif", Font.BOLD, 12));
            
            formPanel.add(label);
            
            if (i == 4) { // Method field
                formPanel.add(methodField);
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
            tableModel.setValueAt(fields[2].getText(), row, 2);
            tableModel.setValueAt(fields[3].getText(), row, 3);
            tableModel.setValueAt(methodField.getSelectedItem(), row, 4);
            
            // Update the data in our allPaymentData list
            for (int i = 0; i < 5; i++) {
                if (i == 4) {
                    allPaymentData.get(row)[i] = methodField.getSelectedItem();
                } else {
                    allPaymentData.get(row)[i] = fields[i].getText();
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
            "Are you sure you want to delete this payment record?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );
        
        if (option == JOptionPane.YES_OPTION) {
            // Remove from both tableModel and allPaymentData
            Object[] rowData = allPaymentData.get(row);
            allPaymentData.remove(row);
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
        
        Component searchComponent = null;
        if (((JPanel) paymentsHeaderPanel.getComponent(1)).getComponent(0) instanceof JPanel) {
            JPanel panel = (JPanel) ((JPanel) paymentsHeaderPanel.getComponent(1)).getComponent(0);
            if (panel.getComponentCount() > 2) {
                searchComponent = panel.getComponent(2);
            }
        }
        
        // Check if it's the method dropdown or text field
        if (searchComponent instanceof JComboBox) {
            searchText = (String) ((JComboBox<?>) searchComponent).getSelectedItem();
        } else if (searchField != null && !searchField.getText().equals("Search")) {
            searchText = searchField.getText().toLowerCase();
        }
        
        // Apply filters
        for (Object[] payment : allPaymentData) {
            boolean include = true;
            
            if (!"All".equals(filterType) && !searchText.isEmpty() && !"All".equals(searchText)) {
                int colIndex = getColumnIndexForFilter(filterType);
                
                if (colIndex >= 0) {
                    String cellValue = payment[colIndex].toString().toLowerCase();
                    
                    // For Method dropdown, exact match is required
                    if (filterType.equals("Method") && searchComponent instanceof JComboBox) {
                        include = cellValue.equals(searchText.toLowerCase());
                    } else {
                        // For other filters, check if cell contains search text
                        include = cellValue.contains(searchText);
                    }
                }
            }
            
            if (include) {
                tableModel.addRow(payment);
            }
        }
    }
    
    private int getColumnIndexForFilter(String filterType) {
        switch (filterType) {
            case "ID": return 0;
            case "Reservation ID": return 1;
            case "Amount": return 2;
            case "Date": return 3;
            case "Method": return 4;
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
    
    // Custom renderer for the method cell
    static class MethodCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 15));
            panel.setBorder(BorderFactory.createEmptyBorder());
            
            JLabel methodLabel = new JLabel(value.toString());
            methodLabel.setOpaque(true);
            methodLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            methodLabel.setForeground(Color.WHITE);
            methodLabel.setHorizontalAlignment(JLabel.CENTER);
            
            // Set different colors based on payment method
            if ("Cash".equals(value)) {
                methodLabel.setBackground(new Color(76, 175, 80)); // Green for cash
            } else if ("Card".equals(value)) {
                methodLabel.setBackground(new Color(33, 150, 243)); // Blue for card
            }
            
            // Add a small icon before the text
            JLabel iconLabel = new JLabel("Cash".equals(value) ? "cash" : "card");
            iconLabel.setForeground(Color.WHITE);
            
            panel.add(iconLabel);
            panel.add(methodLabel);
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
            new PaiementGui();
        });
    }
}