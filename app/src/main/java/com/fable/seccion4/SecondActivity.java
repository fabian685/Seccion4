package com.fable.seccion4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private TextView CajaTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        CajaTexto = findViewById(R.id.CajaTextoPrincipal);

        //Tomar los datos del intent
        Bundle paquete = getIntent().getExtras();
        if (paquete != null) {
            String Saludo = paquete.getString("TextoSaludo");
            Toast.makeText(SecondActivity.this, "Mensaje: " + Saludo, Toast.LENGTH_LONG).show();
            CajaTexto.setText(Saludo);
        }
        else {
            Toast.makeText(SecondActivity.this, "Sin Mensaje", Toast.LENGTH_LONG);
        }
    }
}
