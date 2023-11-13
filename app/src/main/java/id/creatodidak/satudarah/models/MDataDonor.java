package id.creatodidak.satudarah.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MDataDonor{

	@SerializedName("MDataDonor")
	private List<MDataDonorItem> mDataDonor;

	public void setMDataDonor(List<MDataDonorItem> mDataDonor){
		this.mDataDonor = mDataDonor;
	}

	public List<MDataDonorItem> getMDataDonor(){
		return mDataDonor;
	}
}