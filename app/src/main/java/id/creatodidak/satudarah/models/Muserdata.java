package id.creatodidak.satudarah.models;

import com.google.gson.annotations.SerializedName;

public class Muserdata {

	@SerializedName("member_id")
	private String memberId;

	@SerializedName("whatsapp")
	private String whatsapp;

	@SerializedName("dus")
	private String dus;

	@SerializedName("qrcode")
	private String qrcode;

	@SerializedName("kec")
	private String kec;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("kab")
	private String kab;

	@SerializedName("tanggallahir")
	private String tanggallahir;

	@SerializedName("nik")
	private String nik;

	@SerializedName("password")
	private String password;

	@SerializedName("des")
	private String des;

	@SerializedName("nama")
	private String nama;

	@SerializedName("foto")
	private String foto;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("golongandarah")
	private String golongandarah;

	@SerializedName("id")
	private int id;

	@SerializedName("prov")
	private String prov;

	public void setMemberId(String memberId){
		this.memberId = memberId;
	}

	public String getMemberId(){
		return memberId;
	}

	public void setWhatsapp(String whatsapp){
		this.whatsapp = whatsapp;
	}

	public String getWhatsapp(){
		return whatsapp;
	}

	public void setDus(String dus){
		this.dus = dus;
	}

	public String getDus(){
		return dus;
	}

	public void setQrcode(String qrcode){
		this.qrcode = qrcode;
	}

	public String getQrcode(){
		return qrcode;
	}

	public void setKec(String kec){
		this.kec = kec;
	}

	public String getKec(){
		return kec;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setKab(String kab){
		this.kab = kab;
	}

	public String getKab(){
		return kab;
	}

	public void setTanggallahir(String tanggallahir){
		this.tanggallahir = tanggallahir;
	}

	public String getTanggallahir(){
		return tanggallahir;
	}

	public void setNik(String nik){
		this.nik = nik;
	}

	public String getNik(){
		return nik;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setDes(String des){
		this.des = des;
	}

	public String getDes(){
		return des;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setGolongandarah(String golongandarah){
		this.golongandarah = golongandarah;
	}

	public String getGolongandarah(){
		return golongandarah;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setProv(String prov){
		this.prov = prov;
	}

	public String getProv(){
		return prov;
	}
}