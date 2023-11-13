package id.creatodidak.satudarah.service;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

import id.creatodidak.satudarah.First;
import id.creatodidak.satudarah.MainActivity;
import id.creatodidak.satudarah.R;
import id.creatodidak.satudarah.dashboard.AgendaGiatDonorDarah;
import id.creatodidak.satudarah.dashboard.DaftarRequestSaya;
import id.creatodidak.satudarah.dashboard.Notifcenter;
import id.creatodidak.satudarah.dashboard.PermohonanDarahSegar;
import id.creatodidak.satudarah.databases.DBHelper;
import id.creatodidak.satudarah.plugin.DateUtils;

public class FirebaseMsg extends FirebaseMessagingService {
    public static MediaPlayer BG;
    public static Vibrator vibrator;
    Intent intent;
    private static final String TAG = "MyFirebaseMsgService";
    private static final String SHARED_PREF_NAME = "MySharedPref";
    private static final String FCM_TOKEN_KEY = "FCM_TOKEN";
    public static final String BROADCAST_ACTION = "id.creatodidak.vrspolreslandak";
    long[] sosPattern = {0, 100, 100, 100, 1000, 1000, 1000, 100, 100, 100, 1000};
    RemoteMessage remoteMessage2;
    DBHelper dbHelper;
    SharedPreferences sh;
    String jabatan, satfung;
    boolean isVerifikator;
    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
        dbHelper = new DBHelper(getApplicationContext());
        sh = getSharedPreferences("SESSION_DATA", MODE_PRIVATE);
        jabatan = sh.getString("jabatan", "");
        satfung = sh.getString("satfung", "");

        isVerifikator = jabatan.contains("KABAG") || jabatan.contains("KAPOLRES") || jabatan.contains("KAPOLSEK") || jabatan.contains("KAPOLSUBSEKTOR");
        //broadcast to intent

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handleIntent(Intent intent) { //handle on background

        try {
            if (intent.getExtras() != null) {
                /**
                 *
                 * @Ambil parameter jika perlu
                 */
                String title = intent.getExtras().getString("gcm.notification.title");
                String body = intent.getExtras().getString("gcm.notification.body");
                String notification_id = intent.getExtras().getString("gcm.notification.notification_id");
                String from = intent.getExtras().getString("gcm.notification.from");
                String channel_id = intent.getExtras().getString("gcm.notification.channel_id"); //notifikasiVRSPolresLandak
                String topic = intent.getExtras().getString("topic"); //NotifikasiKarhutlaVRS

                //if(channel_id.equals("notifikasiVRSPolresLandak")) { //pakai ini jika ingin deteksi "notifikasiVRSPolresLandak"

                /**
                 *
                 * Build ulang dan kirim ke fungsi onMessageReceived
                 */

                RemoteMessage.Builder builder = new RemoteMessage.Builder("FirebaseMsg");
                for (String key : intent.getExtras().keySet()) {
                    builder.addData(key, intent.getExtras().get(key).toString());
                }
                onMessageReceived(builder.build());
                //}

            } else {
                super.handleIntent(intent);
            }
        } catch (Exception e) {
            super.handleIntent(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        remoteMessage2 = remoteMessage;
        if (remoteMessage.getNotification() != null && !remoteMessage.getData().isEmpty()) {
            int notificationId = Integer.parseInt(Objects.requireNonNull(remoteMessage.getData().get("notification_id")));
            String channelId = "NOTIFIKASI";
            CharSequence channelName = "NOTIFIKASI";
            Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.notif);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setSound(sound, new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build());
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{0, 200, 100, 200, 100, 200});
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

//            NotificationChannel alarm = new NotificationChannel("ALARM_KARHUTLA", "ALARM KARHUTLA", NotificationManager.IMPORTANCE_HIGH);
//            Uri sound2 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.alarm);
//            alarm.setSound(sound2, new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                    .build());
//            alarm.enableVibration(true);
//            alarm.setVibrationPattern(sosPattern);
//            NotificationManager notificationManager2 = getSystemService(NotificationManager.class);
//            notificationManager2.createNotificationChannel(alarm);

            if (Objects.equals(remoteMessage.getData().get("topic"), "update")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=id.creatodidak.satudarah"));
                intent.setPackage("com.android.vending");
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.notificon)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icps))
                        .setBadgeIconType(R.drawable.notificon)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {return;}
                notificationManagerCompat.notify(notificationId, builder.build());
            }

            if (Objects.equals(remoteMessage.getData().get("topic"), "umum")) {
                if(dbHelper.saveNotif(remoteMessage.getData().get("notification_id"), remoteMessage.getData().get("topic"), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), null, DateUtils.getNow())){
                    Intent intent = new Intent(this, Notifcenter.class);
                    intent.putExtra("notifid", remoteMessage.getData().get("notification_id"));
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.drawable.notificon)
                            .setBadgeIconType(R.drawable.notificon)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setContentTitle(remoteMessage.getNotification().getTitle())
                            .setContentText(remoteMessage.getNotification().getBody())
                            .setContentIntent(pendingIntent)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(true);



                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {return;}
                    notificationManagerCompat.notify(notificationId, builder.build());
                }
            }

            if (Objects.equals(remoteMessage.getData().get("topic"), "request")) {
                if(dbHelper.saveNotif(remoteMessage.getData().get("notification_id"), remoteMessage.getData().get("topic"), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), null, DateUtils.getNow())){
                    Intent intent = new Intent(this, PermohonanDarahSegar.class);
                    intent.putExtra("notifid", remoteMessage.getData().get("notification_id"));
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.drawable.notificon)
                            .setBadgeIconType(R.drawable.notificon)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setContentTitle(remoteMessage.getNotification().getTitle())
                            .setContentText(remoteMessage.getNotification().getBody())
                            .setContentIntent(pendingIntent)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {return;}
                    notificationManagerCompat.notify(notificationId, builder.build());
                }
            }

            if (Objects.equals(remoteMessage.getData().get("topic"), "statusrequest")) {
                if(dbHelper.saveNotif(remoteMessage.getData().get("notification_id"), remoteMessage.getData().get("topic"), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), null, DateUtils.getNow())){
                    Intent intent = new Intent(this, DaftarRequestSaya.class);
                    intent.putExtra("notifid", remoteMessage.getData().get("notification_id"));
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.drawable.notificon)
                            .setBadgeIconType(R.drawable.notificon)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setContentTitle(remoteMessage.getNotification().getTitle())
                            .setContentText(remoteMessage.getNotification().getBody())
                            .setContentIntent(pendingIntent)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {return;}
                    notificationManagerCompat.notify(notificationId, builder.build());
                }
            }

            if (Objects.equals(remoteMessage.getData().get("topic"), "event")) {
                if(dbHelper.saveNotif(remoteMessage.getData().get("notification_id"), remoteMessage.getData().get("topic"), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), null, DateUtils.getNow())){
                    Intent intent = new Intent(this, AgendaGiatDonorDarah.class);
                    intent.putExtra("notifid", remoteMessage.getData().get("notification_id"));
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.drawable.notificon)
                            .setBadgeIconType(R.drawable.notificon)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setContentTitle(remoteMessage.getNotification().getTitle())
                            .setContentText(remoteMessage.getNotification().getBody())
                            .setContentIntent(pendingIntent)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {return;}
                    notificationManagerCompat.notify(notificationId, builder.build());
                }
            }
        }
    }

//    private void playSound() {
//        BG = MediaPlayer.create(getBaseContext(), R.raw.alarm);
//        BG.setLooping(true);
//        BG.setVolume(100, 100);
//        BG.start();
//
//        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(sosPattern, 0);
//    }
}

