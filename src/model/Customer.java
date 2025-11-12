package model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Akun {

    private List<Integer> riwayatPesanan;

    public Customer(int id, String nama, String password) {
        super(id, nama, password);
        this.riwayatPesanan = new ArrayList<>();
    }

    public List<Integer> getRiwayatPesanan() {
        return riwayatPesanan;
    }

    public void tambahRiwayatPesanan(int idPesanan) {
        if (idPesanan <= 0) {
            throw new IllegalArgumentException("ID pesanan harus lebih dari 0");
        }
        if (riwayatPesanan.contains(idPesanan)) {
            throw new IllegalArgumentException("ID pesanan sudah ada di riwayat");
        }
        riwayatPesanan.add(idPesanan);
    }

    @Override
    public String toString() {
        return "Customer: " + getNama();
    }
}