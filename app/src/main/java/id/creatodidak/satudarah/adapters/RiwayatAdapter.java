package id.creatodidak.satudarah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.models.MDataDonorItem;
import id.creatodidak.satudarah.plugin.DateUtils;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {
    private List<MDataDonorItem> riwayatList;
    private Context context;

    public RiwayatAdapter(Context context, List<MDataDonorItem> riwayatList) {
        this.context = context;
        this.riwayatList = riwayatList;
    }

    @NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.riwayat_item, parent, false);
        return new RiwayatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatViewHolder holder, int position) {
        MDataDonorItem riwayatItem = riwayatList.get(position);

        holder.tvJudulRiwayat.setText("DONOR DARAH KE - "+(riwayatList.size()-position));
        holder.tvJumlahRiwayat.setText(riwayatItem.getJumlah()+" Kantong");
        holder.tvTanggalRiwayat.setText(DateUtils.tanggalsajadaricreatedat(riwayatItem.getCreatedAt()));
    }

    @Override
    public int getItemCount() {
        return riwayatList.size();
    }

    public class RiwayatViewHolder extends RecyclerView.ViewHolder {
        public TextView tvJudulRiwayat, tvLokasiRiwayat, tvJumlahRiwayat, tvTanggalRiwayat;

        public RiwayatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudulRiwayat = itemView.findViewById(R.id.tvJudulRiwayat);
            tvLokasiRiwayat = itemView.findViewById(R.id.tvLokasiRiwayat);
            tvJumlahRiwayat = itemView.findViewById(R.id.tvJumlahRiwayat);
            tvTanggalRiwayat = itemView.findViewById(R.id.tvTanggalRiwayat);
        }
    }
}
