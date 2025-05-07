import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

public class TemperaturaUI extends JFrame {

    private JTextField poleNazwisko;
    private JTextField poleImie;
    private JTextField poleTemp;
    private JButton przyciskDodaj;
    private JTable tabelaDanych;
    private DefaultTableModel modelTabeliDanych;
    private JLabel informacja;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TemperaturaUI().setVisible(true));
    }


    public TemperaturaUI() {
        przygotujGUI();
        zaladujDaneOsob();
    }

    private void przygotujGUI() {
        setTitle("Zarz¹dzanie Temperaturami");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        utworzPanelLewy();
        utworzTabeleDanych();
        utworzPanelDolny();
    }

    private void utworzPanelLewy() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        poleNazwisko = new JTextField(10);
        poleImie = new JTextField(10);
        poleTemp = new JTextField(5);
        przyciskDodaj = new JButton("Zapisz dane");

        Dimension rozmiarPolTekstowych = new Dimension(Integer.MAX_VALUE, poleNazwisko.getPreferredSize().height);
        poleNazwisko.setMaximumSize(rozmiarPolTekstowych);
        poleImie.setMaximumSize(rozmiarPolTekstowych);
        poleTemp.setMaximumSize(rozmiarPolTekstowych);

        panel.add(new JLabel("Nazwisko:"));
        panel.add(poleNazwisko);
        panel.add(new JLabel("Imiê:"));
        panel.add(poleImie);
        panel.add(new JLabel("Temp.:"));
        panel.add(poleTemp);
        panel.add(przyciskDodaj);

        add(panel, BorderLayout.WEST);
        przyciskDodaj.addActionListener(this::zapiszDoBazy);
    }

    private void utworzTabeleDanych() {
        String[] nazwyKolumn = {"Imiê", "Nazwisko", "Œrednia °C", "Œrednia °F"};
        modelTabeliDanych = new DefaultTableModel(null, nazwyKolumn) {
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex >= 2 ? Double.class : String.class;
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaDanych = new JTable(modelTabeliDanych);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelTabeliDanych);
        tabelaDanych.setRowSorter(sorter);
        sorter.setSortKeys(java.util.Collections.singletonList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));

        add(new JScrollPane(tabelaDanych), BorderLayout.CENTER);
        
        tabelaDanych.addMouseListener((MouseListener) new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    String imie = (String) tabelaDanych.getValueAt(row, 0);
                    String nazwisko = (String) tabelaDanych.getValueAt(row, 1);
                    pokazSzczegolowePomiary(imie, nazwisko);
                }
            }
        });

    }
    
    private void utworzPanelDolny() {
        informacja = new JLabel("Aby otworzyæ szczegó³y pomiarów kliknij dwukrotnie na wybran¹ osobê");
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(informacja);

        add(panel, BorderLayout.SOUTH);
    }
    
    private void pokazSzczegolowePomiary(String imie, String nazwisko) {
        JDialog szczegolyDialog = new JDialog(this, "Szczegó³y pomiarów: " + imie + " " + nazwisko, true);
        szczegolyDialog.setSize(400, 300);
        szczegolyDialog.setLayout(new BorderLayout());

        DefaultTableModel modelSzczegolow = new DefaultTableModel(new Object[]{"Temperatura °C", "Temperatura °F", "Data"}, 0);
        JTable tabelaSzczegolow = new JTable(modelSzczegolow);

        zaladujSzczegolyPomiarowDlaOsoby(imie, nazwisko, modelSzczegolow);

        szczegolyDialog.add(new JScrollPane(tabelaSzczegolow), BorderLayout.CENTER);
        szczegolyDialog.setVisible(true);
    }
    
    private void zaladujSzczegolyPomiarowDlaOsoby(String imie, String nazwisko, DefaultTableModel model) {
        String url = "jdbc:sqlite:BazaSQL/BazaSQLite.db";
        String sql = "SELECT p.temperatura_c, p.temperatura_f, p.data FROM pomiary p " +
                     "JOIN osoby o ON p.id_osoby = o.id " +
                     "WHERE o.imie = ? AND o.nazwisko = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, imie);
            pstmt.setString(2, nazwisko);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Double temperaturaC = resultSet.getDouble("temperatura_c");
                Double temperaturaF = resultSet.getDouble("temperatura_f");
                String data = resultSet.getString("data");

                model.addRow(new Object[]{temperaturaC, temperaturaF, data});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "B³¹d podczas ³adowania danych szczegó³owych: " + e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void zapiszDoBazy(ActionEvent e) {
        String nazwisko = poleNazwisko.getText().trim();
        String imie = poleImie.getText().trim();
        String temperaturaStr = poleTemp.getText().trim();

        if (nazwisko.isEmpty() || imie.isEmpty() || temperaturaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Wszystkie pola musz¹ byæ wype³nione.", "B³¹d", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double temperaturaC;
        try {
            temperaturaC = Double.parseDouble(temperaturaStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Temperatura musi byæ liczb¹.", "B³¹d", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double temperaturaF = temperaturaC * 9 / 5 + 32;
        String url = "jdbc:sqlite:BazaSQL/BazaSQLite.db";

        String sqlDodajOsobe = "INSERT INTO osoby (nazwisko, imie) SELECT ?, ? WHERE NOT EXISTS (SELECT 1 FROM osoby WHERE nazwisko = ? AND imie = ?)";
        String sqlDodajPomiar = "INSERT INTO pomiary (id_osoby, temperatura_c, temperatura_f, data) VALUES ((SELECT id FROM osoby WHERE nazwisko = ? AND imie = ?), ?, ?, datetime('now'))";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement psDodajOsobe = connection.prepareStatement(sqlDodajOsobe);
             PreparedStatement psDodajPomiar = connection.prepareStatement(sqlDodajPomiar)) {

            psDodajOsobe.setString(1, nazwisko);
            psDodajOsobe.setString(2, imie);
            psDodajOsobe.setString(3, nazwisko);
            psDodajOsobe.setString(4, imie);
            psDodajOsobe.executeUpdate();

            psDodajPomiar.setString(1, nazwisko);
            psDodajPomiar.setString(2, imie);
            psDodajPomiar.setDouble(3, temperaturaC);
            psDodajPomiar.setDouble(4, temperaturaF);
            psDodajPomiar.executeUpdate();

            JOptionPane.showMessageDialog(this, "Zapisano pomiar temperatury.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            zaladujDaneOsob();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "B³¹d podczas zapisu do bazy danych: " + ex.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void zaladujDaneOsob() {
        String url = "jdbc:sqlite:BazaSQL/BazaSQLite.db";
        String sql = "SELECT o.imie, o.nazwisko, AVG(p.temperatura_c) AS SredniaCelsjusza, AVG(p.temperatura_f) AS SredniaFahrenheita " +
                     "FROM osoby o " +
                     "JOIN pomiary p ON o.id = p.id_osoby " +
                     "GROUP BY o.id";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            modelTabeliDanych.setRowCount(0);

            while (resultSet.next()) {
                String imie = resultSet.getString("imie");
                String nazwisko = resultSet.getString("nazwisko");
                double sredniaCelsjusza = resultSet.getDouble("SredniaCelsjusza");
                double sredniaFahrenheita = resultSet.getDouble("SredniaFahrenheita");

                modelTabeliDanych.addRow(new Object[]{imie, nazwisko, sredniaCelsjusza, sredniaFahrenheita});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "B³¹d podczas ³adowania danych: " + e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
        }
    }

}
