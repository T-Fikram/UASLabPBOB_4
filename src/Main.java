import model.*;
import service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static RestaurantSystem system = new RestaurantSystem();
    private static List<Customer> customerList = new ArrayList<>();

    public static void main(String[] args) {
        seedData();
        System.out.println("=== SISTEM RESTORAN ===");

        while (true) {
            System.out.println("\n--- Layar Login ---");
            System.out.println("1. Login Pegawai");
            System.out.println("2. Login Customer");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            int pilihan = readInt();

            if (pilihan == 0) {
                System.out.println("Keluar. Terima kasih!");
                break;
            } else if (pilihan == 1) {
                Pegawai pegawai = loginPegawai();
                if (pegawai != null) {
                    handlePegawaiMenu(pegawai);
                }
            } else if (pilihan == 2) {
                Customer cust = loginCustomer();
                if (cust != null) {
                    handleCustomerMenu(cust);
                }
            } else {
                System.out.println("Pilihan tidak valid, coba lagi.");
            }
        }
    }

    // SEED DATA 
    private static void seedData() {
        system.tambahMenu(new Makanan("Nasi Goreng", 20000, 2, "Main Course"));
        system.tambahMenu(new Makanan("Mie Ayam", 18000, 1, "Main Course"));
        system.tambahMenu(new Minuman("Es Teh", 5000, "Gelas", "Dingin"));
        system.tambahMenu(new Minuman("Kopi Hitam", 8000, "Cangkir", "Panas"));

        system.tambahPegawai(new Pegawai(1, "Andi", "123", "pelayan"));
        system.tambahPegawai(new Pegawai(2, "Budi", "123", "koki"));
        system.tambahPegawai(new Pegawai(3, "Citra", "123", "kasir"));

        customerList.add(new Customer(101, "Rina", "111"));
        customerList.add(new Customer(102, "Doni", "222"));
    }

    // LOGIN 
    private static Pegawai loginPegawai() {
        System.out.print("Nama pegawai: ");
        String nama = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        // Note: class RestaurantSystem yang kamu sertakan sebelumnya harus menyediakan getDaftarPegawai().
        // Jika belum ada, tambahkan public List<Pegawai> getDaftarPegawai() { return new ArrayList<>(daftarPegawai); }
        List<Pegawai> daftar = null;
        try {
            daftar = system.getDaftarPegawai();
        } catch (Throwable t) {
            // jika getter tidak tersedia, kita handle gracefully
            System.out.println("⚠️ RestaurantSystem belum memiliki getter daftar pegawai. Pastikan ada method getDaftarPegawai().");
            return null;
        }

        for (Pegawai p : daftar) {
            if (p.getNama().equalsIgnoreCase(nama) && p.getPassword().equals(pass)) {
                System.out.println("Login sukses sebagai Pegawai (" + p.getPeran() + ")");
                return p;
            }
        }
        System.out.println("Pegawai tidak ditemukan / password salah.");
        return null;
    }

    private static Customer loginCustomer() {
        System.out.print("Nama customer: ");
        String nama = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        for (Customer c : customerList) {
            if (c.getNama().equalsIgnoreCase(nama) && c.getPassword().equals(pass)) {
                System.out.println("Login sukses sebagai Customer (" + c.getNama() + ")");
                return c;
            }
        }
        System.out.println("Customer tidak ditemukan / password salah.");
        return null;
    }

    // MENU UTAMA PEGAWAI
    private static void handlePegawaiMenu(Pegawai pegawai) {
        String role = pegawai.getPeran().toLowerCase();
        if ("pelayan".equals(role)) {
            pelayanMenu(pegawai);
        } else if ("koki".equals(role)) {
            kokiMenu(pegawai);
        } else if ("kasir".equals(role)) {
            kasirMenu(pegawai);
        } else {
            System.out.println("Peran pegawai tidak dikenali.");
        }
    }

    // PELAYAN
    private static void pelayanMenu(Pegawai pelayan) {
        while (true) {
            System.out.println("\n=== MENU PELAYAN ===");
            System.out.println("1. Buat pesanan baru");
            System.out.println("2. Lihat semua pesanan singkat");
            System.out.println("3. Logout");
            System.out.print("Pilih: ");
            int p = readInt();
            if (p == 1) {
                buatPesananByPelayan();
            } else if (p == 2) {
                List<Pesanan> list = system.getDaftarPesanan();
                if (list.isEmpty()) {
                    System.out.println("(Belum ada pesanan)");
                } else {
                    for (Pesanan ps : list) {
                        System.out.println("ID: " + ps.getIdPesanan() + " | Meja: " + ps.getMeja().getNomor() + " | Status: " + ps.getStatus());
                    }
                }
            } else if (p == 3) {
                System.out.println("Logout pelayan.");
                break;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void buatPesananByPelayan() {
        System.out.println("\n=== BUAT PESANAN BARU (oleh Pelayan) ===");
        System.out.println("Pilih customer terdaftar:");
        for (Customer c : customerList) {
            System.out.println(c.getId() + " - " + c.getNama());
        }
        System.out.print("Masukkan ID customer: ");
        int idCust = readInt();
        Customer cust = findCustomerById(idCust);
        if (cust == null) {
            System.out.println("Customer tidak ditemukan. Batalkan pembuatan pesanan.");
            return;
        }

        System.out.print("Masukkan nomor meja: ");
        int noMeja = readInt();
        Meja meja;
        try {
            meja = new Meja(noMeja);
        } catch (IllegalArgumentException e) {
            System.out.println("Nomor meja tidak valid: " + e.getMessage());
            return;
        }
        meja.setStatus(Meja.StatusMeja.TERISI);

        Pesanan pesanan = new Pesanan(generatePesananId(), meja);

        while (true) {
            System.out.println("\n--- Daftar Menu ---");
            List<MenuItem> daftarMenu = system.getDaftarMenu();
            for (int i = 0; i < daftarMenu.size(); i++) {
                System.out.println((i + 1) + ". " + daftarMenu.get(i));
            }
            System.out.print("Pilih nomor menu (0 selesai): ");
            int pilihMenu = readInt();
            if (pilihMenu == 0) break;
            if (pilihMenu < 1 || pilihMenu > daftarMenu.size()) {
                System.out.println("Pilihan menu tidak valid.");
                continue;
            }
            MenuItem item = daftarMenu.get(pilihMenu - 1);
            System.out.print("Jumlah: ");
            int jumlah = readInt();
            scanner.nextLine(); // buang newline
            System.out.print("Catatan (opsional): ");
            String catatan = scanner.nextLine();

            try {
                pesanan.addDetail(new DetailPesanan(item, jumlah, catatan));
                System.out.println(item.getNama() + " x" + jumlah + " ditambahkan.");
            } catch (IllegalArgumentException e) {
                System.out.println("Gagal menambah item: " + e.getMessage());
            }
        }

        if (pesanan.getDaftarItem().isEmpty()) {
            System.out.println("Pesanan kosong — batal.");
            return;
        }

        system.tambahPesanan(pesanan);
        try {
            cust.tambahRiwayatPesanan(pesanan.getIdPesanan());
        } catch (IllegalArgumentException e) {
            // jika riwayat duplikat, abaikan tetapi tetap simpan pesanan
        }
        System.out.println("✅ Pesanan dibuat. ID Pesanan: " + pesanan.getIdPesanan());
    }

    // KOKI
    private static void kokiMenu(Pegawai koki) {
        while (true) {
            System.out.println("\n=== MENU KOKI ===");
            System.out.println("1. Lihat detail semua pesanan");
            System.out.println("2. Tandai pesanan selesai dimasak");
            System.out.println("3. Logout");
            System.out.print("Pilih: ");
            int p = readInt();

            if (p == 1) {
                tampilkanDetailSemuaPesanan();
            } else if (p == 2) {
                tampilkanDetailSemuaPesanan();
                System.out.print("Masukkan ID pesanan yang selesai dimasak: ");
                int id = readInt();
                Pesanan ps = findPesananById(id);
                if (ps == null) {
                    System.out.println("Pesanan tidak ditemukan.");
                } else {
                    try {
                        ps.setStatus("Selesai");
                        System.out.println("✅ Pesanan #" + id + " status -> Selesai");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Gagal mengubah status: " + e.getMessage());
                    }
                }
            } else if (p == 3) {
                System.out.println("Logout koki.");
                break;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // KASIR
    private static void kasirMenu(Pegawai kasir) {
        while (true) {
            System.out.println("\n=== MENU KASIR ===");
            System.out.println("1. Lihat pesanan & bayar");
            System.out.println("2. Logout");
            System.out.print("Pilih: ");
            int p = readInt();
    
            if (p == 1) {
                List<Pesanan> daftar = system.getDaftarPesanan();
                boolean ada = false;
    
                // Tampilkan hanya pesanan yang siap dibayar
                for (Pesanan ps : daftar) {
                    if ("Selesai".equalsIgnoreCase(ps.getStatus())) {
                        ada = true;
                        System.out.println("ID: " + ps.getIdPesanan() + " | Meja: " 
                            + ps.getMeja().getNomor() + " | Total: Rp" + ps.hitungTotal());
                    }
                }
    
                if (!ada) {
                    System.out.println("Belum ada pesanan yang siap dibayar.");
                    continue;
                }
    
                System.out.print("Masukkan ID pesanan yang akan dibayar: ");
                int id = readInt();
                Pesanan ps = findPesananById(id);
    
                if (ps == null) {
                    System.out.println("Pesanan tidak ditemukan.");
                    continue;
                }
    
                // ❗ Cegah pembayaran ulang
                if (!"Selesai".equalsIgnoreCase(ps.getStatus())) {
                    System.out.println("Pesanan ini tidak bisa dibayar (status: " + ps.getStatus() + ").");
                    continue;
                }
    
                System.out.println("Total yang harus dibayar: Rp" + ps.hitungTotal());
                System.out.println("Pilih metode pembayaran:");
                System.out.println("1. Cash");
                System.out.println("2. Card");
                System.out.println("3. QRIS");
                System.out.print("Pilihan: ");
                int m = readInt();
    
                Pembayaran metode = null;
                String metodeString = "";
    
                if (m == 1) {
                    metode = new CashPayment();
                    metodeString = "cash";
                } else if (m == 2) {
                    metode = new CardPayment();
                    metodeString = "card";
                } else if (m == 3) {
                    metode = new QRISPayment();
                    metodeString = "qris";
                } else {
                    System.out.println("Metode tidak valid.");
                    continue;
                }
    
                try {
                    boolean sukses = metode.prosesPembayaran(ps.hitungTotal());
    
                    if (sukses) {
    
                        // ❗ Buat transaksi sebelum status berubah
                        Transaksi trx = new Transaksi(ps, metodeString);
    
                        // Cetak struk
                        Struk struk = new Struk(trx);
                        struk.cetak();
    
                        // Baru setelah selesai -> ubah status
                        ps.setStatus("Selesai");
                        ps.getMeja().setStatus(Meja.StatusMeja.KOSONG);
    
                        System.out.println("Pembayaran sukses. ID Pembayaran: " + metode.getIdPembayaran());
                    } else {
                        System.out.println("Pembayaran gagal.");
                    }
                } catch (Exception e) {
                    System.out.println("Error saat pembayaran: " + e.getMessage());
                }
            }
    
            else if (p == 2) {
                System.out.println("Logout kasir.");
                break;
            }
    
            else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }
    
    // MENU CUSTOMER
    private static void handleCustomerMenu(Customer cust) {
        while (true) {
            System.out.println("\n=== MENU CUSTOMER (" + cust.getNama() + ") ===");
            System.out.println("1. Lihat daftar menu");
            System.out.println("2. Minta pelayan buat pesanan");
            System.out.println("3. Lihat riwayat pesanan");
            System.out.println("4. Logout");
            System.out.print("Pilih: ");
            int p = readInt();

            if (p == 1) {
                List<MenuItem> daftar = system.getDaftarMenu();
                for (MenuItem mi : daftar) {
                    System.out.println(mi);
                }
            } else if (p == 2) {
                buatPesananByPelayanForCustomer(cust);
            } else if (p == 3) {
                System.out.println("Riwayat pesanan: " + cust.getRiwayatPesanan());
            } else if (p == 4) {
                System.out.println("Logout customer.");
                break;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void buatPesananByPelayanForCustomer(Customer cust) {
        System.out.println("\n--- Membuat pesanan untuk customer: " + cust.getNama() + " ---");
        System.out.print("Masukkan nomor meja: ");
        int noMeja = readInt();
        Meja meja;
        try {
            meja = new Meja(noMeja);
        } catch (IllegalArgumentException e) {
            System.out.println("Nomor meja tidak valid: " + e.getMessage());
            return;
        }
        meja.setStatus(Meja.StatusMeja.TERISI);

        Pesanan pesanan = new Pesanan(generatePesananId(), meja);

        while (true) {
            List<MenuItem> daftar = system.getDaftarMenu();
            for (int i = 0; i < daftar.size(); i++) {
                System.out.println((i + 1) + ". " + daftar.get(i));
            }
            System.out.print("Pilih nomor menu (0 selesai): ");
            int pilih = readInt();
            if (pilih == 0) break;
            if (pilih < 1 || pilih > daftar.size()) {
                System.out.println("Pilihan tidak valid.");
                continue;
            }
            MenuItem item = daftar.get(pilih - 1);
            System.out.print("Jumlah: ");
            int jumlah = readInt();
            scanner.nextLine();
            System.out.print("Catatan (opsional): ");
            String catatan = scanner.nextLine();
            try {
                pesanan.addDetail(new DetailPesanan(item, jumlah, catatan));
                System.out.println(item.getNama() + " x" + jumlah + " ditambahkan.");
            } catch (IllegalArgumentException e) {
                System.out.println("Gagal menambah item: " + e.getMessage());
            }
        }

        if (pesanan.getDaftarItem().isEmpty()) {
            System.out.println("Pesanan kosong. Batal.");
            return;
        }

        system.tambahPesanan(pesanan);
        try {
            cust.tambahRiwayatPesanan(pesanan.getIdPesanan());
        } catch (IllegalArgumentException e) {
            // abaikan jika duplikat
        }
        System.out.println("Pesanan dibuat. ID Pesanan: " + pesanan.getIdPesanan());
    }

    // BANTUAN / UTILS 
    private static int readInt() {
        int val = -1;
        try {
            val = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            // kembalikan -1 jika input bukan integer
        }
        return val;
    }

    private static Customer findCustomerById(int id) {
        for (Customer c : customerList) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    private static Pesanan findPesananById(int id) {
        List<Pesanan> list = system.getDaftarPesanan();
        for (Pesanan p : list) {
            if (p.getIdPesanan() == id) return p;
        }
        return null;
    }

    private static void tampilkanDetailSemuaPesanan() {
        List<Pesanan> list = system.getDaftarPesanan();
        if (list.isEmpty()) {
            System.out.println("(Belum ada pesanan)");
            return;
        }
        for (Pesanan p : list) {
            System.out.println("----------------------------------------");
            System.out.println("ID Pesanan : " + p.getIdPesanan());
            System.out.println("Meja       : " + p.getMeja().getNomor());
            System.out.println("Status     : " + p.getStatus());
            System.out.println("Daftar Item:");
            for (DetailPesanan d : p.getDaftarItem()) {
                System.out.println(" - " + d.getItem().getNama() + " x" + d.getJumlah() +
                        (d.getCatatan() == null || d.getCatatan().isEmpty() ? "" : " (" + d.getCatatan() + ")"));
            }
        }
        System.out.println("----------------------------------------");
    }

    // Generate ID pesanan sederhana
    private static int generatePesananId() {
        List<Pesanan> list = system.getDaftarPesanan();
        int max = 0;
        for (Pesanan p : list) {
            if (p.getIdPesanan() > max) max = p.getIdPesanan();
        }
        return max + 1;
    }
}
