package id.creatodidak.satudarah.models;

import com.google.gson.annotations.SerializedName;

public class ListutdItem{

	@SerializedName("nama")
	private String nama;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("id_faskes")
	private String idFaskes;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("id_utd")
	private String idUtd;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setIdFaskes(String idFaskes){
		this.idFaskes = idFaskes;
	}

	public String getIdFaskes(){
		return idFaskes;
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

	public void setIdUtd(String idUtd){
		this.idUtd = idUtd;
	}

	public String getIdUtd(){
		return idUtd;
	}
}