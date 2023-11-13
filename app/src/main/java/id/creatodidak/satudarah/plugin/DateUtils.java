package id.creatodidak.satudarah.plugin;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static String getTodayFormatted() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMMM yyyy", new Locale("id"));
        return sdf.format(today);
    }

    public static String getNow() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("id"));
        return sdf.format(today);
    }

    public static String deteksiWaktu() {
        Calendar calendar = Calendar.getInstance();
        int jam = calendar.get(Calendar.HOUR_OF_DAY);

        if (jam >= 0 && jam < 12) {
            return "Selamat Pagi";
        } else if (jam >= 12 && jam < 17) {
            return "Selamat Siang";
        } else if (jam >= 17 && jam < 20) {
            return "Selamat Sore";
        } else {
            return "Selamat Malam";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getCurrentTimeWithFormat() {
        // Get the current time
        Date currentTime = new Date();

        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat sdf = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sdf = new SimpleDateFormat("HH:mm:ss z");
        }
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7")); // Set the desired time zone

        // Format the current time using the SimpleDateFormat object
        String formattedTime = sdf.format(currentTime);

        return formattedTime;
    }

    public static String tanggaldaricreatedat(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy 'pukul' HH:mm:ss zzz", new Locale("id", "ID"));

        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ""; // Return empty string if parsing fails
    }

    public static String tanggalsajadaricreatedat(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));

        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ""; // Return empty string if parsing fails
    }

    public static boolean ceklayakdonor(String tanggal) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.US);

        try {
            Date date = inputFormat.parse(tanggal);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 3);

            Date tanggalLayakDonor = calendar.getTime();
            Date today = new Date();

            if (today.after(tanggalLayakDonor) || today.equals(tanggalLayakDonor)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static String tambahtigabulan(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));

        try {
            Date date = inputFormat.parse(inputDate);

            // Menggunakan Calendar untuk menambah 3 bulan
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 3);

            // Mengambil tanggal setelah ditambahkan 3 bulan
            Date newDate = calendar.getTime();

            return outputFormat.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ""; // Return empty string if parsing fails
    }


    public static String tanggaldaricreatedatlocal(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy '\uD83D\uDD58'HH:mm 'WIB'", new Locale("id", "ID"));

        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ""; // Return empty string if parsing fails
    }

    public static String tanggalnotif(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy ' Jam 'HH:mm 'WIB'", new Locale("id", "ID"));

        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ""; // Return empty string if parsing fails
    }

    public static String haridantanggallaporan(String tanggal) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("id"));
        SimpleDateFormat outputFormat = new SimpleDateFormat("'hari 'EEEE 'tanggal' dd MMMM yyyy", new Locale("id", "ID"));

        try {
            Date date = inputFormat.parse(tanggal);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String waktuchat(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", new Locale("id", "ID"));

        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ""; // Return empty string if parsing fails
    }

    public static String waktuchatkemaren(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd mm' ' HH:mm:ss", new Locale("id", "ID"));

        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ""; // Return empty string if parsing fails
    }

    public static String sisahari(String dimulai, String selesai) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date tanggalDimulai = dateFormat.parse(dimulai);
            Date tanggalSelesai = dateFormat.parse(selesai);
            Date tanggalHariIni = new Date(); // Mendapatkan tanggal dan waktu hari ini

            if (tanggalHariIni.after(tanggalSelesai)) {
                return "SELESAI";
            } else if (tanggalHariIni.after(tanggalDimulai) && tanggalHariIni.before(tanggalSelesai)) {
                return "HARI INI";
            } else if (tanggalHariIni.before(tanggalDimulai)) {
                long selisihMillis = tanggalDimulai.getTime() - tanggalHariIni.getTime();
                long selisihJam = selisihMillis / (60 * 60 * 1000);
                long selisihHari = selisihJam / 24;

                if (selisihHari > 0) {
                    return selisihHari + "hari";
                } else {
                    if(selisihJam > 0){
                        return selisihJam + "jam";
                    }else{
                        return "<1jam";
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }



}
