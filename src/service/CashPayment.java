package model;

import java.util.Scanner;

public class CashPayment implements Pembayaran {
    private double jumlahDibayar;

    @Override
    public boolean prosesPembayaran(double total) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan jumlah uang tunai: Rp");
        jumlahDibayar = scanner.nextDouble();

        if (jumlahDibayar < total) {
            throw new Exception("Uang tidak cukup untuk membayar total pesanan!");
        }

        double kembalian = jumlahDibayar - total;
        System.out.printf("Pembayaran berhasil. Kembalian: Rp%.0f\n", kembalian);
        return true;
    }

    @Override
    public String toString() {
        return "Metode Pembayaran: Cash";
    }
}
