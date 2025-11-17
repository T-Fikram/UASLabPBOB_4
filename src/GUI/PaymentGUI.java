package gui;

import model.*;
import service.RestaurantSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class PaymentGUI extends JFrame {
    private RestaurantSystem system;
    private JComboBox<String> orderBox;
    private JTextArea detailArea;
    private JTextField totalField;
    private JComboBox<String> paymentMethod;

    public PaymentGUI(RestaurantSystem system) {
        this.system = system;

        setTitle("Payment - Kasir");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        loadUI();
    }

    private void loadUI() {
        JPanel panel = new JPanel(new BorderLayout());

        // Top: Pesanan siap bayar
        JPanel top = new JPanel(new GridLayout(2, 1));
        orderBox = new JComboBox<>();
        loadOrders();
        top.add(new JLabel("Pilih Pesanan:"));
        top.add(orderBox);
        panel.add(top, BorderLayout.NORTH);

        // Middle: Detail pesanan
        detailArea = new JTextArea();
        detailArea.setEditable(false);
        panel.add(new JScrollPane(detailArea), BorderLayout.CENTER);

        // Bottom: Payment section
        JPanel bottom = new JPanel(new GridLayout(4, 2));
        bottom.add(new JLabel("Total: "));
        totalField = new JTextField();
        totalField.setEditable(false);
        bottom.add(totalField);

        bottom.add(new JLabel("Metode Pembayaran:"));
        paymentMethod = new JComboBox<>(new String[]{"Cash", "Card", "QRIS"});
        bottom.add(paymentMethod);

        JButton payButton = new JButton("Bayar");
        bottom.add(payButton);

        panel.add(bottom, BorderLayout.SOUTH);

        add(panel);

        orderBox.addActionListener(e -> showOrderDetail());
        payButton.addActionListener(this::processPayment);
    }

    private void loadOrders() {
        for (Pesanan p : system.getDaftarPesanan()) {
            if ("Selesai".equalsIgnoreCase(p.getStatus())) {
                orderBox.addItem(p.getIdPesanan() + " - Meja " + p.getMeja().getNomor());
            }
        }
    }

    private void showOrderDetail() {
        detailArea.setText("");
        totalField.setText("");

        int idx = orderBox.getSelectedIndex();
        if (idx < 0) return;

        String selected = (String) orderBox.getSelectedItem();
        int id = Integer.parseInt(selected.split(" - ")[0]);

        Pesanan ps = system.getDaftarPesanan().stream()
                .filter(p -> p.getIdPesanan() == id)
                .findFirst().orElse(null);
        if (ps == null) return;

        for (DetailPesanan d : ps.getDaftarItem()) {
            detailArea.append(d.getItem().getNama() + " x" + d.getJumlah() + "\n");
        }
        detailArea.append("\nStatus: " + ps.getStatus());

        totalField.setText("Rp" + ps.hitungTotal());
    }

    private void processPayment(ActionEvent e) {
        String selected = (String) orderBox.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Tidak ada pesanan.");
            return;
        }

        int id = Integer.parseInt(selected.split(" - ")[0]);
        Pesanan ps = system.getDaftarPesanan().stream()
                .filter(p -> p.getIdPesanan() == id)
                .findFirst().orElse(null);

        if (ps == null) return;

        // Tentukan metode pembayaran
        Pembayaran metode = switch (paymentMethod.getSelectedIndex()) {
            case 0 -> new CashPayment();
            case 1 -> new CardPayment();
            case 2 -> new QRISPayment();
            default -> null;
        };

        if (metode == null) return;

        boolean success = metode.prosesPembayaran(ps.hitungTotal());
        if (success) {
            ps.getMeja().setStatus(Meja.StatusMeja.KOSONG);
            JOptionPane.showMessageDialog(this, "Pembayaran berhasil! ID Pembayaran: " + metode.getIdPembayaran());
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Pembayaran gagal!");
        }
    }
}
