package model;

public class Minuman extends MenuItem {
    private String ukuran;
    private String suhu;

    public Minuman(String nama, double harga, String ukuran, String suhu) {
        super(nama, harga);

        if (ukuran == null || ukuran.trim().isEmpty()) {
            throw new IllegalArgumentException("Ukuran minuman tidak boleh kosong");
        }

        if (suhu == null || suhu.trim().isEmpty()) {
            throw new IllegalArgumentException("Suhu minuman tidak boleh kosong");
        }

        String suhuLower = suhu.trim().toLowerCase();
        if (!suhuLower.equals("dingin") && !suhuLower.equals("panas")) {
            throw new IllegalArgumentException("Suhu minuman harus 'dingin' atau 'panas'");
        }

        this.ukuran = ukuran.trim();
        this.suhu = suhuLower;
    }

    public String getUkuran() {
        return ukuran;
    }

    public String getSuhu() {
        return suhu;
    }

    @Override
    public String toString() {
        return "Minuman: " + getNama() +
               " | Harga: Rp" + getHarga() +
               " | Ukuran: " + ukuran +
               " | Suhu: " + suhu;
    }
}