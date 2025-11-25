package GUI;

import javax.swing.*;
import java.awt.*;

import service.RestaurantSystem;
import service.Pembayaran;
import service.CashPayment;
import service.CardPayment;
import service.QRISPayment;

import model.Pegawai;
import model.Pesanan;
import model.Meja;
import model.Transaksi;
import model.Struk;

public class KasirGUI extends JFrame {

    public KasirGUI(RestaurantSystem system, Pegawai kasir) {

        setTitle("Kasir - " + kasir.getNama());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);

        JButton bayarBtn = new JButton("Proses Pembayaran");

        refresh(system, model);

        add(new JScrollPane(list), BorderLayout.CENTER);
        add(bayarBtn, BorderLayout.SOUTH);

        bayarBtn.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx == -1) {
                JOptionPane.showMessageDialog(this, "Pilih pesanan dulu!");
                return;
            }

            Pesanan ps = system.getDaftarPesanan().get(idx);

            if (!"Selesai".equalsIgnoreCase(ps.getStatus())) {
                JOptionPane.showMessageDialog(this, 
                    "Pesanan belum selesai dimasak!");
                return;
            }

            // mencegah pembayaran ulang
            if (ps.getMeja().getStatus() == Meja.StatusMeja.KOSONG) {
                JOptionPane.showMessageDialog(this, 
                    "Pesanan ini sudah dibayar.");
                return;
            }

            double total = ps.hitungTotal();   // FIX: gunakan double

            String[] options = {"Cash", "Card", "QRIS", "Batal"};
            int pilih = JOptionPane.showOptionDialog(
                    this,
                    "Total: Rp " + total + "\nPilih metode pembayaran:",
                    "Metode Pembayaran",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            Pembayaran metode = null;
            String metodeString = "";

            if (pilih == 0) {
                metode = new CashPayment();
                metodeString = "cash";
            } else if (pilih == 1) {
                metode = new CardPayment();
                metodeString = "card";
            } else if (pilih == 2) {
                metode = new QRISPayment();
                metodeString = "qris";
            } else {
                return;
            }

            boolean sukses = false;

            try {
                // ⚠ PEMBAYARAN KAMU MENGGUNAKAN SCANNER (CLI)
                // Jadi harus tetap dijalankan di backend console.
                sukses = metode.prosesPembayaran(total);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Terjadi error: " + ex.getMessage());
            }

            if (sukses) {
                Transaksi trx = new Transaksi(ps, metodeString);

                Struk struk = new Struk(trx);
                struk.cetak();  // print ke console (sesuai Main.java versi CLI)

                ps.getMeja().setStatus(Meja.StatusMeja.KOSONG);

                JOptionPane.showMessageDialog(this,
                        "Pembayaran sukses!\nID Pembayaran: " + metode.getIdPembayaran());

                refresh(system, model);
            } else {
                JOptionPane.showMessageDialog(this, "Pembayaran gagal.");
            }
        });
    }

    private void refresh(RestaurantSystem system, DefaultListModel<String> model) {
        model.clear();

        for (Pesanan p : system.getDaftarPesanan()) {
            model.addElement(
                    "ID: " + p.getIdPesanan() +
                    " | Meja: " + p.getMeja().getNomor() +
                    " | Status: " + p.getStatus() +
                    " | Total: Rp" + p.hitungTotal()
            );
        }
    }
}
