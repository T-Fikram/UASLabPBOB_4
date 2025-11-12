package model;

import java.time.LocalDateTime;

public class Transaksi {
    private Pesanan pesanan;
    private double total;
    private String metodePembayaran;
    private LocalDateTime waktu;

    public Transaksi(Pesanan pesanan, String metodePembayaran) {
        if (pesanan == null) {
            throw new IllegalArgumentException("Pesanan tidak boleh null");
        }

        if (pesanan.getDaftarItem().isEmpty()) {
            throw new IllegalArgumentException("Pesanan belum memiliki item, tidak dapat membuat transaksi");
        }

        if (metodePembayaran == null || metodePembayaran.trim().isEmpty()) {
            throw new IllegalArgumentException("Metode pembayaran tidak boleh kosong");
        }

        String metodeLower = metodePembayaran.trim().toLowerCase();
        if (!metodeLower.equals("cash") && 
            !metodeLower.equals("card") && 
            !metodeLower.equals("qris")) {
            throw new IllegalArgumentException("Metode pembayaran tidak valid. Gunakan: cash, card, atau qris");
        }

        this.pesanan = pesanan;
        this.metodePembayaran = metodeLower;
        this.waktu = LocalDateTime.now();
        this.total = hitungTotal();

        if (this.total <= 0) {
            throw new IllegalStateException("Total transaksi tidak valid");
        }
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
        double total = pesanan.getDaftarItem().stream()
                .mapToDouble(d -> d.getItem().getHarga() * d.getJumlah())
                .sum();

        if (total <= 0) {
            throw new IllegalStateException("Pesanan tidak memiliki item valid untuk dihitung total");
        }

        return total;
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