package id.creatodidak.satudarah.models;

import com.google.gson.annotations.SerializedName;

public class EventsItem{

	@SerializedName("event_id")
	private String eventId;

	@SerializedName("utd")
	private String utd;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("dimulai")
	private String dimulai;

	@SerializedName("lokasi")
	private String lokasi;

	@SerializedName("selesai")
	private String selesai;

	@SerializedName("penyelenggara")
	private String penyelenggara;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("judul")
	private String judul;

	@SerializedName("target")
	private String target;

	@SerializedName("terpenuhi")
	private String terpenuhi;

	public void setEventId(String eventId){
		this.eventId = eventId;
	}

	public String getEventId(){
		return eventId;
	}

	public void setUtd(String utd){
		this.utd = utd;
	}

	public String getUtd(){
		return utd;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setDimulai(String dimulai){
		this.dimulai = dimulai;
	}

	public String getDimulai(){
		return dimulai;
	}

	public void setLokasi(String lokasi){
		this.lokasi = lokasi;
	}

	public String getLokasi(){
		return lokasi;
	}

	public void setSelesai(String selesai){
		this.selesai = selesai;
	}

	public String getSelesai(){
		return selesai;
	}

	public void setPenyelenggara(String penyelenggara){
		this.penyelenggara = penyelenggara;
	}

	public String getPenyelenggara(){
		return penyelenggara;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setJudul(String judul){
		this.judul = judul;
	}

	public String getJudul(){
		return judul;
	}

	public void setTarget(String target){
		this.target = target;
	}

	public String getTarget(){
		return target;
	}

	public void setTerpenuhi(String terpenuhi){
		this.terpenuhi = terpenuhi;
	}

	public String getTerpenuhi(){
		return terpenuhi;
	}
}