package service;

public interface Pembayaran {
    boolean prosesPembayaran(double total) throws Exception;
    String getIdPembayaran();
}
