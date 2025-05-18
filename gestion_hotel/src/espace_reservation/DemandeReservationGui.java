package espace_reservation;

import javax.swing.*;

import javax.swing.table.*;

import client_dashboard.ClientSettings;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;


import gestion_base_donnees.Connect;

public class DemandeReservationGui extends JFrame {
    // Colors
    private final Color YELLOW_BG = new Color(255, 204, 51);
    private final Color DARK_TEXT = new Color(51, 51, 51);
    private final Color PANEL_BG = new Color(255, 255, 255);
    private final Color SELECTED_DATE = new Color(51, 51, 51);
    private final Color HEADER_BG = new Color(255, 204, 51);
    private final Color DISABLED_DATE = new Color(200, 200, 200);
    
    // Components
    private JPanel mainPanel, guestsPanel, checkInPanel, checkOutPanel;
    private JLabel guestsInfoLabel, checkInInfoLabel, checkOutInfoLabel;
    private JButton guestsDoneBtn, searchBtn;
    private JSpinner adultsSpinner, childrenSpinner;
    private JTable calendarTable;
    private DefaultTableModel calendarModel;
    private JLabel monthYearLabel;
    
    // Calendar panels
    private JPanel checkInCalendarPanel, checkOutCalendarPanel;
    
    // State variables
    private int currentMonth, currentYear;
    private Date selectedCheckInDate, selectedCheckOutDate;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
    private boolean isCheckInCalendarActive = false;
    private boolean isCheckOutCalendarActive = false;
    private int selectedAdults = 1;
    private int selectedChildren = 0;
    
    public DemandeReservationGui() {
        setTitle("RestHive");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 550);
        setResizable(false);
        
        // Initialize with current date
        Calendar cal = Calendar.getInstance();
        currentMonth = cal.get(Calendar.MONTH);
        currentYear = cal.get(Calendar.YEAR);
        
        // Initialize default dates
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        selectedCheckInDate = tomorrow.getTime();
        
        Calendar dayAfterTomorrow = Calendar.getInstance();
        dayAfterTomorrow.add(Calendar.DAY_OF_MONTH, 2);
        selectedCheckOutDate = dayAfterTomorrow.getTime();
        
        createComponents();
        layoutComponents();
        
        setVisible(true);
    }
    
    private void createComponents() {
        // Main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(YELLOW_BG);
        
        // Create header panel
        createHeaderPanel();
        
        // Create content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(YELLOW_BG);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create panels
        createGuestsPanel();
        createCheckInPanel();
        createCheckOutPanel();
        
        // Add panels to content
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(guestsPanel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(checkInPanel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(checkOutPanel);
        
        // Add search button
        searchBtn = new JButton("SEARCH AVAILABILITY");
        searchBtn.setBackground(DARK_TEXT);
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFont(new Font("Arial", Font.BOLD, 14));
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setPreferredSize(new Dimension(0, 45));
        searchBtn.addActionListener(e -> performSearch());
        
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(YELLOW_BG);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        buttonPanel.add(searchBtn, BorderLayout.CENTER);
        
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(buttonPanel);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }
    
    private void performSearch() {
        if (selectedCheckInDate == null || selectedCheckOutDate == null) {
            JOptionPane.showMessageDialog(this, "Please select both check-in and check-out dates", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (selectedCheckOutDate.before(selectedCheckInDate) || selectedCheckOutDate.equals(selectedCheckInDate)) {
            JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Calculate nights
        long diff = selectedCheckOutDate.getTime() - selectedCheckInDate.getTime();
        int nights = (int) (diff / (1000 * 60 * 60 * 24));

        // Calculate total guests
        int totalGuests = selectedAdults + selectedChildren;

        // Call the database function to search and reserve a room
        boolean reservationSuccess = searchAndReserveRoom(totalGuests, selectedCheckInDate, selectedCheckOutDate, nights);
        if (reservationSuccess) {
            String message = String.format(
                "Reservation Details:\n\n" +
                "Guests: %d adults, %d children\n" +
                "Check-in: %s\n" +
                "Check-out: %s\n" +
                "Nights: %d\n\n",
                selectedAdults, selectedChildren,
                dateFormat.format(selectedCheckInDate),
                dateFormat.format(selectedCheckOutDate),
                nights
            );
            
            JOptionPane.showMessageDialog(this, message, "Reservation Confirmed", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // New method to handle database operations
private boolean searchAndReserveRoom(int totalGuests, Date checkInDate, Date checkOutDate, int nights) {
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        // Get database connection
        connection = new Connect().getConnection();
        
        // First, find an available room that meets the requirements
        String findRoomSQL = "SELECT num_chambre, prix_par_jour FROM chambre " +
                           "WHERE capacite >= ? AND etat_chambre = 'libre' " +
                           "AND num_chambre NOT IN (" +
                           "    SELECT num_chambre FROM reservation " +
                           "    WHERE (check_in_date <= ? AND check_out_date >= ?) " +
                           "    OR (check_in_date <= ? AND check_out_date >= ?) " +
                           "    OR (check_in_date >= ? AND check_out_date <= ?)" +
                           ") ORDER BY capacite ASC, prix_par_jour ASC";
        
        stmt = connection.prepareStatement(findRoomSQL);
        stmt.setInt(1, totalGuests);
        
        // Convert dates to SQL dates
        java.sql.Date sqlCheckIn = new java.sql.Date(checkInDate.getTime());
        java.sql.Date sqlCheckOut = new java.sql.Date(checkOutDate.getTime());
        
        stmt.setDate(2, sqlCheckOut);
        stmt.setDate(3, sqlCheckIn);
        stmt.setDate(4, sqlCheckIn);
        stmt.setDate(5, sqlCheckOut);
        stmt.setDate(6, sqlCheckIn);
        stmt.setDate(7, sqlCheckOut);
        
        rs = stmt.executeQuery();
        
        if (rs.next()) {
            // Found an available room
            int roomNumber = rs.getInt("num_chambre");
            double pricePerNight = rs.getDouble("prix_par_jour");
            double totalPrice = pricePerNight * nights;
            
            // For this example, we'll use a hardcoded client ID
            // In a real application, you would get this from the logged-in user
            int clientId = 1; // Default client ID
            
            // Create the reservation
            String insertReservationSQL = "INSERT INTO reservation (id_client, num_chambre, date_reservation, check_in_date, check_out_date) " +
                                       "VALUES (?, ?, CURRENT_DATE, ?, ?)";
            
            stmt = connection.prepareStatement(insertReservationSQL, new String[] {"id_reservation"});
            stmt.setInt(1, clientId);
            stmt.setInt(2, roomNumber);
            stmt.setDate(3, sqlCheckIn);
            stmt.setDate(4, sqlCheckOut);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                JOptionPane.showMessageDialog(this, "Failed to create reservation", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Get the generated reservation ID
            int reservationId = -1;
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                reservationId = rs.getInt(1);
            }
            
            // Create payment record
            String insertPaymentSQL = "INSERT INTO paiement (id_reservation, montant, date_paiement, methode_paiement) " +
                                    "VALUES (?, ?, CURRENT_DATE, 'card')";
            
            stmt = connection.prepareStatement(insertPaymentSQL);
            stmt.setInt(1, reservationId);
            stmt.setDouble(2, totalPrice);
            stmt.executeUpdate();
            
            // Update room status
            String updateRoomSQL = "UPDATE chambre SET etat_chambre = 'reservee' WHERE num_chambre = ?";
            stmt = connection.prepareStatement(updateRoomSQL);
            stmt.setInt(1, roomNumber);
            stmt.executeUpdate();
            
            // Commit the transaction
            // connection.commit();
            
            return true;
        } else {
            // No available rooms found
            JOptionPane.showMessageDialog(this, 
                "No available rooms found for the selected dates and number of guests.\n" +
                "Please try different dates or adjust your guest count.", 
                "No Availability", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
    } catch (SQLException e) {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "An error occurred while processing your reservation: " + e.getMessage(), 
            "Database Error", 
            JOptionPane.ERROR_MESSAGE);
        return false;
    } finally {
        // Close resources
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
private void createHeaderPanel() {
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new BorderLayout());
    headerPanel.setBackground(HEADER_BG);
    headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    
    // Logo
    JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    logoPanel.setBackground(HEADER_BG);
    
    JLabel logoIconLabel = new JLabel(createTextIcon("🐝", 20));
    logoIconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
    
    JLabel logoTextLabel = new JLabel("RestHive");
    logoTextLabel.setFont(new Font("Arial", Font.BOLD, 18));
    logoTextLabel.setForeground(DARK_TEXT);
    
    logoPanel.add(logoIconLabel);
    logoPanel.add(logoTextLabel);
    
    // Settings icon
    JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    settingsPanel.setBackground(HEADER_BG);
    
    JLabel settingsIconLabel = new JLabel(createTextIcon("⚙️", 20));
    settingsPanel.add(settingsIconLabel);

    settingsIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    settingsIconLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            // Close current window
                            Window currentWindow = SwingUtilities.getWindowAncestor(settingsIconLabel);
                            currentWindow.dispose();
                            
                            // Open Reservation window
                            SwingUtilities.invokeLater(() -> {
                                new ClientSettings().setVisible(true);
                            });
                        }
                        
                        // Hover effects
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            settingsIconLabel.setForeground(Color.BLUE);
                        }
                        
                        @Override
                        public void mouseExited(MouseEvent e) {
                            settingsIconLabel.setForeground(Color.BLACK);
                        }
                    });
    
    // Add Reservation Settings title in the center
    JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    centerPanel.setBackground(HEADER_BG);
    
    headerPanel.add(logoPanel, BorderLayout.WEST);
    headerPanel.add(centerPanel, BorderLayout.CENTER);
    headerPanel.add(settingsPanel, BorderLayout.EAST);

    
    
    mainPanel.add(headerPanel, BorderLayout.NORTH);
}
    
    private void createGuestsPanel() {
        guestsPanel = new JPanel();
        guestsPanel.setLayout(new BorderLayout());
        guestsPanel.setBackground(PANEL_BG);
        guestsPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        
        // Header
        JPanel guestsHeaderPanel = new JPanel();
        guestsHeaderPanel.setLayout(new BorderLayout());
        guestsHeaderPanel.setBackground(PANEL_BG);
        guestsHeaderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        
        JPanel guestsIconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        guestsIconPanel.setBackground(PANEL_BG);
        guestsIconPanel.add(new JLabel(createTextIcon("👥", 16)));
        
        JLabel guestsLabel = new JLabel("Guests");
        guestsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        guestsIconPanel.add(guestsLabel);
        
        guestsInfoLabel = new JLabel("1 adult, 0 children");
        guestsInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JPanel guestsInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        guestsInfoPanel.setBackground(PANEL_BG);
        guestsInfoPanel.add(guestsInfoLabel);
        
        guestsHeaderPanel.add(guestsIconPanel, BorderLayout.WEST);
        guestsHeaderPanel.add(guestsInfoPanel, BorderLayout.EAST);
        
        // Guest selection content
        JPanel guestsContentPanel = new JPanel();
        guestsContentPanel.setLayout(new BorderLayout());
        guestsContentPanel.setBackground(PANEL_BG);
        guestsContentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Adults section
        JPanel adultsPanel = new JPanel();
        adultsPanel.setLayout(new BorderLayout());
        adultsPanel.setBackground(PANEL_BG);
        
        JLabel adultsLabel = new JLabel("Adults");
        adultsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        SpinnerNumberModel adultsModel = new SpinnerNumberModel(1, 1, 10, 1);
        adultsSpinner = new JSpinner(adultsModel);
        JComponent adultsEditor = adultsSpinner.getEditor();
        JSpinner.DefaultEditor adultsFieldEditor = (JSpinner.DefaultEditor) adultsEditor;
        adultsFieldEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        
        adultsPanel.add(adultsLabel, BorderLayout.WEST);
        adultsPanel.add(adultsSpinner, BorderLayout.EAST);
        
        // Children section
        JPanel childrenPanel = new JPanel();
        childrenPanel.setLayout(new BorderLayout());
        childrenPanel.setBackground(PANEL_BG);
        childrenPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel childrenLabel = new JLabel("Children");
        childrenLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        SpinnerNumberModel childrenModel = new SpinnerNumberModel(0, 0, 10, 1);
        childrenSpinner = new JSpinner(childrenModel);
        JComponent childrenEditor = childrenSpinner.getEditor();
        JSpinner.DefaultEditor childrenFieldEditor = (JSpinner.DefaultEditor) childrenEditor;
        childrenFieldEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        
        childrenPanel.add(childrenLabel, BorderLayout.WEST);
        childrenPanel.add(childrenSpinner, BorderLayout.EAST);
        
        // Done button
        guestsDoneBtn = new JButton("DONE");
        guestsDoneBtn.setBackground(YELLOW_BG);
        guestsDoneBtn.setForeground(DARK_TEXT);
        guestsDoneBtn.setFont(new Font("Arial", Font.BOLD, 12));
        guestsDoneBtn.setFocusPainted(false);
        guestsDoneBtn.setBorderPainted(false);
        
        JPanel guestsDonePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        guestsDonePanel.setBackground(PANEL_BG);
        guestsDonePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        guestsDonePanel.add(guestsDoneBtn);
        
        // Add to content panel
        JPanel guestsSelectionPanel = new JPanel();
        guestsSelectionPanel.setLayout(new BoxLayout(guestsSelectionPanel, BoxLayout.Y_AXIS));
        guestsSelectionPanel.setBackground(PANEL_BG);
        guestsSelectionPanel.add(adultsPanel);
        guestsSelectionPanel.add(childrenPanel);
        
        guestsContentPanel.add(guestsSelectionPanel, BorderLayout.NORTH);
        guestsContentPanel.add(guestsDonePanel, BorderLayout.SOUTH);
        
        // Initially hidden
        guestsContentPanel.setVisible(false);
        
        // Add to guests panel
        guestsPanel.add(guestsHeaderPanel, BorderLayout.NORTH);
        guestsPanel.add(guestsContentPanel, BorderLayout.CENTER);
        
        // Add toggle functionality
        guestsHeaderPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean isVisible = guestsContentPanel.isVisible();
                guestsContentPanel.setVisible(!isVisible);
                checkInCalendarPanel.setVisible(false);
                checkOutCalendarPanel.setVisible(false);
                guestsPanel.revalidate();
                guestsPanel.repaint();
            }
        });
        
        // Add done button functionality
        guestsDoneBtn.addActionListener(e -> {
            selectedAdults = (int) adultsSpinner.getValue();
            selectedChildren = (int) childrenSpinner.getValue();
            guestsInfoLabel.setText(selectedAdults + " adult" + (selectedAdults > 1 ? "s" : "") + 
                                  ", " + selectedChildren + " children");
            guestsContentPanel.setVisible(false);
            guestsPanel.revalidate();
            guestsPanel.repaint();
        });
    }
    
    private void createCheckInPanel() {
        checkInPanel = new JPanel();
        checkInPanel.setLayout(new BorderLayout());
        checkInPanel.setBackground(PANEL_BG);
        checkInPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        
        // Header
        JPanel checkInHeaderPanel = new JPanel();
        checkInHeaderPanel.setLayout(new BorderLayout());
        checkInHeaderPanel.setBackground(PANEL_BG);
        checkInHeaderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        
        JPanel checkInIconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkInIconPanel.setBackground(PANEL_BG);
        checkInIconPanel.add(new JLabel(createTextIcon("📅", 16)));
        
        JLabel checkInLabel = new JLabel("Check-in");
        checkInLabel.setFont(new Font("Arial", Font.BOLD, 14));
        checkInIconPanel.add(checkInLabel);
        
        checkInInfoLabel = new JLabel(dateFormat.format(selectedCheckInDate));
        checkInInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JPanel checkInInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        checkInInfoPanel.setBackground(PANEL_BG);
        checkInInfoPanel.add(checkInInfoLabel);
        
        checkInHeaderPanel.add(checkInIconPanel, BorderLayout.WEST);
        checkInHeaderPanel.add(checkInInfoPanel, BorderLayout.EAST);
        
        // Calendar content
        checkInCalendarPanel = createCalendarPanel(true);
        checkInCalendarPanel.setVisible(false);
        
        // Add to check-in panel
        checkInPanel.add(checkInHeaderPanel, BorderLayout.NORTH);
        checkInPanel.add(checkInCalendarPanel, BorderLayout.CENTER);
        
        // Add toggle functionality
        checkInHeaderPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Hide other panels
                ((JPanel)guestsPanel.getComponent(1)).setVisible(false);
                checkOutCalendarPanel.setVisible(false);
                
                // Toggle visibility
                boolean wasVisible = checkInCalendarPanel.isVisible();
                checkInCalendarPanel.setVisible(!wasVisible);
                
                if (!wasVisible) {
                    isCheckInCalendarActive = true;
                    isCheckOutCalendarActive = false;
                    // Reset to current month/year when showing
                    Calendar cal = Calendar.getInstance();
                    currentMonth = cal.get(Calendar.MONTH);
                    currentYear = cal.get(Calendar.YEAR);
                    updateCalendar();
                }
                
                checkInPanel.revalidate();
                checkInPanel.repaint();
            }
        });
    }
    
    private void createCheckOutPanel() {
        checkOutPanel = new JPanel();
        checkOutPanel.setLayout(new BorderLayout());
        checkOutPanel.setBackground(PANEL_BG);
        checkOutPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        
        // Header
        JPanel checkOutHeaderPanel = new JPanel();
        checkOutHeaderPanel.setLayout(new BorderLayout());
        checkOutHeaderPanel.setBackground(PANEL_BG);
        checkOutHeaderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        
        JPanel checkOutIconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkOutIconPanel.setBackground(PANEL_BG);
        checkOutIconPanel.add(new JLabel(createTextIcon("📅", 16)));
        
        JLabel checkOutLabel = new JLabel("Check-out");
        checkOutLabel.setFont(new Font("Arial", Font.BOLD, 14));
        checkOutIconPanel.add(checkOutLabel);
        
        checkOutInfoLabel = new JLabel(dateFormat.format(selectedCheckOutDate));
        checkOutInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JPanel checkOutInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        checkOutInfoPanel.setBackground(PANEL_BG);
        checkOutInfoPanel.add(checkOutInfoLabel);
        
        checkOutHeaderPanel.add(checkOutIconPanel, BorderLayout.WEST);
        checkOutHeaderPanel.add(checkOutInfoPanel, BorderLayout.EAST);
        
        // Calendar content
        checkOutCalendarPanel = createCalendarPanel(false);
        checkOutCalendarPanel.setVisible(false);
        
        // Add to check-out panel
        checkOutPanel.add(checkOutHeaderPanel, BorderLayout.NORTH);
        checkOutPanel.add(checkOutCalendarPanel, BorderLayout.CENTER);
        
        // Add toggle functionality
        checkOutHeaderPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean isVisible = checkOutCalendarPanel.isVisible();
                guestsPanel.getComponent(1).setVisible(false);
                checkInCalendarPanel.setVisible(false);
                checkOutCalendarPanel.setVisible(!isVisible);
                
                if (!isVisible) {
                    isCheckInCalendarActive = false;
                    isCheckOutCalendarActive = true;
                    updateCalendar();
                }
                
                checkOutPanel.revalidate();
                checkOutPanel.repaint();
            }
        });
    }
    
    private JPanel createCalendarPanel(boolean isCheckIn) {
        JPanel calendarPanel = new JPanel();
        calendarPanel.setLayout(new BorderLayout());
        calendarPanel.setBackground(PANEL_BG);
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Month and year header
        JPanel monthYearPanel = new JPanel(new BorderLayout());
        monthYearPanel.setBackground(PANEL_BG);
        
        JButton prevMonthBtn = new JButton("◀");
        prevMonthBtn.setFont(new Font("Arial", Font.PLAIN, 10));
        prevMonthBtn.setBorderPainted(false);
        prevMonthBtn.setContentAreaFilled(false);
        prevMonthBtn.setFocusPainted(false);
        
        JButton nextMonthBtn = new JButton("▶");
        nextMonthBtn.setFont(new Font("Arial", Font.PLAIN, 10));
        nextMonthBtn.setBorderPainted(false);
        nextMonthBtn.setContentAreaFilled(false);
        nextMonthBtn.setFocusPainted(false);
        
        monthYearLabel = new JLabel("", SwingConstants.CENTER);
        monthYearLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        monthYearPanel.add(prevMonthBtn, BorderLayout.WEST);
        monthYearPanel.add(monthYearLabel, BorderLayout.CENTER);
        monthYearPanel.add(nextMonthBtn, BorderLayout.EAST);
        
        // Day names header
        String[] dayNames = {"S", "M", "T", "W", "T", "F", "S"};
        JPanel dayNamesPanel = new JPanel(new GridLayout(1, 7));
        dayNamesPanel.setBackground(PANEL_BG);
        
        for (String dayName : dayNames) {
            JLabel dayLabel = new JLabel(dayName, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 12));
            dayNamesPanel.add(dayLabel);
        }


        // Initialize calendar model
        calendarModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        calendarModel.setColumnCount(7);
        calendarModel.setRowCount(6);  // Ensure 6 rows
        
        // Fill with empty strings initially
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                calendarModel.setValueAt("", i, j);
            }
        }
        
        calendarTable = new JTable(calendarModel);
        calendarTable.setRowHeight(30);
        calendarTable.setFillsViewportHeight(true);
        calendarTable.setBackground(PANEL_BG);
        calendarTable.setShowGrid(false);
        calendarTable.setIntercellSpacing(new Dimension(0, 0));
        calendarTable.setDefaultRenderer(Object.class, new CalendarCellRenderer());
        calendarTable.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Remove table header
        calendarTable.setTableHeader(null);
        
        // Calendar panel (days)
        JPanel daysPanel = new JPanel(new BorderLayout());
        daysPanel.setBackground(PANEL_BG);
        daysPanel.add(dayNamesPanel, BorderLayout.NORTH);
        daysPanel.add(calendarTable, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(PANEL_BG);
        
        JButton doneButton = new JButton("DONE");
        doneButton.setBackground(YELLOW_BG);
        doneButton.setForeground(DARK_TEXT);
        doneButton.setFont(new Font("Arial", Font.BOLD, 12));
        doneButton.setFocusPainted(false);
        doneButton.setBorderPainted(false);
        
        buttonPanel.add(doneButton);
        
        // Add components to calendar panel
        calendarPanel.add(monthYearPanel, BorderLayout.NORTH);
        calendarPanel.add(daysPanel, BorderLayout.CENTER);
        calendarPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add event listeners
        prevMonthBtn.addActionListener(e -> {
            currentMonth--;
            if (currentMonth < 0) {
                currentMonth = 11;
                currentYear--;
            }
            updateCalendar();
        });
        
        nextMonthBtn.addActionListener(e -> {
            currentMonth++;
            if (currentMonth > 11) {
                currentMonth = 0;
                currentYear++;
            }
            updateCalendar();
        });
        
        calendarTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = calendarTable.rowAtPoint(e.getPoint());
                int col = calendarTable.columnAtPoint(e.getPoint());
                
                if (row >= 0 && col >= 0) {
                    Object value = calendarTable.getValueAt(row, col);
                    if (value != null && !value.toString().isEmpty()) {
                        int day = Integer.parseInt(value.toString());
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(currentYear, currentMonth, day);
                        
                        // Check if date is in the past
                        Calendar today = Calendar.getInstance();
                        today.set(Calendar.HOUR_OF_DAY, 0);
                        today.set(Calendar.MINUTE, 0);
                        today.set(Calendar.SECOND, 0);
                        today.set(Calendar.MILLISECOND, 0);
                        
                        if (selectedDate.before(today)) {
                            JOptionPane.showMessageDialog(DemandeReservationGui.this, 
                                "Cannot select dates in the past", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        if (isCheckInCalendarActive) {
                            selectedCheckInDate = selectedDate.getTime();
                            // Auto-set check-out to next day if it's before check-in
                            Calendar checkOutCal = Calendar.getInstance();
                            checkOutCal.setTime(selectedCheckOutDate);
                            if (!checkOutCal.after(selectedDate)) {
                                checkOutCal.setTime(selectedDate.getTime());
                                checkOutCal.add(Calendar.DAY_OF_MONTH, 1);
                                selectedCheckOutDate = checkOutCal.getTime();
                                checkOutInfoLabel.setText(dateFormat.format(selectedCheckOutDate));
                            }
                        } else if (isCheckOutCalendarActive) {
                            // Check if check-out is after check-in
                            if (selectedDate.getTime().after(selectedCheckInDate)) {
                                selectedCheckOutDate = selectedDate.getTime();
                            } else {
                                JOptionPane.showMessageDialog(DemandeReservationGui.this, 
                                    "Check-out date must be after check-in date", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        
                        updateCalendar();
                    }
                }
            }
        });
        
        doneButton.addActionListener(e -> {
            if (isCheckInCalendarActive && selectedCheckInDate != null) {
                checkInInfoLabel.setText(dateFormat.format(selectedCheckInDate));
                calendarPanel.setVisible(false);
            } else if (isCheckOutCalendarActive && selectedCheckOutDate != null) {
                checkOutInfoLabel.setText(dateFormat.format(selectedCheckOutDate));
                calendarPanel.setVisible(false);
            }
        });
        
        return calendarPanel;
    }
    
    private void updateCalendar() {
        // Update month year label
        String[] monthNames = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", 
                            "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        monthYearLabel.setText(monthNames[currentMonth] + " " + currentYear);
        
        // Clear table
        calendarModel.setRowCount(0);  // Clear existing rows
        calendarModel.setRowCount(6);  // Reset to 6 rows
        
        // Get first day of month
        Calendar cal = Calendar.getInstance();
        cal.set(currentYear, currentMonth, 1);
        int firstDayOfMonth = cal.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
        
        // Get number of days in month
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        // Get today's date
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        
        // Fill calendar
        int day = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 0 && j < firstDayOfMonth) {
                    calendarModel.setValueAt("", i, j);
                } else if (day <= daysInMonth) {
                    // Check if date is in the past
                    cal.set(currentYear, currentMonth, day);
                    if (cal.before(today)) {
                        calendarModel.setValueAt("<html><font color='#cccccc'>" + day + "</font></html>", i, j);
                    } else {
                        calendarModel.setValueAt(String.valueOf(day), i, j);
                    }
                    day++;
                } else {
                    calendarModel.setValueAt("", i, j);
                }
            }
        }
        
        calendarTable.revalidate();
        calendarTable.repaint();
    }
    
    private void layoutComponents() {
        setContentPane(mainPanel);
    }
    
    private Icon createTextIcon(String text, int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2d.setFont(new Font("Dialog", Font.PLAIN, size));
                g2d.drawString(text, x, y + size);
                g2d.dispose();
            }
            
            @Override
            public int getIconWidth() {
                return size;
            }
            
            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }
    
    // Calendar cell renderer for highlighting selected dates
    private class CalendarCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                     boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder());
            
            // Reset background and foreground
            label.setBackground(PANEL_BG);
            label.setForeground(DARK_TEXT);
            
            // Check if this is a valid date cell
            if (value != null && !value.toString().isEmpty() && !value.toString().startsWith("<html>")) {
                int day = Integer.parseInt(value.toString());
                
                // Create a calendar for this cell
                Calendar cellDate = Calendar.getInstance();
                cellDate.set(currentYear, currentMonth, day);
                
                // Check if this is today
                Calendar today = Calendar.getInstance();
                if (cellDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                    cellDate.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                    cellDate.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                    label.setBorder(BorderFactory.createLineBorder(YELLOW_BG, 2));
                }
                
                // Check if this is selected check-in date
                if (selectedCheckInDate != null) {
                    Calendar selectedCal = Calendar.getInstance();
                    selectedCal.setTime(selectedCheckInDate);
                    
                    if (selectedCal.get(Calendar.YEAR) == currentYear && 
                        selectedCal.get(Calendar.MONTH) == currentMonth && 
                        selectedCal.get(Calendar.DAY_OF_MONTH) == day) {
                        label.setBackground(SELECTED_DATE);
                        label.setForeground(Color.WHITE);
                    }
                }
                
                // Check if this is selected check-out date
                if (selectedCheckOutDate != null) {
                    Calendar selectedCal = Calendar.getInstance();
                    selectedCal.setTime(selectedCheckOutDate);
                    
                    if (selectedCal.get(Calendar.YEAR) == currentYear && 
                        selectedCal.get(Calendar.MONTH) == currentMonth && 
                        selectedCal.get(Calendar.DAY_OF_MONTH) == day) {
                        label.setBackground(SELECTED_DATE);
                        label.setForeground(Color.WHITE);
                    }
                }
                
                // Highlight dates between check-in and check-out
                if (selectedCheckInDate != null && selectedCheckOutDate != null) {
                    Calendar checkInCal = Calendar.getInstance();
                    checkInCal.setTime(selectedCheckInDate);
                    Calendar checkOutCal = Calendar.getInstance();
                    checkOutCal.setTime(selectedCheckOutDate);
                    
                    if (cellDate.after(checkInCal) && cellDate.before(checkOutCal)) {
                        label.setBackground(new Color(240, 240, 240));
                    }
                }
            }
            
            return label;
        }
    }
    
    public static void main(String[] args) {
        // Set the look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create the application
        SwingUtilities.invokeLater(() -> new DemandeReservationGui());
    }
}