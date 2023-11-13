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

import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.adapters.NotifAdapter;
import id.creatodidak.satudarah.databases.DBHelper;
import id.creatodidak.satudarah.models.MNotifikasi;

public class Notifcenter extends AppCompatActivity {
    SwipeRefreshLayout sw;
    RecyclerView rv;
    LinearLayoutManager lm;
    List<MNotifikasi> data = new ArrayList<>();
    NotifAdapter adp;
    DBHelper db;
    TextView tvNull2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifcenter);

        db = new DBHelper(this);
        sw = findViewById(R.id.swNotifikasi);
        rv = findViewById(R.id.rvNotifikasi);
        lm = new LinearLayoutManager(this);
        tvNull2 = findViewById(R.id.tvNull2);
        data = db.getNotifikasi();

        if(data.isEmpty()){
            tvNull2.setVisibility(View.VISIBLE);
        }else{
            tvNull2.setVisibility(View.GONE);
        }

        adp = new NotifAdapter(this, data);
        rv.setLayoutManager(lm);
        rv.setAdapter(adp);

        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNotif();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadNotif() {
        data.clear();
        adp.notifyDataSetChanged();

        List<MNotifikasi> datas = db.getNotifikasi();
        if(datas.isEmpty()){
            sw.setRefreshing(false);
            tvNull2.setVisibility(View.VISIBLE);
        }else{
            sw.setRefreshing(false);
            data.addAll(datas);
            tvNull2.setVisibility(View.GONE);
            adp.notifyDataSetChanged();
        }
    }
}