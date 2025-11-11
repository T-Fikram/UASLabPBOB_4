package model;

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
        daftarMenu.add(item);
    }

    public void lihatMenu() {
        System.out.println("=== DAFTAR MENU ===");
        for (int i = 0; i < daftarMenu.size(); i++) {
            System.out.println((i + 1) + ". " + daftarMenu.get(i));
        }
    }

    public void tambahPegawai(Pegawai p) {
        daftarPegawai.add(p);
    }

    public void tambahPesanan(Pesanan p) {
        daftarPesanan.add(p);
    }

    public void prosesPembayaran(Pesanan pesanan, Pembayaran metode) {
        try {
            double total = pesanan.hitungTotal();
            System.out.printf("Total yang harus dibayar: Rp%.0f\n", total);
            boolean sukses = metode.prosesPembayaran(total);

            if (sukses) {
                Transaksi transaksi = new Transaksi(pesanan, metode.toString());
                Struk struk = new Struk(transaksi);
                struk.cetak();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
