package com.example.ahorros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.ahorros.db.dbAhorros;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        pedirPermisos();
    }

    //Para crear el menu de opciones
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal,menu);
        return true;
    }

    //Menu de opciones
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_nuevo:
                form();
                return true;
//            case R.id.menu_exportarCSV:
//                System.out.println("AAAAAA");
//                dbAhorros db_Ahorro = new dbAhorros(this);
//                db_Ahorro.exportarCSV();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Funcion que nos manda a la pantalla de formulario
    public void form(){
        Intent i = new Intent(this,formulario.class);
        startActivity(i);
    }

    //Pantalla que nos manda a la pantalla donde se muestran los datos por categorias
    public void data(View v){
        Intent i = new Intent(this,Datos.class);
        startActivity(i);
    }

    public void pedirPermisos() {
        // PERMISOS PARA ANDROID 6 O SUPERIOR
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0
            );
        }
    }
}