package model;

public class Pegawai extends Akun {
    private String peran; // "pelayan", "koki", "kasir"

    public Pegawai(int id, String nama, String password, String peran) {
        super(id, nama, password);
        setPeran(peran); // pakai setter biar validasi jalan
    }

    public String getPeran() {
        return peran;
    }

    public void setPeran(String peran) {
        if (peran == null || peran.trim().isEmpty()) {
            throw new IllegalArgumentException("Peran tidak boleh kosong");
        }

        String role = peran.trim().toLowerCase();
        if (!role.equals("pelayan") && !role.equals("koki") && !role.equals("kasir")) {
            throw new IllegalArgumentException("Peran harus 'pelayan', 'koki', atau 'kasir'");
        }

        this.peran = role; // disimpan dalam huruf kecil agar konsisten
    }

    @Override
    public String toString() {
        return "Pegawai: " + getNama() + " (" + peran + ")";
    }
}