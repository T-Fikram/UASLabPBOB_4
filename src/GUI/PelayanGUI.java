package GUI;

import javax.swing.*;
import java.awt.*;
import service.RestaurantSystem;
import model.Pegawai;
import model.Pesanan;

public class PelayanGUI extends JFrame {

    public PelayanGUI(RestaurantSystem system, Pegawai pelayan) {

        setTitle("Pelayan - " + pelayan.getNama());
        setSize(500, 400);
        setLocationRelativeTo(null);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);

        refresh(system, model);

        JButton refreshBtn = new JButton("Refresh");

        add(new JScrollPane(list), BorderLayout.CENTER);
        add(refreshBtn, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> refresh(system, model));
    }

    private void refresh(RestaurantSystem system, DefaultListModel<String> model) {

        model.clear();
        for (Pesanan p : system.getDaftarPesanan()) {
            model.addElement(p.toString());
        }
    }
}
