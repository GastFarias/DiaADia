package com.example.eldiaadia;

import android.app.Application;
import android.content.Intent;
import android.view.View;

public class cambiosActivity extends Application{

    public void irFrase(View view){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }
    public void irAgregarAvisos(View view){
        Intent i = new Intent(getApplicationContext(),AgregarAvisos_Activity.class);
        startActivity(i);
    }
    public void irConfiguracion(View view){
        Intent i = new Intent(getApplicationContext(),Configuracion_Activity.class);
        startActivity(i);
    }
}
