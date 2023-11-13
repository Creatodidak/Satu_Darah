package id.creatodidak.satudarah.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import id.creatodidak.satudarah.R;

public class AkunSaya extends AppCompatActivity {
    TextView tvMemberNama;
    TextView tvMemberUmur;
    TextView tvMemberJenisKelamin;
    TextView tvMemberId;
    TextView tvMemberAlamat;
    TextView tvMemberGoldar;
    ImageView ivMemberFoto;
    ImageView ivQR1;
    ImageView ivQR2;

    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_saya);
        sh = getSharedPreferences("USERDATA", MODE_PRIVATE);

        tvMemberNama = findViewById(R.id.tvMemberNama);
        tvMemberUmur = findViewById(R.id.tvMemberUmur);
        tvMemberJenisKelamin = findViewById(R.id.tvMemberJenisKelamin);
        tvMemberId = findViewById(R.id.tvMemberId);
        tvMemberAlamat = findViewById(R.id.tvMemberAlamat);
        tvMemberGoldar = findViewById(R.id.tvMemberGoldar);
        ivMemberFoto = findViewById(R.id.ivMemberFoto);
        ivQR1 = findViewById(R.id.ivQR1);
        ivQR2 = findViewById(R.id.ivQR2);
        String alamat = "DSN. "+sh.getString("dus", "--") +", DS. "+sh.getString("des", "--") +", KEC. "+sh.getString("kec", "--") +", KAB. "+sh.getString("kab", "--") +", "+sh.getString("prov", "--");
        String[] tgl = sh.getString("tanggallahir", "--").split("-");

        tvMemberNama.setText(sh.getString("nama", "--"));
        tvMemberId.setText(sh.getString("memberid", "--"));
        tvMemberUmur.setText(tgl[0]+" "+bulan(tgl[1])+" "+tgl[2]);
        tvMemberJenisKelamin.setText(sh.getString("jeniskelamin", "--"));
        tvMemberGoldar.setText(sh.getString("golongandarah", "--"));
        tvMemberAlamat.setText(alamat);

        Glide.with(this)
                .load(sh.getString("foto", null))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivMemberFoto);

        Glide.with(this)
                .load(sh.getString("qrcode", null))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivQR1);
        Glide.with(this)
                .load(sh.getString("qrcode", null))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivQR2);
    }

    public String bulan(String bulan){
        String r = "";

        if(bulan.equals("1")){
            r = "JANUARI";
        }else if(bulan.equals("2")){
            r = "FEBRUARI";
        }else if(bulan.equals("3")){
            r = "MARET";
        }else if(bulan.equals("4")){
            r = "APRIL";
        }else if(bulan.equals("5")){
            r = "MEI";
        }else if(bulan.equals("6")){
            r = "JUNI";
        }else if(bulan.equals("7")){
            r = "JULI";
        }else if(bulan.equals("8")){
            r = "AGUSTUS";
        }else if(bulan.equals("9")){
            r = "SEPTEMBER";
        }else if(bulan.equals("10")){
            r = "OKTOBER";
        }else if(bulan.equals("11")){
            r = "NOVEMBER";
        }else if(bulan.equals("12")){
            r = "DESEMBER";
        }

        return r;
    }
}