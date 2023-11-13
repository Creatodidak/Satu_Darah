package id.creatodidak.satudarah.registrasi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

public class Verifikasi extends AppCompatActivity {
    SharedPreferences sh;
    Boolean isVerified;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi);

        sh = getSharedPreferences("USERDATA", MODE_PRIVATE);
        isVerified = !sh.getString("verified", "NO").equals("NO");

        Intent i;

        if(sh.getString("verified", "NO").equals("NO")){
            i = null;
        }else{
            if(sh.getString("golongandarah", "NO").equals("NO")){
                i = new Intent(this, SetGolonganDarah.class);
            }else{
                if(sh.getString("foto", "NO").equals("NO")){
                    i = new Intent(this, SetFotoProfil.class);
                }else{
                    i = new Intent(this, MainActivity.class);
                }
            }
        }

        if(i != null){
            startActivity(i);
            finish();
        }

        Button btVerifikasi = findViewById(R.id.btVerifikasi);

        btVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekverifikasi();
            }
        });
    }

    private void cekverifikasi() {
        AlertDialog alerts = CDialog.up(
                Verifikasi.this,
                "Memverifikasi...",
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

        Endpoint endpoint = ApiClient.getClient().create(Endpoint.class);
        Call<MResponse> call = endpoint.cekVerifikasi(sh.getString("memberid", "0"));
        call.enqueue(new Callback<MResponse>() {
            @Override
            public void onResponse(Call<MResponse> call, Response<MResponse> response) {
                alerts.dismiss();
                if(response.isSuccessful() && response.body() != null && response.body().isStatus()){
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("verified", "YES");
                    ed.apply();

                    Verifikasi.this.recreate();
                }else{
                    CDialog.up(
                            Verifikasi.this,
                            "Informasi",
                            "Gagal memverifikasi, silahkan ulangi!",
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
                        Verifikasi.this,
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