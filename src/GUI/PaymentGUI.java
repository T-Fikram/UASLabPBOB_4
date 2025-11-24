package GUI;

import javax.swing.*;
import java.awt.*;

public class PaymentGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JComboBox<String> pesananDropdown;
    private JComboBox<String> metodeDropdown;

    private JTextField cashField;
    private JTextField cardNumberField;
    private JTextField cardNameField;

    private JLabel receiptLabel;

    public PaymentReceiptGUI() {
        setTitle("Payment & Receipt");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Panel
        mainPanel.add(selectOrderPanel(), "selectOrder");
        mainPanel.add(paymentPanel(), "payment");
        mainPanel.add(receiptPanel(), "receipt");

        add(mainPanel);
        setVisible(true);
    }

    private JPanel selectOrderPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JLabel title = new JLabel("PILIH PESANAN", SwingConstants.CENTER);

        pesananDropdown = new JComboBox<>();
        pesananDropdown.addItem("Pesanan #1 - Rp50000");
        pesananDropdown.addItem("Pesanan #2 - Rp32000");
        pesananDropdown.addItem("Pesanan #3 - Rp45000");

        JButton btnNext = new JButton("Lanjut ke Pembayaran");
        JButton btnExit = new JButton("Kembali");

        panel.add(title);
        panel.add(pesananDropdown);
        panel.add(btnNext);
        panel.add(btnExit);

        btnNext.addActionListener(e -> cardLayout.show(mainPanel, "payment"));
        btnExit.addActionListener(e -> dispose());

        return panel;
    }

    private JPanel paymentPanel() {
        JPanel panel = new JPanel(new GridLayout(10, 1, 5, 5));

        JLabel title = new JLabel("METODE PEMBAYARAN", SwingConstants.CENTER);

        metodeDropdown = new JComboBox<>();
        metodeDropdown.addItem("Cash");
        metodeDropdown.addItem("Card");
        metodeDropdown.addItem("QRIS");

        cashField = new JTextField();
        cardNumberField = new JTextField();
        cardNameField = new JTextField();

        JButton btnPay = new JButton("Bayar");
        JButton btnBack = new JButton("Kembali");

        panel.add(title);
        panel.add(new JLabel("Metode:"));
        panel.add(metodeDropdown);
        panel.add(new JLabel("Jumlah Cash (jika Cash):"));
        panel.add(cashField);
        panel.add(new JLabel("Nomor Kartu (jika Card):"));
        panel.add(cardNumberField);
        panel.add(new JLabel("Nama Pemilik (jika Card):"));
        panel.add(cardNameField);
        panel.add(btnPay);
        panel.add(btnBack);

        btnPay.addActionListener(e -> processPayment());
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "selectOrder"));

        return panel;
    }

    private JPanel receiptPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("STRUK PEMBAYARAN", SwingConstants.CENTER);

        receiptLabel = new JLabel("", SwingConstants.CENTER);

        JButton btnFinish = new JButton("Selesai");

        panel.add(title, BorderLayout.NORTH);
        panel.add(receiptLabel, BorderLayout.CENTER);
        panel.add(btnFinish, BorderLayout.SOUTH);

        btnFinish.addActionListener(e -> dispose());

        return panel;
    }

    private void processPayment() {
        String metode = (String) metodeDropdown.getSelectedItem();
        String pesanan = (String) pesananDropdown.getSelectedItem();

        if (metode.equals("Cash")) {
            if (cashField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Masukkan jumlah uang!");
                return;
            }
            JOptionPane.showMessageDialog(this, "✅ Pembayaran Cash Berhasil!");

        } else if (metode.equals("Card")) {
            if (cardNumberField.getText().length() < 10) {
                JOptionPane.showMessageDialog(this, "⚠️ Nomor kartu minimal 10 digit!");
                return;
            }
            if (cardNameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Nama pemilik wajib diisi!");
                return;
            }
            JOptionPane.showMessageDialog(this, "✅ Pembayaran Card Berhasil!");

        } else if (metode.equals("QRIS")) {
            JOptionPane.showMessageDialog(this, "✅ QRIS Terscan & Berhasil!");
        }

        // Tampilkan struk
        receiptLabel.setText("<html>"
                + "Pesanan: " + pesanan + "<br>"
                + "Metode: " + metode + "<br>"
                + "Status: Lunas ✅"
                + "</html>");

        cardLayout.show(mainPanel, "receipt");
    }

    public static void main(String[] args) {
        new PaymentReceiptGUI();
    }
}
