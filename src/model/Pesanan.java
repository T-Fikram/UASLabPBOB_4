package model;

import java.util.ArrayList;
import java.util.List;

public class Pesanan {
    private int idPesanan;
    private String status;
    private List<DetailPesanan> daftarItem;
    private Meja meja;

    public Pesanan(int idPesanan, Meja meja) {
        this.idPesanan = idPesanan;
        this.status = "Menunggu";
        this.daftarItem = new ArrayList<>();
        this.meja = meja;
    }

    public int getIdPesanan() { return idPesanan; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<DetailPesanan> getDaftarItem() { return daftarItem; }
    public Meja getMeja() { return meja; }

    public void addDetail(DetailPesanan d) {
        daftarItem.add(d);
    }
    public double hitungTotal() {
        double total = 0;
        for (DetailPesanan item : daftarItem) {
            total += item.getItem().getHarga() * item.getJumlah();
        }
        return total;
    }
}
