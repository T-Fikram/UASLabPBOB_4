package service;
import java.util.Scanner;

public class CashPayment implements Pembayaran {

    private double total;
    private boolean status;

    @Override
    public void prosesPembayaran(double total) {
       this.total = total;
        Scanner input = new Scanner(System.in);

        System.out.println("\n=== PEMBAYARAN TUNAI ===");
        System.out.println("Total  : Rp" + total);
        System.out.print("Masukkan jumlah uang tunai: Rp");
        double uang = input.nextDouble();

        if (uang < total) {
            System.out.println("❌ Uang tidak cukup, pembayaran dibatalkan.");
            status = false;
        } else {
            double kembalian = uang - total;
            System.out.println("✅ Pembayaran tunai berhasil!");
            System.out.println("Kembalian: Rp" + kembalian);
            status = true;
        }

        System.out.println("Status transaksi : " + (status ? "Berhasil" : "Gagal"));
        System.out.println("--------------------------------\n");
    }
}
