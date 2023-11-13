package id.creatodidak.satudarah.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MDataPengguna{

	@SerializedName("msg")
	private String msg;

	@SerializedName("userdata")
	private List<Muserdata> userdata;

	@SerializedName("status")
	private boolean status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setUserdata(List<Muserdata> userdata){
		this.userdata = userdata;
	}

	public List<Muserdata> getUserdata(){
		return userdata;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}