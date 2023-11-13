package id.creatodidak.satudarah.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MRequestSaya{

	@SerializedName("jumlah")
	private int jumlah;

	@SerializedName("listrequestsaya")
	private List<ListrequestsayaItem> listrequestsaya;

	public void setJumlah(int jumlah){
		this.jumlah = jumlah;
	}

	public int getJumlah(){
		return jumlah;
	}

	public void setListrequestsaya(List<ListrequestsayaItem> listrequestsaya){
		this.listrequestsaya = listrequestsaya;
	}

	public List<ListrequestsayaItem> getListrequestsaya(){
		return listrequestsaya;
	}
}