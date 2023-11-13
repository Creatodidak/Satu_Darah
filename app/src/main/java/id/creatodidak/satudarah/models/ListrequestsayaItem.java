package id.creatodidak.satudarah.models;

import com.google.gson.annotations.SerializedName;

public class ListrequestsayaItem{

	@SerializedName("utd")
	private String utd;

	@SerializedName("bisadonor")
	private int bisadonor;

	@SerializedName("dilihat")
	private int dilihat;

	@SerializedName("listdonor")
	private Object listdonor;

	@SerializedName("namacp")
	private String namacp;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("diagnosa")
	private String diagnosa;

	@SerializedName("nama")
	private String nama;

	@SerializedName("jumlah")
	private int jumlah;

	@SerializedName("requestid")
	private String requestid;

	@SerializedName("menolakdonor")
	private int menolakdonor;

	@SerializedName("kontakcp")
	private String kontakcp;

	@SerializedName("golongandarah")
	private String golongandarah;

	@SerializedName("faskes")
	private String faskes;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("notifikasi")
	private String notifikasi;

	@SerializedName("terpenuhi")
	private int terpenuhi;

	public void setUtd(String utd){
		this.utd = utd;
	}

	public String getUtd(){
		return utd;
	}

	public void setBisadonor(int bisadonor){
		this.bisadonor = bisadonor;
	}

	public int getBisadonor(){
		return bisadonor;
	}

	public void setDilihat(int dilihat){
		this.dilihat = dilihat;
	}

	public int getDilihat(){
		return dilihat;
	}

	public void setListdonor(Object listdonor){
		this.listdonor = listdonor;
	}

	public Object getListdonor(){
		return listdonor;
	}

	public void setNamacp(String namacp){
		this.namacp = namacp;
	}

	public String getNamacp(){
		return namacp;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setDiagnosa(String diagnosa){
		this.diagnosa = diagnosa;
	}

	public String getDiagnosa(){
		return diagnosa;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setJumlah(int jumlah){
		this.jumlah = jumlah;
	}

	public int getJumlah(){
		return jumlah;
	}

	public void setRequestid(String requestid){
		this.requestid = requestid;
	}

	public String getRequestid(){
		return requestid;
	}

	public void setMenolakdonor(int menolakdonor){
		this.menolakdonor = menolakdonor;
	}

	public int getMenolakdonor(){
		return menolakdonor;
	}

	public void setKontakcp(String kontakcp){
		this.kontakcp = kontakcp;
	}

	public String getKontakcp(){
		return kontakcp;
	}

	public void setGolongandarah(String golongandarah){
		this.golongandarah = golongandarah;
	}

	public String getGolongandarah(){
		return golongandarah;
	}

	public void setFaskes(String faskes){
		this.faskes = faskes;
	}

	public String getFaskes(){
		return faskes;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setNotifikasi(String notifikasi){
		this.notifikasi = notifikasi;
	}

	public String getNotifikasi(){
		return notifikasi;
	}

	public void setTerpenuhi(int terpenuhi){
		this.terpenuhi = terpenuhi;
	}

	public int getTerpenuhi(){
		return terpenuhi;
	}
}