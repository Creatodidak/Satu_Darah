package id.creatodidak.satudarah.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.satudarah.ApiClient;
import id.creatodidak.satudarah.Endpoint;
import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.adapters.DonorReqSayaAdp;
import id.creatodidak.satudarah.models.ListrequestsayaItem;
import id.creatodidak.satudarah.models.MRequestSaya;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarRequestSaya extends AppCompatActivity {
    TextView tvNull3;
    SwipeRefreshLayout sw;
    RecyclerView rv;
    LinearLayoutManager lm;
    DonorReqSayaAdp adp;
    List<ListrequestsayaItem> data = new ArrayList<>();
    Endpoint endpoint;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_request_saya);
        sh = getSharedPreferences("USERDATA", MODE_PRIVATE);
        endpoint = ApiClient.getClient().create(Endpoint.class);

        tvNull3 = findViewById(R.id.tvNull3);
        sw = findViewById(R.id.swDaftar);
        rv = findViewById(R.id.rvDaftar);
        lm = new LinearLayoutManager(this);
        adp = new DonorReqSayaAdp(this, data, new DonorReqSayaAdp.OnItemClickListener() {
            @Override
            public void onDonor(ListrequestsayaItem item) {

            }
        });

        rv.setLayoutManager(lm);
        rv.setAdapter(adp);

        loadReqSaya();

        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadReqSaya();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadReqSaya() {
        sw.setRefreshing(true);
        data.clear();
        adp.notifyDataSetChanged();

        Call<MRequestSaya> call = endpoint.getReqDarahSaya(sh.getString("memberid", ""));
        call.enqueue(new Callback<MRequestSaya>() {
            @Override
            public void onResponse(Call<MRequestSaya> call, Response<MRequestSaya> response) {
                sw.setRefreshing(false);
                if(response.body() != null && response.isSuccessful()){
                    tvNull3.setVisibility(View.GONE);
                    data.addAll(response.body().getListrequestsaya());
                    adp.notifyDataSetChanged();
                }else{
                    tvNull3.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<MRequestSaya> call, Throwable t) {
                sw.setRefreshing(false);
                tvNull3.setVisibility(View.VISIBLE);
            }
        });

    }
}