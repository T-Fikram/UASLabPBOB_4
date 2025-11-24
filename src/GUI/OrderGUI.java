package GUI;

import model.*;
import service.RestaurantSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class OrderGUI extends JFrame {
    private RestaurantSystem system;
    private Customer customer;
    private DefaultListModel<String> menuListModel;
    private JList<String> menuList;
    private JTextArea cartArea;

    private Pesanan pesanan;

    public OrderGUI(RestaurantSystem system, Customer customer) {
        this.system = system;
        this.customer = customer;

        setTitle("Order Menu - " + customer.getNama());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
    }
}
