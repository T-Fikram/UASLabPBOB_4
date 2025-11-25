package GUI;

import javax.swing.*;
import service.RestaurantSystem;
import java.awt.*;

public class LoginGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private RestaurantSystem system;

    public LoginGUI(RestaurantSystem system) {
        this.system = system;

        setTitle("Login / Sign Up");
        setSize(420, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(loginChoicePanel(), "choice");
        mainPanel.add(loginCustomerPanel(), "loginCustomer");
        mainPanel.add(loginPegawaiPanel(), "loginPegawai");
        mainPanel.add(signUpPanel(), "signup");
        mainPanel.add(signUpPegawaiPanel(), "signupPegawai");   // 🔥 PANEL BARU

        add(mainPanel);
        cardLayout.show(mainPanel, "choice");
        setVisible(true);
    }

    // ================================
    // PANEL PILIH LOGIN
    // ================================
    private JPanel loginChoicePanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        JLabel title = new JLabel("PILIH JENIS LOGIN", SwingConstants.CENTER);
        JButton btnCustomer = new JButton("Login Customer");
        JButton btnPegawai = new JButton("Login Pegawai");
        JButton btnSignup = new JButton("Sign Up Customer");
        JButton btnSignupPegawai = new JButton("Sign Up Pegawai"); // 🔥 BARU

        panel.add(title);
        panel.add(btnCustomer);
        panel.add(btnPegawai);
        panel.add(btnSignup);
        panel.add(btnSignupPegawai);

        btnCustomer.addActionListener(e -> cardLayout.show(mainPanel, "loginCustomer"));
        btnPegawai.addActionListener(e -> cardLayout.show(mainPanel, "loginPegawai"));
        btnSignup.addActionListener(e -> cardLayout.show(mainPanel, "signup"));
        btnSignupPegawai.addActionListener(e -> cardLayout.show(mainPanel, "signupPegawai")); // 🔥 BARU

        return panel;
    }

    // ================================
    // LOGIN CUSTOMER
    // ================================
    private JPanel loginCustomerPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 1, 5, 5));

        JLabel title = new JLabel("LOGIN CUSTOMER", SwingConstants.CENTER);

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton back = new JButton("Kembali");

        panel.add(title);
        panel.add(new JLabel("Username:", SwingConstants.CENTER));
        panel.add(usernameField);
        panel.add(new JLabel("Password:", SwingConstants.CENTER));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(back);

        loginButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            boolean success = system.login(user, pass);

            if (!success) {
                JOptionPane.showMessageDialog(this, "❌ Username atau password salah!");
                return;
            }

            JOptionPane.showMessageDialog(this, "✅ Login Customer Berhasil!");
            DashboardGUI dash = new DashboardGUI(system, user, "customer");
            dash.setVisible(true);
            dispose();
        });

        back.addActionListener(e -> cardLayout.show(mainPanel, "choice"));

        return panel;
    }

    // ================================
    // LOGIN PEGAWAI
    // ================================
    private JPanel loginPegawaiPanel() {
        JPanel panel = new JPanel(new GridLayout(9, 1, 5, 5));

        JLabel title = new JLabel("LOGIN PEGAWAI", SwingConstants.CENTER);

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JLabel roleLabel = new JLabel("Pilih Role Pegawai:", SwingConstants.CENTER);
        String[] roles = { "pelayan", "koki", "kasir" };
        JComboBox<String> roleBox = new JComboBox<>(roles);

        JButton loginButton = new JButton("Login");
        JButton back = new JButton("Kembali");

        panel.add(title);
        panel.add(new JLabel("Username:", SwingConstants.CENTER));
        panel.add(usernameField);
        panel.add(new JLabel("Password:", SwingConstants.CENTER));
        panel.add(passwordField);
        panel.add(roleLabel);
        panel.add(roleBox);
        panel.add(loginButton);
        panel.add(back);

        loginButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            boolean success = system.login(user, pass);

            if (!success) {
                JOptionPane.showMessageDialog(this, "❌ Login pegawai gagal!");
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "✅ Login Pegawai Berhasil sebagai " + role);

            DashboardGUI dash = new DashboardGUI(system, user, role);
            dash.setVisible(true);
            dispose();
        });

        back.addActionListener(e -> cardLayout.show(mainPanel, "choice"));

        return panel;
    }

    // ================================
    // SIGN UP CUSTOMER
    // ================================
    private JPanel signUpPanel() {
        JPanel panel = new JPanel(new GridLayout(9, 1, 5, 5));

        JLabel title = new JLabel("SIGN UP CUSTOMER", SwingConstants.CENTER);

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmField = new JPasswordField();

        JButton signUpButton = new JButton("Daftar");
        JButton back = new JButton("Kembali");

        panel.add(title);
        panel.add(new JLabel("Username:", SwingConstants.CENTER));
        panel.add(usernameField);
        panel.add(new JLabel("Password:", SwingConstants.CENTER));
        panel.add(passwordField);
        panel.add(new JLabel("Konfirmasi Password:", SwingConstants.CENTER));
        panel.add(confirmField);
        panel.add(signUpButton);
        panel.add(back);

        signUpButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            String confirm = new String(confirmField.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Username dan password wajib diisi!");
                return;
            }

            if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "❌ Konfirmasi password tidak cocok!");
                return;
            }

            boolean registered = system.register(user, pass);

            if (!registered) {
                JOptionPane.showMessageDialog(this, "⚠️ Username sudah digunakan!");
                return;
            }

            JOptionPane.showMessageDialog(this, "✅ Akun Customer berhasil dibuat!");
            cardLayout.show(mainPanel, "choice");
        });

        back.addActionListener(e -> cardLayout.show(mainPanel, "choice"));

        return panel;
    }

    // ================================
    // SIGN UP PEGAWAI (BARU)
    // ================================
    private JPanel signUpPegawaiPanel() {
        JPanel panel = new JPanel(new GridLayout(11, 1, 5, 5));

        JLabel title = new JLabel("SIGN UP PEGAWAI", SwingConstants.CENTER);

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmField = new JPasswordField();

        JLabel roleLabel = new JLabel("Pilih Role Pegawai:", SwingConstants.CENTER);
        String[] roles = { "pelayan", "koki", "kasir" };
        JComboBox<String> roleBox = new JComboBox<>(roles);

        JButton signUpButton = new JButton("Daftar Pegawai");
        JButton back = new JButton("Kembali");

        panel.add(title);
        panel.add(new JLabel("Username:", SwingConstants.CENTER));
        panel.add(usernameField);
        panel.add(new JLabel("Password:", SwingConstants.CENTER));
        panel.add(passwordField);
        panel.add(new JLabel("Konfirmasi Password:", SwingConstants.CENTER));
        panel.add(confirmField);
        panel.add(roleLabel);
        panel.add(roleBox);
        panel.add(signUpButton);
        panel.add(back);

        signUpButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            String confirm = new String(confirmField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Username dan password wajib diisi!");
                return;
            }

            if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "❌ Konfirmasi password tidak cocok!");
                return;
            }

            boolean registered = system.register(user, pass);  // 🔥 REGISTER PEGAWAI

            if (!registered) {
                JOptionPane.showMessageDialog(this, "⚠️ Username sudah digunakan!");
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "✅ Akun Pegawai berhasil dibuat sebagai " + role + "!");
            cardLayout.show(mainPanel, "choice");
        });

        back.addActionListener(e -> cardLayout.show(mainPanel, "choice"));

        return panel;
    }
}
