package id.creatodidak.satudarah;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.creatodidak.satudarah.adapters.GridLayoutAdapter;
import id.creatodidak.satudarah.dashboard.AgendaGiatDonorDarah;
import id.creatodidak.satudarah.dashboard.AkunSaya;
import id.creatodidak.satudarah.dashboard.EdukasiDonorDarah;
import id.creatodidak.satudarah.dashboard.GantiFotoProfil;
import id.creatodidak.satudarah.dashboard.Notifcenter;
import id.creatodidak.satudarah.dashboard.PermohonanDarahSegar;
import id.creatodidak.satudarah.dashboard.PetaFasilitasKesehatan;
import id.creatodidak.satudarah.dashboard.PoinKeaktifanRelawan;
import id.creatodidak.satudarah.dashboard.RiwayatDonorSaya;
import id.creatodidak.satudarah.dashboard.ScanQR;
import id.creatodidak.satudarah.databases.DBHelper;
import id.creatodidak.satudarah.models.GridLayoutData;
import id.creatodidak.satudarah.models.ListrequestItem;
import id.creatodidak.satudarah.models.MDataDonor;
import id.creatodidak.satudarah.models.MNotifikasi;
import id.creatodidak.satudarah.models.MPointRank;
import id.creatodidak.satudarah.models.MRequestDarah;
import id.creatodidak.satudarah.models.MResponse;
import id.creatodidak.satudarah.models.MyRanks;
import id.creatodidak.satudarah.models.RankItem;
import id.creatodidak.satudarah.plugin.CDialog;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    GridView gm;
    GridLayoutAdapter adp;
    List<GridLayoutData> data = new ArrayList<>();
    List<Integer> gambar = Arrays.asList(R.drawable.m1, R.drawable.m2, R.drawable.m3, R.drawable.m4, R.drawable.m5, R.drawable.m6, R.drawable.m7, R.drawable.m8);
    ImageView fotoProfile, scanqr;
    Endpoint endpoint;
    SharedPreferences sh;
    TextView tvGoldar, tvRencanaJudul, tvRencanaBelumAda, tvPointKeaktifan, tvRencanaJudul2, tvJumlahNotif;
    Button tvRencanaKonfirmasi, tvRencanaBatal;
    List<MNotifikasi> notif = new ArrayList<>();
    private static final int RC_ALL_PERMISSIONS = 123;

    CardView btNotif;
    String reqid;

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() != null) {
                    if(result.getContents().contains("EVENTS")){
                        confirmEvent(result.getContents());
                    }else{
                        reqid = result.getContents();
                        sudahDonor();
                    }
                }
            });

    @SuppressLint("InlinedApi")
    private final String[] allPermissions = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.POST_NOTIFICATIONS,
    };

    private Handler handler = new Handler();
    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            loadNotifikasi();
            handler.postDelayed(this, 5000);
        }
    };

    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestAllPermissions();
        Intent is = getIntent();
        String notifId = is.getStringExtra("notifid");

        if (notifId != null && !notifId.isEmpty()) {
            db.updStatusNotif(notifId);
        }


        sh = getSharedPreferences("USERDATA", MODE_PRIVATE);
        endpoint = ApiClient.getClient().create(Endpoint.class);
        scanqr = findViewById(R.id.scanqr);
        gm = findViewById(R.id.gridmenu);
        fotoProfile = findViewById(R.id.fotoProfile);
        tvGoldar = findViewById(R.id.tvGoldar);
        tvGoldar.setText(sh.getString("golongandarah", "--"));
        tvRencanaJudul = findViewById(R.id.tvRencanaJudul);
        tvRencanaBelumAda = findViewById(R.id.tvRencanaBelumAda);
        tvRencanaKonfirmasi = findViewById(R.id.tvRencanaKonfirmasi);
        tvRencanaBatal = findViewById(R.id.tvRencanaBatal);
        tvPointKeaktifan = findViewById(R.id.tvPointKeaktifan);
        tvRencanaJudul2 = findViewById(R.id.tvRencanaJudul2);
        tvJumlahNotif = findViewById(R.id.tvJumlahNotif);
        btNotif = findViewById(R.id.btNotif);
        db = new DBHelper(this);

        btNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Notifcenter.class);
                startActivity(i);
            }
        });

        fotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GantiFotoProfil.class);
                startActivity(i);
            }
        });


        for (int i = 0; i < gambar.size(); i++) {
            GridLayoutData d = new GridLayoutData();
            d.setId(i+1);
            d.setGambar(gambar.get(i));
            data.add(d);
        }

        adp = new GridLayoutAdapter(this, data, new GridLayoutAdapter.OnClickListener() {
            @Override
            public void onClick(int id) {
                if(id == 1){
                    Intent i = new Intent(MainActivity.this, PoinKeaktifanRelawan.class);
                    startActivity(i);
                }else if(id == 2){
                    Intent i = new Intent(MainActivity.this, PermohonanDarahSegar.class);
                    startActivity(i);
                }else if(id == 3){
                    Intent i = new Intent(MainActivity.this, AgendaGiatDonorDarah.class);
                    startActivity(i);
                }else if(id == 4){
                    Intent i = new Intent(MainActivity.this, EdukasiDonorDarah.class);
                    startActivity(i);
                }else if(id == 5){
                    Intent i = new Intent(MainActivity.this, PetaFasilitasKesehatan.class);
                    startActivity(i);
                }else if(id == 6){
                    Intent i = new Intent(MainActivity.this, RiwayatDonorSaya.class);
                    startActivity(i);
                }else if(id == 7){
                    Intent i = new Intent(MainActivity.this, AkunSaya.class);
                    startActivity(i);
                }else if(id == 8){
                    CDialog.up(
                            MainActivity.this,
                            "Konfirmasi",
                            "Anda yakin ingin mengeluarkan akun anda?",
                            true, false, false,
                            "BATAL", "KELUAR", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    SharedPreferences.Editor ed = sh.edit();
                                    ed.clear();
                                    ed.apply();

                                    Intent i = new Intent(MainActivity.this, First.class);
                                    startActivity(i);
                                    finish();
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
            }
        });

        gm.setAdapter(adp);

        tvRencanaBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CDialog.up(
                        MainActivity.this,
                        "Konfirmasi",
                        "Anda akan membatalkan rencana donor ini, lanjutkan?",
                        true, false, false,
                        "BATAL", "LANJUTKAN", "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                cancelRencana();
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

        tvRencanaKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CDialog.up(
                        MainActivity.this,
                        "Konfirmasi",
                        "Keluarga pasien akan menerima notifikasi bahwa anda telah melakukan donor darah, lanjutkan?",
                        true, false, false,
                        "BATAL", "LANJUTKAN", "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                sudahDonor();
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

    }

    private void confirmEvent(String eventid) {
        AlertDialog alerts = CDialog.up(
                this,
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

        Call<MResponse> call = endpoint.confirmEvent(sh.getString("memberid", "0"), eventid);
        call.enqueue(new Callback<MResponse>() {
            @Override
            public void onResponse(Call<MResponse> call, Response<MResponse> response) {
                alerts.dismiss();
                if(response.isSuccessful() && response.body() != null){
                    CDialog.up(
                            MainActivity.this,
                            "Informasi",
                            response.body().getMsg(),
                            false, false, false,
                            "", "LANJUTKAN", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    recreate();
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
                            MainActivity.this,
                            "Informasi",
                            "Gagal Menghubungi Server, Silahkan ulangi...",
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
                        MainActivity.this,
                        "Informasi",
                        "Gagal Menghubungi Server, Periksa Jaringan Internet Anda...",
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

    private void sudahDonor() {
        AlertDialog alerts = CDialog.up(
                this,
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
        Call<MResponse> call = endpoint.sudahDonor(reqid, sh.getString("memberid", "0"));
        call.enqueue(new Callback<MResponse>() {
            @Override
            public void onResponse(Call<MResponse> call, Response<MResponse> response) {
                alerts.dismiss();
                if(response.isSuccessful() && response.body() != null && response.body().isStatus()){
                    CDialog.up(
                            MainActivity.this,
                            "Informasi",
                            "Berhasil mengkonfirmasi kepada keluarga pasien!",
                            false, false, false,
                            "", "LANJUTKAN", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    recreate();
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
                            MainActivity.this,
                            "Informasi",
                            "Gagal Menghubungi Server, Silahkan ulangi...",
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
                        MainActivity.this,
                        "Informasi",
                        "Gagal Menghubungi Server, Periksa Jaringan Internet Anda...",
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

    private void cancelRencana() {
        AlertDialog alerts = CDialog.up(
                this,
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
        Call<MResponse> call = endpoint.cancelDonor(reqid, sh.getString("memberid", "0"));
        call.enqueue(new Callback<MResponse>() {
            @Override
            public void onResponse(Call<MResponse> call, Response<MResponse> response) {
                alerts.dismiss();
                if(response.isSuccessful() && response.body() != null && response.body().isStatus()){
                    CDialog.up(
                            MainActivity.this,
                            "Informasi",
                            "Berhasil membatalkan rencana donor darah!",
                            false, false, false,
                            "", "LANJUTKAN", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    recreate();
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
                            MainActivity.this,
                            "Informasi",
                            "Gagal Menghubungi Server, Silahkan ulangi...",
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
                        MainActivity.this,
                        "Informasi",
                        "Gagal Menghubungi Server, Periksa Jaringan Internet Anda...",
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

    private void loadNotifikasi() {
        notif.clear();
        notif = db.getNotifikasi();
        tvJumlahNotif.setText(String.valueOf(notif.size()));
    }

    private void loadOnlineData() {
        Call<MRequestDarah> getDataDonor = endpoint.getRequestDarah(sh.getString("memberid", "0"));
        Call<MyRanks> getPoin = endpoint.mypoint(sh.getString("memberid", "0"));

        getPoin.enqueue(new Callback<MyRanks>() {
            @Override
            public void onResponse(Call<MyRanks> call, Response<MyRanks> response) {
                if(response.body() != null && response.isSuccessful()){
                    tvPointKeaktifan.setText(response.body().getRank());
                }else{
                    tvPointKeaktifan.setText("--");
                }
            }

            @Override
            public void onFailure(Call<MyRanks> call, Throwable t) {
                tvPointKeaktifan.setText("--");
            }
        });

        getDataDonor.enqueue(new Callback<MRequestDarah>() {
            @Override
            public void onResponse(Call<MRequestDarah> call, Response<MRequestDarah> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<ListrequestItem> datas = response.body().getListrequest();
                    for(ListrequestItem d : datas){
                        if(d.getDonor() != null && d.getDonor().contains(sh.getString("memberid", "0"))){
                            if(d.getListterpenuhi() != null && !d.getListterpenuhi().contains(sh.getString("memberid", "0"))){
                                tvRencanaBelumAda.setVisibility(View.GONE);
                                tvRencanaJudul.setVisibility(View.VISIBLE);
                                tvRencanaJudul2.setVisibility(View.VISIBLE);
                                tvRencanaBatal.setVisibility(View.VISIBLE);
                                tvRencanaKonfirmasi.setVisibility(View.VISIBLE);
                                String judul = "Rencana donor darah untuk pasien a.n. "+d.getNama()+" di "+d.getFaskes();
                                tvRencanaJudul.setText(judul.toUpperCase());
                                reqid = d.getRequestid();
                            }else if(d.getListterpenuhi() != null && d.getListterpenuhi().contains(sh.getString("memberid", "0"))){
                                tvRencanaBelumAda.setVisibility(View.VISIBLE);
                                tvRencanaJudul.setVisibility(View.GONE);
                                tvRencanaJudul2.setVisibility(View.GONE);
                                tvRencanaBatal.setVisibility(View.GONE);
                                tvRencanaKonfirmasi.setVisibility(View.GONE);
                            }else if(d.getListterpenuhi() == null){
                                tvRencanaBelumAda.setVisibility(View.GONE);
                                tvRencanaJudul.setVisibility(View.VISIBLE);
                                tvRencanaJudul2.setVisibility(View.VISIBLE);
                                tvRencanaBatal.setVisibility(View.VISIBLE);
                                tvRencanaKonfirmasi.setVisibility(View.VISIBLE);
                                String judul = "Rencana donor darah untuk pasien a.n. "+d.getNama()+" di "+d.getFaskes();
                                tvRencanaJudul.setText(judul.toUpperCase());
                                reqid = d.getRequestid();
                            }
                            break;

                        }else{
                            tvRencanaBelumAda.setVisibility(View.VISIBLE);
                            tvRencanaJudul.setVisibility(View.GONE);
                            tvRencanaJudul2.setVisibility(View.GONE);
                            tvRencanaBatal.setVisibility(View.GONE);
                            tvRencanaKonfirmasi.setVisibility(View.GONE);
                        }
                    }
                }else{
                    tvRencanaBelumAda.setVisibility(View.VISIBLE);
                    tvRencanaJudul.setVisibility(View.GONE);
                    tvRencanaJudul2.setVisibility(View.GONE);
                    tvRencanaBatal.setVisibility(View.GONE);
                    tvRencanaKonfirmasi.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MRequestDarah> call, Throwable t) {
                tvRencanaBelumAda.setVisibility(View.VISIBLE);
                tvRencanaJudul.setVisibility(View.GONE);
                tvRencanaJudul2.setVisibility(View.GONE);
                tvRencanaBatal.setVisibility(View.GONE);
                tvRencanaKonfirmasi.setVisibility(View.GONE);
            }
        });
    }

    @AfterPermissionGranted(RC_ALL_PERMISSIONS)
    private void requestAllPermissions() {
        if (EasyPermissions.hasPermissions(this, allPermissions)) {

        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "Aplikasi memerlukan izin agar dapat bekerja secara optimal!",
                    RC_ALL_PERMISSIONS,
                    allPermissions
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        handler.post(runnableCode);
        loadOnlineData();

        Glide.with(this)
                .load(sh.getString("foto", null))
                .circleCrop()
                .error(R.drawable.mrdonor)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(fotoProfile);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnableCode);
    }

    public void scanBarcodeCustomLayout(View view) {
        ScanOptions options = new ScanOptions();
        options.setCaptureActivity(ScanQR.class);
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
//        options.setPrompt("Scan something");
        options.setOrientationLocked(true);
        options.setBeepEnabled(false);
        barcodeLauncher.launch(options);
    }
}