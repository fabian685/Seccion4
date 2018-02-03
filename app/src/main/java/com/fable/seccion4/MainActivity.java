package com.fable.seccion4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private View btn;
    private String Saludo = "Hola desde el otro lado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.BotonPrincipal);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Acceder al segundo activity y mandarle una cadena
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("TextoSaludo", Saludo);
                startActivity(intent);
            }
        });
    }
}