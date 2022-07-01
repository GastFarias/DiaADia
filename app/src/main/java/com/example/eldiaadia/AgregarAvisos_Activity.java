package com.example.eldiaadia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AgregarAvisos_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_avisos);
    }
    public void irFrase(View view){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

    public void irConfiguracion(View view){
        Intent i = new Intent(getApplicationContext(),Configuracion_Activity.class);
        startActivity(i);
    }
}