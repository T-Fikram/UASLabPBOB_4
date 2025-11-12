package model;

public class Meja {

    public enum StatusMeja {
        KOSONG, TERISI
    }

    private int nomor;
    private StatusMeja status;

    public Meja(int nomor) {
        if (nomor <= 0) {
            throw new IllegalArgumentException("Nomor meja harus lebih dari 0");
        }
        this.nomor = nomor;
        this.status = StatusMeja.KOSONG; // default
    }

    public int getNomor() {
        return nomor;
    }

    public StatusMeja getStatus() {
        return status;
    }

    public void setStatus(StatusMeja status) {
        if (status == null) {
            throw new IllegalArgumentException("Status meja tidak boleh null");
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return "Meja " + nomor + " - Status: " + status;
    }
}