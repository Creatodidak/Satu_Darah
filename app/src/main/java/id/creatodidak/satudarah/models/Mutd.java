package id.creatodidak.satudarah.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Mutd{

	@SerializedName("listutd")
	private List<ListutdItem> listutd;

	public void setListutd(List<ListutdItem> listutd){
		this.listutd = listutd;
	}

	public List<ListutdItem> getListutd(){
		return listutd;
	}
}