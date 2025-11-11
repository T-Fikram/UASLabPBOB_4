package model;

public class Struk {
    private Transaksi transaksi;

    public Struk(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public void cetak() {
        System.out.println("\n========== STRUK PEMBAYARAN ==========");
        System.out.println("Waktu: " + transaksi.getWaktu());
        System.out.println("Metode Pembayaran: " + transaksi.getMetodePembayaran());
        System.out.println("--------------------------------------");

        System.out.println("Pesanan:");
        for (DetailPesanan d : transaksi.getPesanan().getDaftarItem()) {
            double subTotal = d.getItem().getHarga() * d.getJumlah();

            System.out.printf("- %s x%d : Rp%.0f",
                    d.getItem().getNama(),
                    d.getJumlah(),
                    subTotal
            );

            if (!d.getCatatan().isEmpty()) {
                System.out.print(" (" + d.getCatatan() + ")");
            }

            System.out.println();
        }

        System.out.println("--------------------------------------");
        System.out.printf("Total: Rp%.0f\n", transaksi.getTotal());
        System.out.println("Status: Berhasil ✅");
        System.out.println("======================================\n");
    }
}