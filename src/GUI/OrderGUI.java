package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import model.*;
import service.RestaurantSystem;

public class OrderGUI extends JFrame {
    private RestaurantSystem system;
    private Pegawai pelayan; 
    private Pesanan currentPesanan;
    private Customer selectedCustomer;

    // Komponen GUI (PERBAIKAN: Menggunakan model.MenuItem untuk mengatasi ambiguitas)
    private JList<model.MenuItem> menuList; // 🔥 SOLUSI #1
    private DefaultListModel<model.MenuItem> menuListModel; // 🔥 SOLUSI #1
    
    private JSpinner spinnerJumlah;
    private JTextArea inputCatatan;
    private JComboBox<String> comboMeja;
    private JComboBox<Customer> comboCustomer;
    private JTextArea cartArea;
    private JLabel labelTotal;

    private static final int JUMLAH_MEJA = 10;
    private static final NumberFormat IDR_FORMAT = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));

    public OrderGUI(RestaurantSystem system, Pegawai pelayan) {
        this.system = system;
        this.pelayan = pelayan;
        this.currentPesanan = null; 
        this.selectedCustomer = null;

        setTitle("Order POS - Pelayan: " + pelayan.getNama());
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        initComponents();
        loadData();
        updateCartDisplay();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // --- PANEL KIRI: Menu List ---
        JPanel panelMenu = new JPanel(new BorderLayout());
        panelMenu.setBorder(new TitledBorder("Daftar Menu"));
        
        // 🔥 PERBAIKAN: Inisialisasi variabel field
        menuListModel = new DefaultListModel<>();
        menuList = new JList<>(menuListModel); // 🔥 Variabel field menuList diinisialisasi
        
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelMenu.add(new JScrollPane(menuList), BorderLayout.CENTER);

        // --- Panel Control Menu ---
        JPanel panelMenuControl = new JPanel(new GridLayout(4, 2, 5, 5));
        spinnerJumlah = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        inputCatatan = new JTextArea(2, 20);
        inputCatatan.setLineWrap(true);

        panelMenuControl.add(new JLabel("Jumlah:"));
        panelMenuControl.add(spinnerJumlah);
        panelMenuControl.add(new JLabel("Catatan:"));
        panelMenuControl.add(new JScrollPane(inputCatatan));
        
        JButton btnAddToCart = new JButton("Tambah ke Keranjang");
        btnAddToCart.addActionListener(this::addToCart);
        panelMenuControl.add(new JLabel()); 
        panelMenuControl.add(btnAddToCart);
        panelMenu.add(panelMenuControl, BorderLayout.SOUTH);
        add(panelMenu, BorderLayout.WEST);

        // --- PANEL TENGAH: Cart & Detail ---
        JPanel panelCart = new JPanel(new BorderLayout(5, 5));
        panelCart.setBorder(new TitledBorder("Keranjang Pesanan"));
        
        cartArea = new JTextArea();
        cartArea.setEditable(false);
        JScrollPane cartScroll = new JScrollPane(cartArea);
        panelCart.add(cartScroll, BorderLayout.CENTER);
        
        // --- Panel Total & Aksi Cart ---
        JPanel panelTotalAksi = new JPanel(new BorderLayout());
        labelTotal = new JLabel("Total: Rp0,00", SwingConstants.RIGHT);
        labelTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        panelTotalAksi.add(labelTotal, BorderLayout.NORTH);

        JButton btnHapusItem = new JButton("Hapus Item Terakhir");
        btnHapusItem.addActionListener(e -> removeItemFromCart());
        panelTotalAksi.add(btnHapusItem, BorderLayout.CENTER);
        
        panelCart.add(panelTotalAksi, BorderLayout.SOUTH);

        // --- PANEL TIMUR: Order Finalization ---
        JPanel panelFinalisasi = new JPanel(new GridLayout(4, 1, 10, 10));
        panelFinalisasi.setBorder(new TitledBorder("Detail & Finalisasi"));

        // Customer
        comboCustomer = new JComboBox<>();
        comboCustomer.setBorder(new TitledBorder("Pilih Customer"));
        loadCustomerData();
        panelFinalisasi.add(comboCustomer);

        // Meja
        comboMeja = new JComboBox<>();
        comboMeja.setBorder(new TitledBorder("Pilih Meja"));
        loadMejaData();
        panelFinalisasi.add(comboMeja);
        
        JButton btnSimpan = new JButton("SIMPAN PESANAN");
        btnSimpan.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSimpan.setBackground(Color.GREEN.darker());
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.addActionListener(this::simpanPesanan);
        panelFinalisasi.add(btnSimpan);

        add(panelCart, BorderLayout.CENTER);
        add(panelFinalisasi, BorderLayout.EAST);
    }

    private void loadData() {
        // Muat Daftar Menu dari RestaurantSystem
        List<model.MenuItem> daftarMenu = system.getDaftarMenu(); // 🔥 Menggunakan model.MenuItem
        menuListModel.clear();
        for (model.MenuItem item : daftarMenu) { // 🔥 Menggunakan model.MenuItem
            menuListModel.addElement(item);
        }
    }

    private void loadCustomerData() {
        List<Customer> customerList = system.getDaftarCustomer(); 
        comboCustomer.removeAllItems();
        
        for (Customer cust : customerList) {
            comboCustomer.addItem(cust);
        }

        Customer guest = new Customer(0, "Customer Baru (Guest)", "");
        comboCustomer.addItem(guest);

        if (comboCustomer.getItemCount() > 0) {
            selectedCustomer = (Customer) comboCustomer.getItemAt(0);
        }
    }

    private void loadMejaData() {
        comboMeja.removeAllItems();
        
        List<Integer> terisi = new ArrayList<>();
        for (Pesanan p : system.getDaftarPesanan()) {
            if (p.getMeja().getStatus() == Meja.StatusMeja.TERISI || "Menunggu".equalsIgnoreCase(p.getStatus()) || "Diproses".equalsIgnoreCase(p.getStatus())) {
                terisi.add(p.getMeja().getNomor());
            }
        }

        for (int i = 1; i <= JUMLAH_MEJA; i++) {
            if (!terisi.contains(i)) { 
                comboMeja.addItem("Meja " + i);
            }
        }
        
        if (comboMeja.getItemCount() == 0) {
            comboMeja.addItem("TIDAK ADA MEJA KOSONG");
            comboMeja.setEnabled(false);
        } else {
            comboMeja.setEnabled(true);
        }
    }

    // --- Logika Cart ---
    private void addToCart(ActionEvent e) {
        model.MenuItem selectedItem = menuList.getSelectedValue(); // 🔥 Menggunakan model.MenuItem
        if (selectedItem == null) {
            JOptionPane.showMessageDialog(this, "Pilih item menu terlebih dahulu.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (comboMeja.getSelectedItem() == null || comboMeja.getSelectedItem().toString().contains("KOSONG")) {
             JOptionPane.showMessageDialog(this, "Pilih nomor meja terlebih dahulu.", "Error", JOptionPane.ERROR_MESSAGE);
             return;
        }

        int jumlah = (int) spinnerJumlah.getValue();
        String catatan = inputCatatan.getText().trim();

        try {
            DetailPesanan detail = new DetailPesanan(selectedItem, jumlah, catatan);

            if (currentPesanan == null) {
                int nextId = system.generateNextPesananId(); 
                
                String mejaText = (String) comboMeja.getSelectedItem();
                int noMeja = Integer.parseInt(mejaText.replace("Meja ", ""));
                Meja selectedMeja = new Meja(noMeja);
                
                currentPesanan = new Pesanan(nextId, selectedMeja);
            }
            
            currentPesanan.addDetail(detail);
            updateCartDisplay();
            
            spinnerJumlah.setValue(1);
            inputCatatan.setText("");
            menuList.clearSelection();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menambah item: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Kesalahan umum: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeItemFromCart() {
        if (currentPesanan == null || currentPesanan.getDaftarItem().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keranjang sudah kosong.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        List<DetailPesanan> items = currentPesanan.getDaftarItem();
        items.remove(items.size() - 1);
        
        if (items.isEmpty()) {
            currentPesanan = null;
        }
        
        updateCartDisplay();
        JOptionPane.showMessageDialog(this, "Item terakhir berhasil dihapus.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateCartDisplay() {
        if (currentPesanan == null || currentPesanan.getDaftarItem().isEmpty()) {
            cartArea.setText("Keranjang kosong.");
            labelTotal.setText("Total: Rp0,00");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Pesanan ID: ").append(currentPesanan.getIdPesanan()).append(" | Meja: ").append(currentPesanan.getMeja().getNomor()).append("\n");
        sb.append("------------------------------------------\n");
        int counter = 1;
        for (DetailPesanan d : currentPesanan.getDaftarItem()) {
            sb.append(String.format("%d. %s x%d", counter++, d.getItem().getNama(), d.getJumlah()));
            if (!d.getCatatan().isEmpty()) {
                sb.append(" (Catatan: ").append(d.getCatatan()).append(")");
            }
            sb.append("\n");
        }
        
        cartArea.setText(sb.toString());
        
        try {
            double total = currentPesanan.hitungTotal();
            labelTotal.setText("Total: " + IDR_FORMAT.format(total));
        } catch (IllegalStateException e) {
            labelTotal.setText("Total: Rp0,00");
        }
    }
    
    // --- Logika Simpan Pesanan ---
    private void simpanPesanan(ActionEvent e) {
        if (currentPesanan == null || currentPesanan.getDaftarItem().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keranjang masih kosong, tidak ada yang disimpan.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Customer cust = (Customer) comboCustomer.getSelectedItem();
        selectedCustomer = cust;
        
        try {
            system.tambahPesanan(currentPesanan);
            currentPesanan.getMeja().setStatus(Meja.StatusMeja.TERISI);
            
            // Asumsi method ini ada di class Customer
            if(selectedCustomer != null) {
                selectedCustomer.tambahRiwayatPesanan(currentPesanan.getIdPesanan());
            }

            JOptionPane.showMessageDialog(this, 
                "✅ Pesanan #" + currentPesanan.getIdPesanan() + " berhasil dibuat.\n" +
                "Untuk Customer: " + (selectedCustomer != null ? selectedCustomer.getNama() : "Guest") + "\n" +
                "Total: " + IDR_FORMAT.format(currentPesanan.hitungTotal()) + "\n" +
                "Meja " + currentPesanan.getMeja().getNomor() + " diset TERISI.", 
                "Sukses", 
                JOptionPane.INFORMATION_MESSAGE);
            
            dispose(); 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan pesanan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
