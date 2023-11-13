package id.creatodidak.satudarah.models;

import com.google.gson.annotations.SerializedName;

public class WilayahItem{

	@SerializedName("des")
	private long des;

	@SerializedName("nama")
	private String nama;

	@SerializedName("kec")
	private int kec;

	@SerializedName("id")
	private long id;

	@SerializedName("prov")
	private int prov;

	@SerializedName("kab")
	private int kab;

	public void setDes(long des){
		this.des = des;
	}

	public long getDes(){
		return des;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setKec(int kec){
		this.kec = kec;
	}

	public int getKec(){
		return kec;
	}

	public void setId(long id){
		this.id = id;
	}

	public long getId(){
		return id;
	}

	public void setProv(int prov){
		this.prov = prov;
	}

	public int getProv(){
		return prov;
	}

	public void setKab(int kab){
		this.kab = kab;
	}

	public int getKab(){
		return kab;
	}
}