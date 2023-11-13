package id.creatodidak.satudarah.registrasi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import id.creatodidak.satudarah.ApiClient;
import id.creatodidak.satudarah.Endpoint;
import id.creatodidak.satudarah.Login;
import id.creatodidak.satudarah.MainActivity;
import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.models.MResponse;
import id.creatodidak.satudarah.plugin.CDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetGolonganDarah extends AppCompatActivity {
    TextView btAplus;
    TextView btAmin;
    TextView btBplus;
    TextView btBmin;
    TextView btOplus;
    TextView btOmin;
    TextView btABplus;
    TextView btABmin;
    Button btSelesai;
    String goldar = "";
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_golongan_darah);
        btAplus = findViewById(R.id.btAplus);
        btAmin = findViewById(R.id.btAmin);
        btBplus = findViewById(R.id.btBplus);
        btBmin = findViewById(R.id.btBmin);
        btOplus = findViewById(R.id.btOplus);
        btOmin = findViewById(R.id.btOmin);
        btABplus = findViewById(R.id.btABplus);
        btABmin = findViewById(R.id.btABmin);
        btSelesai = findViewById(R.id.btSelesai);
        sh = getSharedPreferences("USERDATA", MODE_PRIVATE);
        int on = R.drawable.bt_on;
        int off = R.drawable.bt_off;

        btAmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAmin.setBackgroundResource(on);
                btAmin.setTextColor(Color.WHITE);
                btAplus.setBackgroundResource(off);
                btAplus.setTextColor(Color.BLACK);
                btBmin.setBackgroundResource(off);
                btBmin.setTextColor(Color.BLACK);
                btBplus.setBackgroundResource(off);
                btBplus.setTextColor(Color.BLACK);
                btOmin.setBackgroundResource(off);
                btOmin.setTextColor(Color.BLACK);
                btOplus.setBackgroundResource(off);
                btOplus.setTextColor(Color.BLACK);
                btABmin.setBackgroundResource(off);
                btABmin.setTextColor(Color.BLACK);
                btABplus.setBackgroundResource(off);
                btABplus.setTextColor(Color.BLACK);
                goldar = "A-";
            }
        });

        btAplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAmin.setBackgroundResource(off);
                btAmin.setTextColor(Color.BLACK);
                btAplus.setBackgroundResource(on);
                btAplus.setTextColor(Color.WHITE);
                btBmin.setBackgroundResource(off);
                btBmin.setTextColor(Color.BLACK);
                btBplus.setBackgroundResource(off);
                btBplus.setTextColor(Color.BLACK);
                btOmin.setBackgroundResource(off);
                btOmin.setTextColor(Color.BLACK);
                btOplus.setBackgroundResource(off);
                btOplus.setTextColor(Color.BLACK);
                btABmin.setBackgroundResource(off);
                btABmin.setTextColor(Color.BLACK);
                btABplus.setBackgroundResource(off);
                btABplus.setTextColor(Color.BLACK);
                goldar = "A+";
            }
        });

        btBmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAmin.setBackgroundResource(off);
                btAmin.setTextColor(Color.BLACK);
                btAplus.setBackgroundResource(off);
                btAplus.setTextColor(Color.BLACK);
                btBmin.setBackgroundResource(on);
                btBmin.setTextColor(Color.WHITE);
                btBplus.setBackgroundResource(off);
                btBplus.setTextColor(Color.BLACK);
                btOmin.setBackgroundResource(off);
                btOmin.setTextColor(Color.BLACK);
                btOplus.setBackgroundResource(off);
                btOplus.setTextColor(Color.BLACK);
                btABmin.setBackgroundResource(off);
                btABmin.setTextColor(Color.BLACK);
                btABplus.setBackgroundResource(off);
                btABplus.setTextColor(Color.BLACK);
                goldar = "B-";
            }
        });

        btBplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAmin.setBackgroundResource(off);
                btAmin.setTextColor(Color.BLACK);
                btAplus.setBackgroundResource(off);
                btAplus.setTextColor(Color.BLACK);
                btBmin.setBackgroundResource(off);
                btBmin.setTextColor(Color.BLACK);
                btBplus.setBackgroundResource(on);
                btBplus.setTextColor(Color.WHITE);
                btOmin.setBackgroundResource(off);
                btOmin.setTextColor(Color.BLACK);
                btOplus.setBackgroundResource(off);
                btOplus.setTextColor(Color.BLACK);
                btABmin.setBackgroundResource(off);
                btABmin.setTextColor(Color.BLACK);
                btABplus.setBackgroundResource(off);
                btABplus.setTextColor(Color.BLACK);
                goldar = "B+";
            }
        });

        btOmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAmin.setBackgroundResource(off);
                btAmin.setTextColor(Color.BLACK);
                btAplus.setBackgroundResource(off);
                btAplus.setTextColor(Color.BLACK);
                btBmin.setBackgroundResource(off);
                btBmin.setTextColor(Color.BLACK);
                btBplus.setBackgroundResource(off);
                btBplus.setTextColor(Color.BLACK);
                btOmin.setBackgroundResource(on);
                btOmin.setTextColor(Color.WHITE);
                btOplus.setBackgroundResource(off);
                btOplus.setTextColor(Color.BLACK);
                btABmin.setBackgroundResource(off);
                btABmin.setTextColor(Color.BLACK);
                btABplus.setBackgroundResource(off);
                btABplus.setTextColor(Color.BLACK);
                goldar = "0-";
            }
        });

        btOplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAmin.setBackgroundResource(off);
                btAmin.setTextColor(Color.BLACK);
                btAplus.setBackgroundResource(off);
                btAplus.setTextColor(Color.BLACK);
                btBmin.setBackgroundResource(off);
                btBmin.setTextColor(Color.BLACK);
                btBplus.setBackgroundResource(off);
                btBplus.setTextColor(Color.BLACK);
                btOmin.setBackgroundResource(off);
                btOmin.setTextColor(Color.BLACK);
                btOplus.setBackgroundResource(on);
                btOplus.setTextColor(Color.WHITE);
                btABmin.setBackgroundResource(off);
                btABmin.setTextColor(Color.BLACK);
                btABplus.setBackgroundResource(off);
                btABplus.setTextColor(Color.BLACK);
                goldar = "O+";
            }
        });

        btABmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAmin.setBackgroundResource(off);
                btAmin.setTextColor(Color.BLACK);
                btAplus.setBackgroundResource(off);
                btAplus.setTextColor(Color.BLACK);
                btBmin.setBackgroundResource(off);
                btBmin.setTextColor(Color.BLACK);
                btBplus.setBackgroundResource(off);
                btBplus.setTextColor(Color.BLACK);
                btOmin.setBackgroundResource(off);
                btOmin.setTextColor(Color.BLACK);
                btOplus.setBackgroundResource(off);
                btOplus.setTextColor(Color.BLACK);
                btABmin.setBackgroundResource(on);
                btABmin.setTextColor(Color.WHITE);
                btABplus.setBackgroundResource(off);
                btABplus.setTextColor(Color.BLACK);
                goldar = "AB-";
            }
        });

        btABplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAmin.setBackgroundResource(off);
                btAmin.setTextColor(Color.BLACK);
                btAplus.setBackgroundResource(off);
                btAplus.setTextColor(Color.BLACK);
                btBmin.setBackgroundResource(off);
                btBmin.setTextColor(Color.BLACK);
                btBplus.setBackgroundResource(off);
                btBplus.setTextColor(Color.BLACK);
                btOmin.setBackgroundResource(off);
                btOmin.setTextColor(Color.BLACK);
                btOplus.setBackgroundResource(off);
                btOplus.setTextColor(Color.BLACK);
                btABmin.setBackgroundResource(off);
                btABmin.setTextColor(Color.BLACK);
                btABplus.setBackgroundResource(on);
                btABplus.setTextColor(Color.WHITE);
                goldar = "AB+";
            }
        });

        btSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goldar != null){
                    updGoldar(goldar);
                }else{
                    CDialog.up(
                            SetGolonganDarah.this,
                            "Peringatan",
                            "Silahkan pilih golongan darah anda terlebih dahulu!",
                            false, false, false,
                            "", "PERBAIKI", "",
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
        });
    }

    private void updGoldar(String goldars) {
        AlertDialog alerts = CDialog.up(
                this,
                "Memproses...",
                ", harap tunggu....",
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

        Endpoint endpoint = ApiClient.getClient().create(Endpoint.class);
        Call<MResponse> call = endpoint.updGoldar(sh.getString("memberid", "0"), goldars);

        call.enqueue(new Callback<MResponse>() {
            @Override
            public void onResponse(Call<MResponse> call, Response<MResponse> response) {
                alerts.dismiss();
                if(response.isSuccessful() && response.body() != null && response.body().isStatus()){
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("golongandarah", response.body().getMsg());
                    ed.apply();

                    Intent i = new Intent(SetGolonganDarah.this, SetFotoProfil.class);
                    startActivity(i);
                    finish();
                }else{
                    CDialog.up(
                            SetGolonganDarah.this,
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
                        SetGolonganDarah.this,
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
}