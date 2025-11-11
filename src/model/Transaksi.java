package model;

import java.time.LocalDateTime;

public class Transaksi {
    private Pesanan pesanan;
    private double total;
    private String metodePembayaran;
    private LocalDateTime waktu;

    public Transaksi(Pesanan pesanan, String metodePembayaran) {
        this.pesanan = pesanan;
        this.metodePembayaran = metodePembayaran;
        this.waktu = LocalDateTime.now();
        this.total = hitungTotal(); // otomatis hitung total saat transaksi dibuat
    }

    public Pesanan getPesanan() {
        return pesanan;
    }

    public double getTotal() {
        return total;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public LocalDateTime getWaktu() {
        return waktu;
    }

    // Hitung total dari semua detail pesanan
    private double hitungTotal() {
        return pesanan.getDaftarItem().stream()
                .mapToDouble(d -> d.getItem().getHarga() * d.getJumlah())
                .sum();
    }

    @Override
    public String toString() {
        return "=== DATA TRANSAKSI ===\n" +
                "ID Pesanan: " + pesanan.getIdPesanan() + "\n" +
                "Total: Rp" + total + "\n" +
                "Metode Pembayaran: " + metodePembayaran + "\n" +
                "Waktu: " + waktu + "\n";
    }
}