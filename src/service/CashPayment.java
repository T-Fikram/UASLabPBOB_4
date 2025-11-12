package service;

import java.util.Scanner;
import java.util.UUID;

public class CashPayment implements Pembayaran {
    private String idPembayaran;
    private double jumlahDibayar;

    public CashPayment() {
        this.idPembayaran = "CASH-" + UUID.randomUUID().toString().substring(0, 6);
    }

    @Override
    public boolean prosesPembayaran(double total) throws Exception {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Masukkan jumlah uang tunai: Rp");
            jumlahDibayar = scanner.nextDouble();

            if (jumlahDibayar <= 0) {
                throw new Exception("Jumlah uang tidak boleh nol atau negatif!");
            }

            if (jumlahDibayar < total) {
                throw new Exception("Uang tidak cukup untuk membayar total pesanan!");
            }

            double kembalian = jumlahDibayar - total;
            System.out.printf("✅ Pembayaran berhasil. Kembalian: Rp%.0f\n", kembalian);
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
        return "Cash Payment (ID: " + idPembayaran + ")";
    }
}
