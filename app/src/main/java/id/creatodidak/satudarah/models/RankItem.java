package id.creatodidak.satudarah.models;

import com.google.gson.annotations.SerializedName;

public class RankItem{

	@SerializedName("member_id")
	private String memberId;

	@SerializedName("jeniskelamin")
	private String jeniskelamin;

	@SerializedName("dus")
	private String dus;

	@SerializedName("kec")
	private String kec;

	@SerializedName("kab")
	private String kab;

	@SerializedName("tanggallahir")
	private String tanggallahir;

	@SerializedName("nik")
	private String nik;

	@SerializedName("total")
	private int total;

	@SerializedName("des")
	private String des;

	@SerializedName("nama")
	private String nama;

	@SerializedName("foto")
	private String foto;

	@SerializedName("golongandarah")
	private String golongandarah;

	@SerializedName("prov")
	private String prov;

	@SerializedName("email")
	private String email;

	public void setMemberId(String memberId){
		this.memberId = memberId;
	}

	public String getMemberId(){
		return memberId;
	}

	public void setJeniskelamin(String jeniskelamin){
		this.jeniskelamin = jeniskelamin;
	}

	public String getJeniskelamin(){
		return jeniskelamin;
	}

	public void setDus(String dus){
		this.dus = dus;
	}

	public String getDus(){
		return dus;
	}

	public void setKec(String kec){
		this.kec = kec;
	}

	public String getKec(){
		return kec;
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

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
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

	public void setGolongandarah(String golongandarah){
		this.golongandarah = golongandarah;
	}

	public String getGolongandarah(){
		return golongandarah;
	}

	public void setProv(String prov){
		this.prov = prov;
	}

	public String getProv(){
		return prov;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}