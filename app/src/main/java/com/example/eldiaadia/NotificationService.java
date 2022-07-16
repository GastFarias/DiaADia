package com.example.eldiaadia;
import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class NotificationService extends IntentService {

    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private static int NOTIFICATION_ID = 1;
    Notification notification;

    int CodigoDia = 0;
    public String fraseConseguida;

    private String fraseDelDia;

    public NotificationService(String name) {
        super(name);
    }

    public NotificationService() {
        super("SERVICE");
    }



    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(Intent intent2) {
        String NOTIFICATION_CHANNEL_ID = getApplicationContext().getString(R.string.app_name);
        Context context = this.getApplicationContext();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, MainActivity.class);
        Resources res = this.getResources();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        //getFrase();

        SharedPreferences sharedPreferences = getSharedPreferences("frases", Context.MODE_PRIVATE);
        String frase = sharedPreferences.getString("FraseDia", "");

        String message = "Nueva Notificacion" /*getString(R.string.new_notification)*/ ;
        String title = "Titulo";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final int NOTIFY_ID = 0; // ID of notification
            String id = NOTIFICATION_CHANNEL_ID; // default_channel_id
            title = NOTIFICATION_CHANNEL_ID; // Default Channel
            PendingIntent pendingIntent;
            NotificationCompat.Builder builder;
            NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notifManager == null) {
                notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle(getString(R.string.app_name)).setCategory(Notification.CATEGORY_SERVICE)
                    .setSmallIcon(R.drawable.ic_launcher_background/*ic_notification*/)   // required
                    .setContentText( frase /*message*/)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher_background/*ic_notification*/))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setSound(soundUri)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(frase))

                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            Notification notification = builder.build();
            notifManager.notify(NOTIFY_ID, notification);

            startForeground(1, notification);

        } else {
            pendingIntent = PendingIntent.getActivity(context, 1, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification = new NotificationCompat.Builder(this)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_launcher_background/*ic_notification*/)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher_background/*ic_notification*/))
                    .setSound(soundUri)
                    .setAutoCancel(true)
                    .setContentTitle(title).setCategory(Notification.CATEGORY_SERVICE)
                    .setContentText(frase)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(frase))
                    .build();
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    public void getFrase(){
         final String[] frasedeldia = new String[1];
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DATE);
        int mes = calendar.get(Calendar.MONTH);
//        int CodigoDia = 0;
//        String frase;
        //switch para generar el codigo del dia
        switch (mes){
            case 1:
                CodigoDia = dia;
                break;
            case 2:
                CodigoDia = dia + 31;
                break;
            case 3:
                CodigoDia = dia + 31 + 28;
                break;
            case 4:
                CodigoDia = dia + 31 + 28 + 31;
                break;
            case 5:
                CodigoDia = dia + 31 + 28 + 31 + 30;
                break;
            case 6:
                CodigoDia = dia + 31 + 28 + 31 + 30 + 31;
                break;
            case 7:
                CodigoDia = dia + 31 + 28 + 31 + 30 + 31 + 30;
                break;
            case 8:
                CodigoDia = dia + 31 + 28 + 31 + 30 + 31 + 30 + 31;
                break;
            case 9:
                CodigoDia = dia + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31;
                break;
            case 10:
                CodigoDia = dia + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30;
                break;
            case 11:
                CodigoDia = dia + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31;
                break;
            case 12:
                CodigoDia = dia + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30;
                break;


        }

        //Toast.makeText(this, "El codigo del dia es: " + CodigoDia , Toast.LENGTH_SHORT).show();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("frase").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long cant = snapshot.getChildrenCount();
               // Toast.makeText(MainActivity.this, "cantidad " + cant, Toast.LENGTH_SHORT).show();
                if (snapshot.exists()){
                    //Este metodo funciona pasando directamente el codigo que conocemos por codigo
                    /*String frase = snapshot.child("1").getValue().toString();
                    TV_frase.setText(frase);*/
                    int ban = 1;
                    while (ban == 1) {
                        try {
                            fraseConseguida = snapshot.child(String.valueOf(CodigoDia)).getValue().toString();
                            ban = 0;
                        } catch (Exception e) {
                            //frase = snapshot.child("1").getValue().toString();
                            CodigoDia = (int) (CodigoDia - (cant));
                        }
                    }

//                    SharedPreferences preferences = getSharedPreferences("frases", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("FraseDia", frase + " \n .");
//                    editor.commit();


                    //TV_frase.setText("" + frase + "\n");
                    //frasedeldia[0] = frase;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //long cantidad;

        //cantidad = dataSnapshot.getChildrenCount();
        //Toast.makeText(this, "children Count" + mDatabase, Toast.LENGTH_SHORT).show();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");
        //myRef.setValue("Hello, World!");

         //return frasedeldia[0];
    }

}