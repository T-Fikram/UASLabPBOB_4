package model;

public abstract class MenuItem {
    private String nama;
    private double harga;

    public MenuItem(String nama, double harga) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama menu tidak boleh kosong");
        }
        if (harga <= 0) {
            throw new IllegalArgumentException("Harga harus lebih dari 0");
        }

        this.nama = nama.trim();
        this.harga = harga;
    }

    public String getNama() { return nama; }
    public double getHarga() { return harga; }

    public String getInfo() {
        return nama + " - Rp" + harga;
    }

    @Override
    public String toString() {
        return getInfo();
    }
}