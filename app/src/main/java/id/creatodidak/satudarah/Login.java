package id.creatodidak.satudarah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.creatodidak.satudarah.models.MRegistrasi;
import id.creatodidak.satudarah.models.MWilayah;
import id.creatodidak.satudarah.models.UserdataItem;
import id.creatodidak.satudarah.models.WilayahItem;
import id.creatodidak.satudarah.plugin.CDialog;
import id.creatodidak.satudarah.registrasi.Verifikasi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    Button btLogin;
    SharedPreferences sh;
    SharedPreferences userdata;
    String svToken;

    EditText etNama;
    EditText etNik;
    EditText etTahun;
    EditText etEmail;
    EditText etPwd;
    EditText etPwd2;
    Spinner spTanggal;
    Spinner spBulan;
    Spinner spProv;
    Spinner spKab;
    Spinner spKec;
    Spinner spDes;
    Spinner spDus;
    Spinner spJenisKelamin;
    Button btRegister;
    CardView wrapperFormRegister;
    ImageView btCloseFormReg;
    EditText etNIK;
    EditText etPassword;
    TextView btDaftarBaru;
    LinearLayout wrapperError;
    TextView tvPesanError;
    TextView btLupaPassword;

    List<String> listtanggal = Arrays.asList("TANGGAL", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
    List<String> listbulan = Arrays.asList("BULAN", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
    List<String> jeniskelamin = Arrays.asList("PILIH JENIS KELAMIN", "PRIA", "WANITA");
    List<String> listprov = new ArrayList<>();
    List<String> listkab = new ArrayList<>();
    List<String> listkec = new ArrayList<>();
    List<String> listdes = new ArrayList<>();
    List<String> listdus = new ArrayList<>();

    List<WilayahItem> provs = new ArrayList<>();
    List<WilayahItem> kabs = new ArrayList<>();
    List<WilayahItem> kecs = new ArrayList<>();
    List<WilayahItem> dess = new ArrayList<>();
    List<WilayahItem> duss = new ArrayList<>();
    Endpoint endpoint;

    ArrayAdapter<String> adpTanggal, adpBulan, adpProv, adpKab, adpKec, adpDes, adpDus, adpJenisKelamin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sh = getSharedPreferences("TOKENFCM", MODE_PRIVATE);
        userdata = getSharedPreferences("USERDATA", MODE_PRIVATE);
        endpoint = ApiClient.getClient().create(Endpoint.class);
        svToken = sh.getString("TOKEN", null);
        btLogin = findViewById(R.id.btLogin);

        etNama = findViewById(R.id.etNama);
        etNik = findViewById(R.id.etNik);
        etTahun = findViewById(R.id.etTahun);
        etEmail = findViewById(R.id.etEmail);
        etPwd = findViewById(R.id.etPwd);
        etPwd2 = findViewById(R.id.etPwd2);
        spTanggal = findViewById(R.id.spTanggal);
        spBulan = findViewById(R.id.spBulan);
        spProv = findViewById(R.id.spProv);
        spKab = findViewById(R.id.spKab);
        spKec = findViewById(R.id.spKec);
        spDes = findViewById(R.id.spDes);
        spDus = findViewById(R.id.spDus);
        spJenisKelamin = findViewById(R.id.spJenisKelamin);
        btRegister = findViewById(R.id.btRegister);
        wrapperFormRegister = findViewById(R.id.wrapperFormRegister);
        wrapperFormRegister.setVisibility(View.GONE);
        btCloseFormReg = findViewById(R.id.btCloseFormReg);
        btDaftarBaru = findViewById(R.id.btDaftarBaru);
        etNIK = findViewById(R.id.etNIK);
        etPassword = findViewById(R.id.etPassword);
        wrapperError = findViewById(R.id.wrapperError);
        tvPesanError = findViewById(R.id.tvPesanError);
        btLupaPassword = findViewById(R.id.btLupaPassword);
        wrapperError.setVisibility(View.GONE);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nik = etNIK.getText().toString();
                String password = etPassword.getText().toString();

                if(nik.length() == 16){
                    if(password.length() >= 8){
                        tryLogin(nik, password);
                    }else{
                        CDialog.up(
                                Login.this,
                                "Peringatan",
                                "Password harus berjumlah sekurang-kurangnya 8 Karakter!",
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
                }else{
                    CDialog.up(
                            Login.this,
                            "Peringatan",
                            "NIK harus berjumlah sekurang-kurangnya 16 Digit!",
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

        if (svToken == null) {
            getToken();
        }

        btCloseFormReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrapperFormRegister.setVisibility(View.GONE);
            }
        });

        btDaftarBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrapperFormRegister.setVisibility(View.VISIBLE);
                loadWilayah("PROVINSI", "0", "0", "0", "0");
            }
        });

        adpTanggal = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listtanggal);
        adpBulan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listbulan);
        adpProv = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listprov);
        adpKab = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listkab);
        adpKec = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listkec);
        adpDes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listdes);
        adpDus = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listdus);
        adpJenisKelamin = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jeniskelamin);
        adpTanggal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adpBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adpProv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adpKab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adpKec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adpDes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adpDus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adpJenisKelamin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spTanggal.setAdapter(adpTanggal);
        spBulan.setAdapter(adpBulan);
        spProv.setAdapter(adpProv);
        spKab.setAdapter(adpKab);
        spKec.setAdapter(adpKec);
        spDes.setAdapter(adpDes);
        spDus.setAdapter(adpDus);
        spJenisKelamin.setAdapter(adpJenisKelamin);

        spProv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spProv.getSelectedItem().toString().equals("PILIH PROVINSI")) {
                    for (WilayahItem data : provs) {
                        if (data.getNama().equals(spProv.getSelectedItem().toString())) {
                            loadWilayah("KABUPATEN", String.valueOf(data.getId()), "0", "0", "0");
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spKab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spKab.getSelectedItem().toString().equals("PILIH KABUPATEN")) {
                    for (WilayahItem data : kabs) {
                        if (data.getNama().equals(spKab.getSelectedItem().toString())) {
                            loadWilayah("KECAMATAN", "0", String.valueOf(data.getId()), "0", "0");
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spKec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spKec.getSelectedItem().toString().equals("PILIH KECAMATAN")) {
                    for (WilayahItem data : kecs) {
                        if (data.getNama().equals(spKec.getSelectedItem().toString())) {
                            loadWilayah("DESA", "0", "0", String.valueOf(data.getId()), "0");
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spDes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spDes.getSelectedItem().toString().equals("PILIH DESA")) {
                    for (WilayahItem data : dess) {
                        if (data.getNama().equals(spDes.getSelectedItem().toString())) {
                            loadWilayah("DUSUN", "0", "0", "0", String.valueOf(data.getId()));
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validasireg()){
                    if(etPwd.getText().toString().equals(etPwd2.getText().toString())){
                        if(etPwd.getText().length() >= 8){
                            registerUser();
                        }else{
                            CDialog.up(
                                    Login.this,
                                    "PERINGATAN",
                                    "Password minimal 8 karakter!",
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
                    }else{
                        CDialog.up(
                                Login.this,
                                "PERINGATAN",
                                "Password tidak sama!",
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
                }else{
                    CDialog.up(
                            Login.this,
                            "PERINGATAN",
                            "Semua data harus diisi/dipilih, silahkan periksa kembali!",
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

        if(userdata.getBoolean("isLoggedIn", false)){
            Intent i = new Intent(Login.this, Verifikasi.class);
            startActivity(i);
            finish();
        }
    }

    private void tryLogin(String nik, String password) {
        AlertDialog alerts = CDialog.up(
                Login.this,
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

        Call<MRegistrasi> call = endpoint.login(nik, password, svToken);
        call.enqueue(new Callback<MRegistrasi>() {
            @Override
            public void onResponse(Call<MRegistrasi> call, Response<MRegistrasi> response) {
                alerts.dismiss();
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    List<UserdataItem> data = response.body().getUserdata();
                    SharedPreferences.Editor ed = userdata.edit();

                    for(UserdataItem d : data){
                        ed.putString("memberid", d.getMemberId());
                        ed.putString("nama", d.getNama());
                        ed.putString("nik", d.getNik());
                        ed.putString("tanggallahir", d.getTanggallahir());
                        ed.putString("prov", d.getProv());
                        ed.putString("kab", d.getKab());
                        ed.putString("kec", d.getKec());
                        ed.putString("des", d.getDes());
                        ed.putString("dus", d.getDus());
                        ed.putString("golongandarah", d.getGolongandarah());
                        ed.putString("email", d.getEmail());
                        ed.putString("foto", d.getFoto());
                        ed.putString("qrcode", d.getQrcode());
                        ed.putString("verified", d.getVerified());
                        ed.putString("jeniskelamin", d.getJeniskelamin());
                        ed.putBoolean("isLoggedIn", true);
                    }

                    ed.apply();

                    Intent i = new Intent(Login.this, Verifikasi.class);
                    startActivity(i);
                    finish();
                }else{
                    CDialog.up(
                            Login.this,
                            "Informasi",
                            response.body().getMsg()+"\nSilahkan ulangi...",
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
            public void onFailure(Call<MRegistrasi> call, Throwable t) {
                alerts.dismiss();
                CDialog.up(
                        Login.this,
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

    private void registerUser() {
        AlertDialog alerts = CDialog.up(
                Login.this,
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

        Call<MRegistrasi> call = endpoint.register(etNama.getText().toString(), etNik.getText().toString(), spTanggal.getSelectedItem().toString()+"-"+spBulan.getSelectedItem().toString()+"-"+etTahun.getText().toString(), spProv.getSelectedItem().toString(), spKab.getSelectedItem().toString(), spKec.getSelectedItem().toString(), spDes.getSelectedItem().toString(), spDus.getSelectedItem().toString(), "NO", etEmail.getText().toString(), "NO", etPwd.getText().toString(), svToken, spJenisKelamin.getSelectedItem().toString());

        call.enqueue(new Callback<MRegistrasi>() {
            @Override
            public void onResponse(Call<MRegistrasi> call, Response<MRegistrasi> response) {
                alerts.dismiss();
                if(response.body() != null && response.body().isStatus() && response.isSuccessful()){
                    List<UserdataItem> data = response.body().getUserdata();
                    SharedPreferences.Editor ed = userdata.edit();

                    for(UserdataItem d : data){
                        ed.putString("memberid", d.getMemberId());
                        ed.putString("nama", d.getNama());
                        ed.putString("nik", d.getNik());
                        ed.putString("tanggallahir", d.getTanggallahir());
                        ed.putString("prov", d.getProv());
                        ed.putString("kab", d.getKab());
                        ed.putString("kec", d.getKec());
                        ed.putString("des", d.getDes());
                        ed.putString("dus", d.getDus());
                        ed.putString("golongandarah", d.getGolongandarah());
                        ed.putString("email", d.getEmail());
                        ed.putString("foto", d.getFoto());
                        ed.putString("qrcode", d.getQrcode());
                        ed.putString("verified", d.getVerified());
                        ed.putString("jeniskelamin", d.getJeniskelamin());
                        ed.putBoolean("isLoggedIn", true);
                    }

                    ed.apply();

                    Intent i = new Intent(Login.this, Verifikasi.class);
                    startActivity(i);
                    finish();
                }else{
                    CDialog.up(
                            Login.this,
                            "Informasi",
                            response.body().getMsg()+"\nSilahkan ulangi...",
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
            public void onFailure(Call<MRegistrasi> call, Throwable t) {
                alerts.dismiss();
                CDialog.up(
                        Login.this,
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

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }

            String token = task.getResult();

            if (!token.isEmpty()) {
                svToken = token;
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("TOKEN", token);
                ed.apply();
            }
        });
    }

    private void loadWilayah(String jenis, String prov, String kab, String kec, String des) {
        if (jenis.equals("PROVINSI")) {
            listprov.clear();
            listkab.clear();
            listkec.clear();
            listdes.clear();
            listdus.clear();
            provs.clear();
            kabs.clear();
            kecs.clear();
            dess.clear();
            duss.clear();
            adpProv.notifyDataSetChanged();
            adpKab.notifyDataSetChanged();
            adpKec.notifyDataSetChanged();
            adpDes.notifyDataSetChanged();
            adpDus.notifyDataSetChanged();
        } else if (jenis.equals("KABUPATEN")) {
            listkab.clear();
            listkec.clear();
            listdes.clear();
            listdus.clear();
            kabs.clear();
            kecs.clear();
            dess.clear();
            duss.clear();
            adpKab.notifyDataSetChanged();
            adpKec.notifyDataSetChanged();
            adpDes.notifyDataSetChanged();
            adpDus.notifyDataSetChanged();
        } else if (jenis.equals("KECAMATAN")) {
            listkec.clear();
            listdes.clear();
            listdus.clear();
            kecs.clear();
            dess.clear();
            duss.clear();
            adpKec.notifyDataSetChanged();
            adpDes.notifyDataSetChanged();
            adpDus.notifyDataSetChanged();
        } else if (jenis.equals("DESA")) {
            listdes.clear();
            listdus.clear();
            dess.clear();
            duss.clear();
            adpDes.notifyDataSetChanged();
            adpDus.notifyDataSetChanged();
        } else if (jenis.equals("DUSUN")) {
            listdus.clear();
            duss.clear();
            adpDus.notifyDataSetChanged();
        }

        Call<MWilayah> call = endpoint.getWilayah(jenis, prov, kab, kec, des);
        call.enqueue(new Callback<MWilayah>() {
            @Override
            public void onResponse(Call<MWilayah> call, Response<MWilayah> response) {
                if (response.body() != null && response.isSuccessful()) {
                    if (jenis.equals("PROVINSI")) {
                        listprov.add("PILIH PROVINSI");
                        listkab.add("PILIH KABUPATEN");
                        listkec.add("PILIH KECAMATAN");
                        listdes.add("PILIH DESA");
                        listdus.add("PILIH DUSUN");
                        provs.addAll(response.body().getWilayah());
                    } else if (jenis.equals("KABUPATEN")) {
                        listkab.add("PILIH KABUPATEN");
                        listkec.add("PILIH KECAMATAN");
                        listdes.add("PILIH DESA");
                        listdus.add("PILIH DUSUN");
                        kabs.addAll(response.body().getWilayah());
                    } else if (jenis.equals("KECAMATAN")) {
                        listkec.add("PILIH KECAMATAN");
                        listdes.add("PILIH DESA");
                        listdus.add("PILIH DUSUN");
                        kecs.addAll(response.body().getWilayah());
                    } else if (jenis.equals("DESA")) {
                        listdes.add("PILIH DESA");
                        listdus.add("PILIH DUSUN");
                        dess.addAll(response.body().getWilayah());
                    } else if (jenis.equals("DUSUN")) {
                        listdus.add("PILIH DUSUN");
                        duss.addAll(response.body().getWilayah());
                    }

                    for (WilayahItem data : response.body().getWilayah()) {
                        if (jenis.equals("PROVINSI")) {
                            listprov.add(data.getNama());
                        } else if (jenis.equals("KABUPATEN")) {
                            listkab.add(data.getNama());
                        } else if (jenis.equals("KECAMATAN")) {
                            listkec.add(data.getNama());
                        } else if (jenis.equals("DESA")) {
                            listdes.add(data.getNama());
                        } else if (jenis.equals("DUSUN")) {
                            listdus.add(data.getNama());
                        }
                    }

                    if (jenis.equals("PROVINSI")) {
                        adpProv.notifyDataSetChanged();
                        adpKab.notifyDataSetChanged();
                        adpKec.notifyDataSetChanged();
                        adpDes.notifyDataSetChanged();
                        adpDus.notifyDataSetChanged();
                    } else if (jenis.equals("KABUPATEN")) {
                        adpKab.notifyDataSetChanged();
                        adpKec.notifyDataSetChanged();
                        adpDes.notifyDataSetChanged();
                        adpDus.notifyDataSetChanged();
                    } else if (jenis.equals("KECAMATAN")) {
                        adpKec.notifyDataSetChanged();
                        adpDes.notifyDataSetChanged();
                        adpDus.notifyDataSetChanged();
                    } else if (jenis.equals("DESA")) {
                        adpDes.notifyDataSetChanged();
                        adpDus.notifyDataSetChanged();
                    } else if (jenis.equals("DUSUN")) {
                        adpDus.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<MWilayah> call, Throwable t) {

            }
        });
    }

    private boolean validasireg(){
        return !TextUtils.isEmpty(etNama.getText()) &&
                !TextUtils.isEmpty(etNik.getText()) &&
                !TextUtils.isEmpty(etEmail.getText()) &&
                !TextUtils.isEmpty(etPwd.getText()) &&
                !TextUtils.isEmpty(etPwd2.getText()) &&
                !TextUtils.isEmpty(etTahun.getText()) &&
                !spTanggal.getSelectedItem().toString().equals("TANGGAL") &&
                !spBulan.getSelectedItem().toString().equals("BULAN") &&
                !spProv.getSelectedItem().toString().equals("PILIH PROVINSI") &&
                !spKab.getSelectedItem().toString().equals("PILIH KABUPATEN") &&
                !spKec.getSelectedItem().toString().equals("PILIH KECAMATAN") &&
                !spDes.getSelectedItem().toString().equals("PILIH DESA") &&
                !spDus.getSelectedItem().toString().equals("PILIH DUSUN") &&
                !spJenisKelamin.getSelectedItem().toString().equals("PILIH JENIS KELAMIN") &&
                !spProv.getSelectedItem().toString().equals("") &&
                !spKab.getSelectedItem().toString().equals("") &&
                !spKec.getSelectedItem().toString().equals("") &&
                !spDes.getSelectedItem().toString().equals("") &&
                !spDus.getSelectedItem().toString().equals("") &&
                !spJenisKelamin.getSelectedItem().toString().equals("");
    }
}