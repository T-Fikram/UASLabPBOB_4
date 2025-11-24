package GUI;

import java.awt.*; // Untuk Pesanan, Meja, DetailPesanan, dll.
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.TitledBorder; // Transaksi & Struk berada di model
import model.*;     // Transaksi & Struk berada di model
import service.CardPayment;
import service.CashPayment;
import service.Pembayaran;
import service.QRISPayment;
import service.RestaurantSystem;

public class PaymentGUI extends JFrame {
    private RestaurantSystem system;
    private Pesanan selectedPesanan;

    // Komponen GUI
    private JList<Pesanan> listPesananSiapBayar;
    private DefaultListModel<Pesanan> listModel;
    private JTextArea detailPesananArea;
    private JComboBox<String> comboMetodeBayar;
    private JLabel labelTotalBayar;
    private JButton btnProsesBayar;

    // Formatter untuk mata uang
    private static final NumberFormat IDR_FORMAT = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));

    public PaymentGUI(RestaurantSystem system) {
        this.system = system;
        this.selectedPesanan = null;

        setTitle("Pembayaran - Kasir POS");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        initComponents();
        loadPesananData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // --- PANEL KIRI: Daftar Pesanan Siap Bayar ---
        JPanel panelList = new JPanel(new BorderLayout());
        panelList.setBorder(new TitledBorder("Pesanan Siap Dibayar (Status: Selesai)"));
        
        listModel = new DefaultListModel<>();
        listPesananSiapBayar = new JList<>(listModel);
        listPesananSiapBayar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        listPesananSiapBayar.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedPesanan = listPesananSiapBayar.getSelectedValue();
                updateDetailDisplay();
            }
        });

        panelList.add(new JScrollPane(listPesananSiapBayar), BorderLayout.CENTER);
        add(panelList, BorderLayout.WEST);

        // --- PANEL KANAN: Detail dan Proses Pembayaran ---
        JPanel panelKanan = new JPanel(new BorderLayout(5, 5));
        
        // Detail Pesanan
        detailPesananArea = new JTextArea();
        detailPesananArea.setEditable(false);
        detailPesananArea.setBorder(new TitledBorder("Detail Pesanan"));
        panelKanan.add(new JScrollPane(detailPesananArea), BorderLayout.CENTER);

        // Panel Pembayaran
        JPanel panelBayar = new JPanel(new GridLayout(3, 1, 5, 5));
        panelBayar.setBorder(new TitledBorder("Proses Pembayaran"));

        labelTotalBayar = new JLabel("Total: Rp0,00", SwingConstants.CENTER);
        labelTotalBayar.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        comboMetodeBayar = new JComboBox<>(new String[]{"Cash", "Card", "QRIS"});
        
        btnProsesBayar = new JButton("PROSES PEMBAYARAN");
        btnProsesBayar.addActionListener(this::prosesPembayaran);
        btnProsesBayar.setEnabled(false);

        panelBayar.add(labelTotalBayar);
        panelBayar.add(comboMetodeBayar);
        panelBayar.add(btnProsesBayar);
        
        panelKanan.add(panelBayar, BorderLayout.SOUTH);
        add(panelKanan, BorderLayout.CENTER);
    }

    // --- Load Data Pesanan ---
    private void loadPesananData() {
        listModel.clear();
        // Menggunakan system.getPesananSiapDibayar yang sudah kita perbaiki
        List<Pesanan> daftarSiapBayar = system.getPesananSiapDibayar(); 
        for (Pesanan p : daftarSiapBayar) {
            listModel.addElement(p);
        }
        updateDetailDisplay();
    }
    
    // --- Tampilkan Detail Pesanan ---
    private void updateDetailDisplay() {
        if (selectedPesanan == null) {
            detailPesananArea.setText("Pilih pesanan di sebelah kiri.");
            labelTotalBayar.setText("Total: Rp0,00");
            btnProsesBayar.setEnabled(false);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("ID Pesanan: #").append(selectedPesanan.getIdPesanan()).append("\n");
        sb.append("Meja      : ").append(selectedPesanan.getMeja().getNomor()).append("\n");
        sb.append("Status    : ").append(selectedPesanan.getStatus()).append("\n");
        sb.append("----------------------------------------\n");
        
        double total = 0;
        try {
            for (DetailPesanan d : selectedPesanan.getDaftarItem()) {
                double subTotal = d.getItem().getHarga() * d.getJumlah();
                sb.append(String.format("%s (x%d) -> %s\n", 
                    d.getItem().getNama(), 
                    d.getJumlah(), 
                    IDR_FORMAT.format(subTotal)));
                if (!d.getCatatan().isEmpty()) {
                    sb.append("   Catatan: ").append(d.getCatatan()).append("\n");
                }
            }
            total = selectedPesanan.hitungTotal();
        } catch (IllegalStateException e) {
            sb.append("Error: Pesanan ini kosong atau data tidak valid.\n");
        }
        
        labelTotalBayar.setText("TOTAL: " + IDR_FORMAT.format(total));
        detailPesananArea.setText(sb.toString());
        btnProsesBayar.setEnabled(true);
    }

    // --- Proses Pembayaran ---
    private void prosesPembayaran(ActionEvent e) {
        if (selectedPesanan == null) {
            JOptionPane.showMessageDialog(this, "Pilih pesanan yang akan dibayar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String metodeString = (String) comboMetodeBayar.getSelectedItem();
        double total = 0;
        
        try {
            total = selectedPesanan.hitungTotal();
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, "Pesanan kosong atau tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        if ("Cash".equals(metodeString)) {
            // Logika Pembayaran Tunai
            String input = JOptionPane.showInputDialog(this, 
                "Total yang harus dibayar: " + IDR_FORMAT.format(total) + "\nMasukkan Jumlah Tunai:", 
                "Pembayaran Tunai", JOptionPane.QUESTION_MESSAGE);
            
            if (input == null) return; 
            
            try {
                double jumlahBayar = Double.parseDouble(input.replaceAll("[^\\d,.]", "").replace(",", ".").trim());

                if (jumlahBayar < total) {
                    JOptionPane.showMessageDialog(this, "Jumlah tunai kurang dari total pembayaran!", "Gagal", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Pembayaran metodeBayar = new CashPayment();
                boolean sukses = metodeBayar.prosesPembayaran(total); 

                if (sukses) {
                    double kembalian = jumlahBayar - total;
                    handleSuccessfulPayment(metodeBayar, metodeString, kembalian);
                } else {
                     JOptionPane.showMessageDialog(this, "❌ Pembayaran Cash Gagal.", "Gagal", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Input tunai tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(this, "Error saat proses Cash: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } else {
            // Logika Pembayaran Non-Tunai (Card/QRIS)
            Pembayaran metodeBayar;
            
            if ("Card".equals(metodeString)) {
                metodeBayar = new CardPayment();
            } else if ("QRIS".equals(metodeString)) {
                metodeBayar = new QRISPayment();
            } else {
                JOptionPane.showMessageDialog(this, "Metode pembayaran tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                boolean sukses = metodeBayar.prosesPembayaran(total); 

                if (sukses) {
                    handleSuccessfulPayment(metodeBayar, metodeString, 0); 
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Pembayaran Non-Tunai Gagal.", "Gagal", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saat proses: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Metode Helper untuk Transaksi Sukses
    private void handleSuccessfulPayment(Pembayaran metode, String metodeString, double kembalian) throws Exception {
        Transaksi trx = new Transaksi(selectedPesanan, metodeString);
        Struk struk = new Struk(trx);
        struk.cetak();

        selectedPesanan.getMeja().setStatus(Meja.StatusMeja.KOSONG);

        String message = "✅ Pembayaran sukses!\n" +
                         "ID Transaksi: " + metode.getIdPembayaran() + "\n" +
                         "Meja " + selectedPesanan.getMeja().getNomor() + " sekarang KOSONG.";
        
        if (kembalian > 0) {
            message += "\nKembalian: " + IDR_FORMAT.format(kembalian);
        }
        
        JOptionPane.showMessageDialog(this, message, "Sukses", JOptionPane.INFORMATION_MESSAGE);

        selectedPesanan = null; 
        loadPesananData();
    }
}
