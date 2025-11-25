# UASLabPBOB_4

# 🍽️ Sistem Manajemen Pesanan Restoran (Java)

Proyek ini merupakan implementasi **UAS Praktikum Pemrograman Berorientasi Objek (PBO) B 2025**.  
Aplikasi ini dibuat menggunakan bahasa **Java** dengan konsep **Object-Oriented Programming (OOP)**.

## 👥 Anggota Kelompok
| Nama | NIM |
|------|-----|
| Inayah Kamila Nurman | 2408107010060 |
| Teuku Fikram Al Syahbanna | 2408107010044 | 
| Ahmad Daniel Chalid | 2408107010061 | 
| Cut Mutia Rahmah | 2408107010062 |

---

## 🎯 Tujuan Proyek
Membuat sistem yang dapat mengelola aktivitas restoran mulai dari:
- Pengelolaan **menu makanan dan minuman**
- **Pemesanan pelanggan**
- **Proses transaksi dan pembayaran**
- **Pencetakan struk pembayaran**

---

## ⚙️ Fitur Utama

### 👨‍🍳 Role Pegawai
- **Pelayan:** membuat pesanan baru untuk customer  
- **Koki:** melihat dan memperbarui status pesanan  
- **Kasir:** memproses pembayaran dan mencetak struk  

### 🧍‍♂️ Role Customer
- Melihat daftar menu  
- Melakukan pemesanan melalui pelayan  
- Melakukan pembayaran dengan berbagai metode (Cash, Card, QRIS)

---

```
## 🧩 Struktur Package
src/
├── model/
│ ├── Akun.java
│ ├── Customer.java
│ ├── Pegawai.java
│ ├── MenuItem.java
│ ├── Makanan.java
│ ├── Minuman.java
│ ├── DetailPesanan.java
│ ├── Pesanan.java
│ ├── Meja.java
│ ├── Transaksi.java
│ └── Struk.java
└── service/
├── Pembayaran.java
├── CashPayment.java
├── CardPayment.java
├── QRISPayment.java
└── RestaurantSystem.java
└── Gui/
    ├── LoginFrame.java
    ├── DashboardFrame.java
    ├── KasirFrame.java
    ├── KokiFrame.java
    ├── PelayanFrame.java
    ├── MenuFrame.java
    ├── OrderFrame.java
    ├── PaymentFrame.java
    └── CustomerFrame.java
```
---

## 💡 Konsep OOP yang Diterapkan

| Konsep | Implementasi |
|--------|---------------|
| **Encapsulation** | Penggunaan atribut `private` dengan getter & setter pada setiap class model |
| **Inheritance** | `Pegawai` & `Customer` merupakan turunan dari class `Akun` |
| **Polymorphism** | Implementasi interface `Pembayaran` pada `CashPayment`, `CardPayment`, dan `QRISPayment` |
| **Abstraction** | `Akun` dan `MenuItem` didefinisikan sebagai class abstrak |
| **Exception Handling** | Validasi input dan error pembayaran (misal uang kurang atau nomor kartu tidak valid) |
| **Collection (ArrayList)** | Menyimpan daftar menu, pesanan, dan pegawai di `RestaurantSystem` |

---

## 🧾 Alur Sistem
1. Pengguna **login** ke sistem  
2. Pelayan membuat pesanan baru dan memilih meja  
3. Koki mengubah status pesanan menjadi *selesai dimasak*  
4. Kasir memproses pembayaran (Cash/Card/QRIS)  
5. Sistem mencetak struk transaksi  

---

## 💻 Cara Menjalankan Program
1. Clone repository:
   ```bash
   git clone https://github.com/T-Fikram/UASLabPBOB_4.git
   cd UASLabPBOB_4

2. Compile Semua File:
   ```bash
   javac model/*.java service/*.java Main.java

3. Jalankan:
   ```bash
   java Main


