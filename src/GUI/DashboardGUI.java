package GUI;

import javax.swing.*;
import java.awt.*;
import service.RestaurantSystem;

public class DashboardGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private RestaurantSystem system;
    private String username;
    private String role;

    // ⬅️ Tambah "role"
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

        // Panel Customer (baru)
        mainPanel.add(customerPanel(), "customer");

        // Panel Pegawai
        mainPanel.add(menuPanel(), "menu");
        mainPanel.add(pelayanPanel(), "pelayan");
        mainPanel.add(kokiPanel(), "koki");
        mainPanel.add(kasirPanel(), "kasir");

        add(mainPanel);

        // Arahkan langsung sesuai role
        if (role.equalsIgnoreCase("customer")) {
            cardLayout.show(mainPanel, "customer");
        } else {
            cardLayout.show(mainPanel, "menu");
        }

        setVisible(true);
    }

    // =============================
    // PANEL CUSTOMER (BARU)
    // =============================
    private JPanel customerPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JLabel welcome = new JLabel("Selamat datang Customer: " + username, SwingConstants.CENTER);

        JButton btnMenu = new JButton("Lihat Menu");
        JButton btnHistory = new JButton("Riwayat Pesanan");
        JButton btnLogout = new JButton("Logout");

        panel.add(welcome);
        panel.add(btnMenu);
        panel.add(btnHistory);
        panel.add(btnLogout);

        btnMenu.addActionListener(e -> JOptionPane.showMessageDialog(this, "(Nanti buka menu makanan)"));
        btnHistory.addActionListener(e -> JOptionPane.showMessageDialog(this, "(Nanti buka riwayat)"));

        btnLogout.addActionListener(e -> {
            dispose();
            new LoginGUI(system);
        });

        return panel;
    }

    // =============================
    // PANEL MENU PEGAWAI
    // =============================
    private JPanel menuPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

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
            new LoginGUI(system);
        });

        return panel;
    }

    // PANEL PELAYAN
    private JPanel pelayanPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("PANEL PELAYAN", SwingConstants.CENTER);

        JButton btnBuatPesanan = new JButton("Buat Pesanan Baru");
        JButton btnKembali = new JButton("Kembali");

        JPanel bottom = new JPanel();
        bottom.add(btnBuatPesanan);
        bottom.add(btnKembali);

        panel.add(title, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        btnBuatPesanan.addActionListener(e -> JOptionPane.showMessageDialog(this, "👉 (Order GUI nanti)"));
        btnKembali.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        return panel;
    }

    // PANEL KOKI
    private JPanel kokiPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("PANEL KOKI", SwingConstants.CENTER);
        JButton btnUpdate = new JButton("Update Status Pesanan");
        JButton btnKembali = new JButton("Kembali");

        JPanel bottom = new JPanel();
        bottom.add(btnUpdate);
        bottom.add(btnKembali);

        panel.add(title, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        btnUpdate.addActionListener(e -> JOptionPane.showMessageDialog(this, "👉 (Update status nanti)"));
        btnKembali.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        return panel;
    }

    // PANEL KASIR
    private JPanel kasirPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("PANEL KASIR", SwingConstants.CENTER);
        JButton btnBayar = new JButton("Proses Pembayaran");
        JButton btnKembali = new JButton("Kembali");

        JPanel bottom = new JPanel();
        bottom.add(btnBayar);
        bottom.add(btnKembali);

        panel.add(title, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        btnBayar.addActionListener(e -> JOptionPane.showMessageDialog(this, "👉 (Payment GUI nanti)"));
        btnKembali.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        return panel;
    }
}
