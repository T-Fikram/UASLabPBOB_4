package model;

public class Makanan extends MenuItem {
    private int tingkatPedas;
    private String kategori;

    public Makanan(String nama, double harga, int tingkatPedas, String kategori) {
        super(nama, harga);

        if (tingkatPedas < 0 || tingkatPedas > 5) {
            throw new IllegalArgumentException("Tingkat pedas harus antara 0 sampai 5");
        }

        if (kategori == null || kategori.trim().isEmpty()) {
            throw new IllegalArgumentException("Kategori makanan tidak boleh kosong");
        }

        this.tingkatPedas = tingkatPedas;
        this.kategori = kategori.trim();
    }

    public int getTingkatPedas() {
        return tingkatPedas;
    }

    public String getKategori() {
        return kategori;
    }

    @Override
    public String toString() {
        return "Makanan: " + getNama() +
               " | Harga: Rp" + getHarga() +
               " | Level Pedas: " + tingkatPedas +
               " | Kategori: " + kategori;
    }
}