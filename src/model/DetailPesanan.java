package model;

public class DetailPesanan {
    private MenuItem item;
    private int jumlah;
    private String catatan;

    public DetailPesanan(MenuItem item, int jumlah, String catatan) {
        if (item == null) {
            throw new IllegalArgumentException("Item pesanan tidak boleh null");
        }

        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah pesanan harus lebih dari 0");
        }

        this.item = item;
        this.jumlah = jumlah;
        this.catatan = (catatan == null) ? "" : catatan.trim();
    }

    public MenuItem getItem() {
        return item;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getCatatan() {
        return catatan;
    }

    @Override
    public String toString() {
        return item.getInfo() + " x" + jumlah +
               (catatan.isEmpty() ? "" : " (" + catatan + ")");
    }
}