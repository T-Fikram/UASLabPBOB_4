package model;

public class Makanan extends MenuItem {
    private int tingkatPedas;
    private String kategori;

    public Makanan(String nama, double harga, int tingkatPedas, String kategori) {
        super(nama, harga);
        this.tingkatPedas = tingkatPedas;
        this.kategori = kategori;
    }

    public int getTingkatPedas() { return tingkatPedas; }
    public String getKategori() { return kategori; }
}
