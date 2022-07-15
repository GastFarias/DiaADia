package com.example.eldiaadia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import java.util.Calendar;

public class Configuracion_Activity extends AppCompatActivity {

    private TimePicker timePicker;
    private Button buttonCambiarHora;
    private Button buttonGuardarHora;

    Calendar actual = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();



    public int minutos, hora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        timePicker = findViewById(R.id.Configuracion_TimePicker);
        buttonCambiarHora = findViewById(R.id.CambiarHora_button);
        buttonGuardarHora = findViewById(R.id.guardarHora_button);

    }

    public void irFrase(View view){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

    public void irAgregarAvisos(View view){
        Intent i = new Intent(getApplicationContext(),AgregarAvisos_Activity.class);
        startActivity(i);
    }

    public void btn_CambiarHora(View view){
        timePicker.setVisibility(View.VISIBLE);
        buttonGuardarHora.setVisibility(View.VISIBLE);
        buttonCambiarHora.setVisibility(View.GONE);

    }

    public void btn_GuardarHora(View view){
        timePicker.setVisibility(View.GONE);
        buttonCambiarHora.setVisibility(View.VISIBLE);
        buttonGuardarHora.setVisibility(View.GONE);
        // Toast con la hora seleccionada
        // Tomar la hora y guardarla para las notificaciones

//        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
//
//            }
//        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hora = timePicker.getHour();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            minutos = timePicker.getMinute();
        }
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minutos);
        setAlarm(0,calendar, getApplicationContext());

    }

    private void setAlarm(int i, Calendar calendar/*Long timestamp,*/, Context ctx){
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(ctx, i, alarmIntent, 0 /*PendingIntent.FLAG_ONE_SHOT*/);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        //
        //alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
        //

        // Set the alarm to start at approximately 2:00 p.m.
        //Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, hora);
//        calendar.set(Calendar.MINUTE, minutos);

        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);


    }

}