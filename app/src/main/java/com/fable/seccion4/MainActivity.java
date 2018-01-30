package com.fable.seccion4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.BotonPrincipal);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = 4;
                Toast.makeText(MainActivity.this, "Botón Presionado " + a, Toast.LENGTH_LONG).show();
            }
        });
    }
}
