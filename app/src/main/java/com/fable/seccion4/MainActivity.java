package com.fable.seccion4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Llamada(View v) {
        int a = 4;
        Toast.makeText(this, "Botón Presionado " + a, Toast.LENGTH_LONG).show();
    }
}
