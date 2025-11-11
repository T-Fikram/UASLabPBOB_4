package service;
import java.util.Scanner;

public interface Pembayaran {
    int getIdPembayaran();
    void prosesPembayaran(double total);
}

class ProsesPembayaran implements Pembayaran {

    private static int counter = 1; 
    private final int idPembayaran;
    private double total;
    private String metode;
    private boolean status;

    public ProsesPembayaran(String metode) {
        this.metode = metode;
        this.status = false;
        this.idPembayaran = counter++;
    }

    @Override
    public int getIdPembayaran() {
        return idPembayaran;
    }

    public double getTotal() {
        return total;
    }

    public String getMetode() {
        return metode;
    }

    public boolean isStatus() {
        return status;
    }

    private void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public void prosesPembayaran(double total) {
        this.total = total;
        Scanner input = new Scanner(System.in);

        try {
            System.out.println("\n=== PEMBAYARAN ===");
            System.out.println("ID Pembayaran : " + getIdPembayaran());
            System.out.println("Metode        : " + metode);
            System.out.println("Total         : Rp" + total);

            System.out.print("Konfirmasi pembayaran? (y/n): ");
            String confirm = input.nextLine();

            if (!confirm.equalsIgnoreCase("y")) {
                throw new Exception("Pembayaran dibatalkan oleh pengguna.");
            }

            setStatus(true);
            System.out.println("✅ Pembayaran berhasil menggunakan " + metode + "!");
        } catch (Exception e) {
            setStatus(false);
            System.out.println("❌ Pembayaran gagal: " + e.getMessage());
        }

        System.out.println("Status transaksi : " + (status ? "Berhasil" : "Gagal"));
        System.out.println("--------------------------------\n");
    }
}

