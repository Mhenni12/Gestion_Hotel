// package utilisateur;

// import gestion_base_donnees.Connect;

// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import javax.swing.table.DefaultTableModel;
// import javax.swing.table.TableCellRenderer;
// import javax.swing.table.TableRowSorter;
// import javax.swing.table.DefaultTableCellRenderer;

// import java.awt.*;
// import java.awt.event.*;
// import java.sql.Connection;
// import java.sql.ResultSet;
// import java.sql.Statement;
// import java.sql.SQLException;

// import javax.swing.table.JTableHeader;

// public class GererUtilisateurs extends JFrame{
//     private JTable clientsTable;
//     private DefaultTableModel tableModel;

//     public GererUtilisateurs() {
//         setTitle("RestHive");
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setSize(1000, 700);
//         setLocationRelativeTo(null);
        
//         // Main panels
//         JPanel mainPanel = new JPanel(new BorderLayout());
        
//         // Creating the header panel with yellow background
//         JPanel headerPanel = new JPanel(new BorderLayout());
//         headerPanel.setBackground(new Color(255, 204, 0)); // Yellow color
//         headerPanel.setPreferredSize(new Dimension(getWidth(), 70));
//         headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        
//         // Menu buttons - centered in header
//         JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 1, 11));
//         menuPanel.setBackground(new Color(255, 204, 0));
        
//         JButton clientsBtn = createMenuButton("Clients", true);
//         JButton reservationBtn = createMenuButton("Reservation", false);
//         JButton roomsBtn = createMenuButton("Rooms", false);
//         JButton paymentBtn = createMenuButton("Payment", false);
//         JButton settingsBtn = createMenuButton("Settings", false);
        
//         menuPanel.add(clientsBtn);
//         menuPanel.add(reservationBtn);
//         menuPanel.add(roomsBtn);
//         menuPanel.add(paymentBtn);
//         menuPanel.add(settingsBtn);
        
//         headerPanel.add(menuPanel, BorderLayout.CENTER);
        
//         // Content panel - white background with rounded corners
//         JPanel contentPanel = new JPanel(new BorderLayout()) {
//             @Override
//             protected void paintComponent(Graphics g) {
//                 Graphics2D g2 = (Graphics2D) g.create();
//                 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                 g2.setColor(getBackground());
//                 g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
//                 g2.dispose();
//             }
//         };
//         contentPanel.setOpaque(false);
//         contentPanel.setBackground(Color.WHITE);
//         contentPanel.setBorder(new EmptyBorder(30, 50, 30, 50));
        
//         // Clients title and search panel
//         JPanel clientsHeaderPanel = new JPanel(new BorderLayout());
//         clientsHeaderPanel.setOpaque(false);
        
//         JLabel clientsLabel = new JLabel("Clients");
//         clientsLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
//         clientsLabel.setForeground(new Color(255, 180, 0));
        
//         JPanel searchFilterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//         searchFilterPanel.setOpaque(false);
        
//         // Search field with icon
//         JPanel searchPanel = new JPanel();
//         searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
//         searchPanel.setBackground(new Color(240, 240, 240));
//         searchPanel.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createLineBorder(new Color(220, 220, 220)),
//                 BorderFactory.createEmptyBorder(5, 10, 5, 10)));
//         searchPanel.setPreferredSize(new Dimension(200, 35));
        
//         //TODO: replace icon
//         JLabel searchIcon = new JLabel("\uD83D\uDD01");
//         JTextField searchField = new JTextField("Search");
//         searchField.setBorder(null);
//         searchField.setBackground(new Color(240, 240, 240));
        
//         searchPanel.add(searchIcon);
//         searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));
//         searchPanel.add(searchField);
        
//         // Filter button
//         JButton filterBtn = new JButton("Filters");
//         filterBtn.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createLineBorder(new Color(220, 220, 220)),
//                 BorderFactory.createEmptyBorder(5, 10, 5, 10)));
//         filterBtn.setBackground(Color.WHITE);
//         filterBtn.setPreferredSize(new Dimension(100, 35));
//         filterBtn.setFocusable(false);
        
//         searchFilterPanel.add(searchPanel);
//         searchFilterPanel.add(Box.createRigidArea(new Dimension(10, 0)));
//         searchFilterPanel.add(filterBtn);
        
//         clientsHeaderPanel.add(clientsLabel, BorderLayout.WEST);
//         clientsHeaderPanel.add(searchFilterPanel, BorderLayout.EAST);

//         // Table setup with same design
//         String[] columnNames = {"Id", "Name", "Address", "Number", "Email", "", ""};
//         tableModel = new DefaultTableModel(columnNames, 0) {
//             @Override
//             public boolean isCellEditable(int row, int column) {
//                 return column == 5 || column == 6; // Only edit and delete buttons columns are editable
//             }
//         };

//         // Retrieve data from database
//         try (Connection connection = new Connect().getConnection();
//         Statement clientStatement = connection.createStatement();
//         Statement emailStatement = connection.createStatement();
//         ResultSet rs = clientStatement.executeQuery("SELECT * FROM client ORDER BY id_client");
//         ResultSet emailResult = emailStatement.executeQuery("SELECT email_utilisateur FROM utilisateur u JOIN client c ON c.id_client = u.id_client ORDER BY u.id_client")) {

//             while (rs.next() && emailResult.next()) {
//             tableModel.addRow(new Object[] {
//                 rs.getInt("id_client"),
//                 rs.getString("nom_client") + " " + rs.getString("prenom_client"),
//                 rs.getString("adresse_client"),
//                 rs.getString("tel_client"),
//                 emailResult.getString("email_utilisateur"),
//                 // TODO: change icons
//                 "\u270F\uFE0F", // Edit icon
//                 "\u2716"       // Delete icon
//             });
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//             JOptionPane.showMessageDialog(this, "Error loading client data", "Database Error", JOptionPane.ERROR_MESSAGE);
//         }

//         // Apply the same styling as before
//         clientsTable = new JTable(tableModel);
//         clientsTable.setRowHeight(50);
//         clientsTable.setShowGrid(true);
//         clientsTable.setGridColor(new Color(220, 220, 220));
//         clientsTable.setIntercellSpacing(new Dimension(0, 0));
//         clientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

//         // Table header styling (same as before)
//         JTableHeader header = clientsTable.getTableHeader();
//         header.setReorderingAllowed(false);
//         header.setResizingAllowed(false);
//         header.setFont(new Font("SansSerif", Font.BOLD, 14));
//         header.setBackground(Color.WHITE);
//         header.setForeground(Color.BLACK);
//         header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
//         header.setPreferredSize(new Dimension(header.getWidth(), 40));

//         // Custom cell renderer for buttons (make sure ButtonRenderer class exists)
//         //TODO: update icons
//         clientsTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("\u270F\uFE0F")); // Edit icon
//         clientsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("\u2716"));       // Delete icon

//         // Set column widths (same as before)
//         clientsTable.getColumnModel().getColumn(0).setPreferredWidth(50);   // Id
//         clientsTable.getColumnModel().getColumn(1).setPreferredWidth(150);  // Name
//         clientsTable.getColumnModel().getColumn(2).setPreferredWidth(200);  // Address
//         clientsTable.getColumnModel().getColumn(3).setPreferredWidth(100);  // Number
//         clientsTable.getColumnModel().getColumn(4).setPreferredWidth(200);  // Email
//         clientsTable.getColumnModel().getColumn(5).setPreferredWidth(50);   // Edit
//         clientsTable.getColumnModel().getColumn(6).setPreferredWidth(50);   // Delete

//         // Add cell click listeners for button columns
//         clientsTable.addMouseListener(new MouseAdapter() {
//             public void mouseClicked(MouseEvent e) {
//                 int col = clientsTable.columnAtPoint(e.getPoint());
//                 int row = clientsTable.rowAtPoint(e.getPoint());
                
//                 if (col == 5) { // Edit button
//                     //TODO: add functionality
//                     JOptionPane.showMessageDialog(GererUtilisateurs.this, 
//                             "Edit client: " + tableModel.getValueAt(row, 1));
//                 } else if (col == 6) { // Delete button
//                     //TODO: add functionality
//                     JOptionPane.showMessageDialog(GererUtilisateurs.this, 
//                             "Delete client: " + tableModel.getValueAt(row, 1));
//                 }
//             }
//         });

//         // Custom cell renderers
//         // ID column (bold text)
//         clientsTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
//             @Override
//             public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
//                     boolean hasFocus, int row, int column) {
//                 Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                 ((JLabel) c).setFont(new Font("SansSerif", Font.BOLD, 12));
//                 ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
//                 return c;
//             }
//         });
        
//         // Scroll pane for table
//         JScrollPane scrollPane = new JScrollPane(clientsTable);
//         scrollPane.setBorder(BorderFactory.createEmptyBorder());
//         scrollPane.getViewport().setBackground(Color.WHITE);

//         // Add components to content panel
//         contentPanel.add(clientsHeaderPanel, BorderLayout.NORTH);
//         contentPanel.add(scrollPane, BorderLayout.CENTER);

//         // Add background and content
//         JPanel backgroundPanel = new JPanel(new BorderLayout());
//         backgroundPanel.setBackground(new Color(240, 240, 240)); // Light gray background

//         // Add padding around the content panel
//         JPanel paddingPanel = new JPanel(new BorderLayout());
//         paddingPanel.setOpaque(false);
//         paddingPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
//         paddingPanel.add(contentPanel, BorderLayout.CENTER);

//         backgroundPanel.add(paddingPanel, BorderLayout.CENTER);

//         mainPanel.add(headerPanel, BorderLayout.NORTH);
//         mainPanel.add(backgroundPanel, BorderLayout.CENTER);

//         setContentPane(mainPanel);
//     }

//     private JButton createMenuButton(String text, boolean selected) {
//         JButton button = new JButton(text);
//         button.setFont(new Font("SansSerif", Font.BOLD, 16));
//         button.setBorderPainted(false);
//         button.setFocusPainted(false);
//         button.setContentAreaFilled(false);
//         button.setForeground(selected ? Color.BLACK : Color.DARK_GRAY);
//         button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
//         if (selected) {
//             button.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
//         }
        
//         return button;
//     }

//     // Custom renderer for button cells
//     class ButtonRenderer extends JButton implements TableCellRenderer {
//         public ButtonRenderer(String text) {
//             setText(text);
//             setOpaque(true);
//             setBorderPainted(false);
//             setContentAreaFilled(false);
//             setHorizontalAlignment(JLabel.CENTER);
//             setFont(new Font("SansSerif", Font.PLAIN, 16));
//         }
        
//         public Component getTableCellRendererComponent(JTable table, Object value,
//                 boolean isSelected, boolean hasFocus, int row, int column) {
//             return this;
//         }
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> {
//             try {
//                 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//             new GererUtilisateurs().setVisible(true);
//         });
//     }
// }





















package utilisateur;

import gestion_base_donnees.Connect;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import admin_dashboard.AdminSettings;
import chambre.ChambreGui;
import espace_paiement.PaiementGui;
import espace_reservation.ReservationGui;

import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;


import javax.swing.table.JTableHeader;

public class GererUtilisateurs extends JFrame {
    private JTable clientsTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField searchField;
    private JComboBox<String> filterComboBox;
    private Connection connection;

    public GererUtilisateurs() {
        setTitle("RestHive");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Establish database connection
        try {
            connection = new Connect().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Main panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Creating the header panel with yellow background
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 204, 0)); // Yellow color
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        // Add logo to the left corner
        ImageIcon logoIcon = new ImageIcon("gestion_hotel/src/images/hotelLogo.png");
        // If logo file doesn't exist, create a text-based logo
        JLabel logoLabel;
        if (logoIcon.getIconWidth() == -1) {
            logoLabel = new JLabel("RestHive");
            logoLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        } else {
            // Resize logo if needed
            Image image = logoIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
            logoLabel = new JLabel(new ImageIcon(image));
        }
        headerPanel.add(logoLabel, BorderLayout.WEST);
        
        // Menu buttons - centered in header
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 11));
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
        
        headerPanel.add(menuPanel, BorderLayout.CENTER);
        
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
        
        // Clients title and search panel
        JPanel clientsHeaderPanel = new JPanel(new BorderLayout());
        clientsHeaderPanel.setOpaque(false);
        
        JLabel clientsLabel = new JLabel("Clients");
        clientsLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        clientsLabel.setForeground(new Color(255, 180, 0));
        
        JPanel searchFilterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchFilterPanel.setOpaque(false);
        
        // Filter dropdown
        String[] filterOptions = {"ID", "Name", "Address", "Number", "Email"};
        filterComboBox = new JComboBox<>(filterOptions);
        filterComboBox.setPreferredSize(new Dimension(100, 35));
        filterComboBox.setBackground(Color.WHITE);
        
        // Search field with icon
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(new Color(240, 240, 240));
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        searchPanel.setPreferredSize(new Dimension(200, 35));
        
        JLabel searchIcon = new JLabel("🔍");
        searchField = new JTextField(15);
        searchField.setBorder(null);
        searchField.setBackground(new Color(240, 240, 240));
        searchField.setText("Search");
        
        // Clear placeholder text on focus
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
        
        // Add search functionality
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchField.getText();
                if (searchText.equals("Search")) {
                    searchText = "";
                }
                filterTable(searchText);
            }
        });
        
        searchPanel.add(searchIcon);
        searchPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        searchPanel.add(searchField);
        
        searchFilterPanel.add(filterComboBox);
        searchFilterPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        searchFilterPanel.add(searchPanel);
        
        clientsHeaderPanel.add(clientsLabel, BorderLayout.WEST);
        clientsHeaderPanel.add(searchFilterPanel, BorderLayout.EAST);

        // Table setup
        String[] columnNames = {"Id", "Name", "Address", "Number", "Email", "", ""};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 6; // Only edit and delete buttons columns are editable
            }
        };

        // Load table data
        loadTableData();

        // Apply styling
        clientsTable = new JTable(tableModel);
        clientsTable.setRowHeight(50);
        clientsTable.setShowGrid(true);
        clientsTable.setGridColor(new Color(220, 220, 220));
        clientsTable.setIntercellSpacing(new Dimension(0, 0));
        clientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Create and set the sorter
        sorter = new TableRowSorter<>(tableModel);
        clientsTable.setRowSorter(sorter);

        // Table header styling
        JTableHeader header = clientsTable.getTableHeader();
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        // Custom cell renderer for buttons
        clientsTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("✏️")); // Edit icon
        clientsTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("❌"));  // Delete icon

        // Set column widths
        clientsTable.getColumnModel().getColumn(0).setPreferredWidth(50);   // Id
        clientsTable.getColumnModel().getColumn(1).setPreferredWidth(150);  // Name
        clientsTable.getColumnModel().getColumn(2).setPreferredWidth(200);  // Address
        clientsTable.getColumnModel().getColumn(3).setPreferredWidth(100);  // Number
        clientsTable.getColumnModel().getColumn(4).setPreferredWidth(200);  // Email
        clientsTable.getColumnModel().getColumn(5).setPreferredWidth(50);   // Edit
        clientsTable.getColumnModel().getColumn(6).setPreferredWidth(50);   // Delete

        // Add cell click listeners for button columns
        clientsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int col = clientsTable.columnAtPoint(e.getPoint());
                int row = clientsTable.rowAtPoint(e.getPoint());
                
                if (row >= 0) {
                    // Convert view row index to model row index
                    int modelRow = clientsTable.convertRowIndexToModel(row);
                    
                    if (col == 5) { // Edit button
                        editClient(modelRow);
                    } else if (col == 6) { // Delete button
                        deleteClient(modelRow);
                    }
                }
            }
        });

        // Custom cell renderers
        // ID column (bold text)
        clientsTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((JLabel) c).setFont(new Font("SansSerif", Font.BOLD, 12));
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });
        
        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(clientsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Add components to content panel
        contentPanel.add(clientsHeaderPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Add background and content
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.setBackground(new Color(240, 240, 240)); // Light gray background

        // Add padding around the content panel
        JPanel paddingPanel = new JPanel(new BorderLayout());
        paddingPanel.setOpaque(false);
        paddingPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        paddingPanel.add(contentPanel, BorderLayout.CENTER);

        backgroundPanel.add(paddingPanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(backgroundPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        
        // Add window listener to close database connection
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void loadTableData() {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Retrieve data from database
        try {
            Statement clientStatement = connection.createStatement();
            Statement emailStatement = connection.createStatement();
            ResultSet rs = clientStatement.executeQuery("SELECT * FROM client ORDER BY id_client");
            ResultSet emailResult = emailStatement.executeQuery("SELECT email_utilisateur FROM utilisateur u JOIN client c ON c.id_client = u.id_client ORDER BY u.id_client");

            while (rs.next() && emailResult.next()) {
                tableModel.addRow(new Object[] {
                    rs.getInt("id_client"),
                    rs.getString("nom_client") + " " + rs.getString("prenom_client"),
                    rs.getString("adresse_client"),
                    rs.getString("tel_client"),
                    emailResult.getString("email_utilisateur"),
                    "✏️", // Edit icon
                    "❌"  // Delete icon
                });
            }
            
            rs.close();
            emailResult.close();
            clientStatement.close();
            emailStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading client data", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterTable(String searchText) {
        if (searchText.trim().isEmpty() || searchText.equals("Search")) {
            sorter.setRowFilter(null);
            return;
        }
        
        // Get selected filter option
        String filterOption = (String) filterComboBox.getSelectedItem();
        int columnIndex;
        
        switch (filterOption) {
            case "ID":
                columnIndex = 0;
                break;
            case "Name":
                columnIndex = 1;
                break;
            case "Address":
                columnIndex = 2;
                break;
            case "Number":
                columnIndex = 3;
                break;
            case "Email":
                columnIndex = 4;
                break;
            default:
                columnIndex = 1; // Default to name
        }
        
        // Create a case-insensitive filter
        RowFilter<DefaultTableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText, columnIndex);
        sorter.setRowFilter(rf);
    }

    private void editClient(int row) {
        // Get client data
        int clientId = (int) tableModel.getValueAt(row, 0);
        String fullName = (String) tableModel.getValueAt(row, 1);
        String address = (String) tableModel.getValueAt(row, 2);
        String phone = (String) tableModel.getValueAt(row, 3);
        String email = (String) tableModel.getValueAt(row, 4);
        
        // Split the full name
        String[] nameParts = fullName.split(" ", 2);
        String lastName = nameParts[0];
        String firstName = nameParts.length > 1 ? nameParts[1] : "";
        
        // Create a panel for the input fields
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create input fields
        JTextField lastNameField = new JTextField(lastName);
        JTextField firstNameField = new JTextField(firstName);
        JTextField addressField = new JTextField(address);
        JTextField phoneField = new JTextField(phone);
        JTextField emailField = new JTextField(email);
        
        // Add components to panel
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        
        // Show dialog
        int result = JOptionPane.showConfirmDialog(
            this, panel, "Edit Client", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Update client information in database
                String updateClientSQL = "UPDATE client SET nom_client=?, prenom_client=?, adresse_client=?, tel_client=? WHERE id_client=?";
                PreparedStatement clientStmt = connection.prepareStatement(updateClientSQL);
                clientStmt.setString(1, lastNameField.getText());
                clientStmt.setString(2, firstNameField.getText());
                clientStmt.setString(3, addressField.getText());
                clientStmt.setString(4, phoneField.getText());
                clientStmt.setInt(5, clientId);
                clientStmt.executeUpdate();
                
                // Update email in database
                String updateEmailSQL = "UPDATE utilisateur SET email_utilisateur=? WHERE id_client=?";
                PreparedStatement emailStmt = connection.prepareStatement(updateEmailSQL);
                emailStmt.setString(1, emailField.getText());
                emailStmt.setInt(2, clientId);
                emailStmt.executeUpdate();
                
                // Update table model
                tableModel.setValueAt(lastNameField.getText() + " " + firstNameField.getText(), row, 1);
                tableModel.setValueAt(addressField.getText(), row, 2);
                tableModel.setValueAt(phoneField.getText(), row, 3);
                tableModel.setValueAt(emailField.getText(), row, 4);
                
                JOptionPane.showMessageDialog(this, "Client updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                clientStmt.close();
                emailStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating client: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

private void deleteClient(int row) {
    int clientId = (int) tableModel.getValueAt(row, 0);
    String clientName = (String) tableModel.getValueAt(row, 1);
    
    // Confirm deletion
    int confirm = JOptionPane.showConfirmDialog(
        this, 
        "Are you sure you want to delete client: " + clientName + "?", 
        "Confirm Deletion", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.WARNING_MESSAGE
    );
    
    if (confirm == JOptionPane.YES_OPTION) {
        Connection conn = null;
        PreparedStatement deletePaymentsStmt = null;
        PreparedStatement deleteReservationsStmt = null;
        PreparedStatement deleteUserStmt = null;
        PreparedStatement deleteClientStmt = null;
        
        try {
            conn = new Connect().getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            // 1. First delete payments for this client's reservations
            String deletePaymentsSql = "DELETE p FROM paiement p " +
                                   "JOIN reservation r ON p.id_reservation = r.id_reservation " +
                                   "WHERE r.id_client = ?";
            deletePaymentsStmt = conn.prepareStatement(deletePaymentsSql);
            deletePaymentsStmt.setInt(1, clientId);
            deletePaymentsStmt.executeUpdate();
            
            // 2. Delete reservations for this client
            String deleteReservationsSql = "DELETE FROM reservation WHERE id_client = ?";
            deleteReservationsStmt = conn.prepareStatement(deleteReservationsSql);
            deleteReservationsStmt.setInt(1, clientId);
            deleteReservationsStmt.executeUpdate();
            
            // 3. Delete the user account associated with this client
            String deleteUserSql = "DELETE FROM utilisateur WHERE id_client = ?";
            deleteUserStmt = conn.prepareStatement(deleteUserSql);
            deleteUserStmt.setInt(1, clientId);
            deleteUserStmt.executeUpdate();
            
            // 4. Finally delete the client record
            String deleteClientSql = "DELETE FROM client WHERE id_client = ?";
            deleteClientStmt = conn.prepareStatement(deleteClientSql);
            deleteClientStmt.setInt(1, clientId);
            int rowsAffected = deleteClientStmt.executeUpdate();
            
            if (rowsAffected > 0) {
                conn.commit(); // Commit transaction if all deletions succeeded
                tableModel.removeRow(row); // Remove from table model
                JOptionPane.showMessageDialog(
                    this, 
                    "Client deleted successfully", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                conn.rollback(); // Rollback if client deletion failed
                JOptionPane.showMessageDialog(
                    this, 
                    "Failed to delete client", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                this, 
                "Error deleting client: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE
            );
        } finally {
            // Close all resources
            try {
                if (deletePaymentsStmt != null) deletePaymentsStmt.close();
                if (deleteReservationsStmt != null) deleteReservationsStmt.close();
                if (deleteUserStmt != null) deleteUserStmt.close();
                if (deleteClientStmt != null) deleteClientStmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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

    // Custom renderer for button cells
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text) {
            setText(text);
            setOpaque(true);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setHorizontalAlignment(JLabel.CENTER);
            setFont(new Font("SansSerif", Font.PLAIN, 16));
        }
        
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
            new GererUtilisateurs().setVisible(true);
        });
    }
}