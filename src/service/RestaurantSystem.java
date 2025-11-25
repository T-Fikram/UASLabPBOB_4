package service;

import java.util.ArrayList;
import java.util.List;
import model.*;

public class RestaurantSystem {

    private List<MenuItem> daftarMenu;
    private List<Pegawai> daftarPegawai;
    private List<Pesanan> daftarPesanan;
    private List<User> daftarUser;
    private List<Customer> daftarCustomer;

    public RestaurantSystem() {
        daftarMenu = new ArrayList<>();
        daftarPegawai = new ArrayList<>();
        daftarPesanan = new ArrayList<>();
        daftarUser = new ArrayList<>();
        daftarCustomer = new ArrayList<>();
    }

    // =============================
    // ====== LOGIN SYSTEM =========
    // =============================

    public boolean register(String username, String password) {
        // Cek apakah username sudah ada
        for (User u : daftarUser) {
            if (u.getUsername().equals(username)) {
                return false; // duplikat
            }
        }

        daftarUser.add(new User(username, password));
        return true;
    }

    public boolean login(String username, String password) {
        for (User u : daftarUser) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true; // berhasil login
            }
        }
        return false;
    }

    public List<User> getDaftarUser() {
        return daftarUser;
    }

    // =============================
    // ====== CUSTOMER SYSTEM ====== // 🔥 SECTION BARU
    // =============================

    public void tambahCustomer(Customer c) {
        if (c == null) {
            System.out.println("❌ Customer tidak boleh null!");
            return;
        }
        daftarCustomer.add(c);
    }
    
    public List<Customer> getDaftarCustomer() {
        return new ArrayList<>(daftarCustomer);
    }
    
    public Customer findCustomerById(int id) {
        for (Customer c : daftarCustomer) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    // =============================
    // ====== MENU SYSTEM ==========
    // =============================

    public void tambahMenu(MenuItem item) {
        if (item == null) {
            System.out.println("❌ Menu tidak boleh null!");
            return;
        }
        daftarMenu.add(item);
    }

    public void lihatMenu() {
        System.out.println("=== DAFTAR MENU ===");
        if (daftarMenu.isEmpty()) {
            System.out.println("(Belum ada menu)");
            return;
        }
        for (int i = 0; i < daftarMenu.size(); i++) {
            System.out.println((i + 1) + ". " + daftarMenu.get(i));
        }
    }

    public List<MenuItem> getDaftarMenu() {
        return new ArrayList<>(daftarMenu);
    }

    // =============================
    // ==== PEGAWAI SYSTEM =========
    // =============================

    public void tambahPegawai(Pegawai p) {
        if (p == null) {
            System.out.println("❌ Pegawai tidak boleh null!");
            return;
        }
        daftarPegawai.add(p);
    }

    public List<Pegawai> getDaftarPegawai() {
        return new ArrayList<>(daftarPegawai);
    }

    public Pegawai findPegawaiByName(String nama) {
    for (Pegawai p : daftarPegawai) {
        if (p.getNama().equalsIgnoreCase(nama)) {
            return p;
        }
    }
    return null;
    }

    // =============================
    // ===== PESANAN SYSTEM ========
    // =============================

    public void tambahPesanan(Pesanan p) {
        if (p == null) {
            System.out.println("❌ Pesanan tidak boleh null!");
            return;
        }
        daftarPesanan.add(p);
    }

    public List<Pesanan> getDaftarPesanan() {
        return new ArrayList<>(daftarPesanan);
    }

    // 🔥 Pindahkan generatePesananId dari Main ke sini
    public int generateNextPesananId() {
        List<Pesanan> list = getDaftarPesanan();
        int max = 0;
        for (Pesanan p : list) {
            if (p.getIdPesanan() > max) max = p.getIdPesanan();
        }
        return max + 1;
    }
    
    // 🔥 BARU: Filter pesanan yang siap dibayar (untuk Kasir)
    public List<Pesanan> getPesananSiapDibayar() {
        List<Pesanan> siapBayar = new ArrayList<>();
        for (Pesanan p : daftarPesanan) {
            // Cek status "Selesai" DAN status meja masih "TERISI" (belum dibayar)
            if ("Selesai".equalsIgnoreCase(p.getStatus()) && p.getMeja().getStatus() == Meja.StatusMeja.TERISI) {
                siapBayar.add(p);
            }
        }
        return siapBayar;
    }

    // =============================
    // === PEMBAYARAN SYSTEM =======
    // =============================

    public void prosesPembayaran(Pesanan pesanan, Pembayaran metode) {
        try {
            if (pesanan == null) throw new Exception("Pesanan tidak ditemukan!");
            if (metode == null) throw new Exception("Metode pembayaran belum dipilih!");

            double total = pesanan.hitungTotal();
            if (total <= 0) throw new Exception("Total pesanan tidak valid!");

            System.out.printf("Total yang harus dibayar: Rp%.0f\n", total);
            boolean sukses = metode.prosesPembayaran(total);

            if (sukses) {
                System.out.println("ID Pembayaran: " + metode.getIdPembayaran());
                Transaksi transaksi = new Transaksi(pesanan, metode.toString());
                Struk struk = new Struk(transaksi);
                struk.cetak();
            } else {
                System.out.println("❌ Pembayaran gagal. Transaksi dibatalkan.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }
}
