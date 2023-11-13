package id.creatodidak.satudarah.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MWilayah{

	@SerializedName("wilayah")
	private List<WilayahItem> wilayah;

	public void setWilayah(List<WilayahItem> wilayah){
		this.wilayah = wilayah;
	}

	public List<WilayahItem> getWilayah(){
		return wilayah;
	}
}