package model;

import java.time.LocalDateTime;

public class Transaksi {
    private Pesanan pesanan;
    private double total;
    private String metodePembayaran;
    private LocalDateTime waktu;

    public Transaksi(Pesanan pesanan, double total, String metodePembayaran) {
        this.pesanan = pesanan;
        this.total = total;
        this.metodePembayaran = metodePembayaran;
        this.waktu = LocalDateTime.now();
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
}