package model;

public class Meja {

    public enum StatusMeja {
        KOSONG, TERISI
    }

    private int nomor;
    private StatusMeja status;

    public Meja(int nomor) {
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
        this.status = status; 
    }

    @Override
    public String toString() {
        return "Meja " + nomor + " - Status: " + status;
    }
}