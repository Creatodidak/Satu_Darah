package id.creatodidak.satudarah.models;

import com.google.gson.annotations.SerializedName;

public class MRiwayat {
//    holder.tvJudulRiwayat.setText(riwayatItem.getJudul())
//        holder.tvLokasiRiwayat.setText(riwayatItem.getLokasi())
//        holder.tvJumlahRiwayat.setText(riwayatItem.getJumlah())
//        holder.tvTanggalRiwayat.setText(riwayatItem.getTanggal())

    @SerializedName("id")
    private String id;

    @SerializedName("judul")
    private String judul;


    @SerializedName("lokasi")
    private String lokasi;

    @SerializedName("jumlah")
    private String jumlah;

    @SerializedName("tanggal")
    private String tanggal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
