import model.*;
import service.*;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final RestaurantSystem restoran = new RestaurantSystem();

    public static void main(String[] args) {
        setupData();

        System.out.println("===== SISTEM RESTORAN BERBASIS OOP =====");
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

