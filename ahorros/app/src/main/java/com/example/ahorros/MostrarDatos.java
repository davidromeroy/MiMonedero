package com.example.ahorros;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ahorros.db.dbAhorros;
import com.example.ahorros.entidades.Gastos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MostrarDatos extends AppCompatActivity {

    EditText txtItem,txtFecha,txtPrecio,txtCategoria,txtDetalle;
    Button btnGuardar;
    FloatingActionButton fabEditar,fabDelete;

    Gastos gasto;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);

        txtItem = findViewById(R.id.etItem);
        txtFecha = findViewById(R.id.etFecha);
        txtPrecio = findViewById(R.id.etPrecio);
        txtCategoria = findViewById(R.id.etCategoria);
        txtDetalle = findViewById(R.id.etDetalle);

        btnGuardar = findViewById(R.id.btnGuardar);
        fabEditar = findViewById(R.id.fabEditar);
        fabDelete = findViewById(R.id.fabEliminar);

        if(savedInstanceState == null){
            Bundle ex = getIntent().getExtras();
            if (ex == null){
                id = Integer.parseInt(null);
            } else {
                id = ex.getInt("id");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("id");
        }
        dbAhorros db_gasto = new dbAhorros(MostrarDatos.this);
        gasto = db_gasto.verGastos(id);

        if (gasto != null){
            txtItem.setText(gasto.getItem());
            txtFecha.setText(gasto.getFecha());
            txtPrecio.setText(gasto.getPrecio());
            txtCategoria.setText(gasto.getCategoria());
            txtDetalle.setText(gasto.getDetalle());
            btnGuardar.setVisibility(View.INVISIBLE);

            txtItem.setInputType(InputType.TYPE_NULL);
            txtFecha.setInputType(InputType.TYPE_NULL);
            txtPrecio.setInputType(InputType.TYPE_NULL);
            txtCategoria.setInputType(InputType.TYPE_NULL);
            txtDetalle.setInputType(InputType.TYPE_NULL);
        }
        fabEditar.setOnClickListener(v -> {
            Intent i = new Intent(MostrarDatos.this,Editar.class);
            i.putExtra("id",id);
            startActivity(i);
        });

        fabDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MostrarDatos.this);
            builder.setMessage("¿Desea eliminar este consumo?").setPositiveButton("SI", (dialog, which) -> {
                boolean cond = db_gasto.eliminarGasto(id);
                if (cond){
                    lista();
                }
            })
                    .setNegativeButton("NO", (dialog, which) -> {

                    }).show();
        });
    }

    private void lista(){
        Intent i = new Intent(this,Datos.class);
        startActivity(i);
    }
}