package id.creatodidak.satudarah.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.models.EventsItem;
import id.creatodidak.satudarah.models.MAgenda;
import id.creatodidak.satudarah.plugin.DateUtils;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.AgendaViewHolder> {
    private Context context;
    private List<EventsItem> agendaItemList;

    public AgendaAdapter(Context context, List<EventsItem> agendaItemList) {
        this.context = context;
        this.agendaItemList = agendaItemList;
    }

    @NonNull
    @Override
    public AgendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.agenda_item, parent, false);
        return new AgendaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaViewHolder holder, int position) {
        EventsItem d = agendaItemList.get(position);

        holder.tvJudul.setText(d.getJudul());
        holder.tvLokasi.setText(d.getLokasi());
        holder.tvWaktu.setText(DateUtils.tanggaldaricreatedatlocal(d.getDimulai()));
        holder.tvWaktu2.setText(DateUtils.tanggaldaricreatedatlocal(d.getSelesai()));
        holder.tvJumlahDarah.setText(d.getTarget()+" Kantong");
        String sisa = DateUtils.sisahari(d.getDimulai(), d.getSelesai());

        if(sisa.equals("SELESAI")){
            holder.tvSisaHari.setText("--");
            holder.tvSisaHari.setTextColor(Color.parseColor("#b1140f"));
            holder.tvKetWaktu.setText(sisa);
            holder.tvKetWaktu.setTextColor(Color.parseColor("#b1140f"));
        }else if(sisa.equals("HARI INI")){
            holder.tvSisaHari.setText("--");
            holder.tvSisaHari.setTextColor(Color.parseColor("#FF388E3C"));
            holder.tvKetWaktu.setText(sisa);
            holder.tvKetWaktu.setTextColor(Color.parseColor("#FF388E3C"));
        }else{
            if(sisa.contains("hari")){
                holder.tvSisaHari.setText(sisa.replace("hari", ""));
                holder.tvSisaHari.setTextColor(Color.parseColor("#000000"));
                holder.tvKetWaktu.setText("HARI LAGI");
                holder.tvKetWaktu.setTextColor(Color.parseColor("#000000"));
            }else{
                holder.tvSisaHari.setText(sisa.replace("jam", ""));
                holder.tvSisaHari.setTextColor(Color.parseColor("#000000"));
                holder.tvKetWaktu.setText("JAM LAGI");
                holder.tvKetWaktu.setTextColor(Color.parseColor("#000000"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return agendaItemList.size();
    }

    public class AgendaViewHolder extends RecyclerView.ViewHolder {
        public TextView tvJudul, tvLokasi, tvWaktu, tvWaktu2, tvJumlahDarah, tvKetWaktu, tvSisaHari;

        public AgendaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvLokasi = itemView.findViewById(R.id.tvLokasi);
            tvWaktu = itemView.findViewById(R.id.tvWaktu);
            tvWaktu2 = itemView.findViewById(R.id.tvWaktu2);
            tvJumlahDarah = itemView.findViewById(R.id.tvJumlahDarah);
            tvKetWaktu = itemView.findViewById(R.id.tvKetWaktu);
            tvSisaHari = itemView.findViewById(R.id.tvSisaHari);
        }
    }
}
