package model;

public class Pegawai extends Akun {
    private String peran; // "pelayan", "koki", "kasir"

    public Pegawai(int id, String nama, String password, String peran) {
        super(id, nama, password);
        this.peran = peran;
    }

    public String getPeran() { return peran; }
    public void setPeran(String peran) { this.peran = peran; }
}
