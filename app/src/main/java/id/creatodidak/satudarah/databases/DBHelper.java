package id.creatodidak.satudarah.databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import id.creatodidak.satudarah.models.MNotifikasi;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    String tbnotifikasi = "CREATE TABLE IF NOT EXISTS notifikasi (id INTEGER PRIMARY KEY, notif_id TEXT NOT NULL, judul TEXT, isi TEXT, topic TEXT, gambar TEXT, status INTEGER, created DATETIME NOT NULL);";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(tbnotifikasi);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS kampanye");
        db.execSQL("DROP TABLE IF EXISTS hotspot");
        db.execSQL("DROP TABLE IF EXISTS draftcekhotspot");

        onCreate(db);
    }

    public void inisialisasi() {
        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL(tbnotifikasi);
    }

    public boolean resetDb() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS kampanye");
            db.execSQL("DROP TABLE IF EXISTS hotspot");
            db.execSQL("DROP TABLE IF EXISTS draftcekhotspot");
            onCreate(db);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean saveNotif(String notificationId, String topic, String judul, String isi, String img, String now) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("notif_id", notificationId);
        values.put("judul", judul);
        values.put("isi", isi);
        values.put("topic", topic);
        values.put("gambar", img);
        values.put("created", now);
        values.put("status", 0);

        long result = db.insert("notifikasi", null, values);
        return result != -1;
    }

    @SuppressLint("Range")
    public List<MNotifikasi> getNotifikasi(){
        List<MNotifikasi> data = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM notifikasi WHERE status = ? ORDER BY id DESC", new String[]{"0"});

        if(cursor != null && cursor.moveToFirst()){
            do {
                MNotifikasi d = new MNotifikasi();
                d.setNotifId(cursor.getString(cursor.getColumnIndex("notif_id")));
                d.setTopic(cursor.getString(cursor.getColumnIndex("topic")));
                d.setJudul(cursor.getString(cursor.getColumnIndex("judul")));
                d.setIsi(cursor.getString(cursor.getColumnIndex("isi")));
                d.setGambar(cursor.getString(cursor.getColumnIndex("gambar")));
                d.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                d.setCreated(cursor.getString(cursor.getColumnIndex("created")));
                data.add(d);
            }while (cursor.moveToNext());
            db.close();
        }

        return data;
    }

    public void updStatusNotif(String notifid){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", 1);

        String whereClause = "notif_id = ?";
        String[] whereArgs = {notifid};

        db.update("notifikasi", values, whereClause, whereArgs);
    }

    public boolean updStatusNotifboolean(String notifid){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", 1);

        String whereClause = "notif_id = ?";
        String[] whereArgs = {notifid};

        int rowsAffected = db.update("notifikasi", values, whereClause, whereArgs);
        return rowsAffected > 0;

    }

}