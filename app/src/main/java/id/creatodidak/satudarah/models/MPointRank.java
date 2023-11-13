package id.creatodidak.satudarah.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MPointRank{

	@SerializedName("jumlahpengguna")
	private int jumlahpengguna;

	@SerializedName("rank")
	private List<RankItem> rank;

	public void setJumlahpengguna(int jumlahpengguna){
		this.jumlahpengguna = jumlahpengguna;
	}

	public int getJumlahpengguna(){
		return jumlahpengguna;
	}

	public void setRank(List<RankItem> rank){
		this.rank = rank;
	}

	public List<RankItem> getRank(){
		return rank;
	}
}