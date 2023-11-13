package id.creatodidak.satudarah.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import id.creatodidak.satudarah.MainActivity;
import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.dashboard.AgendaGiatDonorDarah;
import id.creatodidak.satudarah.dashboard.DaftarRequestSaya;
import id.creatodidak.satudarah.dashboard.PermohonanDarahSegar;
import id.creatodidak.satudarah.dashboard.PoinKeaktifanRelawan;
import id.creatodidak.satudarah.dashboard.RiwayatDonorSaya;
import id.creatodidak.satudarah.databases.DBHelper;
import id.creatodidak.satudarah.models.MNotifikasi;
import id.creatodidak.satudarah.plugin.DateUtils;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifViewHolder> {
    private Context context;
    private List<MNotifikasi> notifList;
    private DBHelper db;

    public NotifAdapter(Context context, List<MNotifikasi> notifList) {
        this.context = context;
        this.notifList = notifList;

        db = new DBHelper(context);
    }

    @NonNull
    @Override
    public NotifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notif_item, parent, false);
        return new NotifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifViewHolder holder, int position) {
        MNotifikasi i = notifList.get(position);
        String aktif = "\uD83D\uDD34";
        String tidakaktif = "\uD83D\uDFE2";
        // Set data to views
        if(i.isStatus()){
            holder.NStatus.setText(tidakaktif);
            holder.NWrapper.setBackgroundColor(Color.parseColor("#ffffff"));
        }else{
            holder.NStatus.setText(aktif);
            holder.NWrapper.setBackgroundColor(Color.parseColor("#ffdddd"));
        }
        holder.NJudul.setText(i.getJudul());
        holder.NIsi.setText(i.getIsi());
        holder.NWaktu.setText(DateUtils.tanggalnotif(i.getCreated()));

        holder.NWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.updStatusNotifboolean(i.getNotifId())){
                    Intent intent = null;
                    if(i.getTopic().equals("umum")){
                        intent = new Intent(context, MainActivity.class);
                    }else if(i.getTopic().equals("request")){
                        intent = new Intent(context, PermohonanDarahSegar.class);
                    }else if(i.getTopic().equals("event")){
                        intent = new Intent(context, AgendaGiatDonorDarah.class);
                    }else if(i.getTopic().equals("riwayat")){
                        intent = new Intent(context, RiwayatDonorSaya.class);
                    }else if(i.getTopic().equals("poin")){
                        intent = new Intent(context, PoinKeaktifanRelawan.class);
                    }else if(i.getTopic().equals("updaterequest")){
                        intent = new Intent(context, DaftarRequestSaya.class);
                    }else if(i.getTopic().equals("statusrequest")){
                        intent = new Intent(context, DaftarRequestSaya.class);
                    }

                    if(intent != null) {
                        context.startActivity(intent);
                    }

                    holder.NStatus.setText(tidakaktif);
                    holder.NWrapper.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifList.size();
    }

    static class NotifViewHolder extends RecyclerView.ViewHolder {
        TextView NStatus, NJudul, NIsi, NWaktu;
        LinearLayout NWrapper;

        NotifViewHolder(View itemView) {
            super(itemView);
            NStatus = itemView.findViewById(R.id.NStatus);
            NJudul = itemView.findViewById(R.id.NJudul);
            NIsi = itemView.findViewById(R.id.NIsi);
            NWaktu = itemView.findViewById(R.id.NWaktu);
            NWrapper = itemView.findViewById(R.id.NWrapper);
        }
    }
}
