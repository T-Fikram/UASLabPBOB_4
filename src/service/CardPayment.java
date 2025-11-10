package service;
import java.util.Scanner;

public class CardPayment implements Pembayaran {

    private double total;
    private boolean status;

    @Override
    public void prosesPembayaran(double total) {
        this.total = total;
        Scanner input = new Scanner(System.in);

        System.out.println("\n=== PEMBAYARAN KARTU ===");
        System.out.println("Total  : Rp" + total);

        System.out.print("Masukkan nomor kartu: ");
        String nomor = input.nextLine();

        System.out.print("Masukkan nama pemilik kartu: ");
        String nama = input.nextLine();

        System.out.print("Konfirmasi pembayaran? (y/n): ");
        String confirm = input.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            System.out.println("✅ Pembayaran berhasil menggunakan kartu " + nama);
            status = true;
        } else {
            System.out.println("❌ Pembayaran dibatalkan oleh pengguna.");
            status = false;
        }

        System.out.println("Status transaksi : " + (status ? "Berhasil" : "Gagal"));
        System.out.println("--------------------------------\n");
    }
}
