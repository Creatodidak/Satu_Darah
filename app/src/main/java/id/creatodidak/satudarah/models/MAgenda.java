package id.creatodidak.satudarah.models;

import com.google.gson.annotations.SerializedName;

public class MAgenda {
    @SerializedName("")
    private String id;

    @SerializedName("")
    private String judul;

    @SerializedName("")
    private String lokasi;

    @SerializedName("")
    private String waktu;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getJudul() {
        return judul;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getWaktu() {
        return waktu;
    }
}
