package id.creatodidak.satudarah.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MRequestDarah{

	@SerializedName("jumlah")
	private int jumlah;

	@SerializedName("listrequest")
	private List<ListrequestItem> listrequest;

	@SerializedName("tanggaldonorterakhir")
	private String last;

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public void setJumlah(int jumlah){
		this.jumlah = jumlah;
	}

	public int getJumlah(){
		return jumlah;
	}

	public void setListrequest(List<ListrequestItem> listrequest){
		this.listrequest = listrequest;
	}

	public List<ListrequestItem> getListrequest(){
		return listrequest;
	}
}