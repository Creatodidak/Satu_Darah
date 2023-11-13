package id.creatodidak.satudarah.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.satudarah.ApiClient;
import id.creatodidak.satudarah.Endpoint;
import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.adapters.AgendaAdapter;
import id.creatodidak.satudarah.models.EventsItem;
import id.creatodidak.satudarah.models.MAgenda;
import id.creatodidak.satudarah.models.Mevents;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendaGiatDonorDarah extends AppCompatActivity {
    RecyclerView rvAgenda;
    AgendaAdapter adp;
    List<EventsItem> data = new ArrayList<>();
    LinearLayoutManager lm;
    TextView tvNoAgenda;
    Endpoint endpoint;
    SwipeRefreshLayout sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_giat_donor_darah);
        endpoint = ApiClient.getClient().create(Endpoint.class);
        rvAgenda = findViewById(R.id.rvAgenda);
        tvNoAgenda = findViewById(R.id.tvNoAgenda);
        sw = findViewById(R.id.sw);

        adp = new AgendaAdapter(this, data);
        lm = new LinearLayoutManager(this);

        rvAgenda.setLayoutManager(lm);
        rvAgenda.setAdapter(adp);
        rvAgenda.setItemViewCacheSize(50);
        rvAgenda.setDrawingCacheEnabled(true);

        loadAgenda();

        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadAgenda();
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadAgenda() {
        sw.setRefreshing(true);

        data.clear();
        adp.notifyDataSetChanged();

        Call<Mevents> call = endpoint.dataEvent();
        call.enqueue(new Callback<Mevents>() {
            @Override
            public void onResponse(Call<Mevents> call, Response<Mevents> response) {
                sw.setRefreshing(false);
                if(response.body() != null && response.isSuccessful()){
                    if(!response.body().getEvents().isEmpty()) {
                        data.addAll(response.body().getEvents());
                        adp.notifyDataSetChanged();
                        tvNoAgenda.setVisibility(View.GONE);
                    }else{
                        tvNoAgenda.setVisibility(View.VISIBLE);
                        tvNoAgenda.setText("BELUM ADA DATA\nSILAHKAN SWIPE KE BAWAH UNTUK REFRESH DATA");
                    }
                }else{
                    tvNoAgenda.setVisibility(View.VISIBLE);
                    tvNoAgenda.setText("BELUM ADA DATA\nSILAHKAN SWIPE KE BAWAH UNTUK REFRESH DATA");
                }
            }

            @Override
            public void onFailure(Call<Mevents> call, Throwable t) {
                sw.setRefreshing(false);
                tvNoAgenda.setVisibility(View.VISIBLE);
                tvNoAgenda.setText("BELUM ADA DATA\nSILAHKAN SWIPE KE BAWAH UNTUK REFRESH DATA");
            }
        });
    }
}