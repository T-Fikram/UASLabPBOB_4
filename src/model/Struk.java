package model;

public class Struk {
    private Transaksi transaksi;

    public Struk(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public void cetak() {
        System.out.println("\n======= STRUK PEMBAYARAN =======");
        System.out.println("Waktu : " + transaksi.getWaktu());
        System.out.println("Metode Pembayaran : " + transaksi.getMetodePembayaran());
        System.out.println("Total : Rp" + transaksi.getTotal());
        System.out.println("================================");
    }
}