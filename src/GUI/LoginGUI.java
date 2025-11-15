package gui;

import model.Akun;
import model.Customer;
import model.Pegawai;
import model.Admin;
import service.LoginService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JTextField txtNama;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private LoginService loginService;

    public LoginGUI() {
        super("Sistem Login");
        loginService = new LoginService(); // Inisialisasi LoginService

        // Setup GUI
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel form
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("Nama:"));
        txtNama = new JTextField();
        panel.add(txtNama);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        panel.add(new JLabel());
        btnLogin = new JButton("Login");
        panel.add(btnLogin);

        add(panel, BorderLayout.CENTER);

        // Action listener untuk tombol login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nama = txtNama.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                try {
                    Akun akun = loginService.login(nama, password);
                    if (akun instanceof Customer) {
                        JOptionPane.showMessageDialog(LoginGUI.this,
                                "Selamat datang Customer: " + akun.getNama(),
                                "Login Berhasil", JOptionPane.INFORMATION_MESSAGE);
                        // TODO: tampilkan GUI khusus customer
                    } else if (akun instanceof Pegawai) {
                        Pegawai pegawai = (Pegawai) akun;
                        JOptionPane.showMessageDialog(LoginGUI.this,
                                "Selamat datang " + pegawai.getPeran() + ": " + pegawai.getNama(),
                                "Login Berhasil", JOptionPane.INFORMATION_MESSAGE);
                        // TODO: tampilkan GUI khusus pegawai
                    } else if (akun instanceof Admin) {
                        JOptionPane.showMessageDialog(LoginGUI.this,
                                "Selamat datang Admin: " + akun.getNama(),
                                "Login Berhasil", JOptionPane.INFORMATION_MESSAGE);
                        // TODO: tampilkan GUI khusus admin
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(LoginGUI.this,
                            ex.getMessage(), "Login Gagal", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }
}
