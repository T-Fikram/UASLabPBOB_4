package gui;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MenuGUI extends JFrame {
    private JComboBox<MenuItem> cmbMenu;
    private JTextField txtJumlah;
    private JTextField txtCatatan;
    private DefaultListModel<String> listModel;
    private JList<String> listKeranjang;
    private JButton btnTambah;
    private JButton btnLanjut;

    private Pesanan pesanan;

    public MenuGUI(Pesanan pesanan, List<MenuItem> daftarMenu) {
        super("Menu Makanan & Minuman");
        this.pesanan = pesanan;

        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel Input menu
        JPanel panelInput = new JPanel(new GridLayout(4, 2, 5, 5));
        panelInput.setBorder(BorderFactory.createTitledBorder("Tambah ke Pesanan"));

        panelInput.add(new JLabel("Pilih Menu:"));
        cmbMenu = new JComboBox<>(daftarMenu.toArray(new MenuItem[0]));
        panelInput.add(cmbMenu);

        panelInput.add(new JLabel("Jumlah:"));
        txtJumlah = new JTextField();
        panelInput.add(txtJumlah);

        panelInput.add(new JLabel("Catatan (opsional):"));
        txtCatatan = new JTextField();
        panelInput.add(txtCatatan);

        btnTambah = new JButton("Tambah");
        panelInput.add(btnTambah);

        add(panelInput, BorderLayout.NORTH);

        // Panel List Keranjang
        listModel = new DefaultListModel<>();
        listKeranjang = new JList<>(listModel);
        add(new JScrollPane(listKeranjang), BorderLayout.CENTER);

        // Panel Tombol Lanjut
        btnLanjut = new JButton("Lanjut ke Pemesanan");
        JPanel panelButton = new JPanel();
        panelButton.add(btnLanjut);
        add(panelButton, BorderLayout.SOUTH);

        // Event Listener tombol tambah
        btnTambah.addActionListener((ActionEvent e) -> {
            try {
                MenuItem item = (MenuItem) cmbMenu.getSelectedItem();
                int jumlah = Integer.parseInt(txtJumlah.getText().trim());
                String catatan = txtCatatan.getText().trim();

                DetailPesanan detail = new DetailPesanan(item, jumlah, catatan);
                pesanan.addDetail(detail);

                listModel.addElement(detail.toString());
                txtJumlah.setText("");
                txtCatatan.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Jumlah harus berupa angka!", "Error Input", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Lanjut ke GUI berikut (OrderGUI)
        btnLanjut.addActionListener((ActionEvent e) -> {
            if (pesanan.getDaftarItem().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Keranjang masih kosong!", "Tidak ada pesanan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            new OrderGUI(pesanan).setVisible(true);
            dispose();
        });
    }
}
