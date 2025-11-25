package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.*;
import service.RestaurantSystem;

public class DashboardGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private RestaurantSystem system;
    private String username;
    private String role;

    public DashboardGUI(RestaurantSystem system, String username, String role) {
        this.system = system;
        this.username = username;
        this.role = role;

        setTitle("Dashboard - " + role + " : " + username);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        if (role.equalsIgnoreCase("customer")) {
            mainPanel.add(customerPanel(), "customer");
            cardLayout.show(mainPanel, "customer");
        } else {
            mainPanel.add(menuPegawaiPanel(), "menu"); 
            mainPanel.add(pelayanPanel(), "pelayan");
            mainPanel.add(kokiPanel(), "koki");
            mainPanel.add(kasirPanel(), "kasir");
            cardLayout.show(mainPanel, "menu");
        }

        add(mainPanel);
    }

    // =============================
    // PANEL CUSTOMER (Lihat Menu)
    // =============================
    private JPanel customerPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));

        JLabel welcome = new JLabel("Selamat datang Customer: " + username, SwingConstants.CENTER);

        JButton btnMenu = new JButton("Lihat Menu");
        JButton btnHistory = new JButton("Riwayat Pesanan");
        JButton btnLogout = new JButton("Logout");

        panel.add(welcome);
        panel.add(btnMenu);
        panel.add(btnHistory);
        panel.add(btnLogout);

        // 🔥 PERBAIKAN SINTAKSIS DAN INTEGRASI MENU:
        btnMenu.addActionListener(e -> {
            new MenuGUI(system).setVisible(true); // Memanggil MenuGUI yang menampilkan menu dalam tabel
        }); 
        
        btnHistory.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Silakan lihat riwayat pesanan (Fitur belum dibuat).", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        btnLogout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI(system);
            login.setVisible(true);
        });

        return panel;
    }
    
    // METHOD showMenuForCustomer() LAMA DIHAPUS KARENA SUDAH DIGANTI MenuGUI.java

    // =============================
    // PANEL MENU PEGAWAI (NAVIGASI)
    // =============================
    private JPanel menuPegawaiPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));

        JLabel welcome = new JLabel("Selamat datang Pegawai: " + username, SwingConstants.CENTER);

        JButton btnPelayan = new JButton("Menu Pelayan");
        JButton btnKoki = new JButton("Menu Koki");
        JButton btnKasir = new JButton("Menu Kasir");
        JButton btnLogout = new JButton("Logout");

        panel.add(welcome);
        panel.add(btnPelayan);
        panel.add(btnKoki);
        panel.add(btnKasir);
        panel.add(btnLogout);

        btnPelayan.addActionListener(e -> cardLayout.show(mainPanel, "pelayan"));
        btnKoki.addActionListener(e -> cardLayout.show(mainPanel, "koki"));
        btnKasir.addActionListener(e -> cardLayout.show(mainPanel, "kasir"));

        btnLogout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI(system);
            login.setVisible(true);
        });

        return panel;
    }

    // =============================
    // PANEL PELAYAN (ORDER GUI)
    // =============================
    private JPanel pelayanPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("PANEL PELAYAN", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        JButton btnBuatPesanan = new JButton("Buat Pesanan Baru");
        btnBuatPesanan.setBackground(Color.CYAN.darker());
        btnBuatPesanan.setForeground(Color.WHITE);
        
        JButton btnKembali = new JButton("Kembali ke Menu Utama");

        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(btnBuatPesanan);
        bottom.add(btnKembali);

        panel.add(title, BorderLayout.NORTH);
        panel.add(bottom, BorderLayout.SOUTH);
        panel.add(new JLabel("Aksi: Membuka Order Point-of-Sale (POS).", SwingConstants.CENTER), BorderLayout.CENTER);


        // INTEGRASI: Buka OrderGUI
        btnBuatPesanan.addActionListener(e -> {
            Pegawai pelayanObj = system.findPegawaiByName(username); 
            if(pelayanObj != null) {
                 new OrderGUI(system, pelayanObj).setVisible(true); 
            } else {
                 JOptionPane.showMessageDialog(this, "Error: Objek Pegawai tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnKembali.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        return panel;
    }

    // =============================
    // PANEL KOKI (Placeholder)
    // =============================
    private JPanel kokiPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("PANEL KOKI", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        JButton btnUpdate = new JButton("Update Status Pesanan");
        JButton btnKembali = new JButton("Kembali ke Menu Utama");

        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(btnUpdate);
        bottom.add(btnKembali);

        panel.add(title, BorderLayout.NORTH);
        panel.add(bottom, BorderLayout.SOUTH);
        panel.add(new JLabel("Aksi Koki: Tandai pesanan sebagai 'Selesai'.", SwingConstants.CENTER), BorderLayout.CENTER);


        btnUpdate.addActionListener(e -> JOptionPane.showMessageDialog(this, "Akses Koki Dashboard (Fitur Belum Dibuat)", "Info", JOptionPane.INFORMATION_MESSAGE));
        btnKembali.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        return panel;
    }

    // =============================
    // PANEL KASIR (PAYMENT GUI)
    // =============================
    private JPanel kasirPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("PANEL KASIR", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));

        JButton btnBayar = new JButton("Proses Pembayaran");
        btnBayar.setBackground(Color.ORANGE.darker());
        btnBayar.setForeground(Color.WHITE);

        JButton btnKembali = new JButton("Kembali ke Menu Utama");

        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(btnBayar);
        bottom.add(btnKembali);

        panel.add(title, BorderLayout.NORTH);
        panel.add(bottom, BorderLayout.SOUTH);
        panel.add(new JLabel("Aksi Kasir: Memproses pembayaran pesanan selesai.", SwingConstants.CENTER), BorderLayout.CENTER);


        // INTEGRASI: Buka PaymentGUI
        btnBayar.addActionListener(e -> {
             new PaymentGUI(system).setVisible(true);
        });
        
        btnKembali.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        return panel;
    }
}
