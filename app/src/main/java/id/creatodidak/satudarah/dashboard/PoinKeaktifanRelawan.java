package id.creatodidak.satudarah.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.satudarah.ApiClient;
import id.creatodidak.satudarah.Endpoint;
import id.creatodidak.satudarah.Login;
import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.adapters.RankAdapter;
import id.creatodidak.satudarah.models.MPointRank;
import id.creatodidak.satudarah.models.MRank;
import id.creatodidak.satudarah.models.RankItem;
import id.creatodidak.satudarah.plugin.CDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PoinKeaktifanRelawan extends AppCompatActivity {
    Endpoint endpoint;
    SharedPreferences sh;
    RecyclerView rvRank;
    ImageView ivRanks, imageView8;
    RankAdapter adp;
    List<MRank> data = new ArrayList<>();
    LinearLayoutManager lm;
    LinearLayout number1;
    TextView tvNameRanks, tvPointRanks, textView9, textView8, tvNoAgenda2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poin_keaktifan_relawan);
        sh = getSharedPreferences("USERDATA", MODE_PRIVATE);
        endpoint = ApiClient.getClient().create(Endpoint.class);
        number1 = findViewById(R.id.number1);
        rvRank = findViewById(R.id.rvRank);
        ivRanks = findViewById(R.id.ivRanks);
        tvNameRanks = findViewById(R.id.tvNameRanks);
        tvPointRanks = findViewById(R.id.tvPointRanks);
        textView9 = findViewById(R.id.textView9);
        textView8 = findViewById(R.id.textView8);
        imageView8 = findViewById(R.id.imageView8);
        tvNoAgenda2 = findViewById(R.id.tvNoAgenda2);
        Glide.with(PoinKeaktifanRelawan.this)
                .load(sh.getString("foto", null))
                .circleCrop()
                .placeholder(R.drawable.mrdonor)
                .error(R.drawable.mrdonor)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView8);
        loadRank();
    }

    private void loadRank() {
        AlertDialog alerts = CDialog.up(
                PoinKeaktifanRelawan.this,
                "Memuat data...",
                "Sedang mendaftar, harap tunggu....",
                false, false, true,
                "", "", "",
                new CDialog.AlertDialogListener() {
                    @Override
                    public void onOpt1(AlertDialog alert) {
                    }

                    @Override
                    public void onOpt2(AlertDialog alert) {

                    }

                    @Override
                    public void onCancel(AlertDialog alert) {

                    }
                }
        );

        alerts.show();
        Call<MPointRank> call = endpoint.cekGlobalRank();
        call.enqueue(new Callback<MPointRank>() {
            @Override
            public void onResponse(Call<MPointRank> call, Response<MPointRank> response) {
                alerts.dismiss();

                if(response.body() != null){
                    List<RankItem> rank = response.body().getRank();
                    textView8.setText("dari "+response.body().getJumlahpengguna()+" Relawan");
                    if(rank.isEmpty()){
                        number1.setVisibility(View.GONE);
                        tvNoAgenda2.setVisibility(View.VISIBLE);
                    }else{
                        number1.setVisibility(View.VISIBLE);
                        tvNoAgenda2.setVisibility(View.GONE);
                    }
                    for (int i = 0; i < rank.size(); i++) {
                        if(i != 0){
                            if(rank.get(i).getTotal() >= 20){
                                MRank r = new MRank();
                                r.setRank("#"+(i+1));
                                r.setPoint(String.valueOf(rank.get(i).getTotal())+" Poin");
                                r.setNama(rank.get(i).getNama());
                                r.setGambar(rank.get(i).getFoto());

                                data.add(r);
                            }
                        }else{
                            Glide.with(PoinKeaktifanRelawan.this)
                                    .load(rank.get(i).getFoto())
                                    .circleCrop()
                                    .placeholder(R.drawable.mrdonor)
                                    .error(R.drawable.mrdonor)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivRanks);

                            tvPointRanks.setText(rank.get(i).getTotal()+" Poin");
                            tvNameRanks.setText(rank.get(i).getNama());
                        }
                    }

                    adp = new RankAdapter(data, PoinKeaktifanRelawan.this);
                    lm = new LinearLayoutManager(PoinKeaktifanRelawan.this);
                    rvRank.setLayoutManager(lm);
                    rvRank.setAdapter(adp);
                    rvRank.setDrawingCacheEnabled(true);
                    rvRank.setItemViewCacheSize(15);

                    for(int i = 0; i < rank.size(); i++){
                        if(rank.get(i).getMemberId().equals(sh.getString("memberid", "0"))){
                            textView9.setText("#"+(i+1));
                            break;
                        }else{
                            textView9.setText("--");
                        }
                    }
                }else{
                    CDialog.up(
                            PoinKeaktifanRelawan.this,
                            "Informasi",
                            "Gagal memanggil server, silahkan ulangi!",
                            false, false, false,
                            "", "ULANGI", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    finish();
                                }

                                @Override
                                public void onOpt2(AlertDialog alert) {

                                }

                                @Override
                                public void onCancel(AlertDialog alert) {

                                }
                            }
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<MPointRank> call, Throwable t) {
                alerts.dismiss();
                CDialog.up(
                        PoinKeaktifanRelawan.this,
                        "Informasi",
                        "Gagal memanggil server, silahkan periksa jaringan internet anda!",
                        false, false, false,
                        "", "ULANGI", "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                finish();
                            }

                            @Override
                            public void onOpt2(AlertDialog alert) {

                            }

                            @Override
                            public void onCancel(AlertDialog alert) {

                            }
                        }
                ).show();
            }
        });
    }
}