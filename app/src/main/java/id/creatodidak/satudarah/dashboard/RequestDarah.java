package id.creatodidak.satudarah.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.creatodidak.satudarah.ApiClient;
import id.creatodidak.satudarah.Endpoint;
import id.creatodidak.satudarah.Login;
import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.models.ListfaskesItem;
import id.creatodidak.satudarah.models.ListutdItem;
import id.creatodidak.satudarah.models.MResponse;
import id.creatodidak.satudarah.models.MWilayah;
import id.creatodidak.satudarah.models.Mfaskes;
import id.creatodidak.satudarah.models.Mutd;
import id.creatodidak.satudarah.models.WilayahItem;
import id.creatodidak.satudarah.plugin.CDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestDarah extends AppCompatActivity {
    TextView etNamaPasien, etAlamatPasien, etJumlahPermintaan, etDiagnosa, etNamaCP, etNomorCP;
    Spinner spGolonganDarah, spFaskes, spUtd, spKabupaten;
    Button btKirimPermohonan;
    List<String> golongandarah = Arrays.asList("PILIH GOLONGAN DARAH", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-");
    List<String> faskes = new ArrayList<>();
    List<String> utd = new ArrayList<>();
    List<String> kab = new ArrayList<>();
    List<ListfaskesItem> listfaskes = new ArrayList<>();
    List<ListutdItem> listutd = new ArrayList<>();
    List<WilayahItem> listkab = new ArrayList<>();
    ArrayAdapter<String> adpGoldar, adpFaskes, adpUtd, adpKabupaten;
    Endpoint endpoint;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_darah);
        endpoint = ApiClient.getClient().create(Endpoint.class);
        sh = getSharedPreferences("USERDATA", MODE_PRIVATE);
        etNamaPasien = findViewById(R.id.etNamaPasien);
        etAlamatPasien = findViewById(R.id.etAlamatPasien);
        etJumlahPermintaan = findViewById(R.id.etJumlahPermintaan);
        etDiagnosa = findViewById(R.id.etDiagnosa);
        etNamaCP = findViewById(R.id.etNamaCP);
        etNomorCP = findViewById(R.id.etNomorCP);
        spGolonganDarah = findViewById(R.id.spGolonganDarah);
        spFaskes = findViewById(R.id.spFaskes);
        spUtd = findViewById(R.id.spUtd);
        spKabupaten = findViewById(R.id.spKabupaten);
        btKirimPermohonan = findViewById(R.id.btKirimPermohonan);

        faskes.add("PILIH FASKES");
        utd.add("PILIH UNIT TRANSFUSI DARAH");
        adpGoldar = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, golongandarah);
        adpFaskes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, faskes);
        adpUtd = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, utd);
        adpKabupaten = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kab);
        adpGoldar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adpFaskes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adpUtd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adpKabupaten.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spFaskes.setAdapter(adpFaskes);
        spUtd.setAdapter(adpUtd);
        spGolonganDarah.setAdapter(adpGoldar);
        spKabupaten.setAdapter(adpKabupaten);
        
        loadKab();

        spKabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!spKabupaten.getSelectedItem().toString().equals("PILIH KABUPATEN FASKES")) {
                    String selected = String.valueOf(listkab.get(position-1).getId());
                    loadFaskes(selected);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spFaskes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!spFaskes.getSelectedItem().toString().equals("PILIH FASKES")){
                    String selected = listfaskes.get(position-1).getIdFaskes();
                    loadUtd(selected);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btKirimPermohonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    CDialog.up(
                            RequestDarah.this,
                            "Konfirmasi",
                            "Apakah anda yakin data yang anda masukan sudah benar?",
                            true, false, false,
                            "BATAL", "YA", "",
                            new CDialog.AlertDialogListener() {
                                @Override
                                public void onOpt1(AlertDialog alert) {
                                    alert.dismiss();
                                    newRequest();
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
                }else{
                    CDialog.up(
                            RequestDarah.this,
                            "Peringatan",
                            "Seluruh data harus diisi/dipilih!",
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

    private void newRequest() {
        AlertDialog alerts = CDialog.up(
                RequestDarah.this,
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

        String memberid = sh.getString("memberid", "");
        String nama = etNamaPasien.getText().toString();
        String alamat = etAlamatPasien.getText().toString();
        String goldar = spGolonganDarah.getSelectedItem().toString();
        String jumlah = etJumlahPermintaan.getText().toString();
        String diagnosa = etDiagnosa.getText().toString();
        String namacp = etNamaCP.getText().toString();
        String kontakcp = etNomorCP.getText().toString();
        String rkab = "";
        String rfaskes = spFaskes.getSelectedItem().toString();
        String rutd = spUtd.getSelectedItem().toString();

        for(WilayahItem d : listkab){
            if(spKabupaten.getSelectedItem().toString().equals(d.getNama())){
                rkab = String.valueOf(d.getId());
            }
        }

        Call<MResponse> call = endpoint.newRequest(memberid, nama, alamat, goldar, jumlah, diagnosa, rkab, rfaskes, rutd, namacp, kontakcp);
        call.enqueue(new Callback<MResponse>() {
            @Override
            public void onResponse(Call<MResponse> call, Response<MResponse> response) {
                alerts.dismiss();
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Intent i = new Intent(RequestDarah.this, DaftarRequestSaya.class);
                    startActivity(i);
                    finish();
                }else{
                    CDialog.up(
                            RequestDarah.this,
                            "Informasi",
                            "Gagal membuat permintaan darah, sialhkan ulangi!",
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
                        RequestDarah.this,
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

    private void loadFaskes(String selected) {
        Call<Mfaskes> call = endpoint.getFaskes(selected);
        call.enqueue(new Callback<Mfaskes>() {
            @Override
            public void onResponse(Call<Mfaskes> call, Response<Mfaskes> response) {
                if(response.isSuccessful() && response.body() != null){
                    listfaskes.clear();
                    faskes.clear();
                    adpFaskes.notifyDataSetChanged();

                    listfaskes.addAll(response.body().getListfaskes());
                    faskes.add("PILIH FASKES");
                    for(ListfaskesItem d : response.body().getListfaskes()){
                        faskes.add(d.getNama());
                    }

                    adpFaskes.notifyDataSetChanged();
                }else{
                    loadFaskes(selected);
                }
            }

            @Override
            public void onFailure(Call<Mfaskes> call, Throwable t) {
                loadFaskes(selected);
            }
        });
    }

    private void loadUtd(String selected) {
        Call<Mutd> call = endpoint.getUtd(selected);
        call.enqueue(new Callback<Mutd>() {
            @Override
            public void onResponse(Call<Mutd> call, Response<Mutd> response) {
                if(response.isSuccessful() && response.body() != null){
                    listutd.clear();
                    utd.clear();
                    adpUtd.notifyDataSetChanged();

                    listutd.addAll(response.body().getListutd());
                    utd.add("PILIH UNIT TRANSFUSI DARAH");
                    for(ListutdItem d : response.body().getListutd()){
                        utd.add(d.getNama());
                    }

                    adpUtd.notifyDataSetChanged();
                }else{
                    loadUtd(selected);
                }
            }

            @Override
            public void onFailure(Call<Mutd> call, Throwable t) {
                loadUtd(selected);
            }
        });
    }

    private void loadKab() {
        Call<MWilayah> call = endpoint.getWilayah("KABUPATEN", "61", "0", "0", "0");
        call.enqueue(new Callback<MWilayah>() {
            @Override
            public void onResponse(Call<MWilayah> call, Response<MWilayah> response) {
                if(response.body() != null && response.isSuccessful()){
                    listkab.addAll(response.body().getWilayah());
                    kab.add("PILIH KABUPATEN FASKES");
                    for(WilayahItem d : response.body().getWilayah()){
                        kab.add(d.getNama());
                    }
                    adpKabupaten.notifyDataSetChanged();
                }else{
                    loadKab();
                }
            }

            @Override
            public void onFailure(Call<MWilayah> call, Throwable t) {
                loadKab();
            }
        });
    }

    private boolean isValid(){
        return !TextUtils.isEmpty(etNamaPasien.getText()) &&
                !TextUtils.isEmpty(etAlamatPasien.getText()) &&
                !TextUtils.isEmpty(etJumlahPermintaan.getText()) &&
                !TextUtils.isEmpty(etDiagnosa.getText()) &&
                !TextUtils.isEmpty(etNamaCP.getText()) &&
                !TextUtils.isEmpty(etNomorCP.getText()) &&
                !TextUtils.isEmpty(spGolonganDarah.getSelectedItem().toString()) &&
                !TextUtils.isEmpty(spFaskes.getSelectedItem().toString()) &&
                !TextUtils.isEmpty(spUtd.getSelectedItem().toString()) &&
                !TextUtils.isEmpty(spKabupaten.getSelectedItem().toString()) &&
                !spGolonganDarah.getSelectedItem().toString().equals("PILIH GOLONGAN DARAH") &&
                !spFaskes.getSelectedItem().toString().equals("PILIH FASKES") &&
                !spUtd.getSelectedItem().toString().equals("PILIH UNIT TRANSFUSI DARAH") &&
                !spKabupaten.getSelectedItem().toString().equals("PILIH KABUPATEN FASKES");
    }
}