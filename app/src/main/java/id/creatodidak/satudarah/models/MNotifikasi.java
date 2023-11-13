package id.creatodidak.satudarah.models;

public class MNotifikasi {
    private String notif_id;
    private String judul;
    private String isi;
    private String topic;
    private String gambar;
    private String created;
    
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isStatus() {
        if(status == 1){
            return true;
        }else{
            return false;
        }
    }

    
    public String getNotifId() {
        return notif_id;
    }

    public void setNotifId(String notif_id) {
        this.notif_id = notif_id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
