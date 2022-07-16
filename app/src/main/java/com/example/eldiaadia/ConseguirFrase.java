package com.example.eldiaadia;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import android.content.SharedPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ConseguirFrase {



    public static int CodigoDia;
    static String frase;

    public void setFrase(String frase) {
        ConseguirFrase.frase = this.conseguirFrase();
    }

    public String conseguirFrase() {

        final String[] frasedeldia = new String[1];
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DATE);
        int mes = calendar.get(Calendar.MONTH);

        DatabaseReference mDatabase;





        //switch para generar el codigo del dia
        switch (mes) {
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

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("frase").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long cant = snapshot.getChildrenCount();
                // Toast.makeText(MainActivity.this, "cantidad " + cant, Toast.LENGTH_SHORT).show();
                if (snapshot.exists()) {
                    //Este metodo funciona pasando directamente el codigo que conocemos por codigo
                    /*String frase = snapshot.child("1").getValue().toString();
                    TV_frase.setText(frase);*/
                    int ban = 1;

                    while (ban == 1) {
                        try {
                            frase = snapshot.child(String.valueOf(CodigoDia)).getValue().toString();
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
        return frase;
    }

}

