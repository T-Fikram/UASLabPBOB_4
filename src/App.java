package gui;

import service.RestaurantSystem;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        // Inisialisasi sistem restoran
        RestaurantSystem system = new RestaurantSystem();

        // Jangan lupa seed data menu ke dalam sistem
        system.tambahMenu(new model.Makanan("Nasi Goreng", 20000, 2, "Main Course"));
        system.tambahMenu(new model.Makanan("Mie Ayam", 18000, 1, "Main Course"));
        system.tambahMenu(new model.Minuman("Es Teh", 5000, "Gelas", "Dingin"));
        system.tambahMenu(new model.Minuman("Kopi Hitam", 8000, "Cangkir", "Panas"));

        // Tampilkan GUI login
        SwingUtilities.invokeLater(() -> {
            new LoginGUI(system).setVisible(true);
        });
    }
}
