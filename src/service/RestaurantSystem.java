package service;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantSystem {
    private List<MenuItem> daftarMenu;
    private List<Pegawai> daftarPegawai;
    private List<Pesanan> daftarPesanan;

    public RestaurantSystem() {
        daftarMenu = new ArrayList<>();
        daftarPegawai = new ArrayList<>();
        daftarPesanan = new ArrayList<>();
    }

    public void tambahMenu(MenuItem item) {
        if (item == null) {
            System.out.println("❌ Menu tidak boleh null!");
            return;
        }
        daftarMenu.add(item);
    }

    public void lihatMenu() {
        System.out.println("=== DAFTAR MENU ===");
        if (daftarMenu.isEmpty()) {
            System.out.println("(Belum ada menu)");
            return;
        }
        for (int i = 0; i < daftarMenu.size(); i++) {
            System.out.println((i + 1) + ". " + daftarMenu.get(i));
        }
    }

    public void tambahPegawai(Pegawai p) {
        if (p == null) {
            System.out.println("❌ Pegawai tidak boleh null!");
            return;
        }
        daftarPegawai.add(p);
    }

    public void tambahPesanan(Pesanan p) {
        if (p == null) {
            System.out.println("❌ Pesanan tidak boleh null!");
            return;
        }
        daftarPesanan.add(p);
    }

    public void prosesPembayaran(Pesanan pesanan, Pembayaran metode) {
        try {
            if (pesanan == null) throw new Exception("Pesanan tidak ditemukan!");
            if (metode == null) throw new Exception("Metode pembayaran belum dipilih!");

            double total = pesanan.hitungTotal();
            if (total <= 0) throw new Exception("Total pesanan tidak valid!");

            System.out.printf("Total yang harus dibayar: Rp%.0f\n", total);
            boolean sukses = metode.prosesPembayaran(total);

            if (sukses) {
                System.out.println("ID Pembayaran: " + metode.getIdPembayaran());
                Transaksi transaksi = new Transaksi(pesanan, metode.toString());
                Struk struk = new Struk(transaksi);
                struk.cetak();
            } else {
                System.out.println("❌ Pembayaran gagal. Transaksi dibatalkan.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }

    // Getter biar bisa diakses dari main
    public List<MenuItem> getDaftarMenu() {
        return new ArrayList<>(daftarMenu);
    }

    public List<Pesanan> getDaftarPesanan() {
        return new ArrayList<>(daftarPesanan);
    }
}
