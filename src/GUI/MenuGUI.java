package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

// 🔥 PERBAIKAN: Menggunakan Import Wildcard untuk memastikan semua model/service ditemukan
import model.*;
import service.*;

public class MenuGUI extends JFrame {
    private RestaurantSystem system;
    private JTable menuTable;
    private DefaultTableModel tableModel;

    private static final NumberFormat IDR_FORMAT = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));

    public MenuGUI(RestaurantSystem system) {
        this.system = system;
        
        setTitle("Daftar Menu Restoran");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        initComponents();
        loadMenuData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        String[] columnNames = {"No.", "Nama Menu", "Harga", "Tipe"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        menuTable = new JTable(tableModel);
        menuTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        
        JLabel title = new JLabel("DAFTAR MENU RESTORAN", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        add(new JScrollPane(menuTable), BorderLayout.CENTER);
        
        // setVisible(true) dipindahkan ke constructor
    }
    
    private void loadMenuData() {
        tableModel.setRowCount(0); 
        // Menggunakan interface List<MenuItem>
        List<MenuItem> daftarMenu = system.getDaftarMenu();
        
        if (daftarMenu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada data menu yang ditemukan.", "Informasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int i = 1;
        for (MenuItem item : daftarMenu) {
            
            // 🔥 PERBAIKAN: Makanan, getHarga(), dan getNama() sekarang dapat diakses
            // Karena kita menggunakan import model.* dan service.*
            
            // Logika untuk menentukan Tipe (Makanan/Minuman)
            String tipe = (item instanceof Makanan) ? "Makanan" : "Minuman"; 
            
            String hargaFormatted = IDR_FORMAT.format(item.getHarga());
            String namaMenu = item.getNama();
            
            tableModel.addRow(new Object[]{
                i++, 
                namaMenu, 
                hargaFormatted, 
                tipe
            });
        }
    }
}
