package service;

import model.Akun;
import model.Pegawai;
import model.Customer;
import model.Admin;
import java.util.ArrayList;
import java.util.List;

public class LoginService {
    private List<Akun> akunList = new ArrayList<>();

    public LoginService() {
        // Data dummy untuk testing login
        akunList.add(new Admin(100, "owner", "admin123"));
        akunList.add(new Pegawai(1, "agung", "1234", "kasir"));
        akunList.add(new Pegawai(2, "budi", "2222", "koki"));
        akunList.add(new Customer(10, "citra", "c123"));
    }

    public Akun authenticate(String nama, String password) {
        for (Akun akun : akunList) {
            if (akun.getNama().equalsIgnoreCase(nama) && akun.getPassword().equals(password)) {
                return akun;
            }
        }
        return null;
    }
}
