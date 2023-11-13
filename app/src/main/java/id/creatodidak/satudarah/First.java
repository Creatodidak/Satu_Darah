package id.creatodidak.satudarah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import id.creatodidak.satudarah.databases.DBHelper;

public class First extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        DBHelper db = new DBHelper(this);
        db.inisialisasi();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(First.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}