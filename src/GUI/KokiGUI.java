package GUI;

import javax.swing.*;
import java.awt.*;
import service.RestaurantSystem;
import model.*;

public class KokiGUI extends JFrame {

    public KokiGUI(RestaurantSystem system, Pegawai koki) {

        setTitle("Koki - " + koki.getNama());
        setSize(450, 350);
        setLocationRelativeTo(null);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);

        JButton selesaiBtn = new JButton("Tandai Selesai");

        refresh(system, model);

        add(new JScrollPane(list), BorderLayout.CENTER);
        add(selesaiBtn, BorderLayout.SOUTH);

        selesaiBtn.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx == -1) return;

            Pesanan p = system.getDaftarPesanan().get(idx);
            p.setStatus("Selesai");

            refresh(system, model);
        });
    }

    private void refresh(RestaurantSystem system, DefaultListModel model) {
        model.clear();
        for (Pesanan p : system.getDaftarPesanan()) {
            model.addElement(p.toString());
        }
    }
}
