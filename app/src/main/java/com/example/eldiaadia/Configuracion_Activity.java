package com.example.eldiaadia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class Configuracion_Activity extends AppCompatActivity {

    private TimePicker timePicker;
    private Button buttonCambiarHora;
    private Button buttonGuardarHora;

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

    }

}