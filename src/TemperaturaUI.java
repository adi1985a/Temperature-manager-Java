import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class TemperaturaUI extends JFrame {

    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField temperatureField;
    private JButton addButton;
    private JButton exportButton;
    private JButton statisticsButton;
    private JButton clearButton;
    private JButton importButton;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;
    private JLabel titleLabel;
    
    // Colors for modern look
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color SECONDARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color WARNING_COLOR = new Color(230, 126, 34);
    private static final Color ERROR_COLOR = new Color(231, 76, 60);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color PANEL_COLOR = Color.WHITE;
    
    // Dodane pola do obsługi dynamicznych przycisków
    private JPanel floatingButtonPanel;
    private JButton editRowButton;
    private JButton addTempButton;
    private int hoveredRow = -1;
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new TemperaturaUI().setVisible(true));
    }

    public TemperaturaUI() {
        super("Temperature Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Set application icon
        try {
            ImageIcon appIcon = new ImageIcon("images/icons/app_icon.png");
            setIconImage(appIcon.getImage());
        } catch (Exception e) {
            System.out.println("Could not load app icon: " + e.getMessage());
        }

        createTitlePanel();
        createLeftPanel();
        createDataTable();
        createBottomPanel();
        loadPersonData();
    }

    private void setupGUI() {
        setTitle("Temperature Management System");
        setSize(1200, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Set application icon
        try {
            ImageIcon appIcon = new ImageIcon("images/icons/app_icon.png");
            setIconImage(appIcon.getImage());
        } catch (Exception e) {
            System.out.println("Could not load app icon: " + e.getMessage());
        }

        createTitlePanel();
        createLeftPanel();
        createDataTable();
        createBottomPanel();
    }

    private void createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(PRIMARY_COLOR);
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        // App logo (max 36px height)
        JLabel logoLabel = null;
        try {
            ImageIcon logoIcon = new ImageIcon("images/logos/app_logo.png");
            Image logoImg = logoIcon.getImage().getScaledInstance(-1, 36, Image.SCALE_SMOOTH);
            logoLabel = new JLabel(new ImageIcon(logoImg));
        } catch (Exception e) {
            logoLabel = new JLabel();
        }
        if (logoLabel != null) {
            titlePanel.add(logoLabel);
        }

        // App title
        titleLabel = new JLabel("Temperature Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        add(titlePanel, BorderLayout.NORTH);
    }

    private void createLeftPanel() {
        // Stała szerokość panelu
        JPanel panel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(340, super.getPreferredSize().height);
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 249, 255));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Logo autora (małe, nad instrukcjami)
        try {
            ImageIcon authorIcon = new ImageIcon("images/logos/author_logo.png");
            Image authorImg = authorIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            JLabel authorLogoLabel = new JLabel(new ImageIcon(authorImg));
            authorLogoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(authorLogoLabel);
        } catch (Exception e) {}

        JLabel authorInfo = new JLabel("Created by: Adrian Lesniak");
        authorInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        authorInfo.setForeground(new Color(100, 100, 100));
        authorInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(authorInfo);
        panel.add(Box.createVerticalStrut(10));

        // Instrukcje
        JLabel instructionsLabel = new JLabel("Instructions:");
        instructionsLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        instructionsLabel.setForeground(new Color(50, 50, 50));
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(instructionsLabel);
        panel.add(Box.createVerticalStrut(5));

        JTextArea instructions = new JTextArea(
            "1. Enter Last Name (e.g., Smith)\n" +
            "2. Enter First Name (e.g., John)\n" +
            "3. Enter Temperature in Celsius (e.g., 36.5)\n" +
            "4. Click 'Add Measurement' to save\n" +
            "5. Double-click table row for details"
        );
        instructions.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        instructions.setForeground(new Color(60, 60, 60));
        instructions.setBackground(new Color(245, 249, 255));
        instructions.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        instructions.setEditable(false);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        instructions.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(instructions);
        panel.add(Box.createVerticalStrut(15));

        // Pola formularza
        lastNameField = createStyledTextField("Last Name (e.g., Smith):");
        firstNameField = createStyledTextField("First Name (e.g., John):");
        temperatureField = createStyledTextField("Temperature (°C) (e.g., 36.5):");
        panel.add(lastNameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(firstNameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(temperatureField);
        panel.add(Box.createVerticalStrut(20));

        // Przyciski z ikonami
        addButton = createStyledButtonWithIcon("Add Measurement", SUCCESS_COLOR, "images/icons/add_button.png");
        exportButton = createStyledButtonWithIcon("Export", PRIMARY_COLOR, "images/icons/export_button.png");
        statisticsButton = createStyledButtonWithIcon("Statistics", SECONDARY_COLOR, "images/icons/stats_button.png");
        clearButton = createStyledButtonWithIcon("Clear Fields", WARNING_COLOR, "images/icons/clear_button.png");
        importButton = createStyledButtonWithIcon("Import", PRIMARY_COLOR, "images/icons/export_button.png");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exportButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        statisticsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        importButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(addButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(exportButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(statisticsButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(clearButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(importButton);
        panel.add(Box.createVerticalGlue());

        add(panel, BorderLayout.WEST);

        // Listeners
        addButton.addActionListener(this::saveToDatabase);
        exportButton.addActionListener(this::exportData);
        statisticsButton.addActionListener(this::showStatistics);
        clearButton.addActionListener(this::clearFields);
        importButton.addActionListener(this::importData);
    }

    private JTextField createStyledTextField(String label) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setBackground(PANEL_COLOR);

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Segoe UI", Font.BOLD, 12));
        labelComponent.setForeground(new Color(40, 40, 40)); // Darker color

        JTextField textField = new JTextField(15);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setForeground(new Color(30, 30, 30)); // Dark text
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        // Add placeholder text
        String placeholder = getPlaceholderText(label);
        textField.setText(placeholder);
        textField.setForeground(new Color(150, 150, 150)); // Gray color for placeholder

        // Add focus listeners to handle placeholder
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(new Color(30, 30, 30)); // Dark text
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(new Color(150, 150, 150)); // Gray color
                }
            }
        });

        fieldPanel.add(labelComponent);
        fieldPanel.add(Box.createVerticalStrut(5));
        fieldPanel.add(textField);

        return textField;
    }

    private String getPlaceholderText(String label) {
        if (label.contains("Last Name")) {
            return "Enter last name...";
        } else if (label.contains("First Name")) {
            return "Enter first name...";
        } else if (label.contains("Temperature")) {
            return "Enter temperature...";
        }
        return "Enter text...";
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(new Color(30, 30, 30)); // Dark text for better visibility
        button.setBackground(color);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private JButton createStyledButtonWithIcon(String text, Color color, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(new Color(30, 30, 30)); // Dark text for better visibility
        button.setBackground(color);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        // Try to set button icon (scaled to 20x20)
        try {
            ImageIcon icon = new ImageIcon(iconPath);
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("Could not load button icon: " + e.getMessage());
        }

        return button;
    }

    private void createDataTable() {
        String[] columnNames = {"First Name", "Last Name", "Avg °C", "Avg °F", "Measurements Count", "Actions"};
        tableModel = new DefaultTableModel(null, columnNames) {
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5) return Object.class;
                return columnIndex >= 2 ? Double.class : String.class;
            }
            public boolean isCellEditable(int row, int column) {
                return column == 5; // tylko kolumna z przyciskami edytowalna
            }
        };

        dataTable = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };
        dataTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dataTable.setForeground(new Color(30, 30, 30));
        dataTable.setRowHeight(28);
        dataTable.setGridColor(new Color(200, 200, 200));
        dataTable.setSelectionBackground(PRIMARY_COLOR);
        dataTable.setSelectionForeground(Color.WHITE);

        // Center align all columns oprócz kolumny z przyciskami
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        for (int i = 0; i < columnNames.length - 1; i++) {
            dataTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // Center header
        javax.swing.table.DefaultTableCellRenderer headerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerRenderer.setFont(new Font("Segoe UI", Font.BOLD, 12));
        headerRenderer.setBackground(Color.WHITE);
        headerRenderer.setForeground(Color.BLACK);
        for (int i = 0; i < columnNames.length; i++) {
            dataTable.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        dataTable.getTableHeader().setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));

        // Dodaj renderer i edytor przycisków do ostatniej kolumny
        dataTable.getColumn("Actions").setCellRenderer(new ActionButtonRenderer());
        dataTable.getColumn("Actions").setCellEditor(new ActionButtonEditor(new JCheckBox()));

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        dataTable.setRowSorter(sorter);
        sorter.setSortKeys(java.util.Collections.singletonList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));

        dataTable.getColumn("Actions").setMinWidth(180);
        dataTable.getColumn("Actions").setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        add(scrollPane, BorderLayout.CENTER);

        // Podwójne kliknięcie na wiersz = historia pomiarów
        dataTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && dataTable.getSelectedRow() != -1) {
                    int row = dataTable.getSelectedRow();
                    String firstName = (String) dataTable.getValueAt(row, 0);
                    String lastName = (String) dataTable.getValueAt(row, 1);
                    showDetailedMeasurements(firstName, lastName);
                }
            }
        });
    }
    
    private void createBottomPanel() {
        statusLabel = new JLabel("Double-click on a person to view detailed measurements");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(PRIMARY_COLOR);
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(PANEL_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(statusLabel);

        add(panel, BorderLayout.SOUTH);
    }
    
    private void showDetailedMeasurements(String firstName, String lastName) {
        JDialog detailsDialog = new JDialog(this, "Detailed Measurements: " + firstName + " " + lastName, true);
        detailsDialog.setSize(600, 400);
        detailsDialog.setLocationRelativeTo(this);
        detailsDialog.getContentPane().setBackground(BACKGROUND_COLOR);

        DefaultTableModel detailsModel = new DefaultTableModel(
            new Object[]{"Temperature °C", "Temperature °F", "Date & Time"}, 0
        );
        JTable detailsTable = new JTable(detailsModel);
        detailsTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        detailsTable.setRowHeight(25);
        detailsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        loadDetailedMeasurementsForPerson(firstName, lastName, detailsModel);

        JScrollPane scrollPane = new JScrollPane(detailsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        detailsDialog.add(scrollPane, BorderLayout.CENTER);

        // Add close button
        JButton closeButton = createStyledButton("Close", PRIMARY_COLOR);
        closeButton.addActionListener(e -> detailsDialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(closeButton);
        detailsDialog.add(buttonPanel, BorderLayout.SOUTH);

        detailsDialog.setVisible(true);
    }
    
    private void loadDetailedMeasurementsForPerson(String firstName, String lastName, DefaultTableModel model) {
        String url = "jdbc:sqlite:BazaSQL/BazaSQLite.db";
        String sql = "SELECT p.temperatura_c, p.temperatura_f, p.data FROM pomiary p " +
                     "JOIN osoby o ON p.id_osoby = o.id " +
                     "WHERE o.imie = ? AND o.nazwisko = ? " +
                     "ORDER BY p.data DESC";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Double temperatureC = resultSet.getDouble("temperatura_c");
                Double temperatureF = resultSet.getDouble("temperatura_f");
                String date = resultSet.getString("data");

                model.addRow(new Object[]{temperatureC, temperatureF, date});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error loading detailed data: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveToDatabase(ActionEvent e) {
        String lastName = lastNameField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String temperatureStr = temperatureField.getText().trim();

        // Check if fields contain placeholder text
        if (lastName.equals("Enter last name...") || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid last name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (firstName.equals("Enter first name...") || firstName.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid first name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (temperatureStr.equals("Enter temperature...") || temperatureStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid temperature.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double temperatureC;
        try {
            temperatureC = Double.parseDouble(temperatureStr);
            if (temperatureC < -273.15) {
                JOptionPane.showMessageDialog(this, 
                    "Temperature cannot be below absolute zero (-273.15°C).", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Temperature must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double temperatureF = temperatureC * 9 / 5 + 32;
        String url = "jdbc:sqlite:BazaSQL/BazaSQLite.db";

        String sqlAddPerson = "INSERT INTO osoby (nazwisko, imie) SELECT ?, ? WHERE NOT EXISTS (SELECT 1 FROM osoby WHERE nazwisko = ? AND imie = ?)";
        String sqlAddMeasurement = "INSERT INTO pomiary (id_osoby, temperatura_c, temperatura_f, data) VALUES ((SELECT id FROM osoby WHERE nazwisko = ? AND imie = ?), ?, ?, datetime('now'))";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement psAddPerson = connection.prepareStatement(sqlAddPerson);
             PreparedStatement psAddMeasurement = connection.prepareStatement(sqlAddMeasurement)) {

            psAddPerson.setString(1, lastName);
            psAddPerson.setString(2, firstName);
            psAddPerson.setString(3, lastName);
            psAddPerson.setString(4, firstName);
            psAddPerson.executeUpdate();

            psAddMeasurement.setString(1, lastName);
            psAddMeasurement.setString(2, firstName);
            psAddMeasurement.setDouble(3, temperatureC);
            psAddMeasurement.setDouble(4, temperatureF);
            psAddMeasurement.executeUpdate();

            JOptionPane.showMessageDialog(this, 
                "Temperature measurement saved successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            clearFields(null);
            loadPersonData();
            updateStatus("Measurement added successfully");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error saving to database: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportData(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Temperature Data");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(new File("temperature_data_" + 
            new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".csv"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                // Write header
                writer.write("First Name,Last Name,Average °C,Average °F,Measurements Count\n");
                
                // Write data
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    writer.write(String.format("%s,%s,%.2f,%.2f,%s\n",
                        tableModel.getValueAt(i, 0),
                        tableModel.getValueAt(i, 1),
                        tableModel.getValueAt(i, 2),
                        tableModel.getValueAt(i, 3),
                        tableModel.getValueAt(i, 4)
                    ));
                }
                
                JOptionPane.showMessageDialog(this, 
                    "✅ Data exported successfully to: " + file.getName(), 
                    "Export Success", JOptionPane.INFORMATION_MESSAGE);
                updateStatus("Data exported to " + file.getName());
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error exporting data: " + ex.getMessage(), 
                    "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showStatistics(ActionEvent e) {
        String url = "jdbc:sqlite:BazaSQL/BazaSQLite.db";
        StringBuilder stats = new StringBuilder();
        java.util.List<Double> allTemps = new java.util.ArrayList<>();
        java.util.Map<String, Integer> personCounts = new java.util.HashMap<>();
        java.util.Map<String, java.util.List<Double>> personTemps = new java.util.HashMap<>();
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            // Statystyki tekstowe
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM pomiary");
            int totalMeasurements = rs.next() ? rs.getInt("total") : 0;
            rs = statement.executeQuery("SELECT COUNT(*) as total FROM osoby");
            int totalPeople = rs.next() ? rs.getInt("total") : 0;
            rs = statement.executeQuery("SELECT AVG(temperatura_c) as avg_c, AVG(temperatura_f) as avg_f FROM pomiary");
            double avgC = 0, avgF = 0;
            if (rs.next()) {
                avgC = rs.getDouble("avg_c");
                avgF = rs.getDouble("avg_f");
            }
            rs = statement.executeQuery("SELECT MIN(temperatura_c) as min_c, MAX(temperatura_c) as max_c FROM pomiary");
            double minC = 0, maxC = 0;
            if (rs.next()) {
                minC = rs.getDouble("min_c");
                maxC = rs.getDouble("max_c");
            }
            stats.append("TEMPERATURE STATISTICS\n\n");
            stats.append("Total People: ").append(totalPeople).append("\n");
            stats.append("Total Measurements: ").append(totalMeasurements).append("\n");
            stats.append("Average Temperature: ").append(String.format("%.2f°C / %.2f°F", avgC, avgF)).append("\n");
            stats.append("Lowest Temperature: ").append(String.format("%.2f°C", minC)).append("\n");
            stats.append("Highest Temperature: ").append(String.format("%.2f°C", maxC)).append("\n");

            // Dane do wykresów
            rs = statement.executeQuery("SELECT o.imie, o.nazwisko, p.temperatura_c, p.data FROM osoby o JOIN pomiary p ON o.id = p.id_osoby");
            while (rs.next()) {
                String person = rs.getString("imie") + " " + rs.getString("nazwisko");
                double temp = rs.getDouble("temperatura_c");
                allTemps.add(temp);
                personCounts.put(person, personCounts.getOrDefault(person, 0) + 1);
                personTemps.computeIfAbsent(person, k -> new java.util.ArrayList<>()).add(temp);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading statistics: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tworzenie okna z wykresami
        JDialog chartDialog = new JDialog(this, "Statistics & Charts", true);
        chartDialog.setSize(1000, 700);
        chartDialog.setLocationRelativeTo(this);
        chartDialog.setLayout(new BorderLayout());
        chartDialog.getContentPane().setBackground(BACKGROUND_COLOR);

        JPanel chartsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        chartsPanel.setBackground(BACKGROUND_COLOR);

        // Histogram rozkładu temperatur
        HistogramDataset histDataset = new HistogramDataset();
        double[] tempArray = allTemps.stream().mapToDouble(Double::doubleValue).toArray();
        if (tempArray.length > 0) {
            histDataset.addSeries("Temperatures", tempArray, Math.max(10, (int)Math.sqrt(tempArray.length)));
        }
        JFreeChart histChart = ChartFactory.createHistogram(
            "Temperature Distribution", "Temperature [°C]", "Count",
            histDataset, PlotOrientation.VERTICAL, false, true, false);
        ChartPanel histPanel = new ChartPanel(histChart);

        // Wykres słupkowy: liczba pomiarów na osobę
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        for (var entry : personCounts.entrySet()) {
            barDataset.addValue(entry.getValue(), "Measurements", entry.getKey());
        }
        JFreeChart barChart = ChartFactory.createBarChart(
            "Measurements per Person", "Person", "Count",
            barDataset, PlotOrientation.VERTICAL, false, true, false);
        ChartPanel barPanel = new ChartPanel(barChart);

        // Wykres liniowy: zmiana temperatury w czasie (dla pierwszej osoby z bazy)
        XYSeriesCollection xyDataset = new XYSeriesCollection();
        if (!personTemps.isEmpty()) {
            String firstPerson = personTemps.keySet().iterator().next();
            XYSeries series = new XYSeries(firstPerson);
            int i = 1;
            for (Double t : personTemps.get(firstPerson)) {
                series.add(i++, t);
            }
            xyDataset.addSeries(series);
        }
        JFreeChart lineChart = ChartFactory.createXYLineChart(
            "Temperature Trend (first person)", "Measurement #", "Temperature [°C]",
            xyDataset, PlotOrientation.VERTICAL, false, true, false);
        ChartPanel linePanel = new ChartPanel(lineChart);

        chartsPanel.add(histPanel);
        chartsPanel.add(barPanel);
        chartsPanel.add(linePanel);

        // Panel tekstowy ze statystykami
        JTextArea statsArea = new JTextArea(stats.toString());
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statsArea.setBackground(new Color(245, 249, 255));
        statsArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        statsArea.setMargin(new Insets(10, 10, 10, 10));

        chartDialog.add(statsArea, BorderLayout.NORTH);
        chartDialog.add(chartsPanel, BorderLayout.CENTER);

        JButton closeButton = createStyledButton("Close", PRIMARY_COLOR);
        closeButton.addActionListener(ev -> chartDialog.dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(closeButton);
        chartDialog.add(buttonPanel, BorderLayout.SOUTH);

        chartDialog.setVisible(true);
    }

    private void clearFields(ActionEvent e) {
        lastNameField.setText("Enter last name...");
        lastNameField.setForeground(new Color(150, 150, 150));
        firstNameField.setText("Enter first name...");
        firstNameField.setForeground(new Color(150, 150, 150));
        temperatureField.setText("Enter temperature...");
        temperatureField.setForeground(new Color(150, 150, 150));
        lastNameField.requestFocus();
        updateStatus("Fields cleared");
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    private void loadPersonData() {
        String url = "jdbc:sqlite:BazaSQL/BazaSQLite.db";
        String sql = "SELECT o.imie, o.nazwisko, AVG(p.temperatura_c) AS SredniaCelsjusza, " +
                     "AVG(p.temperatura_f) AS SredniaFahrenheita, COUNT(p.id) as MeasurementsCount " +
                     "FROM osoby o " +
                     "JOIN pomiary p ON o.id = p.id_osoby " +
                     "GROUP BY o.id " +
                     "ORDER BY o.imie";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            tableModel.setRowCount(0);

            while (resultSet.next()) {
                String firstName = resultSet.getString("imie");
                String lastName = resultSet.getString("nazwisko");
                double avgCelsius = resultSet.getDouble("SredniaCelsjusza");
                double avgFahrenheit = resultSet.getDouble("SredniaFahrenheita");
                int measurementsCount = resultSet.getInt("MeasurementsCount");

                tableModel.addRow(new Object[]{firstName, lastName, avgCelsius, avgFahrenheit, measurementsCount, null});
            }
            
            updateStatus("Data loaded successfully");
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error loading data: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Renderer przycisków w tabeli
    class ActionButtonRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
        private final JButton editButton = new JButton("Edit");
        private final JButton addButton = new JButton("Add Temp");
        public ActionButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0));
            editButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            addButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            add(editButton);
            add(addButton);
        }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Edytor przycisków w tabeli
    class ActionButtonEditor extends DefaultCellEditor {
        private JPanel panel = new JPanel();
        private JButton editButton = new JButton("Edit");
        private JButton addButton = new JButton("Add Temp");
        private int currentRow;
        public ActionButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0));
            editButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            addButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            panel.add(editButton);
            panel.add(addButton);
            editButton.addActionListener(e -> {
                fireEditingStopped();
                String firstName = (String) dataTable.getValueAt(currentRow, 0);
                String lastName = (String) dataTable.getValueAt(currentRow, 1);
                showEditTemperatureDialog(firstName, lastName);
            });
            addButton.addActionListener(e -> {
                fireEditingStopped();
                String firstName = (String) dataTable.getValueAt(currentRow, 0);
                String lastName = (String) dataTable.getValueAt(currentRow, 1);
                showAddTemperatureDialog(firstName, lastName);
            });
        }
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row;
            return panel;
        }
        public Object getCellEditorValue() {
            return "";
        }
    }

    // Dialog do edycji ostatniego pomiaru
    private void showEditTemperatureDialog(String firstName, String lastName) {
        String url = "jdbc:sqlite:BazaSQL/BazaSQLite.db";
        String sql = "SELECT p.id, p.temperatura_c FROM pomiary p JOIN osoby o ON p.id_osoby = o.id WHERE o.imie = ? AND o.nazwisko = ? ORDER BY p.data DESC LIMIT 1";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int pomiarId = rs.getInt("id");
                double oldTemp = rs.getDouble("temperatura_c");
                String newTempStr = JOptionPane.showInputDialog(this, "Edit last temperature for " + firstName + " " + lastName + ":", oldTemp);
                if (newTempStr != null && !newTempStr.trim().isEmpty()) {
                    try {
                        double newTemp = Double.parseDouble(newTempStr);
                        double newTempF = newTemp * 9 / 5 + 32;
                        String updateSql = "UPDATE pomiary SET temperatura_c = ?, temperatura_f = ? WHERE id = ?";
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                            updateStmt.setDouble(1, newTemp);
                            updateStmt.setDouble(2, newTempF);
                            updateStmt.setInt(3, pomiarId);
                            updateStmt.executeUpdate();
                        }
                        JOptionPane.showMessageDialog(this, "Temperature updated.");
                        loadPersonData();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid temperature value.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No measurements found for this person.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error editing temperature: " + ex.getMessage());
        }
    }

    // Dialog do dodania nowego pomiaru
    private void showAddTemperatureDialog(String firstName, String lastName) {
        String tempStr = JOptionPane.showInputDialog(this, "Add new temperature for " + firstName + " " + lastName + ":");
        if (tempStr != null && !tempStr.trim().isEmpty()) {
            try {
                double tempC = Double.parseDouble(tempStr);
                double tempF = tempC * 9 / 5 + 32;
                String url = "jdbc:sqlite:BazaSQL/BazaSQLite.db";
                String sql = "INSERT INTO pomiary (id_osoby, temperatura_c, temperatura_f, data) VALUES ((SELECT id FROM osoby WHERE nazwisko = ? AND imie = ?), ?, ?, datetime('now'))";
                try (Connection connection = DriverManager.getConnection(url);
                     PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, lastName);
                    pstmt.setString(2, firstName);
                    pstmt.setDouble(3, tempC);
                    pstmt.setDouble(4, tempF);
                    pstmt.executeUpdate();
                }
                JOptionPane.showMessageDialog(this, "Temperature added.");
                loadPersonData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid temperature value.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error adding temperature: " + ex.getMessage());
            }
        }
    }

    private void importData(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Import Temperature Data");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                int imported = 0;
                reader.readLine(); // pomiń nagłówek
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length < 5) continue;
                    String firstName = parts[0].trim();
                    String lastName = parts[1].trim();
                    double tempC, tempF;
                    try {
                        tempC = Double.parseDouble(parts[2]);
                        tempF = Double.parseDouble(parts[3]);
                    } catch (NumberFormatException ex) { continue; }
                    // Dodaj osobę jeśli nie istnieje
                    String url = "jdbc:sqlite:BazaSQL/BazaSQLite.db";
                    String sqlAddPerson = "INSERT INTO osoby (nazwisko, imie) SELECT ?, ? WHERE NOT EXISTS (SELECT 1 FROM osoby WHERE nazwisko = ? AND imie = ?)";
                    String sqlAddMeasurement = "INSERT INTO pomiary (id_osoby, temperatura_c, temperatura_f, data) VALUES ((SELECT id FROM osoby WHERE nazwisko = ? AND imie = ?), ?, ?, datetime('now'))";
                    try (Connection connection = DriverManager.getConnection(url);
                         PreparedStatement psAddPerson = connection.prepareStatement(sqlAddPerson);
                         PreparedStatement psAddMeasurement = connection.prepareStatement(sqlAddMeasurement)) {
                        psAddPerson.setString(1, lastName);
                        psAddPerson.setString(2, firstName);
                        psAddPerson.setString(3, lastName);
                        psAddPerson.setString(4, firstName);
                        psAddPerson.executeUpdate();
                        psAddMeasurement.setString(1, lastName);
                        psAddMeasurement.setString(2, firstName);
                        psAddMeasurement.setDouble(3, tempC);
                        psAddMeasurement.setDouble(4, tempF);
                        psAddMeasurement.executeUpdate();
                        imported++;
                    } catch (SQLException ex) { continue; }
                }
                JOptionPane.showMessageDialog(this, "Imported: " + imported + " records.", "Import Success", JOptionPane.INFORMATION_MESSAGE);
                loadPersonData();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error importing data: " + ex.getMessage(), "Import Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
