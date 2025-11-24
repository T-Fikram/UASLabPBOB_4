package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CustomerGUI extends JFrame {

    private DefaultListModel<String> menuModel;
    private DefaultListModel<String> cartModel;

    public CustomerGUI() {
        setTitle("Customer Menu");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // LIST MENU DAN PESANAN
        menuModel = new DefaultListModel<>();
        cartModel = new DefaultListModel<>();

        // Dummy Menu (sementara)
        menuModel.addElement("Nasi Goreng - Rp20000");
        menuModel.addElement("Mie Aceh - Rp25000");
        menuModel.addElement("Teh Manis - Rp5000");

        add(mainPanel());
        setVisible(true);
    }

    private JPanel mainPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("MENU CUSTOMER", SwingConstants.CENTER);
        panel.add(title, BorderLayout.NORTH);

        // Panel List Menu & Keranjang
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));

        JList<String> menuList = new JList<>(menuModel);
        JList<String> cartList = new JList<>(cartModel);

        centerPanel.add(new JScrollPane(menuList));
        centerPanel.add(new JScrollPane(cartList));

        panel.add(centerPanel, BorderLayout.CENTER);

        // Tombol aksi
        JPanel buttonPanel = new JPanel();

        JButton btnAdd = new JButton("Tambah ke Pesanan");
        JButton btnSubmit = new JButton("Submit Pesanan");
        JButton btnClear = new JButton("Hapus Semua");
        JButton btnExit = new JButton("Keluar");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExit);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // ACTION LISTENER
        btnAdd.addActionListener(e -> {
            String selected = menuList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "⚠️ Pilih menu terlebih dahulu!");
            } else {
                cartModel.addElement(selected);
                JOptionPane.showMessageDialog(this, "✅ Ditambahkan ke pesanan!");
            }
        });

        btnSubmit.addActionListener(e -> {
            if (cartModel.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Keranjang masih kosong!");
                return;
            }

            // Ambil pesanan
            ArrayList<String> pesanan = new ArrayList<>();
            for (int i = 0; i < cartModel.size(); i++) {
                pesanan.add(cartModel.get(i));
            }

            JOptionPane.showMessageDialog(this,
                    "✅ Pesanan berhasil dikirim!\nTotal item: " + pesanan.size());

            cartModel.clear();
        });

        btnClear.addActionListener(e -> {
            cartModel.clear();
            JOptionPane.showMessageDialog(this, "🗑️ Keranjang dikosongkan.");
        });

        btnExit.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(this, "👋 Terima kasih!");
        });

        return panel;
    }

    // Testing manual
    public static void main(String[] args) {
        new CustomerGUI();
    }
}
