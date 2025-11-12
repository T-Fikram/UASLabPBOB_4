package model;

import java.util.ArrayList;
import java.util.List;

public class Pesanan {
    private int idPesanan;
    private String status;
    private List<DetailPesanan> daftarItem;
    private Meja meja;

    public Pesanan(int idPesanan, Meja meja) {
        if (idPesanan <= 0) {
            throw new IllegalArgumentException("ID pesanan harus lebih dari 0");
        }
        if (meja == null) {
            throw new IllegalArgumentException("Meja tidak boleh null");
        }

        this.idPesanan = idPesanan;
        this.status = "Menunggu";
        this.daftarItem = new ArrayList<>();
        this.meja = meja;
    }

    public int getIdPesanan() {
        return idPesanan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (!status.equalsIgnoreCase("Menunggu") &&
            !status.equalsIgnoreCase("Diproses") &&
            !status.equalsIgnoreCase("Selesai") &&
            !status.equalsIgnoreCase("Dibatalkan")) {
            throw new IllegalArgumentException("Status pesanan tidak valid");
        }
        this.status = status;
    }

    public List<DetailPesanan> getDaftarItem() {
        return daftarItem;
    }

    public Meja getMeja() {
        return meja;
    }

    public void addDetail(DetailPesanan d) {
        if (d == null) {
            throw new IllegalArgumentException("Detail pesanan tidak boleh null");
        }
        daftarItem.add(d);
    }

    public double hitungTotal() {
        if (daftarItem.isEmpty()) {
            throw new IllegalStateException("Pesanan belum memiliki item");
        }

        double total = 0;
        for (DetailPesanan item : daftarItem) {
            total += item.getItem().getHarga() * item.getJumlah();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Pesanan #" + idPesanan + " (" + status + "), Meja " + meja.getNomor();
    }
}