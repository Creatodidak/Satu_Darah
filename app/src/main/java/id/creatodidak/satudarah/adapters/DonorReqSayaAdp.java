package id.creatodidak.satudarah.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.satudarah.Endpoint;
import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.models.ListrequestsayaItem;
import id.creatodidak.satudarah.plugin.DateUtils;

public class DonorReqSayaAdp extends RecyclerView.Adapter<DonorReqSayaAdp.DonorReqViewHolder> {
    private Context context;
    private List<ListrequestsayaItem> donorRequestItems;

    private OnItemClickListener onItemClickListener;
    private SharedPreferences sh;
    private Endpoint endpoint;

    public DonorReqSayaAdp(Context context, List<ListrequestsayaItem> donorRequestItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.donorRequestItems = donorRequestItems;
        this.onItemClickListener = onItemClickListener;
        sh = context.getSharedPreferences("USERDATA", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public DonorReqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.requestsaya_item, parent, false);
        return new DonorReqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorReqViewHolder holder, int position) {
        ListrequestsayaItem item = donorRequestItems.get(position);
        float terpenuhi = (float) item.getTerpenuhi();
        float jumlah = (float) item.getJumlah();
        float bagi = terpenuhi / jumlah;
        int percent = (int) (bagi*100);
        Log.i("PERSEN", String.valueOf(percent));

        holder.tvRJudul.setText("PERMINTAAN DARAH SEGAR GOLONGAN "+item.getGolongandarah());
        holder.tvRProgress.setText("Terpenuhi "+item.getTerpenuhi()+" dari "+item.getJumlah());
        holder.tvRPasien.setText(item.getNama().toUpperCase());
        holder.tvRAlamat.setText(item.getAlamat().toUpperCase());
        holder.tvRGoldar.setText(item.getGolongandarah().toUpperCase());
        holder.tvRJumlah.setText(String.valueOf(item.getJumlah())+" Kantong");
        holder.tvRDiagnosa.setText(item.getDiagnosa().toUpperCase());
        holder.tvRFaskes.setText(item.getFaskes().toUpperCase());
        holder.tvTglPermintaan.setText(DateUtils.tanggaldaricreatedat(item.getTanggal()).toUpperCase() );
        holder.tvRDilihat.setText(item.getDilihat()+" KALI");
        holder.tvRDirespon.setText(item.getBisadonor()+" KALI");
        holder.pbR.setProgress(percent);
    }

    @Override
    public int getItemCount() {
        return donorRequestItems.size();
    }

    public class DonorReqViewHolder extends RecyclerView.ViewHolder {
        TextView tvRJudul, tvRPasien, tvRAlamat, tvRGoldar, tvRJumlah, tvRDiagnosa, tvRFaskes, tvTglPermintaan, tvRDilihat, tvRDirespon, tvRProgress;
        CardView cardView;
        ProgressBar pbR;

        public DonorReqViewHolder(View itemView) {
            super(itemView);
            tvRJudul = itemView.findViewById(R.id.tvRJudul);
            tvRPasien = itemView.findViewById(R.id.tvRPasien);
            tvRAlamat = itemView.findViewById(R.id.tvRAlamat);
            tvRGoldar = itemView.findViewById(R.id.tvRGoldar);
            tvRJumlah = itemView.findViewById(R.id.tvRJumlah);
            tvRDiagnosa = itemView.findViewById(R.id.tvRDiagnosa);
            tvRFaskes = itemView.findViewById(R.id.tvRFaskes);
            tvTglPermintaan = itemView.findViewById(R.id.tvTglPermintaan);
            tvRDilihat = itemView.findViewById(R.id.tvRDilihat);
            tvRDirespon = itemView.findViewById(R.id.tvRDirespon);
            cardView = itemView.findViewById(R.id.cvWrapper);
            pbR = itemView.findViewById(R.id.pbR);
            tvRProgress= itemView.findViewById(R.id.tvRProgress);
        }
    }

    public interface OnItemClickListener{
        void onDonor(ListrequestsayaItem item);
    }
}
