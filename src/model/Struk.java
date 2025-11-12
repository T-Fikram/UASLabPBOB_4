package model;

public class Struk {
    private Transaksi transaksi;

    public Struk(Transaksi transaksi) {
        if (transaksi == null) {
            throw new IllegalArgumentException("Transaksi tidak boleh null saat mencetak struk");
        }
        this.transaksi = transaksi;
    }

    public void cetak() {
        try {
            if (transaksi.getPesanan() == null) {
                throw new IllegalStateException("Pesanan pada transaksi tidak boleh null");
            }

            if (transaksi.getPesanan().getDaftarItem().isEmpty()) {
                throw new IllegalStateException("Pesanan kosong, tidak dapat mencetak struk");
            }

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

                if (d.getCatatan() != null && !d.getCatatan().isEmpty()) {
                    System.out.print(" (" + d.getCatatan() + ")");
                }

                System.out.println();
            }

            System.out.println("--------------------------------------");
            System.out.printf("Total: Rp%.0f\n", transaksi.getTotal());
            System.out.println("Status: Berhasil ✅");
            System.out.println("======================================\n");

        } catch (Exception e) {
            System.err.println("❌ Gagal mencetak struk: " + e.getMessage());
        }
    }
}