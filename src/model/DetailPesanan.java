package model;

public class DetailPesanan {
    private MenuItem item;
    private int jumlah;
    private String catatan;

    public DetailPesanan(MenuItem item, int jumlah, String catatan) {
        this.item = item;
        this.jumlah = jumlah;
        this.catatan = catatan;
    }

    public MenuItem getItem() { return item; }
    public int getJumlah() { return jumlah; }
    public String getCatatan() { return catatan; }

    @Override
    public String toString() {
        return item.getInfo() + " x" + jumlah + 
            (catatan.isEmpty() ? "" : " (" + catatan + ")");
    }
}
