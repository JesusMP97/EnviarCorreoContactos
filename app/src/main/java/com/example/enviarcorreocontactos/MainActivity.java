package com.example.enviarcorreocontactos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    EditText etNombre, etNumero;
    Button btBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initEvents();
    }

    private void initComponents() {
        etNombre = findViewById(R.id.etNombre);
        etNumero = findViewById(R.id.etNumero);
        btBuscar = findViewById(R.id.btBuscar);
    }

    private void initEvents() {
        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
            }
        });
    }

    private void lanzarSegundaActividad() {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("nombre", etNombre.getText().toString());
        intent.putExtra("telefono", etNumero.getText().toString());
        startActivity(intent);
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Sin permiso
            // Debemos pedir el permiso
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                showAlert("La aplicacion necesita acceder a los contactos para recoger los datos");
            } else {
                // No se necesita explicacion. Pedir el permiso
                askPermission();
            }
        } else { // Con permiso. Podemos hacer el trabajo
            lanzarSegundaActividad();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    lanzarSegundaActividad();
                } else {
                    // Permiso denegado
                }
                return;
            }

        }
    }

    private void showAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        askPermission();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void askPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
    }
}
