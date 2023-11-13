package id.creatodidak.satudarah.registrasi;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;

import java.io.File;

import id.creatodidak.satudarah.ApiClient;
import id.creatodidak.satudarah.Endpoint;
import id.creatodidak.satudarah.MainActivity;
import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.models.MResponse;
import id.creatodidak.satudarah.plugin.CDialog;
import id.creatodidak.satudarah.plugin.RandomStringGenerator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SetFotoProfil extends AppCompatActivity {
    SharedPreferences sh;
    Endpoint endpoint;
    ImageView icFotoProfile, fotoProfile2;
    ActivityResultLauncher<Intent> openGallery;
    ActivityResultLauncher<CropImageContractOptions> cropImage;
    Button btPilihFoto, btUploadFoto;
    String jk;
    TextView tv1, tv2;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_foto_profil);
        sh = getSharedPreferences("USERDATA", MODE_PRIVATE);
        jk = sh.getString("jeniskelamin", "PRIA");

        endpoint = ApiClient.getClient().create(Endpoint.class);
        icFotoProfile = findViewById(R.id.icFotoProfile);
        btPilihFoto = findViewById(R.id.btPilihFoto);
        tv1 = findViewById(R.id.textView30);
        tv2 = findViewById(R.id.textView31);
        fotoProfile2 = findViewById(R.id.fotoProfile2);
        btUploadFoto = findViewById(R.id.btUploadFoto);

        if(jk.equals("PRIA")){
            icFotoProfile.setImageResource(R.drawable.profilel);
        }else{
            icFotoProfile.setImageResource(R.drawable.profilep);
        }

        openGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri selectedImageUri = result.getData().getData();

                if (selectedImageUri != null) {
                    CropImageOptions cropImageOptions = new CropImageOptions();
                    cropImageOptions.imageSourceIncludeGallery = true;
                    cropImageOptions.fixAspectRatio = true;
                    cropImageOptions.aspectRatioX = 3;
                    cropImageOptions.aspectRatioY = 4;
                    cropImageOptions.allowRotation = false;
                    cropImageOptions.allowFlipping = false;
                    cropImageOptions.progressBarColor = Color.parseColor("#b1140f");
                    cropImageOptions.toolbarColor = Color.parseColor("#b1140f");
                    cropImageOptions.activityBackgroundColor = Color.parseColor("#000000");
                    cropImageOptions.activityTitle = "Pangkas Foto";
                    CropImageContractOptions cropImageContractOptions = new CropImageContractOptions(selectedImageUri, cropImageOptions);
                    cropImage.launch(cropImageContractOptions);
                }
            }
        });

        cropImage = registerForActivityResult(new CropImageContract(), result -> {
            if (result.isSuccessful()) {
                Bitmap cropped = BitmapFactory.decodeFile(result.getUriFilePath(getApplicationContext(), true));
                uri = Uri.parse(result.getUriFilePath(getApplicationContext(), true));
                icFotoProfile.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
                fotoProfile2.setVisibility(View.VISIBLE);
                btUploadFoto.setVisibility(View.VISIBLE);
                btPilihFoto.setText("GANTI");
                Glide.with(this)
                        .load(cropped)
                        .circleCrop()
                        .into(fotoProfile2);
            }
        });

        btPilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                openGallery.launch(i);
            }
        });

        btUploadFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFoto();
            }
        });
    }

    private void uploadFoto() {
        AlertDialog alerts = CDialog.up(
                this,
                "Mengupload Foto...",
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

        File file = new File(uri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<MResponse> call = endpoint.uploadFotoProfile(sh.getString("memberid", "0"), image);
        call.enqueue(new Callback<MResponse>() {
            @Override
            public void onResponse(Call<MResponse> call, Response<MResponse> response) {
                alerts.dismiss();
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("foto", response.body().getMsg());
                    ed.apply();
                    Intent i = new Intent(SetFotoProfil.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    CDialog.up(
                            SetFotoProfil.this,
                            "Informasi",
                            "Gagal mengupload foto, silahkan ulangi!",
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
                        SetFotoProfil.this,
                        "Informasi",
                        "Gagal mengupload foto, silahkan periksa jaringan internet anda!",
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