package service;

import java.util.Random;

public class QRISPayment implements Pembayaran {

    @Override
    public boolean prosesPembayaran(double total) throws Exception {
        System.out.println("Silakan scan QRIS untuk pembayaran sebesar Rp" + total);
        Thread.sleep(2000); // simulasi waktu proses
        boolean sukses = new Random().nextBoolean();

        if (!sukses) {
            throw new Exception("Transaksi QRIS gagal. Silakan coba lagi.");
        }

        System.out.println("Pembayaran QRIS berhasil!");
        return true;
    }

    @Override
    public String toString() {
        return "Metode Pembayaran: QRIS";
    }
}
