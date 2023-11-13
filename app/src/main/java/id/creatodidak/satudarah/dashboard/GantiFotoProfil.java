package id.creatodidak.satudarah.dashboard;

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

import com.bumptech.glide.Glide;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GantiFotoProfil extends AppCompatActivity {
    ImageView ivFotoProfil;
    Button btGantiFoto;
    SharedPreferences sh;
    ActivityResultLauncher<Intent> openGallery;
    ActivityResultLauncher<CropImageContractOptions> cropImage;
    Uri uri;
    Endpoint endpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_foto_profil);
        sh = getSharedPreferences("USERDATA", MODE_PRIVATE);
        endpoint = ApiClient.getClient().create(Endpoint.class);

        ivFotoProfil = findViewById(R.id.ivFotoProfil);
        btGantiFoto = findViewById(R.id.btGantiFoto);
        Glide.with(this)
                .load(sh.getString("foto", ""))
                .error(R.drawable.mrdonor)
                .placeholder(R.drawable.mrdonor)
                .into(ivFotoProfil);

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
                uploadFoto();
            }
        });
        
        btGantiFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                openGallery.launch(i);
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

        Call<MResponse> call = endpoint.updateFotoProfile(sh.getString("memberid", "0"), image);
        call.enqueue(new Callback<MResponse>() {
            @Override
            public void onResponse(Call<MResponse> call, Response<MResponse> response) {
                alerts.dismiss();
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("foto", response.body().getMsg());
                    ed.apply();
                    Glide.with(GantiFotoProfil.this)
                            .load(response.body().getMsg())
                            .error(R.drawable.mrdonor)
                            .placeholder(R.drawable.mrdonor)
                            .into(ivFotoProfil);
                }else{
                    CDialog.up(
                            GantiFotoProfil.this,
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
                        GantiFotoProfil.this,
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