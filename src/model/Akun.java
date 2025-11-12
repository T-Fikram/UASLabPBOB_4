package model;

public abstract class Akun {
    private int id;
    private String nama;
    private String password;

    public Akun(int id, String nama, String password) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID harus lebih dari 0");
        }
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password tidak boleh kosong");
        }

        this.id = id;
        this.nama = nama;
        this.password = password;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public String getPassword() { return password; }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong");
        }
        this.nama = nama;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password tidak boleh kosong");
        }
        this.password = password;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nama: " + nama;
    }
}