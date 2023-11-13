package id.creatodidak.satudarah.models;
import com.google.gson.annotations.SerializedName;

public class MRank {
    @SerializedName("rank")
    private String rank;
    @SerializedName("nama")
    private String nama;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("point")
    private String point;

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getGambar() {
        return gambar;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPoint() {
        return point;
    }
}
