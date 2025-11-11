package service;

import java.util.Scanner;

public class CardPayment implements Pembayaran {
    private String nomorKartu;
    private String namaPemilik;

    @Override
    public boolean prosesPembayaran(double total) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nomor kartu: ");
        nomorKartu = scanner.nextLine();
        System.out.print("Masukkan nama pemilik kartu: ");
        namaPemilik = scanner.nextLine();

        if (nomorKartu.length() < 10) {
            throw new Exception("Nomor kartu tidak valid!");
        }

        System.out.println("Pembayaran menggunakan kartu berhasil.");
        return true;
    }

    @Override
    public String toString() {
        return "Metode Pembayaran: Kartu";
    }
}
