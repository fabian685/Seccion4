package com.fable.seccion4;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText CampoTelefono, CampoTextoWeb;
    private ImageButton BtnTelefono, BtnWeb, BtnCamara;

    private final int CodigoLlamadaTelefono = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        CampoTelefono = findViewById(R.id.CampoTelefono);
        CampoTextoWeb = findViewById(R.id.CampoTextoWeb);
        BtnTelefono = findViewById(R.id.BtnTelefono);
        BtnWeb = findViewById(R.id.BtnWeb);
        BtnCamara = findViewById(R.id.BtnCamara);

        BtnTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NumeroTelefono = CampoTelefono.getText().toString();
                if (NumeroTelefono != null && !NumeroTelefono.isEmpty()) {
                    //Comprobar versión actual de android que estamos corriendo
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CodigoLlamadaTelefono);
                    } else {
                        VersionesViejas(NumeroTelefono);
                    }
                }
                else {
                    Toast.makeText(ThirdActivity.this, "Ingrese un número telefónico válido", Toast.LENGTH_LONG).show();
                }
            }

            private void VersionesViejas(String NumeroTelefono) {
                Intent IntentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + NumeroTelefono));
                if (ComprobarPermiso(Manifest.permission.CALL_PHONE)) {
                    if (ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;
                    startActivity(IntentLlamada);
                } else {
                    Toast.makeText(ThirdActivity.this, "Usted denegó el acceso", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Estamos en el caso del teléfono
        switch (requestCode) {
            case CodigoLlamadaTelefono:
                String Permiso = permissions[0];
                int Resultado = grantResults[0];
                if (Permiso.equals(Manifest.permission.CALL_PHONE)) {
                    //Comprobar si ha sido aceptada o denegada la petición de permiso
                    if (Resultado == PackageManager.PERMISSION_GRANTED) {
                        //Concedió el permiso
                        String NumeroTelefono = CampoTelefono.getText().toString();
                        Intent IntentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + NumeroTelefono));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;
                        startActivity(IntentLlamada);
                    }
                    else {
                        //No concedió el permiso
                        Toast.makeText(ThirdActivity.this, "No concedió el permiso", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private boolean ComprobarPermiso(String Permiso) {
        int resultado = this.checkCallingOrSelfPermission(Permiso);
        boolean retorno = (resultado == PackageManager.PERMISSION_GRANTED);
        return retorno;
    }
}
