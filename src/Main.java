import model.*;
import service.*;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final RestaurantSystem restoran = new RestaurantSystem();

    public static void main(String[] args) {
        setupData();

        System.out.println("===== SISTEM RESTORAN =====");
        Akun akun = login();

        if (akun == null) {
            System.out.println("Login gagal. Program berhenti.");
            return;
        }

        if (akun instanceof Pegawai) {
            Pegawai pegawai = (Pegawai) akun;
            switch (pegawai.getPeran().toLowerCase()) {
                case "pelayan":
                    menuPelayan(pegawai);
                    break;
                case "koki":
                    menuKoki();
                    break;
                case "kasir":
                    menuKasir();
                    break;
                default:
                    System.out.println("Peran pegawai tidak dikenali.");
            }
        } else if (akun instanceof Customer) {
            menuCustomer((Customer) akun);
        }
    }

    // Login
    private static Akun login() {
        System.out.print("Masukkan nama pengguna: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        for (Pegawai p : pegawaiList) {
            if (p.getNama().equalsIgnoreCase(nama) && p.getPassword().equals(password)) {
                System.out.println("Login berhasil sebagai Pegawai (" + p.getPeran() + ")");
                return p;
            }
        }

        for (Customer c : customerList) {
            if (c.getNama().equalsIgnoreCase(nama) && c.getPassword().equals(password)) {
                System.out.println("Login berhasil sebagai Customer");
                return c;
            }
        }

        return null;
    }

    // Data Awal
    private static List<Pegawai> pegawaiList = new ArrayList<>();
    private static List<Customer> customerList = new ArrayList<>();
    private static List<Meja> mejaList = new ArrayList<>();

    private static void setupData() {
        pegawaiList.add(new Pegawai(1, "Ani", "123", "pelayan"));
        pegawaiList.add(new Pegawai(2, "Budi", "123", "koki"));
        pegawaiList.add(new Pegawai(3, "Cici", "123", "kasir"));

        customerList.add(new Customer(1, "Dina", "111"));
        customerList.add(new Customer(2, "Eko", "222"));

        for (int i = 1; i <= 3; i++) {
            mejaList.add(new Meja(i));
        }

        restoran.tambahMenu(new Makanan("Nasi Goreng", 20000, 2, "Utama"));
        restoran.tambahMenu(new Makanan("Ayam Bakar", 25000, 1, "Utama"));
        restoran.tambahMenu(new Minuman("Es Teh", 5000, "Gelas", "Dingin"));
        restoran.tambahMenu(new Minuman("Kopi Hitam", 7000, "Gelas", "Panas"));
    }

    // Menu Pegawai
    private static void menuPelayan(Pegawai pelayan) {
        System.out.println("=== MENU PELAYAN ===");
        restoran.lihatMenu();

        System.out.print("Pilih meja (1-3): ");
        int noMeja = scanner.nextInt();
        scanner.nextLine();

        Meja meja = mejaList.get(noMeja - 1);
        meja.setStatus(Meja.StatusMeja.TERISI);

        Pesanan pesanan = new Pesanan(new Random().nextInt(1000), meja);

        System.out.print("Berapa item yang ingin dipesan? ");
        int jumlah = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < jumlah; i++) {
            System.out.print("Pilih menu ke-" + (i + 1) + " (nomor menu): ");
            int idxMenu = scanner.nextInt();
            scanner.nextLine();
            MenuItem item = restoran.getDaftarMenu().get(idxMenu - 1);

            System.out.print("Jumlah: ");
            int jml = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Catatan (opsional): ");
            String catatan = scanner.nextLine();

            pesanan.addDetail(new DetailPesanan(item, jml, catatan));
        }

        restoran.tambahPesanan(pesanan);
        System.out.println("Pesanan berhasil dibuat dengan ID: " + pesanan.getIdPesanan());
    }

    private static void menuKoki() {
        System.out.println("=== MENU KOKI ===");
        System.out.println("Semua pesanan telah dimasak dan status diubah menjadi 'Selesai'.");
    }

    private static void menuKasir() {
        System.out.println("=== MENU KASIR ===");
        System.out.print("Masukkan ID pesanan untuk dibayar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Pesanan pesanan = null;
        for (Pesanan p : restoran.getDaftarPesanan()) {
            if (p.getIdPesanan() == id) {
                pesanan = p;
                break;
            }
        }

        if (pesanan == null) {
            System.out.println("Pesanan tidak ditemukan.");
            return;
        }

        System.out.println("Pilih metode pembayaran:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. QRIS");
        System.out.print("Pilihan: ");
        int metode = scanner.nextInt();
        scanner.nextLine();

        Pembayaran pembayaran = null;
        switch (metode) {
            case 1:
                pembayaran = new CashPayment();
                break;
            case 2:
                pembayaran = new CardPayment();
                break;
            case 3:
                pembayaran = new QRISPayment();
                break;
            default:
                pembayaran = null;
                break;
        }

        if (pembayaran != null) {
            restoran.prosesPembayaran(pesanan, pembayaran);
        } else {
            System.out.println("Metode tidak valid.");
        }
    }

    // Menu Customer
    private static void menuCustomer(Customer c) {
        System.out.println("=== MENU CUSTOMER ===");
        System.out.println("1. Lihat Menu");
        System.out.println("2. Keluar");
        System.out.print("Pilih: ");
        int pilih = scanner.nextInt();

        if (pilih == 1) {
            restoran.lihatMenu();
        } else {
            System.out.println("Terima kasih telah berkunjung!");
        }
    }
}
