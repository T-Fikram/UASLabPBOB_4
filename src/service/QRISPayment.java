package service;

import java.util.Random;
import java.util.UUID;

public class QRISPayment implements Pembayaran {
    private String idPembayaran;

    public QRISPayment() {
        this.idPembayaran = "QRIS-" + UUID.randomUUID().toString().substring(0, 6);
    }

    @Override
    public boolean prosesPembayaran(double total) throws Exception {
        try {
            System.out.println("Silakan scan QRIS untuk pembayaran sebesar Rp" + total);
            Thread.sleep(2000); // simulasi waktu proses

            boolean sukses = new Random().nextBoolean();

            if (!sukses) {
                throw new Exception("Transaksi QRIS gagal. Silakan coba lagi.");
            }

            System.out.println("✅ Pembayaran QRIS berhasil!");
            return true;

        } catch (Exception e) {
            System.out.println("❌ Gagal memproses pembayaran QRIS: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String getIdPembayaran() {
        return idPembayaran;
    }

    @Override
    public String toString() {
        return "QRIS Payment (ID: " + idPembayaran + ")";
    }
}
