package id.creatodidak.satudarah.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.satudarah.ApiClient;
import id.creatodidak.satudarah.Endpoint;
import id.creatodidak.satudarah.Login;
import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.adapters.DonorReqAdp;
import id.creatodidak.satudarah.models.ListrequestItem;
import id.creatodidak.satudarah.models.MRequestDarah;
import id.creatodidak.satudarah.models.MResponse;
import id.creatodidak.satudarah.plugin.CDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PermohonanDarahSegar extends AppCompatActivity {
    Endpoint endpoint;
    SwipeRefreshLayout sr;
    RecyclerView rv;
    CardView btAdd, btList;
    LinearLayoutManager lm;
    DonorReqAdp adp;
    List<ListrequestItem> data = new ArrayList<>();
    TextView tvNull, tvTotals;
    CardView xxx;
    SharedPreferences sh;
    String last = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permohonan_darah_segar);
        endpoint = ApiClient.getClient().create(Endpoint.class);
        sh = getSharedPreferences("USERDATA", MODE_PRIVATE);
        lm = new LinearLayoutManager(this);
        sr = findViewById(R.id.swiperefresh);
        rv = findViewById(R.id.rvRequest);
        btAdd = findViewById(R.id.btAddRequest);
        btList = findViewById(R.id.btListRequestSaya);
        tvNull = findViewById(R.id.tvNull);
        tvTotals = findViewById(R.id.tvTotals);
        xxx = findViewById(R.id.xxx);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PermohonanDarahSegar.this, RequestDarah.class);
                startActivity(i);
            }
        });

        btList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PermohonanDarahSegar.this, DaftarRequestSaya.class);
                startActivity(i);
            }
        });
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadReqData();
            }
        });
        loadReqData();

    }

    private void responserequest(String memberid, String requestid) {
        AlertDialog alerts = CDialog.up(
                PermohonanDarahSegar.this,
                "Memproses...",
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

        Call<MResponse> call = endpoint.resprequest(memberid, requestid);
        call.enqueue(new Callback<MResponse>() {
            @Override
            public void onResponse(Call<MResponse> call, Response<MResponse> response) {
                alerts.dismiss();
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    CDialog.up(
                            PermohonanDarahSegar.this,
                            "Informasi",
                            "Berhasil Meresponse, silahkan konfirmasi jika anda sudah melakukan donor darah di beranda pada Daftar Rencana Donor Saya!",
                            false, false, false,
                            "", "MENGERTI", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    loadReqData();
                                }

                                @Override
                                public void onOpt2(AlertDialog alert) {

                                }

                                @Override
                                public void onCancel(AlertDialog alert) {

                                }
                            }
                    ).show();
                }else{
                    CDialog.up(
                            PermohonanDarahSegar.this,
                            "Informasi",
                            "Gagal memanggil server, silahkan ulangi!",
                            false, false, false,
                            "", "ULANGI", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
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
            public void onFailure(Call<MResponse> call, Throwable t) {
                alerts.dismiss();
                CDialog.up(
                        PermohonanDarahSegar.this,
                        "Informasi",
                        "Gagal memanggil server, silahkan periksa jaringan internet anda!",
                        false, false, false,
                        "", "ULANGI", "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
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

    @SuppressLint("NotifyDataSetChanged")
    private void loadReqData() {
        SharedPreferences sh = getSharedPreferences("USERDATA", MODE_PRIVATE);
        xxx.setVisibility(View.GONE);

        data.clear();
        if(adp != null){
            adp.notifyDataSetChanged();
        }

        sr.setRefreshing(true);
        Call<MRequestDarah> call = endpoint.getRequestDarah(sh.getString("memberid", "0"));
        call.enqueue(new Callback<MRequestDarah>() {
            @Override
            public void onResponse(Call<MRequestDarah> call, Response<MRequestDarah> response) {
                sr.setRefreshing(false);
                if(response.isSuccessful() && response.body() != null){
                    xxx.setVisibility(View.VISIBLE);
                    tvTotals.setText(response.body().getListrequest().size()+" Permintaan");
                    data.addAll(response.body().getListrequest());
                    last = response.body().getLast();

                    fetchRV();

                    if(response.body().getListrequest().size() == 0){
                        tvNull.setVisibility(View.VISIBLE);
                    }else{
                        tvNull.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<MRequestDarah> call, Throwable t) {
                sr.setRefreshing(false);
            }
        });
    }

    private void fetchRV() {
        adp = new DonorReqAdp(this, last, data, new DonorReqAdp.OnItemClickListener() {
            @Override
            public void onDonor(ListrequestItem item) {
                CDialog.up(
                        PermohonanDarahSegar.this,
                        "Konfirmasi",
                        "Apakah anda yakin untuk membantu "+item.getNama().toUpperCase()+"?",
                        true, false, false,
                        "BATAL", "YA", "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                responserequest(sh.getString("memberid", "0"), item.getRequestid());
                            }

                            @Override
                            public void onOpt2(AlertDialog alert) {

                            }

                            @Override
                            public void onCancel(AlertDialog alert) {
                                alert.dismiss();
                            }
                        }
                ).show();
            }
        });
        rv.setLayoutManager(lm);
        rv.setAdapter(adp);
        rv.setDrawingCacheEnabled(true);
        rv.setItemViewCacheSize(50);
    }
}