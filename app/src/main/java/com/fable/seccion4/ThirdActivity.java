package com.fable.seccion4;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
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
    private final int CodigoFotoCamara = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Activar flecha ir atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CampoTelefono = findViewById(R.id.CampoTelefono);
        CampoTextoWeb = findViewById(R.id.CampoTextoWeb);
        BtnTelefono = findViewById(R.id.BtnTelefono);
        BtnWeb = findViewById(R.id.BtnWeb);
        BtnCamara = findViewById(R.id.BtnCamara);

        //Botón para la llamada
        BtnTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NumeroTelefono = CampoTelefono.getText().toString();
                if (NumeroTelefono != null && !NumeroTelefono.isEmpty()) {
                    //Comprobar versión actual de android que estamos corriendo
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //Comprobar si ha aceptado, no ha aceptado o nunca se le ha preguntado
                        if (ComprobarPermiso(Manifest.permission.CALL_PHONE)) {
                            //Ha aceptado
                            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + NumeroTelefono));
                            if (ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;
                            startActivity(i);
                        }
                        else{
                            //No ha aceptado o es la primera vez que se le pregunta
                            if(!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                                //No se le ha preguntado aún
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CodigoLlamadaTelefono);
                            }
                            else {
                                //Ha denegado
                                Toast.makeText(ThirdActivity.this, "Por favor habilite el acceso", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                i.addCategory(Intent.CATEGORY_DEFAULT);
                                i.setData(Uri.parse("package:" + getPackageName()));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(i);
                            }
                        }
                        //
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

        //Botón para la dirección web
        BtnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Direccion = CampoTextoWeb.getText().toString();
                String CorreoE = "fabian.leguizamo@gmail.com";
                if (Direccion != null && !Direccion.isEmpty()) {
                    //Intent IntentW = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + Direccion));
                    Intent IntentW = new Intent();
                    IntentW.setAction(Intent.ACTION_VIEW);
                    IntentW.setData(Uri.parse("http://" + Direccion));

                    //Contactos
                    Intent IntentContactos = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));

                    //Email rápido
                    Intent IntentEmailR = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto: " + CorreoE));

                    //Email Completo
                    Intent IntentEmailC = new Intent(Intent.ACTION_SEND, Uri.parse(CorreoE));
                    //IntentEmailC.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                    IntentEmailC.setType("text/plain");
                    IntentEmailC.putExtra(Intent.EXTRA_SUBJECT, "Título del Correo");
                    IntentEmailC.putExtra(Intent.EXTRA_TEXT, "Hola, me encanta mi aplicación.");
                    IntentEmailC.putExtra(Intent.EXTRA_EMAIL, new String[]{"fernando@gmail.com", "antonio@gmail.com"});
                    //Preguntar siempre
                    //startActivity(Intent.createChooser(IntentEmailC,"Escoja cliente de correo"));

                    //Teléfono 2, sin permisos requeridos
                    Intent IntentTelDos = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:666111222"));

                    startActivity(IntentEmailC);
                }
                else {
                    Toast.makeText(ThirdActivity.this, "Debe escribir algo", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Botón para la cámara
        BtnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent IntentCamara = new Intent("android.media.action.IMAGE_CAPTURE");
                //startActivity(IntentCamara);
                startActivityForResult(IntentCamara, CodigoFotoCamara);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CodigoFotoCamara:
                if (resultCode == Activity.RESULT_OK) {
                    String resultado = data.toUri(0);
                    Toast.makeText(this, "Resultado: " + resultado, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "Ocurrió un problema con la foto.", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
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
