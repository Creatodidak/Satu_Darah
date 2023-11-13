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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.satudarah.ApiClient;
import id.creatodidak.satudarah.Endpoint;
import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.models.ListrequestItem;
import id.creatodidak.satudarah.models.MDataDonor;
import id.creatodidak.satudarah.models.MDataDonorItem;
import id.creatodidak.satudarah.plugin.DateUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonorReqAdp extends RecyclerView.Adapter<DonorReqAdp.DonorReqViewHolder> {
    private Context context;
    private List<ListrequestItem> donorRequestItems;

    private OnItemClickListener onItemClickListener;
    private SharedPreferences sh;
    private Endpoint endpoint;

    String last;
    boolean isExist = false;
    boolean isMe = false;

    public DonorReqAdp(Context context, String last, List<ListrequestItem> donorRequestItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.donorRequestItems = donorRequestItems;
        this.onItemClickListener = onItemClickListener;
        this.last = last;
        sh = context.getSharedPreferences("USERDATA", Context.MODE_PRIVATE);
        for(ListrequestItem i : donorRequestItems){
            if(i.getDonor() != null && i.getDonor().contains(sh.getString("memberid", ""))){
                isExist = true;
                break;
            }
        }

        for(ListrequestItem i : donorRequestItems){
            if(i.getListterpenuhi() != null && i.getListterpenuhi().contains(sh.getString("memberid", ""))){
                isMe = true;
                break;
            }
        }
    }

    @NonNull
    @Override
    public DonorReqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_item, parent, false);
        return new DonorReqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorReqViewHolder holder, int position) {
        if(last != null){
            ListrequestItem item = donorRequestItems.get(position);
            float terpenuhi = (float) item.getTerpenuhi();
            float jumlah = (float) item.getJumlah();
            float bagi = terpenuhi / jumlah;
            int percent = (int) (bagi*100);

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
            holder.btRDonor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onDonor(item);
                }
            });

            if(!item.getRequestid().contains(sh.getString("memberid", ""))) {
                String req = item.getGolongandarah().replace("-", "");
                String fReq = req.replace("+", "");

                String my = sh.getString("golongandarah", "").replace("-", "");
                if(fReq.equals(my.replace("+", ""))){
                    if (isExist) {
                        if(isMe){
                            holder.btRDonor.setVisibility(View.GONE);
                            holder.btRDonor2.setVisibility(View.VISIBLE);
                            holder.btRDonor2.setText("ANDA BELUM LAYAK DONOR");
                        }else{
                            holder.btRDonor.setVisibility(View.GONE);
                            holder.btRDonor2.setVisibility(View.VISIBLE);
                            holder.btRDonor2.setText("ANDA SUDAH ADA RENCANA DONOR");
                        }
                    } else {
                        if (DateUtils.ceklayakdonor(last)) {
                            if (item.getDonor() != null && item.getDonor().contains(sh.getString("memberid", ""))) {
                                holder.btRDonor.setVisibility(View.GONE);
                                holder.btRDonor2.setVisibility(View.VISIBLE);
                                holder.btRDonor2.setText("ANDA SUDAH ADA RENCANA DONOR");
                            } else {
                                holder.btRDonor.setVisibility(View.VISIBLE);
                                holder.btRDonor2.setVisibility(View.GONE);
                            }
                        } else {
                            holder.btRDonor.setVisibility(View.GONE);
                            holder.btRDonor2.setVisibility(View.VISIBLE);
                            holder.btRDonor2.setText("ANDA BELUM LAYAK DONOR");
                        }
                    }
                }else{
                    holder.btRDonor.setVisibility(View.GONE);
                    holder.btRDonor2.setVisibility(View.GONE);
                }
            }else{
                holder.btRDonor.setVisibility(View.GONE);
                holder.btRDonor2.setVisibility(View.GONE);
            }

        }else{
            holder.cardView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return donorRequestItems.size();
    }

    public class DonorReqViewHolder extends RecyclerView.ViewHolder {
        TextView tvRJudul, tvRPasien, tvRAlamat, tvRGoldar, tvRJumlah, tvRDiagnosa, tvRFaskes, tvTglPermintaan, tvRDilihat, tvRDirespon, tvRProgress;
        CardView cardView;
        ProgressBar pbR;

        Button btRDonor, btRDonor2;

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
            btRDonor = itemView.findViewById(R.id.btRDonor);
            btRDonor2 = itemView.findViewById(R.id.btRDonor2);
        }
    }

    public interface OnItemClickListener{
        void onDonor(ListrequestItem item);
    }
}
