package service;

import java.util.Scanner;
import java.util.UUID;

public class CardPayment implements Pembayaran {
    private String idPembayaran;
    private String nomorKartu;
    private String namaPemilik;

    public CardPayment() {
        this.idPembayaran = "CARD-" + UUID.randomUUID().toString().substring(0, 6);
    }

    @Override
    public boolean prosesPembayaran(double total) throws Exception {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Masukkan nomor kartu: ");
            nomorKartu = scanner.nextLine();

            System.out.print("Masukkan nama pemilik kartu: ");
            namaPemilik = scanner.nextLine();

            if (nomorKartu == null || nomorKartu.trim().isEmpty() || nomorKartu.length() < 10) {
                throw new Exception("Nomor kartu tidak valid! Minimal 10 digit.");
            }

            if (namaPemilik.trim().isEmpty()) {
                throw new Exception("Nama pemilik tidak boleh kosong!");
            }

            System.out.println("✅ Pembayaran menggunakan kartu berhasil.");
            return true;

        } catch (Exception e) {
            System.out.println("❌ Gagal memproses pembayaran: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String getIdPembayaran() {
        return idPembayaran;
    }

    @Override
    public String toString() {
        return "Card Payment (ID: " + idPembayaran + ")";
    }
}
