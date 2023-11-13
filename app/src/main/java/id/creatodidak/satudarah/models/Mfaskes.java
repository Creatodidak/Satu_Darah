package id.creatodidak.satudarah.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Mfaskes{

	@SerializedName("listfaskes")
	private List<ListfaskesItem> listfaskes;

	public void setListfaskes(List<ListfaskesItem> listfaskes){
		this.listfaskes = listfaskes;
	}

	public List<ListfaskesItem> getListfaskes(){
		return listfaskes;
	}
}