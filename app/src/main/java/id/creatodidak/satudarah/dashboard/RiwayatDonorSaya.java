package id.creatodidak.satudarah.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.satudarah.ApiClient;
import id.creatodidak.satudarah.Endpoint;
import id.creatodidak.satudarah.Login;
import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.adapters.RiwayatAdapter;
import id.creatodidak.satudarah.models.MDataDonor;
import id.creatodidak.satudarah.models.MDataDonorItem;
import id.creatodidak.satudarah.models.MRegistrasi;
import id.creatodidak.satudarah.models.MRiwayat;
import id.creatodidak.satudarah.plugin.CDialog;
import id.creatodidak.satudarah.plugin.DateUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatDonorSaya extends AppCompatActivity {
    Endpoint endpoint;
    SharedPreferences sh;
    RecyclerView rv;
    LinearLayoutManager lm;
    RiwayatAdapter adp;
    List<MDataDonorItem> data = new ArrayList<>();
    TextView tvTanggalTerakhir, tvTanggalSelanjutnya;
    LinearLayout lyRiw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_donor_saya);
        endpoint = ApiClient.getClient().create(Endpoint.class);
        sh = getSharedPreferences("USERDATA", MODE_PRIVATE);

        rv = findViewById(R.id.rvRiwayat);
        lm = new LinearLayoutManager(this);
        tvTanggalTerakhir = findViewById(R.id.tvTanggalTerakhir);
        tvTanggalSelanjutnya = findViewById(R.id.tvTanggalSelanjutnya);
        lyRiw = findViewById(R.id.lyRiw);
        lyRiw.setVisibility(View.GONE);
        adp = new RiwayatAdapter(this, data);
        rv.setLayoutManager(lm);
        rv.setAdapter(adp);
        rv.setItemViewCacheSize(50);
        rv.setDrawingCacheEnabled(true);

        loadDataDonor();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadDataDonor() {
        AlertDialog alerts = CDialog.up(
                RiwayatDonorSaya.this,
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

        Call<MDataDonor> call = endpoint.getDataDonor(sh.getString("memberid", "0"));
        call.enqueue(new Callback<MDataDonor>() {
            @Override
            public void onResponse(Call<MDataDonor> call, Response<MDataDonor> response) {
                alerts.dismiss();
                if(response.isSuccessful() && response.body() != null){
                    if(response.body().getMDataDonor() != null){
                        lyRiw.setVisibility(View.GONE);
                        List<MDataDonorItem> d = response.body().getMDataDonor();
                        data.addAll(d);
                        adp.notifyDataSetChanged();

                        tvTanggalTerakhir.setText(DateUtils.tanggalsajadaricreatedat(d.get(0).getCreatedAt()));
                        tvTanggalSelanjutnya.setText(DateUtils.tambahtigabulan(d.get(0).getCreatedAt()));
                    }else{
                        lyRiw.setVisibility(View.VISIBLE);
                        tvTanggalTerakhir.setText("-");
                        tvTanggalSelanjutnya.setText("-");
                    }
                }else{
                    CDialog.up(
                            RiwayatDonorSaya.this,
                            "Informasi",
                            "Gagal mengambil data\nSilahkan ulangi...",
                            false, false, false,
                            "", "ULANGI", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    loadDataDonor();
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
            public void onFailure(Call<MDataDonor> call, Throwable t) {
                alerts.dismiss();
                CDialog.up(
                        RiwayatDonorSaya.this,
                        "Informasi",
                        "Gagal memanggil server, silahkan periksa jaringan internet anda!",
                        false, false, false,
                        "", "ULANGI", "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                RiwayatDonorSaya.this.finish();
                            }

                            @Override
                            public void onOpt2(AlertDialog alert) {

                            }

                            @Override
                            public void onCancel(AlertDialog alert) {

                            }
                        }
                ).show();

                Log.e("ERROR", t.getLocalizedMessage().toString() );
            }

        });
    }
}